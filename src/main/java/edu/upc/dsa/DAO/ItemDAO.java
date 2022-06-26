package edu.upc.dsa.DAO;

import edu.upc.dsa.models.Inventory;
import edu.upc.dsa.models.Item;
import edu.upc.dsa.models.User;

import java.util.List;

public interface ItemDAO {

    //Función create
    boolean create(Item item);

    //Gets
    List<Item> getAllItems();
    Item getItemByName(String name);
    Item getByParameter(String parameter, Object value);

    //Existe item
    boolean existsItem(String name);

    //Updates
    boolean update(Item item);
    boolean updateByParameter(Item item, String parameter, Object value);

    //Deletes
    void delete(Item item);
    boolean deleteByParameter(String parameter, Object value);
}
