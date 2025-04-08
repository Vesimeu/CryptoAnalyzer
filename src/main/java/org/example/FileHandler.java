package org.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Files;

public class FileHandler {
    private static final String INPUT_DIR = "src/main/resources/FileInput/";
    private static final String OUTPUT_DIR = "src/main/resources/FileOutput/";

    public static String readFile(String fileName) {
        String fullPath = Paths.get(INPUT_DIR, fileName).toString();
        StringBuilder text = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fullPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                text.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения файла: " + fullPath, e);
        }
        return text.toString().toUpperCase();
    }

    public static void writeFile(String fileName, String content) {
        String fullPath = Paths.get(OUTPUT_DIR, fileName).toString();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fullPath))) {
            writer.write(content);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка записи в файл: " + fullPath, e);
        }
    }

    public static void writeImageFile(String fileName, byte[] imageData) {
        String fullPath = Paths.get(OUTPUT_DIR, fileName).toString();
        try {
            Files.createDirectories(Paths.get(OUTPUT_DIR)); // Создаем директорию, если её нет
            Files.write(Paths.get(fullPath), imageData);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка записи изображения: " + fullPath, e);
        }
    }
}