package edu.upc.dsa;

import edu.upc.dsa.models.*;
import org.apache.log4j.Logger;

import java.lang.Object;
import java.util.*;

public class UserManagerImpl implements UserManager{
    private List<User> userList;
    private List<Item> itemList;
    private static UserManagerImpl instance;
    //logs
    final static Logger logger = Logger.getLogger(UserManagerImpl.class);

    private UserManagerImpl() {
        userList = new LinkedList<>();
        itemList = new LinkedList<>();
    }
    public static UserManagerImpl getInstance(){
        //logger.info(instance);
        if(instance == null)
            instance = new UserManagerImpl();
        //logger.info(instance);
        return instance;
    }
    public void addItem(String name, double price, String desc){
        itemList.add(new Item(price,name,desc));
    }

    public List<Item> getListObjectsByPrice() {
        itemList.sort(Comparator.comparingDouble(Item::getCoins).reversed());
        return itemList;
    }

    @Override
    public int userRegister(String id, String name, String lastName, String pass, String bornDate, String mail){
        logger.info("New User Reister. ID: " + id + " -Name: " + name + " " + lastName + " -Pass: " + pass + " -Mail: " + mail);
        if(searchUser(mail) == -1){
            logger.error("User with mail already exists.");
            return -1;
        }

        userList.add( new User(id,name,lastName,pass,bornDate,mail));
        logger.info("User " + id + " registered");
        return 0;
    }

    @Override
    public void userLogIn(String mail, String pass) {
        logger.info("User log-in with mail: " + mail + " Password: " + pass);
        for (User u:userList) {
            if(u.getMail().equals(mail) && u.getPassword().equals(pass)){
                logger.info("User " + u.getName() + " loged-in");
                return;
            }
        }
        logger.warn("User not registered");
    }

    @Override
    public List<User> getUserList() {
        userList.sort(Comparator.comparing(User::getLastName).thenComparing(User::getName));
        return userList;
    }
    private int searchUser(String mail){
        for (User u:userList) {
            if(u.getMail().equals(mail))
                return userList.indexOf(u);
        }
        return -1;
    }

    private Item searchItem(String itemID){
        for (Item i:itemList) {
            if(i.getName().equals(itemID))
                return i;
        }
        return null;
    }
    @Override
    public int itemBuyByUser(String mail, String itemID) {
        int index = searchUser(mail);
        Item i = searchItem(itemID);

        if(userList.get(index) == null){
            logger.error("User "+ mail + " not registered");
            return -1;
        }

        if(userList.get(index).getNumCoins() < i.getCoins()){
            logger.warn("Insuficient coins to buy " + i.getName());
            return -2;
        }

        else
            userList.get(index).addItem(itemID);
        logger.info("User " + mail + " bought: " + itemID);
        return 0;
    }

    @Override
    public List<String> getItemsByUser(String mail) {
        return userList.get(searchUser(mail)).getItems();
    }
}
