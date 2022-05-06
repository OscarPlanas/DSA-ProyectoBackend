package edu.upc.dsa;

import edu.upc.dsa.models.*;
import org.junit.*;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class UserManagerTest {
    UserManagerImpl manager;

    @Before
    public void setUp(){
        manager = UserManagerImpl.getInstance();
        manager.userRegister("41536799R","Miquel","Garcia Salva","1234t","04/03/1998","miquel@gmail.com");
        manager.userRegister("56743298Y","Barto", "Garcia Salva", "6543r", "08/11/2001","barto@gmail.com");
        manager.addItem("Cola",2.2,"Refresc");
        manager.addItem("Pan",1,"Barra de pa");
        manager.addItem("Empanada",3,"Empanada de carn");
        manager.addItem("Tarta",3.5,"Tarta de chocolata");
    }

    @Test
    public void testUserItems(){
        List<User> users = manager.getUserList();
        List<Item> items = manager.getListObjectsByPrice();
        assertEquals("Barto",users.get(0).getName());
        assertEquals("Miquel", users.get(1).getName());
        manager.itemBuyByUser("barto@gmail.com","Cola");
        manager.itemBuyByUser("miqeul@gmail.com","Tarta");
        manager.itemBuyByUser("barto@gmail.com","Pan");
        assertEquals(2,manager.getItemsByUser("barto@gmail.com").size());
    }
}
