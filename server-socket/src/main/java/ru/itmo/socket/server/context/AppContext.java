package ru.itmo.socket.server.context;

import ru.itmo.socket.common.entity.Product;
import ru.itmo.socket.common.entity.UnitOfMeasure;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Context of whole app, contains and works with collection of elements
 */
public class AppContext {
    private static final LocalDateTime initializationTime = LocalDateTime.now();

    private static List<Product> elements = new LinkedList<>();

    // unused
    private AppContext(){}

    public static void info(ObjectOutputStream oos) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("Тип коллекции: " + elements.getClass().getName()).append(System.lineSeparator());
        sb.append("Дата инициализации: " + initializationTime).append(System.lineSeparator());
        sb.append("Количество элементов: " + elements.size()).append(System.lineSeparator());
        oos.writeUTF(sb.toString());
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
                .map(Product::toString)
                .toList();
    }

    /**
     * maps, filters not null, selects unique values and sort and gets string representation of them
     */
    public static List<String> getAllUniqueDescendingUnitOfMeasure() {
        return elements
                .stream()
                .map(Product::getUnitOfMeasure)
                .filter(Objects::nonNull)
                .distinct()
                .sorted(Comparator.reverseOrder())
                .map(UnitOfMeasure::toString)
                .toList();
    }

    /**
     * @return String representation of element OR null if not found
     */
    public static String getFirst() {
        if (elements.isEmpty()) {
            return null;
        } else {
            return elements.get(0).toString();
        }
    }

    /**
     * @param product new product
     * @return true if new object was added, false if object was already present
     */
    public static boolean add(Product product) {
        if (elements.contains(product)) {
            return false;
        }
        elements.add(product);
        Collections.sort(elements);
        return true;
    }

    /**
     * @param product new product
     * @return true if new object was added, false if object was not added
     */
    public static boolean addIfMax(Product product) {
        if (elements.isEmpty() || product.compareTo(elements.get(elements.size() - 1)) > 0) {
            return add(product);
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
     * @param product product smaller than the ones that need to be deleted
     * @return number of deleted elements
     */
    public static int removeAllGreaterThan(Product product) {
        int before = elements.size();
        elements.removeIf(p -> p.compareTo(product) > 0);
        int after = elements.size();
        return before - after;
    }

    /**
     * @param price of product to delete
     * @return id of deleted element OR null if none is found
     */
    public static Integer removeOneByPrice(Double price) {
        Optional<Product> toRemove = elements.stream()
                .filter(p -> Objects.equals(p.getPrice(), price))
                .findFirst();

        if (toRemove.isPresent()) {
            Product productToDelete = toRemove.get();
            elements.remove(productToDelete);
            return productToDelete.getId();
        }

        return null;
    }

    /**
     * @param updatedProduct product to update
     * @return true if object was updated, false if object wasn't found
     */
    public static boolean updateEntity(Product updatedProduct) {
        boolean found = remove(updatedProduct);

        if (found) {
            add(updatedProduct);
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
     * @param product product to remove
     * @return true if object was deleted, false if object wasn't found
     */
    private static boolean remove(Product product) {
        return elements.remove(product);
    }

}
