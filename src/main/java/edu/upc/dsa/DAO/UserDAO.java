package edu.upc.dsa.DAO;

import edu.upc.dsa.models.User;

import java.util.List;

public interface UserDAO {

    public int addUser(String name, String username, String password, String email);

    public List<User> getAllUsers();
    public User getUser(String username);
    boolean getPasswordByUsername(String username, String password);
    Object getParameterByParameter(String parameter, String byParameter, Object value);

    public int userListSize();

    void deleteUser(User user);
    boolean deleteUserByUsername(String username);
    boolean deleteByParameter(String parameter, Object value);

    boolean updateUser(User user);
    boolean updateByParameter(User user, String parameter, Object value);
    boolean updateUserCoinsByUsername(int userCoins, String username);
    User updateUserParameters(User oldUser, User changed);
    boolean updateParameterByParameter(String parameter, Object parameterValue, String byParameter, Object byParameterValue);

    public boolean existsusername(String username);
    public boolean existsmail(String email);

}
