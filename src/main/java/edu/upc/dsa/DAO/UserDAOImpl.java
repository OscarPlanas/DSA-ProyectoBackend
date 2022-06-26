package edu.upc.dsa.DAO;

import edu.upc.dsa.UserManagerImpl;
import edu.upc.dsa.models.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.util.List;
import java.util.LinkedList;

public class UserDAOImpl implements UserDAO {
    private static UserDAO instance;
    private final Session session;
    final static Logger logger = Logger.getLogger(SessionImpl.class);

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
            User usuario = new User(name, username, password, email, 0);

            session.save(usuario);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return usuarioID;
    }

    @Override
    public List<User> getAllUsers() {
        try {
            return ((List) session.queryObjects(User.class));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            //session.close();
        }
    }

    @Override
    public User getUser(String username) {
        try {
            return ((User) session.getByParameter(User.class, "username", username));
        } catch (Exception e) {
           e.printStackTrace();
            return null;
        } finally {
            //session.close();
        }

    }

    @Override
    public int userListSize() {
        try {
            session.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void deleteUser(User user) {
        try {
            session.delete(user);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //session.close();
        }
    }

    @Override
    public boolean deleteUserByUsername(String username) {
        try {
            return session.deleteByParameter(User.class, "username", username);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //session.close();
        }
        return false;
    }

    @Override
    public boolean deleteByParameter(String parameter, Object value) {
        try {
            return session.deleteByParameter(User.class, parameter, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //session.close();
        }
        return false;
    }

    @Override
    public boolean existsusername(String username) {

        try {
            if(session.getParameterByParameter(User.class, "username", "username", username) == null){
                return false;
            }else return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            //session.close();

        }
    }

    @Override
    public boolean existsmail(String email) {
        try {
            if(session.getByParameter(User.class, "mail", email) == null){
                return false;
            }else return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            //session.close();

        }
    }

    @Override
    public boolean getPasswordByUsername(String username, String password) {
        try {
            String passwordUser = (String) session.getParameterByParameter(User.class, "password", "username", username);
            if (password.equals(passwordUser))
                return true;
            else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            //session.close();
        }
    }

    @Override
    public Object getParameterByParameter(String parameter, String byParameter, Object value) {
        try {
            return session.getParameterByParameter(User.class, parameter, byParameter, value);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            //session.close();
        }
    }

    @Override
    public boolean updateUser(User user) {
        try {
            return session.update(user);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            //session.close();
        }
    }

    @Override
    public boolean updateByParameter(User user, String parameter, Object value) {
        try {
            return session.updateByParameter(user, parameter, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            //session.close();
        }
    }

    @Override
    public boolean updateUserCoinsByUsername(int userCoins, String userName) {
        try {
            return session.updateParameterByParameter(User.class, "coins", userCoins, "username", userName);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            //session.close();
        }
    }

    @Override
    public User updateUserParameters(User oldUser, User changed) {
        try {
            session.updateParameterByParameter(User.class, "name", changed.getName(), "name", oldUser.getName());
            session.updateParameterByParameter(User.class, "password", changed.getPassword(), "password", oldUser.getPassword());
            session.updateParameterByParameter(User.class, "username", changed.getUsername(), "username", oldUser.getUsername());
            session.updateParameterByParameter(User.class, "mail", changed.getMail(), "mail", oldUser.getMail());
            logger.info("Old Name: " + oldUser.getName());
            logger.info("New Name: " + changed.getName());
        }catch (Exception e){

        }finally {
            //session.close();
            return changed;
        }
    }

    @Override
    public boolean updateParameterByParameter(String parameter, Object parameterValue, String byParameter, Object byParameterValue) {
        try {
            return session.updateParameterByParameter(User.class, parameter, parameterValue, byParameter, byParameterValue);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            //session.close();
        }
    }

    /*@Override
    public Object getPassHash(String parameter) {
        try {
            return session.getHash(parameter);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            //session.close();

        }*/


}

