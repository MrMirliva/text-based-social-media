package memento.core;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.lang.reflect.Field;

///TODO: MACRepository sınıfını test et.
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

    ///FIXME: Default değerler için bir çözüm bulunmalı.
    public boolean add(T item) {
        if (item == null) 
        return false;
        
        item.setId(getCurrentId());
        item.setCreatedAt(LocalDateTime.now());
        item.setUpdatedAt(LocalDateTime.now());
        items.add(item.getId(), item);
        return true;
    }

    public boolean update(T item) {
        if(item == null || !contains(item.getId()))
        return false;

        item.setUpdatedAt(LocalDateTime.now());
        int index = items.indexOf(item);

        if (index != -1) {
            items.set(index, item);
            return true;
        }

        return false;
    }

    public boolean deleteById(int id) {
        if (!contains(id)) 
        return false;
        
        T item = findById(id).orElse(null);
        if (item != null) {
            items.remove(item);
            return true;
        }
        
        return false;
    }

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

    public boolean contains(int id) {
        if (id < 0 || id >= items.size())
        return false;
        
        return items.contains(id);
    }

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
