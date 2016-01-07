package pl.edu.wat.wcy.pz.model.dao;

import java.util.List;

public interface GenericDao<T> {
	T create(T t);
	void delete(Object id);
	T retrieve(Object id);
	T update(T t);
    List<T> findAll();
}
