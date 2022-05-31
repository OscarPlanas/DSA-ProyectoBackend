package edu.upc.dsa.util;


public class QueryHelper {

    public static String createQueryINSERT(Object entity) {

        StringBuffer sb = new StringBuffer("INSERT INTO ");
        sb.append(entity.getClass().getSimpleName()).append(" (");
        System.out.println(entity.getClass().getSimpleName());

        for (String field: ObjectHelper.getFields(entity)) {
            sb.append(field + ",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(") VALUES (");

        for (String field: ObjectHelper.getFields(entity)) {
            sb.append("?,");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");

        return sb.toString();
    }
    /*StringBuffer sb = new StringBuffer("INSERT INTO ");
        sb.append("users").append(" ");
        //sb.append(entity.getClass().getSimpleName()).append(" ");
        sb.append("(");

        String [] fields = ObjectHelper.getFields(entity);

        sb.append("id, name, password, username, mail");

        sb.append(") VALUES (");

        for (String field: fields) {
            sb.append(", ?");
        }

        sb.append(")");

        return sb.toString();*/

    public static String createQuerySELECT(Object entity) {
        StringBuffer sb = new StringBuffer("SELECT * FROM ");
        sb.append(entity.getClass().getSimpleName());
        sb.append(" WHERE ID = ?");

        return sb.toString();
    }
    public static String createQuerySELECTAll(Class theClass){

        StringBuffer sb = new StringBuffer("SELECT * FROM ");
        sb.append(theClass.getSimpleName());

        return sb.toString();
    }

    public static String createQuerySELECTbyParameter(Class theClass, String byParameter) {

        StringBuffer sb = new StringBuffer("SELECT * FROM ");
        sb.append(theClass.getSimpleName());
        sb.append(" WHERE " + byParameter + " = ?");

        return sb.toString();
    }

}