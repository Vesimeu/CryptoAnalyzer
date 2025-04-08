package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс для частотного анализа текста.
 * Подсчитывает частоты букв и биграмм, предоставляет методы для получения статистики.
 */
public class FrequencyAnalyzer {
    /** Исходный текст для анализа, неизменяемый после создания объекта */
    private final String text;
    /** Карта для хранения частот букв: ключ - буква, значение - количество появлений */
    private final Map<Character, Integer> letterFreq = new HashMap<>();
    /** Карта для хранения частот биграмм: ключ - биграмма, значение - количество появлений */
    private final Map<String, Integer> bigramFreq = new HashMap<>();

    /**
     * Конструктор класса.
     * Инициализирует текст и запускает анализ частот.
     *
     * @param text - текст для анализа
     */
    public FrequencyAnalyzer(String text) {
        this.text = text;
        analyzeFrequencies(); // Сразу выполняем анализ при создании объекта
    }

    /**
     * Анализирует частоты букв и биграмм в тексте.
     * Заполняет letterFreq и bigramFreq на основе текста.
     */
    private void analyzeFrequencies() {
        // Подсчет частот букв
        for (char c : text.toCharArray()) {
            if (CipherUtils.charToIndex.containsKey(c)) { // Проверяем, входит ли символ в алфавит
                letterFreq.put(c, letterFreq.getOrDefault(c, 0) + 1); // Увеличиваем частоту
            }
        }
        // Подсчет частот биграмм
        for (int i = 0; i < text.length() - 1; i++) {
            String bigram = text.substring(i, i + 2); // Берем пару символов
            if (bigram.matches("[" + CipherUtils.ALPHABET + "]{2}")) { // Проверяем, что оба символа из алфавита
                bigramFreq.put(bigram, bigramFreq.getOrDefault(bigram, 0) + 1); // Увеличиваем частоту
            }
        }
    }

    /**
     * Возвращает вероятности появления каждой буквы в тексте.
     *
     * @return Map<Character, Double> - карта с вероятностями (частота / общая длина текста)
     */
    public Map<Character, Double> getLetterProbabilities() {
        Map<Character, Double> probabilities = new HashMap<>();
        int total = text.length(); // Общее количество символов в тексте
        for (Map.Entry<Character, Integer> entry : letterFreq.entrySet()) {
            probabilities.put(entry.getKey(), (double) entry.getValue() / total); // Частота делится на длину текста
        }
        return probabilities;
    }

    /**
     * Возвращает список из n самых частых букв.
     *
     * @param n - количество элементов в списке
     * @return List<Map.Entry<?, Integer>> - топ-n букв с их частотами
     */
    public List<Map.Entry<?, Integer>> getTopLetters(int n) {
        return letterFreq.entrySet().stream()
                .sorted(Map.Entry.<Character, Integer>comparingByValue().reversed()) // Сортировка по убыванию частоты
                .limit(n) // Ограничиваем до n элементов
                .collect(Collectors.toList()); // Собираем в список
    }

    /**
     * Возвращает список из n самых частых биграмм.
     *
     * @param n - количество элементов в списке
     * @return List<Map.Entry<?, Integer>> - топ-n биграмм с их частотами
     */
    public List<Map.Entry<?, Integer>> getTopBigrams(int n) {
        return bigramFreq.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()) // Сортировка по убыванию частоты
                .limit(n) // Ограничиваем до n элементов
                .collect(Collectors.toList()); // Собираем в список
    }
}