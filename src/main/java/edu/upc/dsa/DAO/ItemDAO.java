package edu.upc.dsa.DAO;

import edu.upc.dsa.models.Inventory;
import edu.upc.dsa.models.Item;
import edu.upc.dsa.models.User;

import java.util.List;

public interface ItemDAO {

    boolean create(Item item);

    List<Item> getAllItems();
    Item getItemByName(String name);
    Item getByParameter(String parameter, Object value);
    //List<Item> getItemsPorPrecio();
    boolean existsItem(String name);

    boolean update(Item item);
    boolean updateByParameter(Item item, String parameter, Object value);


    void delete(Item item);
    boolean deleteByParameter(String parameter, Object value);
}
