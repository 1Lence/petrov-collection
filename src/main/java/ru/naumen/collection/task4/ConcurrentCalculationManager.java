package ru.naumen.collection.task4;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/**
 * Класс управления расчётами
 */

/**
 * За основу взята реализация мапы ConcurrentHashMap, т.к. мне необходима потокобезопасная коллекция, сохраняется
 * порядок хранения задач с помощью ключей.
 * <p>Сложность О(1), если не брать в учет время ожидания задач.
 * Добавление элементов в мапу О(1), но так же по условию у нас все задаваемые данные ограничены</p>
 */
public class ConcurrentCalculationManager<T> {
    private final Map<Integer, T> map = new ConcurrentHashMap<>();
    private final AtomicInteger count = new AtomicInteger(0);
    private final AtomicInteger current = new AtomicInteger(0);
    /**
     * Добавить задачу на параллельное вычисление
     */
    public void addTask(Supplier<T> task) {
        new Thread(() -> map.put(count.incrementAndGet(), task.get())).start();
    }

    /**
     * Получить результат вычисления.
     * Возвращает результаты в том порядке, в котором добавлялись задачи.
     */
    public T getResult() {
        int currentTask = current.incrementAndGet();
        while (true) {
            T result = map.get(currentTask);
            if (result != null) {
                return result;
            }
        }
    }
}