package org.sobngwi.oca.exam;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Q3 {

    public static void main(String[] args) {
        // Files.copy throws IO eception
        Path one = Paths.get("/capybara/food", "apples.txt");
        Path two = Paths.get("/capybara/food", "bananas.txt");
        try {
            Files.copy(one, two); // IO exception
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
