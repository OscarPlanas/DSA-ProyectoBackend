package edu.upc.dsa.DAO;

import edu.upc.dsa.UserManagerImpl;
import edu.upc.dsa.models.User;

import java.sql.Connection;
import java.util.List;
import java.util.LinkedList;

public class UserDAOImpl implements UserDAO {
    private static UserDAO instance;
    private final Session session;

    private UserDAOImpl() {

        session = FactorySession.openSession();
    }

    public static UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAOImpl();
        }
        return instance;
    }

    @Override
    public int addUser(String name, String username, String password, String email) {
        int usuarioID = 0;

        try {
            User usuario = new User(name, username, password, email);
            session.save(usuario);
        } catch (Exception e) {
            // LOG
        } finally {
            session.close();
        }

        return usuarioID;
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public User getUser(String username) {
        try {
            return ((User) session.getByParameter(User.class, "username", username));
        } catch (Exception e) {
            // LOG
            return null;
        } finally {
            session.close();
        }

    }

    @Override
    public int userListSize() {
        try {
            session.size();
        } catch (Exception e) {
            // LOG
        }
        return 0;
    }

    @Override
    public void deleteUser(String username) {
        try {
            Session UserDAO = FactorySession.openSession();
            User usuario = this.getUser(username);
            UserDAO.delete(usuario);
        } catch (Exception e) {
            // LOG
        } finally {
            session.close();
        }
    }

    @Override
    public boolean existsusername(String username) {
        if(session.getByParameter(User.class, "username", username) == null){
            return false;
        }else return true;
    }

    @Override
    public boolean existsmail(String email) {
        if(session.getByParameter(User.class, "mail", email) == null){
            return false;
        }else return true;
    }
}

