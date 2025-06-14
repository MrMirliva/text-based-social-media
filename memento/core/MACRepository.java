package memento.core;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import java.lang.reflect.Field;

import memento.anatation.Default;
import memento.anatation.Encrypted;
import memento.anatation.Unique;

/**
 * MACRepository is an abstract generic repository class for managing persistent storage of models
 * that extend the MACModel class. It provides basic CRUD (Create, Read, Update, Delete) operations,
 * unique constraint enforcement, default value assignment via annotations, and serialization/deserialization
 * logic for saving and loading model data from a text file.
 * <p>
 * The repository uses reflection to handle model fields, supports custom annotations for default values
 * and uniqueness, and ensures that all returned model instances are clones to prevent unintended modifications.
 * <p>
 * Subclasses should implement or extend this class to provide concrete repository logic for specific model types.
 *
 * @param <T> The type of MACModel this repository manages
 * @author Mirliva (Abdullah Gündüz)
 * @version 1.0
 * @since 2025-06-04
 */
public abstract class MACRepository<T extends MACModel> {
    
    protected final List<T> items = new ArrayList<>();
    protected final Class<T> modelClass;
    private final String fileName;
    private final String DELIMINATOR = "<-!->";

    private int currentId = 0;

    public MACRepository(Class<T> modelClass) {
        this.modelClass = modelClass;
        this.fileName = "database\\" + modelClass.getSimpleName() + ".txt";
        ///TOTO: updateColumns() yazılarak Column anatation'unu kullanabilirim.
        load();
    }

    /**
     * Adds an item to the repository.
     * If the item is null, it returns null.
     * If the item has a unique field that already exists in the repository, it throws an IllegalArgumentException.
     * It sets the ID, createdAt, and updatedAt fields of the item.
     * It also sets default values for fields annotated with @Default if they are null.
     *
     * @param item The item to add
     * @return The added item or null if the input was null
     */
    public T add(T item) {
        if (item == null)
        return null;

        item.setId(getCurrentId());
        item.setCreatedAt(LocalDateTime.now());
        item.setUpdatedAt(LocalDateTime.now());
        
        try {
            for (Field field : getAllFields(modelClass)) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(Default.class)) {
                    Default defaultAnnotation = field.getAnnotation(Default.class);
                    String defaultValue = defaultAnnotation.value();
                    Class<?> type = field.getType();
                    Object value = null;
                    if (type == int.class || type == Integer.class) {
                        value = Integer.parseInt(defaultValue);
                    } else if (type == boolean.class || type == Boolean.class) {
                        value = Boolean.parseBoolean(defaultValue);
                    } else if (type == double.class || type == Double.class) {
                        value = Double.parseDouble(defaultValue);
                    } else if (type == float.class || type == Float.class) {
                        value = Float.parseFloat(defaultValue);
                    } else if (type == long.class || type == Long.class) {
                        value = Long.parseLong(defaultValue);
                    } else if (type == short.class || type == Short.class) {
                        value = Short.parseShort(defaultValue);
                    } else if (type == byte.class || type == Byte.class) {
                        value = Byte.parseByte(defaultValue);
                    } else if (type == char.class || type == Character.class) {
                        value = defaultValue.length() > 0 ? defaultValue.charAt(0) : '\0';
                    } else if (type == String.class) {
                        value = defaultValue;
                    } else if (type == LocalDateTime.class) {
                        value = LocalDateTime.parse(defaultValue);
                    }
                    if (value != null && field.get(item) == null) {
                        field.set(item, value);
                    }
                }

                if(field.isAnnotationPresent(Unique.class)) {
                    Unique uniqueAnnotation = field.getAnnotation(Unique.class);
                    if (uniqueAnnotation != null) {
                        field.setAccessible(true);
                        Object fieldValue = field.get(item);
                        for (T existingItem : items) {
                            if (existingItem != null && fieldValue.equals(field.get(existingItem))) {
                                throw new IllegalArgumentException("Unique constraint violated for field: " + field.getName());
                            }
                        }
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        

        items.add(item.getId(), item);
        return findById(item.getId()).orElse(null);
    }

    /**
     * Updates an item in the repository.
     * If the item is null or does not exist in the repository, it returns null.
     * It updates the updatedAt field of the item.
     *
     * @param item The item to update
     * @return The updated item or null if the input was null or the item does not exist
     */
    public T update(T item) {
        if(item == null || !contains(item.getId()))
            return null;

        item.setUpdatedAt(LocalDateTime.now());

        items.set(item.getId(), item);

        return findById(item.getId()).orElse(null);
    }

    /**
     * Deletes an item by its ID.
     * If the item does not exist, it returns false.
     * If the item exists, it sets the item at that index to null and returns true.
     *
     * @param id The ID of the item to delete
     * @return true if the item was deleted, false if it did not exist
     */
    public boolean deleteById(int id) {
        if (!contains(id)) 
        return false;
        
        T item = findById(id).orElse(null);
        if (item != null) {
            items.set(id, null);
            return true;
        }
        
        return false;
    }

    /**
     * Finds an item by its ID.
     * If the item does not exist, it returns an empty Optional.
     * If the item exists, it returns a cloned version of the item wrapped in an Optional.
     *
     * @param id The ID of the item to find
     * @return An Optional containing the cloned item if found, or empty if not found
     */
    public Optional<T> findById(int id) {
        if (!contains(id)) 
        return Optional.empty();
        
        T item = items.get(id);

        if (item != null) 
        try {
            @SuppressWarnings("unchecked")
            T clonedItem = (T) item.clone();
            return Optional.of(clonedItem);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return Optional.empty();
        }

        return Optional.empty();
    }

    /**
     * Gets an item by its ID.
     * If the item does not exist, it returns null.
     * If the item exists, it returns a cloned version of the item.
     *
     * @param id The ID of the item to get
     * @return The cloned item if found, or null if not found
     */
    public T getById(int id) {
        return findById(id).orElse(null);
    }

    /**
     * Checks if an item with the given ID exists in the repository.
     * If the ID is out of bounds, it returns false.
     * If the item exists, it returns true; otherwise, it returns false.
     *
     * @param id The ID to check
     * @return true if the item exists, false otherwise
     */
    public boolean contains(int id) {
        if (id < 0 || id >= items.size())
        return false;
        
        return items.get(id) != null;
    }

    /**
     * Gets all items in the repository.
     * It returns a list of cloned items to avoid modifying the original items.
     * If an item is null, it is skipped.
     *
     * @return A list of cloned items
     */
    public List<T> getAll() {
        List<T> allItems = new ArrayList<>();
        for (T item : items) {
            if (item != null)  {
                try {
                    @SuppressWarnings("unchecked")
                    T clonedItem = (T) item.clone();
                    allItems.add(clonedItem);
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
        }
        return allItems;
    }

    /**
     * Finds all items in the repository.
     * If there are no items, it returns an empty Optional.
     * If there are items, it returns a list of cloned items wrapped in an Optional.
     *
     * @return An Optional containing a list of cloned items if found, or empty if not found
     */
    public Optional<List<T>> findAll() {
        List<T> allItems = getAll();
        if (allItems.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(allItems);
    }

    /**
     * Counts the number of items in the repository.
     * It counts only non-null items.
     *
     * @return The count of non-null items
     */
    public int count() {
        return (int) items.stream().filter(item -> item != null).count();
    }

    /**
     * Loads data from the associated file into the repository.
     * <p>
     * This method reads the file specified by {@code fileName}, parses the header and type lines,
     * and then reads each data row. For each row, it creates a new instance of the model class,
     * sets its fields using reflection, and handles decryption for fields annotated with {@code @Encrypted}.
     * The loaded objects are then added to the repository's internal list, ensuring the list size matches the IDs.
     * </p>
     * <ul>
     *   <li>Reads headers and types from the first two lines of the file.</li>
     *   <li>Parses each data row and converts string values to the appropriate field types.</li>
     *   <li>Decrypts values for fields annotated with {@code @Encrypted}.</li>
     *   <li>Populates the {@code items} list with the loaded objects, preserving their IDs.</li>
     * </ul>
     * If the file does not exist, the method returns immediately.
     */
    private void load() {
        File file = new File(fileName);
        if (!file.exists()) {
            return;
        }

        // Temporary storage for data rows
        List<String[]> dataRows = new ArrayList<>();
        String[] headers;
        String[] types;

        // 1️ Read the file, extract headers and types, and add data rows to dataRows
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String headerLine = reader.readLine();
            if (headerLine == null) return;
            headers = headerLine.split(Pattern.quote(DELIMINATOR), -1);

            String typeLine = reader.readLine();
            if (typeLine == null)
                throw new IllegalArgumentException("Type line not found in file");
            types = typeLine.split(Pattern.quote(DELIMINATOR), -1);

            String line;
            while ((line = reader.readLine()) != null) {
                dataRows.add(line.split(Pattern.quote(DELIMINATOR), -1));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // 2️ Map fields from modelClass by name
        List<Field> allFields = getAllFields(modelClass);
        Map<String, Field> fieldMap = new HashMap<>();
        for (Field f : allFields) {
            f.setAccessible(true);
            fieldMap.put(f.getName(), f);
        }

        // 3️ For each data row, create a new instance and set its fields
        List<T> loadedItems = new ArrayList<>();
        for (String[] row : dataRows) {
            try {
                T obj = modelClass.getDeclaredConstructor().newInstance();

                for (int i = 0; i < headers.length; i++) {
                    Field field = fieldMap.get(headers[i]);
                    if (field == null) {
                        throw new IllegalArgumentException("No field found matching header: " + headers[i]);
                    }

                    // 3.a) Get raw string value
                    String rawValue = row[i];

                    // 3.b) Decrypt if field is annotated with @Encrypted
                    if (field.isAnnotationPresent(Encrypted.class)) {
                        rawValue = CryptoUtil.decrypt(rawValue);
                    }

                    // 3.c) Convert string to appropriate type
                    Object convertedValue;
                    switch (types[i]) {
                        case "int":
                            convertedValue = Integer.parseInt(rawValue);
                            break;
                        case "String":
                            convertedValue = rawValue;
                            break;
                        case "LocalDateTime":
                            convertedValue = LocalDateTime.parse(rawValue);
                            break;
                        default:
                            throw new IllegalArgumentException("Unsupported type: " + types[i]);
                    }

                    // 3.d) Set value to field
                    field.set(obj, convertedValue);
                }

                loadedItems.add(obj);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // 4️ Add all loaded items to the items list, ensuring correct list size and IDs
        items.clear();
        
        for (T item : loadedItems) {
            if (item == null) continue;
            int id = item.getId();

            while (items.size() <= id) {
                items.add(null);
            }

            items.set(id, item);
            currentId = Math.max(currentId, id + 1);
        }
    }
    
    /**
     * The `close()` method writes data from a list of items to a file, encrypting certain fields if
     * they are annotated with `Encrypted`.
     */
    public void close() {
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (PrintWriter writer = new PrintWriter(file)) {
            // Write headers
            StringBuilder headerLine = new StringBuilder();
            StringBuilder typeLine = new StringBuilder();

            List<Field> fields = getAllFields(modelClass);
            for (Field field : fields) {
                headerLine.append(field.getName()).append(DELIMINATOR);
                typeLine.append(field.getType().getSimpleName()).append(DELIMINATOR);
            }

            // Remove the last DELIMINATOR
            if (headerLine.length() > 0) {
                headerLine.setLength(headerLine.length() - DELIMINATOR.length());
                typeLine.setLength(typeLine.length() - DELIMINATOR.length());
            }

            writer.println(headerLine.toString());
            writer.println(typeLine.toString());

            // Write data
            for (T item : items) {
                if (item == null) continue;
                StringBuilder dataLine = new StringBuilder();
                for (Field field : fields) {
                    field.setAccessible(true);
                    Object rawValue = field.get(item);
                    String strValue = rawValue != null ? rawValue.toString() : "";


                    // The above code is checking if the `field` has an annotation `Encrypted` present.
                    // If the annotation is present, it encrypts the `strValue` using the
                    // `CryptoUtil.encrypt` method.
                    if (field.isAnnotationPresent(Encrypted.class)) {
                        strValue = CryptoUtil.encrypt(strValue);
                    }

                    dataLine.append(strValue).append(DELIMINATOR);
                }
                // Remove the last DELIMINATOR
                if (dataLine.length() > 0) {
                    dataLine.setLength(dataLine.length() - DELIMINATOR.length());
                }
                writer.println(dataLine.toString());
            }

        } catch (IOException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    private int getCurrentId() {
        while (contains(currentId)) 
        currentId++;
        
        return currentId;   
    }


    private List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        List<Class<?>> classHierarchy = new ArrayList<>();
        // Sınıf hiyerarşisini Object'e kadar topla
        while (clazz != null && clazz != Object.class) {
            classHierarchy.add(0, clazz); // Üst sınıflar başa eklensin
            clazz = clazz.getSuperclass();
        }
        // Hiyerarşide sırayla field'ları ekle
        for (Class<?> c : classHierarchy) {
            for (Field f : c.getDeclaredFields()) {
                fields.add(f);
            }
        }
        return fields;
    }
}
