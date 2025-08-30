package ru.itmo.lena.ex.manager;

import ru.itmo.lena.ex.entity.City;
import ru.itmo.lena.ex.entity.Coordinates;
import ru.itmo.lena.ex.util.Console;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * Менеджер коллекции городов
 */
public class CollectionManager {
    private ArrayList<City> collection = new ArrayList<>();
    private final ZonedDateTime creationTime;
    private final String fileName;
    private final Console console;


    public CollectionManager(String fileName, Console console) {
        this.creationTime = ZonedDateTime.now();
        this.fileName = fileName;
        this.console = console;
        loadCollection();
    }


    /**
     * Загружает коллекцию из файла
     */
    private void loadCollection() {
        try {
            this.collection = FileManager.readCitiesFromCSV(fileName);
            updateNextId();
        } catch (Exception e) {
            System.err.println("Ошибка загрузки коллекции: " + e.getMessage());
            this.collection = new ArrayList<>();
        }
//    private void loadCollection() {
//        try {
//            this.collection = FileManager.readCitiesFromCSV(fileName);
//
//            // Проверка уникальности ID
//            Set<Long> ids = new HashSet<>();
//            for (City city : collection) {
//                if (!ids.add(city.getId())) {
//                    throw new BuildObjectException("Найдены дубликаты ID: " + city.getId());
//                }
//            }
//
//            updateNextId(); // Обновляем nextId только после успешной проверки
//        } catch (Exception e) {
//            System.err.println("Ошибка загрузки коллекции: " + e.getMessage());
//            this.collection = new ArrayList<>();
//        }
//    }

    }


    public void update(City oldCity, City newCity) {
        oldCity.setName(newCity.getName());
        oldCity.setArea(newCity.getArea());
        oldCity.setCapital(newCity.getCapital());
        oldCity.setCoordinates(newCity.getCoordinates());
        oldCity.setGovernment(newCity.getGovernment());
        oldCity.setStandardOfLiving(newCity.getStandardOfLiving());
        oldCity.setGovernor(newCity.getGovernor());
        oldCity.setMetersAboveSeaLevel(newCity.getMetersAboveSeaLevel());
        oldCity.setPopulation(newCity.getPopulation());
    }

    /**
     * Обновляет следующее значение ID
     */
    private void updateNextId() {
        long maxId = 0;
        for (City city : collection) {
            if (city.getId() > maxId) {
                maxId = city.getId();
            }
        }
        City.setNextId(maxId + 1);
    }

    /**
     * Возвращает время создания коллекции
     */
    public ZonedDateTime getCreationTime() {
        return creationTime;
    }

    /**
     * Возвращает тип коллекции
     */
    public String getCollectionType() {
        return collection.getClass().getName();
    }

    /**
     * Возвращает размер коллекции
     */
    public int getCollectionSize() {
        return collection.size();
    }

    /**
     * Проверяет, пуста ли коллекция
     */
    public boolean isEmpty() {
        return collection.isEmpty();
    }

    /**
     * Добавляет город в коллекцию
     */
    public void add(City city) {
        city.setId(City.getNextId());
        collection.add(city);
        City.increaseNextId();
        sortCollection();
    }

    /**
     * Удаляет город из коллекции
     */
    public void remove(City city) {
        collection.remove(city);
    }

    /**
     * Очищает коллекцию
     */
    public void clear() {
        collection.clear();
        City.setNextId(1);
    }

    /**
     * Находит город по ID
     */
    public City findById(long id) {
        for (City city : collection) {
            if (city.getId() == id) {
                return city;
            }
        }
        return null;
    }

    public City findByNameAndCords(String name, Coordinates coords) {
        for (City city : collection) {
            if (city.getName().equals(name) && city.getCoordinates().equals(coords)) {
                return city;
            }
        }
        return null;
    }

    /**
     * Возвращает минимальный город (по natural ordering)
     */
    public City findMin() {
        if (collection.isEmpty()) {
            return null;
        }
        return Collections.min(collection);
    }

    /**
     * Возвращает максимальный город (по natural ordering)
     */
    public City findMax() {
        if (collection.isEmpty()) {
            return null;
        }
        return Collections.max(collection);
    }

    /**
     * Удаляет города, меньшие чем заданный
     * @return количество удаленных элементов
     */
    public int removeLower(City city) {
        List<City> toRemove = new ArrayList<>();
        for (City c : collection) {
            if (city.compareTo(c) > 0) {
                toRemove.add(c);
            }
        }
        collection.removeAll(toRemove);
        return toRemove.size();
    }

    /**
     * Сохраняет коллекцию в файл
     */
    public void saveCollection() throws IOException {
        FileManager.writeCitiesToCSV(fileName, collection);
    }

    /**
     * Возвращает копию коллекции
     */
    public ArrayList<City> getCollection() {
        return this.collection;
    }

    /**
     * Сортирует коллекцию
     */
    private void sortCollection() {
        collection.sort(Comparator.naturalOrder());
    }

    public Console getConsole() {
        return console;
    }

    public String getFileName() {
        return fileName;
    }

    public int removeAllByMeters(Integer meters) {
        List<City> toRemove = collection.stream()
                .filter(c -> Objects.equals(c.getMetersAboveSeaLevel(), meters))
                .toList();
        collection.removeAll(toRemove);
        return toRemove.size();
    }

    public long sumOfMeters() {
        return collection.stream()
                .map(City::getMetersAboveSeaLevel)
                .filter(Objects::nonNull)
                .mapToLong(Integer::longValue)
                .sum();
    }



}
