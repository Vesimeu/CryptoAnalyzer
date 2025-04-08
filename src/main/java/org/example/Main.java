package org.example;

import java.util.logging.Logger;
import java.util.logging.Level;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static final int CAESAR_SHIFT = 5;
    private static final String VIGENERE_KEY = "КЛЮЧ"; // Ключ длиной 4 символа

    public static void main(String[] args) {
        // Настройка логирования
        LOGGER.setLevel(Level.INFO);

        // Генерация зашифрованных текстов
        LOGGER.info("Чтение исходного текста из input.txt");
        String inputText = FileHandler.readFile("input.txt");
        if (inputText.length() < 2000) {
            LOGGER.warning("Текст в input.txt короче 2000 символов!");
        }

        LOGGER.info("Генерация outputCaesar.txt с шифром Цезаря, сдвиг = " + CAESAR_SHIFT);
        String caesarCipherText = CipherUtils.encryptCaesar(inputText, CAESAR_SHIFT);
        FileHandler.writeFile("outputCaesar.txt", caesarCipherText);

        LOGGER.info("Генерация outputVigener.txt с шифром Виженера, ключ = " + VIGENERE_KEY);
        String vigenereCipherText = CipherUtils.encryptVigenere(inputText, VIGENERE_KEY);
        FileHandler.writeFile("outputVigener.txt", vigenereCipherText);

        // Криптоанализ Цезаря
        LOGGER.info("Анализ шифра Цезаря...");
        int caesarShift = CaesarCipherAnalyzer.findCaesarShift(caesarCipherText);
        String caesarDecrypted = CaesarCipherAnalyzer.decrypt(caesarCipherText, caesarShift);
        FileHandler.writeFile("decryptedCaesar.txt", caesarDecrypted);
        LOGGER.info("Цезарь: Найденный сдвиг = " + caesarShift);

        // Криптоанализ Виженера
        LOGGER.info("Анализ шифра Виженера...");
        int keyLength = VigenereCipherAnalyzer.findKeyLength(vigenereCipherText);
        String vigenereKey = VigenereCipherAnalyzer.findKey(vigenereCipherText, keyLength);
        String vigenereDecrypted = VigenereCipherAnalyzer.decrypt(vigenereCipherText, vigenereKey);
        FileHandler.writeFile("decryptedVigener.txt", vigenereDecrypted);
        LOGGER.info("Виженер: Длина ключа = " + keyLength + ", Найденный ключ = " + vigenereKey);

        // Частотный анализ большого текста
        LOGGER.info("Чтение большого текста из largeText.txt для частотного анализа");
        String largeText = FileHandler.readFile("largeText.txt");
        if (largeText.length() < 100000) {
            LOGGER.warning("Текст в largeText.txt короче 100000 символов!");
        }
        FrequencyAnalyzer largeTextAnalyzer = new FrequencyAnalyzer(largeText);
        LOGGER.info("Генерация диаграмм...");
        ChartGenerator.generateChart("Топ-10 букв", largeTextAnalyzer.getTopLetters(10), "topLetters.png");
        ChartGenerator.generateChart("Топ-10 биграмм", largeTextAnalyzer.getTopBigrams(10), "topBigrams.png");
        LOGGER.info("Программа завершена успешно");
    }
}