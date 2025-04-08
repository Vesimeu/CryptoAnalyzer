package org.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Класс для генерации гистограмм с использованием библиотеки JFreeChart.
 * Создает диаграммы частот (например, букв или биграмм) и сохраняет их в PNG-файлы.
 */
public class ChartGenerator {
    private static final Logger LOGGER = Logger.getLogger(ChartGenerator.class.getName());

    /**
     * Генерирует гистограмму и сохраняет её в файл.
     *
     * @param title - заголовок диаграммы
     * @param data - список пар (элемент, частота) для отображения
     * @param fileName - имя файла для сохранения диаграммы
     */
    public static void generateChart(String title, List<Map.Entry<?, Integer>> data, String fileName) {
        LOGGER.setLevel(Level.INFO); // Устанавливаем уровень логирования

        // Создаем набор данных для гистограммы
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<?, Integer> entry : data) {
            // Добавляем значение: частота (value) для элемента (key) в категорию "Частота"
            dataset.addValue(entry.getValue(), "Частота", entry.getKey().toString());
        }

        // Создаем гистограмму с заданным заголовком и подписями осей
        JFreeChart chart = ChartFactory.createBarChart(title, "Элемент", "Частота", dataset);

        try {
            LOGGER.info("Сохранение диаграммы: " + fileName);
            // Преобразуем диаграмму в изображение размером 800x600 и кодируем в PNG
            byte[] imageData = ChartUtils.encodeAsPNG(chart.createBufferedImage(800, 600));
            // Сохраняем изображение через FileHandler
            FileHandler.writeImageFile(fileName, imageData);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Ошибка при генерации диаграммы: " + fileName, e);
            throw new RuntimeException("Ошибка сохранения диаграммы: " + fileName, e);
        }
    }
}