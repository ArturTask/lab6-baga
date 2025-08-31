package ru.itmo.socket.server.context;

import ru.itmo.socket.common.entity.City;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Context of whole app, contains and works with collection of elements
 */
public class AppContext {
    private static final LocalDateTime initializationTime = LocalDateTime.now();

    private static List<City> elements = new ArrayList<>();

    // unused
    private AppContext() {
    }

    public static String info() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("Тип коллекции: " + elements.getClass().getName()).append(System.lineSeparator());
        sb.append("Дата инициализации: " + initializationTime).append(System.lineSeparator());
        sb.append("Количество элементов: " + elements.size()).append(System.lineSeparator());
        return sb.toString();
    }

    public static void saveContext(String filename) {
        ContextLoader.saveToFile(filename, elements);
    }

    public static void loadContext(String filename) {
        elements = ContextLoader.loadFromFile(filename);
        Collections.sort(elements);
    }

    /**
     * @return representation of all elements
     */
    public static List<String> getAllElements() {
        return elements
                .stream()
                .map(City::toString)
                .toList();
    }

    /**
     * @param city new city
     * @return true if new object was added, false if object was already present
     */
    public static boolean add(City city) {
        if (elements.contains(city)) {
            return false;
        }
        elements.add(city);
        Collections.sort(elements);
        return true;
    }

    /**
     * @param city new city
     * @return true if new object was added, false if object was not added
     */
    public static boolean addIfMin(City city) {
        if (elements.isEmpty() || city.compareTo(elements.get(elements.size() - 1)) < 0) {
            return add(city);
        }
        return false;
    }

    /**
     * @param id id to remove
     * @return true if object was deleted, false if object wasn't found
     */
    public static boolean removeById(int id) {
        return elements.removeIf(p -> p.getId() == id);
    }

    /**
     * @param city city greater than the ones that need to be deleted
     * @return number of deleted elements
     */
    public static int removeAllLowerThan(City city) {
        int before = elements.size();
        elements.removeIf(p -> p.compareTo(city) < 0);
        int after = elements.size();
        return before - after;
    }

    /**
     * @param updatedCity city to update
     * @return true if object was updated, false if object wasn't found
     */
    public static boolean updateEntity(City updatedCity) {
        boolean found = remove(updatedCity);

        if (found) {
            add(updatedCity);
        }

        return found;

    }

    /**
     * Removes all elems
     */
    public static void removeAll() {
        elements.clear();
    }


    /**
     * @param city city to remove
     * @return true if object was deleted, false if object wasn't found
     */
    private static boolean remove(City city) {
        return elements.remove(city);
    }

    public static long sumOfMeters() {
        return elements.stream()
                .map(City::getMetersAboveSeaLevel)
                .filter(Objects::nonNull)
                .mapToLong(Integer::longValue)
                .sum();
    }
}
