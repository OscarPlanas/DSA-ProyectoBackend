package edu.upc.dsa.models;

public class Inventory {
    String username;
    String NameItem;
    int quantItem;

    public Inventory() {}

    public Inventory(String username, String NameItem, int quantItem) {
        this.username = username;
        this.NameItem = NameItem;
        this.quantItem = quantItem;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNameItem() {
        return NameItem;
    }

    public void setNameItem(String nameItem) {
        NameItem = nameItem;
    }

    public int getQuantItem() {
        return quantItem;
    }

    public void setQuantItem(int quantItem) {
        this.quantItem = quantItem;
    }
}
