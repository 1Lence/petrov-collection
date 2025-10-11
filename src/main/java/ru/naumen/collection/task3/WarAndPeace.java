package ru.naumen.collection.task3;

import java.nio.file.Path;
import java.util.*;

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
    /**
     * Сложность алгоритма O(n) благодаря оптимизациям:
     * <pre>
     *     1) Установкой фиксированного размера очереди;
     *     2) Уменьшение количества попыток вставить в очередь;
     * </pre>
     * <p>Подробнее в самом алгоритме</p>
     */
    private static final Path WAR_AND_PEACE_FILE_PATH = Path.of("src/main/resources",
            "Лев_Толстой_Война_и_мир_Том_1,_2,_3,_4_(UTF-8).txt");

    public static void main(String[] args) {
        Map<String, Integer> wordsCount = new LinkedHashMap<>();

        //Сам алгоритм парсера работает в среднем за O(n)
        new WordParser(WAR_AND_PEACE_FILE_PATH)
                .forEachWord(word ->
                        wordsCount.merge(word, 1, Integer::sum)
                );

        findTopUsedAndTopUnused(wordsCount);
    }

    private static void findTopUsedAndTopUnused(Map<String, Integer> wordsCount) {
        //Преимущества данной коллекции в том, что она позволяет легко удалить наименьший элемент
        PriorityQueue<Map.Entry<String, Integer>> topUsed =
                new PriorityQueue<>(11, Map.Entry.comparingByValue());
        PriorityQueue<Map.Entry<String, Integer>> topUnused =
                new PriorityQueue<>(11, Map.Entry.<String, Integer>comparingByValue().reversed());

        // O(n) цикл
        for (Map.Entry<String, Integer> entry : wordsCount.entrySet()) {
            // O(log k) операции с PriorityQueue
            addToTop(topUsed, entry, Map.Entry.comparingByValue());
            addToTop(topUnused, entry, Map.Entry.<String, Integer>comparingByValue().reversed());
        }

        System.out.println("Top 10 most used");

        printTopInOrder(topUsed);

        System.out.println("\nTop 10 most unused");

        for (int i = 0; i < 10; i++) {
            System.out.println(topUnused.poll());
        }
    }

    /**
     * Оптимизация благодаря которой мы не будем пытаться вставить элемент, если head больше или меньше вставляемого
     * элемента (в зависимости от компаратора)
     * <p>O(1)</p>
     */
    private static void addToTop(
            PriorityQueue<Map.Entry<String, Integer>> topTierQueue,
            Map.Entry<String, Integer> entry,
            Comparator<Map.Entry<String, Integer>> comparator) {
        if (topTierQueue.peek() != null && comparator.compare(topTierQueue.peek(), entry) > 0) {
            return;
        }
        //O(log k) обе операции
        topTierQueue.offer(entry);
        if (topTierQueue.size() > 10) {
            topTierQueue.poll();
        }
    }

    private static void printTopInOrder(PriorityQueue<Map.Entry<String, Integer>> queue) {
        if (queue.isEmpty()) return;

        Map.Entry<String, Integer> item = queue.poll();

        printTopInOrder(queue);

        System.out.println(item);
    }
}