package ru.naumen.collection.task3;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Написать консольное приложение, которое принимает на вход произвольный текстовый файл в формате txt.
 * Нужно собрать все встречающийся слова и посчитать для каждого из них количество раз, сколько слово встретилось.
 * Морфологию не учитываем.</p>
 * <p>Вывести на экран наиболее используемые (TOP) 10 слов и наименее используемые (LAST) 10 слов</p>
 * <p>Проверить работу на романе Льва Толстого “Война и мир”</p>
 *
 * @author vpyzhyanov
 * @since 19.10.2023
 */
public class WarAndPeace {

    private static final Path WAR_AND_PEACE_FILE_PATH = Path.of("src/main/resources",
            "Лев_Толстой_Война_и_мир_Том_1,_2,_3,_4_(UTF-8).txt");
    //Примерная сложность O(n + m log m), n - количество слов файле, m размер мапы wordCountMap
    public static void main(String[] args) {
        Map<String, Long> wordCountMap = new HashMap<>();
        //Сам алгоритм парсера работает в среднем за O(n)
        new WordParser(WAR_AND_PEACE_FILE_PATH)
                .forEachWord(word -> {
                    //O(n)
                    wordCountMap.merge(word, 1L, Long::sum);
                });
        //O(n log n)
        List<Map.Entry<String, Long>> sortedList = wordCountMap.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .toList();

        //О(1)
        for (int i = 0; i < 10; i++) {
            System.out.println(sortedList.get(i));
        }
        System.out.println("==================================");
        for (int i = sortedList.size()-1; i > sortedList.size()-11; i--) {
            System.out.println(sortedList.get(i));
        }
    }
}