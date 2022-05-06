package edu.upc.dsa.models;

import java.util.LinkedList;
import java.util.List;

public class User {
    private String name;
    private String lastName;
    private String bornDate;
    private String password;
    private String mail;
    private String id;
    private double numCoins;
    private List<String> items;
    public User(String id, String name,String ln,String pass, String borndDate, String mail) {
        this.id = id;
        this.name = name;
        this.bornDate=borndDate;
        this.lastName = ln;
        this.mail = mail;
        this.numCoins = 50;
        this.password = pass;
        this.items = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBornDate() {
        return bornDate;
    }

    public void setBornDate(String bornDate) {
        this.bornDate = bornDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public double getNumCoins() {
        return numCoins;
    }

    public void setNumCoins(double numCoins) {
        this.numCoins = numCoins;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }
    public void addItem(String itemID){
        this.items.add(itemID);
    }
}
