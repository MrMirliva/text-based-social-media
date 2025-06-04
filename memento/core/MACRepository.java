package memento.core;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import memento.anatation.Default;
import memento.anatation.Unique;

import java.lang.reflect.Field;

public abstract class MACRepository<T extends MACModel> {
    
    protected final List<T> items = new ArrayList<>();
    protected final Class<T> modelClass;
    private final String fileName;
    private final String DELIMINATOR = "<-!->";

    private int currentId = 0;

    public MACRepository(Class<T> modelClass) {
        this.modelClass = modelClass;
        this.fileName = "database\\" + modelClass.getSimpleName() + ".txt";
        load();
    }

    //Kontrol edildi
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
                    } else if (type == java.time.LocalDateTime.class) {
                        value = java.time.LocalDateTime.parse(defaultValue);
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

    //Kontrol edildi
    public T update(T item) {
        if(item == null || !contains(item.getId()))
            return null;

        item.setUpdatedAt(LocalDateTime.now());

        items.set(item.getId(), item);

        return findById(item.getId()).orElse(null);
    }

    //Kontrol edildi
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

    //Kontrol edildi
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

    //Kontrol edildi
    public T getById(int id) {
        return findById(id).orElse(null);
    }

    //Kontrol edildi
    public boolean contains(int id) {
        if (id < 0 || id >= items.size())
        return false;
        
        return items.get(id) != null;
    }

    //Kontrol edildi
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

    //Kontrol edildi
    public Optional<List<T>> findAll() {
        List<T> allItems = getAll();
        if (allItems.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(allItems);
    }

    //Kontrol edildi
    public int count() {
        return (int) items.stream().filter(item -> item != null).count();
    }

    ///TODO: Implement load method, (Şifrelenmiş veri varsa çözülmeli, deliminatöre dikkat edilmeli)
    private void load() {

        java.io.File file = new java.io.File(fileName);
        if (!file.exists()) {
            return;
        }
        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(file))) {
            String line = reader.readLine();

            if (line == null) {
                return;
            }

            String[] headers = splitLine(line);
            line = reader.readLine();

            if (line == null) {
                throw new IllegalArgumentException("No data found in file");
            }

            String[] types = splitLine(line);

            if (headers.length != types.length) {
                throw new IllegalArgumentException("Header count does not match type count");
            }

            List<Field> fields = getAllFields(modelClass);

            if (fields.size() != headers.length) {
                throw new IllegalArgumentException("Field count does not match header count");
            }

            for (int i = 0; i < headers.length; i++) {
                Field field = fields.get(i);
                String header = headers[i];
                HeaderType headerType = convertHeaderType(types[i]);

                if (!isFieldTypeCompatible(field, headerType)) {
                    throw new IllegalArgumentException("Field type does not match header type : fieldType " + field.getType() + " headerType " + headerType + " fieldName " + field.getName() + " headerName " + header);
                }

                if (!header.equals(field.getName())) {
                    throw new IllegalArgumentException("Header name does not match field name");
                }
            }

            ///TODO: Data okuma işlemi yapılacak. Her bir obje için değişkenlerin atamaları çözülecek
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

    }

    ///TODO: Implement save method (Şifrelenecek veri varsa şifrelenmeli, deliminatöre dikkat edilmeli)
    public void close() {
        
    }

    private int getCurrentId() {
        while (contains(currentId)) 
        currentId++;
        
        return currentId;   
    }

    //FIXME: Kaçınma sembolüne göre de splitlemeli
    private String[] splitLine(String line) {
        return line.split(DELIMINATOR);
    }

    public HeaderType convertHeaderType(String a) {
        switch (a) {
            case "int":
                return HeaderType.INT;
            case "String":
                return HeaderType.STRING;
            case "boolean":
                return HeaderType.BOOLEAN;
            case "double":
                return HeaderType.DOUBLE;
            case "float":
                return HeaderType.FLOAT;
            case "long":
                return HeaderType.LONG;
            case "short":
                return HeaderType.SHORT;
            case "byte":
                return HeaderType.BYTE;
            case "char":
                return HeaderType.CHAR;
            case "LocalDateTime":
                return HeaderType.LOCALDATETIME;
            default:
                throw new IllegalArgumentException("Unknown type: " + a);
        }
    }

    enum HeaderType {
        INT,
        STRING,
        BOOLEAN,
        DOUBLE,
        FLOAT,
        LONG,
        SHORT,
        BYTE,
        CHAR,
        LOCALDATETIME
    }

    private boolean isFieldTypeCompatible(Field field, HeaderType headerType) {
        Class<?> type = field.getType();
        switch (headerType) {
            case INT:
                return type == int.class || type == Integer.class;
            case STRING:
                return type == String.class;
            case BOOLEAN:
                return type == boolean.class || type == Boolean.class;
            case DOUBLE:
                return type == double.class || type == Double.class;
            case FLOAT:
                return type == float.class || type == Float.class;
            case LONG:
                return type == long.class || type == Long.class;
            case SHORT:
                return type == short.class || type == Short.class;
            case BYTE:
                return type == byte.class || type == Byte.class;
            case CHAR:
                return type == char.class || type == Character.class;
            case LOCALDATETIME:
                return type == java.time.LocalDateTime.class;
            default:
                return false;
        }
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
