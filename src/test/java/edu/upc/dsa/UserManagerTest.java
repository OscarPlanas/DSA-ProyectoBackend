package edu.upc.dsa;

import edu.upc.dsa.models.*;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.*;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class UserManagerTest {
    private static Logger logger = Logger.getLogger(UserManagerTest.class);
    UserManagerImpl manager;
    User user;
    @Before
    public void setUp() {
        this.manager = UserManagerImpl.getInstance();
        //Configuring Log4j
        PropertyConfigurator.configure("src/main/resources/log4j.properties");
        logger.debug("Debug Test Message!");
        logger.info("Info Test Message!");
        logger.warn("Warning Test Message!");
        logger.error("Error Test Message!");
        //Initialzing Test User
        this.manager.addUser(new User("9876g", "Renuka", "Renu12","Renujeje" ,"renuka@gmail.com"));

    }
    @Test
    public void loginTest(){
        manager.userLogIn("EstheMC", "12345");
        manager.userLogIn("Renujeje", "Renu12");
    }
    @Test
    public void RegistroTest(){
        this.manager.addUser(new User("20297698P", "Esther", "12345", "EstheMC", "esther@gmail.com"));
    }
   /* @Before
    public void setUp(){
        manager = UserManagerImpl.getInstance();
        manager.addUser("41536799R","Miquel","1234t");
        manager.addUser("56743298Y","Barto", "6543r");
    }

    @Test
    public void addUserTest(){
        //Adding a user to the GameManager
        //manager.addUser(user.getId(),user.getName(),user.getPassword());
        //Adding a second user to the GameManager
        manager.addUser("abc","Toni","Oller");

    }

    @After
    public void tearDown() {
        manager.getInstance().clear();
        manager.delete();
    }*/
}
