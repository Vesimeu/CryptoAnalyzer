package org.example;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс для криптоанализа текста, зашифрованного шифром Цезаря.
 * Реализует методы для определения сдвига (ключа) и расшифровки текста
 * на основе частотного анализа русского языка.
 */
public class CaesarCipherAnalyzer {

    /**
     * Частотная таблица букв русского языка.
     * Содержит вероятности появления букв в типичном русском тексте.
     * Инициализируется статически через метод initRussianFreq().
     */
    private static final Map<Character, Double> RUSSIAN_FREQ = initRussianFreq();

    /**
     * Инициализирует таблицу частот букв русского языка.
     * Создает HashMap и заполняет его значениями для наиболее частых букв.
     * Значения взяты из статистики русского языка (например, 'О' - самая частая буква).
     *
     * @return Map<Character, Double> - таблица частот букв
     */
    private static Map<Character, Double> initRussianFreq() {
        Map<Character, Double> freq = new HashMap<>();
        freq.put('О', 0.1097); // Буква 'О' встречается в 10.97% случаев
        freq.put('Е', 0.0845);
        freq.put('А', 0.0801);
        freq.put('И', 0.0735);
        freq.put('Н', 0.0670);
        freq.put('Т', 0.0632);
        freq.put('С', 0.0547);
        freq.put('Р', 0.0453);
        freq.put('В', 0.0435);
        freq.put('Л', 0.0348); // Дополнены частоты для примера
        // Примечание: таблица неполная, для точного анализа нужно добавить все буквы алфавита
        return freq;
    }

    /**
     * Определяет сдвиг (ключ) шифра Цезаря с помощью частотного анализа.
     * Сравнивает частоты букв в шифротексте с ожидаемыми частотами русского языка.
     *
     * @param cipherText - зашифрованный текст для анализа
     * @return int - найденный сдвиг (ключ шифра)
     */
    public static int findCaesarShift(String cipherText) {
        // Создаем объект FrequencyAnalyzer для подсчета частот букв в шифротексте
        FrequencyAnalyzer analyzer = new FrequencyAnalyzer(cipherText);
        // Получаем вероятности появления каждой буквы в шифротексте
        Map<Character, Double> cipherFreq = analyzer.getLetterProbabilities();

        // Переменные для поиска минимальной разницы и лучшего сдвига
        double minDiff = Double.MAX_VALUE; // Инициализация максимальным значением для поиска минимума
        int bestShift = 0;

        // Перебираем все возможные сдвиги (от 0 до длины алфавита)
        for (int shift = 0; shift < CipherUtils.ALPHABET.length(); shift++) {
            double diff = 0; // Сумма разностей частот для текущего сдвига
            for (Map.Entry<Character, Double> entry : cipherFreq.entrySet()) {
                // Сдвигаем букву шифротекста назад на текущий shift
                // Используем модульную арифметику для корректного перехода через алфавит
                char shiftedChar = CipherUtils.indexToChar.get(
                        (CipherUtils.charToIndex.get(entry.getKey()) - shift + CipherUtils.ALPHABET.length()) % CipherUtils.ALPHABET.length()
                );
                // Получаем ожидаемую частоту для этой буквы в русском языке (0, если буквы нет в таблице)
                double expectedFreq = RUSSIAN_FREQ.getOrDefault(shiftedChar, 0.0);
                // Добавляем абсолютную разницу между реальной и ожидаемой частотой
                diff += Math.abs(expectedFreq - entry.getValue());
            }
            // Если текущая разница меньше минимальной, обновляем лучший сдвиг
            if (diff < minDiff) {
                minDiff = diff;
                bestShift = shift;
            }
        }
        return bestShift; // Возвращаем найденный сдвиг
    }

    /**
     * Расшифровывает текст, зашифрованный шифром Цезаря, используя заданный сдвиг.
     *
     * @param cipherText - зашифрованный текст
     * @param shift - сдвиг, используемый для шифрования
     * @return String - расшифрованный текст
     */
    public static String decrypt(String cipherText, int shift) {
        StringBuilder result = new StringBuilder();
        // Проходим по каждому символу шифротекста
        for (char c : cipherText.toCharArray()) {
            if (CipherUtils.charToIndex.containsKey(c)) {
                // Если символ есть в алфавите, сдвигаем его назад на shift
                // + CipherUtils.ALPHABET.length() предотвращает отрицательные индексы
                int index = (CipherUtils.charToIndex.get(c) - shift + CipherUtils.ALPHABET.length()) % CipherUtils.ALPHABET.length();
                result.append(CipherUtils.indexToChar.get(index));
            } else {
                // Если символа нет в алфавите (например, знак препинания), оставляем его без изменений
                result.append(c);
            }
        }
        return result.toString(); // Возвращаем расшифрованный текст
    }
}