package ru.naumen.collection.task2;

import java.util.*;

/**
 * Дано:
 * <pre>
 * public class User {
 *     private String username;
 *     private String email;
 *     private byte[] passwordHash;
 *     …
 * }
 * </pre>
 * Нужно реализовать метод
 * <pre>
 * public static List<User> findDuplicates(Collection<User> collA, Collection<User> collB);
 * </pre>
 * <p>который возвращает дубликаты пользователей, которые есть в обеих коллекциях.</p>
 * <p>Одинаковыми считаем пользователей, у которых совпадают все 3 поля: username,
 * email, passwordHash. Дубликаты внутри коллекций collA, collB можно не учитывать.</p>
 * <p>Метод должен быть оптимален по производительности.</p>
 * <p>Пользоваться можно только стандартными классами Java SE.
 * Коллекции collA, collB изменять запрещено.</p>
 *
 * См. {@link User}
 *
 * @author vpyzhyanov
 * @since 19.10.2023
 */
public class Task2
{
    /**
     * Возвращает дубликаты пользователей, которые есть в обеих коллекциях
     *
     * <p>Сложность алгоритма О(n), где n сумма размеров передаваемых коллекций</p>
     */
    public static List<User> findDuplicates(Collection<User> collA, Collection<User> collB) {
        /*
         * Выбран Set потому что у меня реализован метод hasCode/equals и поиск по такой коллекции будет быстрее,
         * чем по той же ArrayList(), в среднем О(1) (имеется в виду contains, который под капотом применяет equals)
         */
        Collection<User> smallest;
        Collection<User> biggest;

        //О(1)
        if (collA.size() > collB.size()) {
            smallest = collB; //Здесь не происходит создания новой коллекции, копируется ссылка
            biggest = collA;
        }else {
            smallest = collA;
            biggest = collB;
        }
        //О(n) - где n размер smallest
        Set<User> userSet = new HashSet<>(smallest);
        //Добавление элемента в конец разницы не имеет по сложности
        List<User> result = new ArrayList<>(Math.min(collA.size(), collB.size()));

        for (User user : biggest) {
            //О(1) в среднем
            if (userSet.contains(user)) {
                result.add(user);
            }
        }

        return result;
    }
}