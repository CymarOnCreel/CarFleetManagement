package application.dao;

import java.util.List;


public interface ICrud<T> {

	public void save(T obj);
	public void deleteById(Object id);
	public T findById(Object id);
	public List<T> getAll();
}
