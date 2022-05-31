package edu.upc.dsa.DAO;

import edu.upc.dsa.util.ObjectHelper;
import edu.upc.dsa.util.QueryHelper;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;


import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class SessionImpl implements Session {
    private static SessionImpl instance;
    final static Logger logger = Logger.getLogger(SessionImpl.class);
    private final Connection conn;

    protected SessionImpl(Connection conn) {

        this.conn = conn;

    }

    @Override
    public boolean create(Object object){
        String insertQuery = QueryHelper.createQueryINSERT(object);
        logger.info(insertQuery);

        PreparedStatement pstm = null;
        try{
            pstm = conn.prepareStatement(insertQuery);
            int i = 1;
            for (String field: ObjectHelper.getFields(object)) {
                pstm.setObject(i++, ObjectHelper.getter(object, field));
            }
            logger.info(pstm.toString());
            pstm.executeQuery();
            return true;

        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public static SessionImpl getInstance(){
        if(instance==null){
            logger.info("Nueva instancia de: " + SessionImpl.class.getName());
            Connection conn = FactorySession.getConnection();
            instance = new SessionImpl(conn);
        }
        return instance;
    }
    public Object getByParameter(Class theClass, String byParameter, Object byParameterValue) {

        String selectQuery = QueryHelper.createQuerySELECTbyParameter(theClass, byParameter);
        logger.info(selectQuery);

        PreparedStatement pstm = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;

        try {
            Object object = theClass.getDeclaredConstructor().newInstance();

            pstm = conn.prepareStatement(selectQuery);

            pstm.setObject(1, byParameterValue);
            pstm.executeQuery();
            rs = pstm.getResultSet();

            if (rs.next()) {

                rsmd = rs.getMetaData();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    String field = rsmd.getColumnName(i);
                    ObjectHelper.setter(object, field, rs.getObject(i));
                }
                return object;

            } else {
                return null;
            }

        } catch (SQLException | NoSuchMethodException | IllegalAccessException
                | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void save(Object entity) {

        String insertQuery = QueryHelper.createQueryINSERT(entity);

        PreparedStatement pstm = null;

        try {
            pstm = conn.prepareStatement(insertQuery);
            pstm.setObject(1, 0);
            int i = 1;

            for (String field: ObjectHelper.getFields(entity)) {
                pstm.setObject(i++, ObjectHelper.getter(entity, field));
            }

            pstm.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void close() {

    }

    public Object get(Class theClass, int id) {
        String query = QueryHelper.createQuerySELECT(theClass);
        ResultSet rs;
        try{
            Statement st = conn.createStatement();
            rs = st.executeQuery(query);
            ResultSetMetaData metadata = rs.getMetaData();
            int numberOfColumns = metadata.getColumnCount();

            Object o = theClass.newInstance();

            while (rs.next()){
                for (int i=1; i<=numberOfColumns; i++){
                    String columnName = metadata.getColumnName(i);
                    ObjectHelper.setter(o, columnName, rs.getObject(i));
                }
            }
            if(ObjectHelper.getter(o, "id") == null){
                return null;
            }
            return o;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void update(Object object) {

    }

    public void delete(Object object) {

    }

    public List<Object> findAll(Class theClass) {
        return null;
    }

    public List<Object> findAll(Class theClass, HashMap params) {
        return null;
    }

    public List<Object> queryObjects(Class theClass) {
        String query = QueryHelper.createQuerySELECTAll(theClass);
        logger.info(query);

        PreparedStatement pstm = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        List<Object> objectResult = new LinkedList<>();
        Object object = null;

        try{
            object = theClass.newInstance();

            pstm = conn.prepareStatement(query);
            logger.info(pstm.toString());
            rs = pstm.executeQuery();

            while(rs.next()) {
                rsmd = rs.getMetaData();

                for(int j=1; j<=rsmd.getColumnCount(); j++) {
                    String name = rsmd.getColumnName(j);
                    ObjectHelper.setter(object,name, rs.getObject(j));
                }
                logger.info("Object added " +object);
                objectResult.add(object);
                object = theClass.newInstance();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return objectResult;
    }
}
