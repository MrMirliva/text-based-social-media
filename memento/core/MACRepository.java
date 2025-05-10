package memento.core;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    }

    ///TODO: Implement save method (Şifrelenecek veri varsa şifrelenmeli, deliminatöre dikkat edilmeli)
    public void close() {
        
    }

    private int getCurrentId() {
        while (contains(currentId)) 
        currentId++;
        
        return currentId;   
    }
}
