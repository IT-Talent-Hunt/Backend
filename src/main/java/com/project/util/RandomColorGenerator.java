package com.project.util;

import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class RandomColorGenerator {
    public String generateRandomColor() {
        Random random = new Random();
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return String.format("#%02x%02x%02x", r, g, b);
    }
}
