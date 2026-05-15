# Travora

Travora is a full-stack Android app that recommends places to visit and dine at, tailored to user interests. Built with Java, Spring Boot, and PostgreSQL. Features interest-based recommendations using a weighted scoring algorithm, local and tourist reviews, and a nightly venue data pipeline using Google Places API.

https://github.com/user-attachments/assets/12be45b6-018d-48ff-9798-806742e53e4a

---

## Tech Stack

**Android:** Java, MVVM, LiveData, Retrofit2, Glide, Material Design  
**Backend:** Spring Boot 4, Spring Data JPA, Spring Security Crypto  
**Database:** PostgreSQL  
**External API:** Google Places API  
**Build:** Gradle (Android), Maven (Backend)

---

## Architecture

```
┌─────────────────────────────────┐
│         Android Client          │
│  MVVM · LiveData · Retrofit2    │
└────────────────┬────────────────┘
                 │ REST (HTTP/JSON)
┌────────────────▼────────────────┐
│        Spring Boot API          │
│  AuthController                 │
│  PlaceController                │
│  ReviewController               │
└──────────┬──────────────────────┘
           │
┌──────────▼──────────────────────┐
│           PostgreSQL            │
│  Users · Dining · Activities    │
│  Reviews · Photo Blobs          │
└──────────┬──────────────────────┘
           │ Nightly cron (00:00)
┌──────────▼──────────────────────┐
│      Google Places API          │
│  Restaurants · Tourist Sites    │
└─────────────────────────────────┘
```

---

## Features

### Hybrid Recommendation Engine

Venues are ranked per request using a two-factor score:

```
score = Jaccard(userTags, placeTags) × BayesianRating(place) / 5.0
```

**Jaccard similarity** matches a user's 4 preference tags (budget tier, diet, dining style, activity type) against each venue's semantic tag set — ensuring recommendations reflect actual user intent rather than raw popularity.

**Bayesian average rating** applies a prior of 5 reviews to smooth scores for venues with little data, preventing new places from being unfairly buried or inflated. The global mean rating is computed dynamically from all user-submitted reviews in the database.

The ranking logic is decoupled behind a `ScoringStrategy` interface, making the algorithm swappable without touching the service layer.

### Nightly Venue Data Pipeline

A scheduled cron job runs daily at midnight and:

1. Calls the Google Places Nearby Search API for restaurants and tourist attractions within 30km of Singapore
2. Fetches up to 40 venue records across both categories
3. Auto-derives semantic tags from raw API metadata — mapping price levels to budget tiers (`budget`, `moderate`, `luxury`) and place types to interest categories (`cultural`, `nature`, `shopping`, `vegetarian`, `halal`)
4. Downloads and stores venue photo blobs directly in PostgreSQL
5. Upserts each record — updating ratings and metadata for existing venues, inserting new ones

This means the Android client never makes a live Google Places call. All data is served from the local database.

### User Preference Onboarding

New users complete a 4-dimension preference profile (budget, diet, dining style, activity type) during registration. Preferences are stored as a tag set on the user record and consumed directly by the ranking engine at query time — no additional database lookups required to personalize results.

### Authentication

Registration and login are handled by a dedicated auth service. Passwords are hashed with **BCrypt** before storage. The client receives a username token on successful login, used to identify the user on subsequent requests.

### Reviews

Authenticated users can submit ratings and written reviews per venue. User-submitted ratings feed into the Bayesian scoring pipeline — meaning community reviews directly influence future recommendation rankings.

---

## Project Structure

```
Travora/
├── app/                        # Android client
│   └── src/main/java/com/travora/app/
│       ├── model/              # Data models
│       ├── network/            # Retrofit API interface
│       ├── repository/         # Data layer
│       ├── ui/                 # Activities (auth, recommendations, reviews, profile)
│       └── viewmodel/          # ViewModels (MVVM)
│
└── backend/                    # Spring Boot server
    └── src/main/java/com/travora/backend/
        ├── controller/         # REST endpoints
        ├── model/              # JPA entities
        ├── repository/         # Spring Data JPA
        └── service/
            ├── scoring/        # ScoringStrategy interface + implementations
            ├── GooglePlacesService.java
            ├── RecommendationService.java
            └── AuthService.java
```

---

## Running Locally

### Prerequisites

- Java 17+
- PostgreSQL
- Android Studio
- Google Places API key

### Backend

1. Create a PostgreSQL database
2. Fill in `backend/src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/your_db
spring.datasource.username=your_user
spring.datasource.password=your_password
spring.datasource.driver-class-name=org.postgresql.Driver

google.places.api.key=YOUR_API_KEY
google.places.location=1.3521,103.8198
google.places.radius=30000
```

3. Run the backend:

```bash
cd backend
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080`. To seed venue data immediately without waiting for the nightly cron, hit `POST /api/places/refresh`.

### Android Client

1. Open the `app/` folder in Android Studio
2. In `RetrofitClient.java`, set the base URL to your backend address (e.g. `http://10.0.2.2:8080/` for the Android emulator)
3. Build and run on an emulator or physical device (Android 7.0+ / API 24+)

---

## API Reference

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/v1/auth/register` | Register a new user |
| `POST` | `/api/v1/auth/login` | Authenticate a user |
| `PATCH` | `/api/v1/auth/preferences` | Update user preferences |
| `GET` | `/api/places/dining?username=` | Get ranked dining recommendations |
| `GET` | `/api/places/activities?username=` | Get ranked activity recommendations |
| `GET` | `/api/places/search?query=` | Search venues by name |
| `GET` | `/api/reviews?placeId=` | Get reviews for a venue |
| `POST` | `/api/reviews` | Submit a review |
| `POST` | `/api/places/refresh` | Manually trigger venue data refresh |
