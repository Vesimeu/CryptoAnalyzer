package org.example;

import java.util.HashMap;
import java.util.Map;

public class CipherUtils {
    public static final String ALPHABET = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"; //Можем убрать или добавить пробел.
    public static final Map<Character, Integer> charToIndex = initCharToIndex();
    public static final Map<Integer, Character> indexToChar = initIndexToChar();

    private static Map<Character, Integer> initCharToIndex() {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < ALPHABET.length(); i++) {
            map.put(ALPHABET.charAt(i), i);
        }
        return map;
    }

    private static Map<Integer, Character> initIndexToChar() {
        Map<Integer, Character> map = new HashMap<>();
        for (int i = 0; i < ALPHABET.length(); i++) {
            map.put(i, ALPHABET.charAt(i));
        }
        return map;
    }

    // Шифрование шифром Цезаря
    public static String encryptCaesar(String text, int shift) {
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (charToIndex.containsKey(c)) {
                int index = (charToIndex.get(c) + shift) % ALPHABET.length();
                result.append(indexToChar.get(index));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    // Шифрование шифром Виженера
    public static String encryptVigenere(String text, String key) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            char k = key.charAt(i % key.length());
            if (charToIndex.containsKey(c)) {
                int index = (charToIndex.get(c) + charToIndex.get(k)) % ALPHABET.length();
                result.append(indexToChar.get(index));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}