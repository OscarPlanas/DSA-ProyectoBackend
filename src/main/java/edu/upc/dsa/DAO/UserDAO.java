package edu.upc.dsa.DAO;

import edu.upc.dsa.models.User;

import java.util.List;

public interface UserDAO {
    //Añadimos
    int addUser(String name, String username, String password, String email);

    //Gets
    List<User> getAllUsers();
    User getUser(String username);
    boolean getPasswordByUsername(String username, String password);
    Object getParameterByParameter(String parameter, String byParameter, Object value);

    //Size lista
    int userListSize();

    //Deletes
    void deleteUser(User user);
    boolean deleteUserByUsername(String username);
    boolean deleteByParameter(String parameter, Object value);

    //Updates
    boolean updateUser(User user);
    boolean updateByParameter(User user, String parameter, Object value);
    boolean updateUserCoinsByUsername(int userCoins, String username);
    User updateUserParameters(User oldUser, User changed);
    boolean updateParameterByParameter(String parameter, Object parameterValue, String byParameter, Object byParameterValue);

    //Existe usuario o mail
    boolean existsusername(String username);
    boolean existsmail(String email);

    //Hash para la contraseña
    String getPassHash(String parameter);

}
