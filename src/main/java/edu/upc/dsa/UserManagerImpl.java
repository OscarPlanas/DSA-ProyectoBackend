package edu.upc.dsa;

import edu.upc.dsa.models.*;
import org.apache.log4j.Logger;

import java.lang.Object;
import java.util.*;

public class UserManagerImpl implements UserManager {
    private List<User> userList;
    private List<Item> itemList;
    private List<User> onlineUsersList;
    private HashMap<String, User> mapUser;
    private static UserManagerImpl instance;
    //logs
    final static Logger logger = Logger.getLogger(UserManagerImpl.class);

    private UserManagerImpl() {
        this.userList = new LinkedList<>();
        this.itemList = new LinkedList<>();
        this.onlineUsersList = new LinkedList<>();
    }

    //Singleton
    public static UserManagerImpl getInstance() {
        //logger.info(instance);
        if (instance == null)
            instance = new UserManagerImpl();
        //logger.info(instance);
        return instance;
    }

    //Añadir usuario
    @Override
    public User addUser(User user) {
        String username = user.getUsername();
        for (User u : this.userList) {
            if (u.getUsername().equals(username)) {
                logger.info("Usuario " + username + " encontrado");
                return null;
            }
        }
        logger.info("Nuevo usuario: " + user);
        this.userList.add(user);
        logger.info("Nuevo usuario añadido: " + user);
        return user;
    }

    @Override
    public User getUser(String username) {
        logger.info("Usuario: " + username);
        for (User user : this.userList) {
            if (user.getUsername().equals((username))) {
                logger.info("Usuario " + username + " encontrado");
                return user;
            }
        }
        logger.info("Usuario " + username + " no encontrado");
        return null;
    }

    @Override
    public void userLogIn(String username, String pass) {
        User u = this.getUser(username);
        if (u == null) {
            logger.info("Este usuario no existe");
        } else if (u.getPassword().equals(pass)) {
            this.onlineUsersList.add(u);
            logger.info("Usuario " + username + " ha podido entrar correctamente");
        } else {
            logger.info("Contraseña incorrecta");
        }
    }

    @Override
    public List<User> getLoggedUsers() {
        return onlineUsersList;
    }

    @Override
    public void logOutUser(String username) {
        User u = this.getUser(username);
        if (u == null) {
            logger.info("El usuario no existe");
        } else {
            this.onlineUsersList.remove(u);
            logger.info("Usuario " + username + " ha salido correctamente");
        }
    }

    private int searchUser(String name) {
        for (User u : userList) {
            if (u.getName().equals(name))
                return userList.indexOf(u);
        }
        return -1;
    }

    @Override
    public void deleteUser(String username) {
        User user = this.getUser(username);
        if (user == null) {
            logger.info("Usuario: " + username + " no encontrado");
        } else {
            this.userList.remove(user);
            logger.info("Usuario " + username + " eliminado");
        }
    }
    @Override
    public List<User> getAllUsers(){
        return this.userList;
    }

    @Override
    public int userListSize() {
        return this.userList.size();
    }

    @Override
    public Item addItem(Item item){
        logger.info("Item nuevo "+ item.getName() +": " + item.getDescription());
        this.itemList.add(item);
        logger.info("Nuevo item añadido: "+item);
        return item;
    }
    @Override
    public Item crearItem(String name, String descripcion, int precio){
        return this.addItem(new Item(name,descripcion,precio));
    }
    @Override
    public Item getItem(String name){
        for(Item item: this.itemList){
            if(item.getName().equals(name)){
                logger.info("Item "+name+ " Encontrado");
                return item;
            }
        }
        logger.info("Item no encontrado");
        return null;
    }
    @Override
    public int itemListSize(){
        return this.itemList.size();
    }
    @Override
    public List<Item> getItemListUser(String username){
        User user = this.getUser(username);
        if(user == null){
            logger.info("Lista de items de "+user.getName());
            List<Item> list = user.getItemList();
            return list;
        }
        else{
            logger.info("Lista no encontrada");
            return null;
        }
    }
}

