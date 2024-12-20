package repository;

import java.util.List;

 
public interface Repository<T> {

    
    public void insert(T data);

    public List<T> selectAll();
    
    public void update(T data);
    

}

