package edu.upc.dsa;

import edu.upc.dsa.DAO.UserDAO;
import edu.upc.dsa.DAO.UserDAOImpl;
import edu.upc.dsa.models.User;
import org.junit.Test;

public class TestBD {
    @Test
    public void testInsert() {
        UserDAO userDAO = UserDAOImpl.getInstance();
        userDAO.addUser("aa", "bb", "cc", "cc@gmail.com");
    }
    @Test
    public void testSelect() {
        UserDAO userDAO = UserDAOImpl.getInstance();
        User usuario = userDAO.getUser("bb");
        System.out.println(usuario.getName());
    }
}
