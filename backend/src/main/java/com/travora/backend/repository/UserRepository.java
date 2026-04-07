package com.travora.backend.repository;

import com.travora.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    boolean existsByUsername(String username);
}

// package com.travora.backend.repository;

// import com.travora.backend.model.User;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;
// import java.util.Optional;


// // interacts with database

// @Repository
// public interface UserRepository extends JpaRepository<User, Long> {
//     Optional<User> findByUsername(String username);  
//     boolean existsByUsername(String username);            
// }