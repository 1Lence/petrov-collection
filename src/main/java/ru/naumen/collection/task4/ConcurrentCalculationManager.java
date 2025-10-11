package ru.naumen.collection.task4;

import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * Класс управления расчётами
 */

/**
 * <p>Сложность О(1), если не брать в учет время ожидания задач.<p>
 */
public class ConcurrentCalculationManager<T> {
    /**
     * Данная коллекция не имеет единой блокировки на операцию вставки и получения. Благодаря чему она идеально подходит,
     * из-за того что задачи добавляются в разных потоках
     */
    private final BlockingQueue<Future<T>> queue = new LinkedBlockingQueue<>();
    /**
     * Добавить задачу на параллельное вычисление
     */
    public void addTask(Supplier<T> task) {
        CompletableFuture<T> future = CompletableFuture.supplyAsync(task);
        queue.add(future);
    }

    /**
     * Получить результат вычисления.
     * Возвращает результаты в том порядке, в котором добавлялись задачи.
     */
    public T getResult() {
        try {
            if(!queue.isEmpty()) {
                return queue.take().get();
            }
            throw new RuntimeException("There is no tasks in queue"); //Если очередь пуста, то вернулся бы null
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}