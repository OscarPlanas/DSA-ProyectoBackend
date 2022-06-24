package edu.upc.dsa.models;

public class Inventory {
    int idUser;
    String NameItem;
    int quantItem;

    public Inventory() {}

    public Inventory(int idUser, String NameItem, int quantItem) {
        this.idUser = idUser;
        this.NameItem = NameItem;
        this.quantItem = quantItem;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
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
