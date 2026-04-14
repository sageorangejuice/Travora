package com.travora.app.ui.anim;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BubbleBackgroundView extends View {

    private static class Bubble {
        float x, y;
        float vx, vy;
        float radius;
    }

    private final List<Bubble> bubbles = new ArrayList<>();
    private final Paint paint = new Paint();
    private final Random random = new Random();

    public BubbleBackgroundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint.setColor(Color.WHITE);
        paint.setAlpha(30); // transparent bubbles

        for (int i = 0; i < 4; i++) {
            Bubble b = new Bubble();
            b.radius = 220 + random.nextInt(120);
            b.x = random.nextInt(500);
            b.y = random.nextInt(800);

            b.vx = (random.nextFloat() - 0.5f) * 4;
            b.vy = (random.nextFloat() - 0.5f) * 4;

            bubbles.add(b);
        }

        post(updateRunnable);
    }

    private final Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            update();
            invalidate(); // redraw
            postDelayed(this, 16); // ~60fps
        }
    };

    private void update() {
        int w = getWidth();
        int h = getHeight();

        for (Bubble b : bubbles) {
            b.x += b.vx;
            b.y += b.vy;

            // bounce off walls
            if (b.x - b.radius < 0 || b.x + b.radius > w) {
                b.vx *= -1;
            }

            if (b.y - b.radius < 0 || b.y + b.radius > h) {
                b.vy *= -1;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (Bubble b : bubbles) {
            canvas.drawCircle(b.x, b.y, b.radius, paint);
        }
    }
}