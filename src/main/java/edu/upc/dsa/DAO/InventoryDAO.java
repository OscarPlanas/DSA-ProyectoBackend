package edu.upc.dsa.DAO;
import edu.upc.dsa.models.Game;
import edu.upc.dsa.models.Inventory;
import edu.upc.dsa.models.Item;
import edu.upc.dsa.models.User;
import io.swagger.models.auth.In;
import java.util.List;

public interface InventoryDAO {

    //Función create inventario
    boolean createInventory(Inventory inventory);

    //Gets
    List<Inventory> getInventoryListByUserName(String userName);
    Inventory getByParameter(String parameter, Object value);
    Inventory getInventoryByUserNameAndItemName(String userName, String itemName);
    Inventory getByTwoParameters(String firstParameter, Object firstValue, String secondParameter, Object secondValue);
    Object getParameterByParameter(String parameter, String byParameter, Object value);

    //Añadimos inventario
    void addInventory(String username, String NameItem);

    boolean existsInventoryByUserNameAndItemName(String userName, String itemName);

    //Updates
    boolean update(Inventory inventory);
    boolean updateByParameter(Inventory inventory, String parameter, Object value);
    boolean updateUsername(String oldName, User newUser);
    boolean updateByTwoParameters(Inventory inventory, String firstParameter, Object firstValue, String secondParameter, Object secondValue);
    boolean updateParameterByParameter(String parameter, Object parameterValue, String byParameter, Object byParameterValue);
    boolean updateParameterByTwoParameters(Class theClass, String parameter, Object parameterValue, String byFirstParameter, Object byFirstParameterValue, String bySecondParameter, Object bySecondParameterValue);
    boolean updateItemQuantityByUserNameAndItemName(int itemQuantity, String userName, String itemName);

    //Deletes
    void delete(Inventory inventory);
    boolean deleteInventoryByUsername(String userName);
    boolean deleteByParameter(String parameter, Object value);
    boolean deleteByTwoParameters(String firstParameter, Object firstValue, String secondParameter, Object secondValue);
}
