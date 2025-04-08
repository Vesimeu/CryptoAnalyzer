package org.example;

import java.util.HashMap;
import java.util.Map;

public class VigenereCipherAnalyzer {
    public static int findKeyLength(String cipherText) {
        Map<Integer, Double> coincidences = new HashMap<>();
        for (int len = 1; len <= 10; len++) { // Ограничиваем поиск до 10
            double coincidence = 0;
            int count = 0;
            for (int i = 0; i < cipherText.length() - len; i++) {
                if (cipherText.charAt(i) == cipherText.charAt(i + len)) {
                    coincidence++;
                }
                count++;
            }
            if (count > 0) {
                coincidences.put(len, coincidence / count);
            }
        }
        // Находим длину с максимальным количеством совпадений
        int bestLength = coincidences.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(4);

        // Проверяем кратность (ключ может повторяться)
        for (int len = 1; len <= bestLength / 2; len++) {
            if (bestLength % len == 0 && coincidences.getOrDefault(len, 0.0) > 0.03) { // Порог совпадений
                return len; // Возвращаем минимальную длину ключа
            }
        }
        return bestLength;
    }

    public static String findKey(String cipherText, int keyLength) {
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < keyLength; i++) {
            StringBuilder subText = new StringBuilder();
            for (int j = i; j < cipherText.length(); j += keyLength) {
                subText.append(cipherText.charAt(j));
            }
            int shift = CaesarCipherAnalyzer.findCaesarShift(subText.toString());
            key.append(CipherUtils.indexToChar.get(shift));
        }
        return key.toString();
    }

    public static String decrypt(String cipherText, String key) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < cipherText.length(); i++) {
            char c = cipherText.charAt(i);
            char k = key.charAt(i % key.length());
            if (CipherUtils.charToIndex.containsKey(c)) {
                int index = (CipherUtils.charToIndex.get(c) - CipherUtils.charToIndex.get(k) + CipherUtils.ALPHABET.length()) % CipherUtils.ALPHABET.length();
                result.append(CipherUtils.indexToChar.get(index));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}