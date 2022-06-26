package edu.upc.dsa.DAO;

import java.util.HashMap;
import java.util.List;

public interface Session<E> {

    //Funciones base
    void save(Object entity);
    boolean create(Object object);
    void close();

    //Gets
    Object get(Class theClass, int ID);
    Object getByParameter(Class theClass, String byParameter, Object byParameterValue);
    Object getByTwoParameters(Class theClass, String byFirstParameter, Object byFirstParameterValue, String bySecondParameter, Object bySecondParameterValue);
    Object getParameterByParameter(Class theClass, String parameter, String byParameter, Object byParameterValue);

    //Updates
    boolean update(Object object);
    boolean updateByParameter(Object object, String byParameter, Object byParameterValue);
    boolean updateByTwoParameters(Object object, String byFirstParameter, Object byFirstParameterValue, String bySecondParameter, Object bySecondParameterValue);
    boolean updateParameterByParameter(Class theClass, String parameter, Object parameterValue, String byParameter, Object byParameterValue);
    boolean updateParameterByTwoParameters(Class theClass, String parameter, Object parameterValue, String byFirstParameter, Object byFirstParameterValue, String bySecondParameter, Object bySecondParameterValue);

    //Deletes
    void delete(Object object);
    boolean deleteByParameter(Class theClass, String byParameter, Object byParameterValue);
    boolean deleteByTwoParameters(Class theClass, String byFirstParameter, Object byFirstParameterValue, String bySecondParameter, Object bySecondParameterValue);

    //Querys
    List<Object> queryObjects(Class theClass);
    List<Object> queryObjectsByParameter(Class theClass, String byParameter, Object byParameterValue);
    List<Object> orderObjectsByParameter(Class theClass, String byParameter);

    //Finds
    List<Object> findAll(Class theClass);
    List<Object> findAll(Class theClass, HashMap params);

    //Get del hash contraseña
    String getHash(String parameter);

    int size();

}