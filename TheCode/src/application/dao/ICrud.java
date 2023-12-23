package application.dao;

import java.util.List;


public interface ICrud<T> {

	public void save(T obj);
	public void update(T obj);
	public void deleteById(Long id);
	public T findById(Long id);
	public List<T> getAll();
}
