package edu.upc.dsa;

import edu.upc.dsa.models.Item;
import edu.upc.dsa.models.User;

import java.util.List;

public interface UserManager {
    public int userRegister(String id, String name, String lastName, String pass, String bornDate, String mail);
    public void userLogIn(String mail, String pass);
    public int itemBuyByUser(String user, String itemID);
    public List<String> getItemsByUser(String mail);
    public List<Item> getListObjectsByPrice();
    public void addItem(String name, double price, String desc);
    public List<User> getUserList();

}
