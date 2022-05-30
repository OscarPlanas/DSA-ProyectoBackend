package edu.upc.dsa.DAO;

import java.util.HashMap;
import java.util.List;

public interface Session<E> {

    void save(Object entity);
    boolean create(Object object);
    void close();
    Object get(Class theClass, int ID);
    void update(Object object);
    void delete(Object object);
    List<Object> findAll(Class theClass);
    List<Object> findAll(Class theClass, HashMap params);
    List<Object> queryObjects(Class theClass);

    Object getByParameter(Class theCass, String byParameter, Object byParameterValue);

}