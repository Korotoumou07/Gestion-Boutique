package repository.list;

import java.util.List;
import java.util.ArrayList;
import repository.Repository;
public class RepositoryImpl<T> implements Repository<T> {
    protected List<T> list= new ArrayList<>();

    @Override
    public void insert(T data){
        list.add(data);
    }
    @Override
    public List<T> selectAll(){
        return list;
    }
    @Override
    public void update(T data) {
        int index = list.indexOf(data);
        if (index != -1) {
            list.set(index, data); 
        } else {
            throw new IllegalArgumentException("Item not found for update.");
        }
    }

    
}
