package edu.upc.dsa.DAO;

import edu.upc.dsa.models.User;

import java.util.List;

public interface UserDAO {
    public int addUser(String name, String username, String password, String email);
    public List<User> getAllUsers();
    public User getUser(String username);
    public int userListSize();
    public void deleteUser(String username);
    public boolean existsusername(String username);
    public boolean existsmail(String email);

}
