package edu.upc.dsa;

import edu.upc.dsa.models.Item;
import edu.upc.dsa.models.User;

import java.util.List;

public interface UserManager {


    //Registro usuario
    public User addUser(User user);
    User getUser(String username);
    //Eliminar usuario
    public void deleteUser(String username);
    //Login usuario
    public void userLogIn(String username, String pass);
    public List<User> getLoggedUsers();
    public void logOutUser(String username);

    //Ver usuario
    public int userListSize();
    public List<User> getAllUsers();

    //Funciones de Item
    public Item addItem(Item item);
    public Item crearItem(String name, String descripcion, int precio);
    public Item getItem(String name);
    public int itemListSize();

    public List<Item> getItemListUser(String username);




}
