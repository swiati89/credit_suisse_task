package dao;

import org.hibernate.HibernateException;

import java.util.List;

public interface SimpleGenericDao<T> {
    void saveDataToDb(T object) throws HibernateException;
    T getDataById(Long id) throws HibernateException;
    List<T> getAll() throws HibernateException;
}
