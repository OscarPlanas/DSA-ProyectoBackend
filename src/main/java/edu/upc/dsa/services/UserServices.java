package edu.upc.dsa.services;



import edu.upc.dsa.DAO.*;
import edu.upc.dsa.UserManager;
import edu.upc.dsa.UserManagerImpl;

import io.swagger.annotations.*;


import edu.upc.dsa.DAO.ItemDAO;
import edu.upc.dsa.DAO.UserDAO;
import edu.upc.dsa.DAO.UserDAOImpl;
import edu.upc.dsa.DAO.InventoryDAO;
import edu.upc.dsa.models.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/user", description = "Endpoint to User Service")
@Path("/user")
public class UserServices {
    final static Logger logger = Logger.getLogger(SessionImpl.class);

    private UserDAO userDAO;
    private InventoryDAO inventoryDAO;
    private ItemDAO itemDAO;
    private GameDAO gameDAO;

    public UserServices() {
        this.userDAO = UserDAOImpl.getInstance();
        this.inventoryDAO = InventoryDAOImpl.getInstance();
        this.itemDAO = ItemDAOImpl.getInstance();
        this.gameDAO = GameDAOImpl.getInstance();

        /*if (userDAO.userListSize() == 0) {
            this.userDAO.addUser("Esther", "EstheMC", "12345", "esther@gmail.com");
            this.userDAO.addUser("Oscar", "Oscar", "123", "oscar@gmail.com");

            Item i1 = new Item("Espada", "Para atacar", 50);
            Item i2 = new Item("Llave", "Abre una puerta", 100);

            //u1.addItem(i1);
            //u2.addItem(i2);
        }*/
    }

    //Login de usuario
    @POST
    @ApiOperation(value = "Login usuario", notes = "Password")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class),
            @ApiResponse(code = 404, message = "User not found"),
            @ApiResponse(code = 407, message = "Password not correct"),
            @ApiResponse(code = 501, message = "Incorrect inputs")
    })
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response userLogIn(CredentialsLogIn credLogin) {
        String username = credLogin.getUsername();
        logger.info("-------------- " + username + " ++++++++++++++");
        User u = this.userDAO.getUser(username);
        logger.info(u.getUsername() + " useruseruser");
        logger.info(u.getPassword() + " paaaaaaaaaaaaaaaaaaaaaaasss");

        logger.info(userDAO.existsusername(credLogin.getUsername())+" kjanakjgnkajsgnkangan");
        logger.info(userDAO.getPasswordByUsername(credLogin.getUsername(), credLogin.getPassword())+ " credpass");
        String hasheada = userDAO.getPassHash(credLogin.getPassword());
        if (credLogin.getUsername().isEmpty() || credLogin.getPassword().isEmpty()){
            return Response.status(501).build();
        }

        if (userDAO.existsusername(credLogin.getUsername())) {
            //if(userDAO.getPasswordByUsername(credLogin.getUsername(), credLogin.getPassword())) {
            if(userDAO.getPasswordByUsername(credLogin.getUsername(), hasheada)) {
                return Response.status(201).entity(u).build();
            }
            else {
                return Response.status(407).build();
            }
        }  else {
            return Response.status(404).build();
        }
    }

    //Registro de usuario
    @POST
    @ApiOperation(value = "Registrar nuevo usuario", notes = "Nombre y password")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class),
            @ApiResponse(code = 500, message = "Validation Error"),
            @ApiResponse(code = 409, message = "User Already Exist")
    })
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(User u) {
        if (u.getUsername().isEmpty() || u.getPassword().isEmpty()) {
            return Response.status(500).build();
        }else if(userDAO.existsusername(u.getUsername()) || userDAO.existsmail(u.getMail())){
            return Response.status(409).build();
        }else{
            List<Item> listitem = itemDAO.getAllItems();
            for(Item it : listitem)
            {
                    inventoryDAO.addInventory(u.getUsername(), it.getName());
            }
            //u.setMail(u.getMail());
            //u.setName(u.getName());
            String hasheada = userDAO.getPassHash(u.getPassword());

            this.userDAO.addUser(u.getName(), u.getUsername(), hasheada, u.getMail());
            return Response.status(201).entity(u).build();
        }
    }

    //Get de la lista de usuarios
    @GET
    @ApiOperation(value = "Get de todos los usuarios", notes = "Get todos los usuarios")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class, responseContainer = "Lista"),
    })
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
       List<User> users = this.userDAO.getAllUsers();
       GenericEntity<List<User>> entity = new GenericEntity<List<User>>(users){};
       return Response.status(201).entity(entity).build();
    }

    //Get Coins
    @GET
    @ApiOperation(value = "Get coins", notes = "Get Coins")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class, responseContainer = "Lista"),
    })
    @Path("/{username}/coins")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getcoins(@PathParam("username") String username) {
        Object oc = this.userDAO.getParameterByParameter("coins", "username", username);
        return Response.status(201).entity(oc).build();
    }

    //Get de un usuario
    @GET
    @ApiOperation(value = "Get de un usuario", notes = "Get un usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class, responseContainer = "Lista"),
    })
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("username") String username) {
        User user = this.userDAO.getUser(username);
        GenericEntity<User> entity = new GenericEntity<User>(user){};
        return Response.status(201).entity(entity).build();
    }

    //Get lista de items de un usuario
    //Falta acabar de implementarla
    /*@GET

    @ApiOperation(value = "Get Item list", notes = "Get Item por username")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class, responseContainer = "Lista"),
            @ApiResponse(code = 404, message = "Username no encontrado"),

    })
    @Path("/item/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItemListUser(@PathParam("username") String username) {
        User user = this.manager.getUser(username);
        if (user == null){
            return Response.status(404).build();
        }
        else{
            List<Item> itemList = user.getItemList();
            GenericEntity<List<Item>> entity = new GenericEntity<List<Item>>(itemList){};
            return Response.status(201).entity(entity).build();
        }
    }*/
    //Eliminar un usuario
    @DELETE
    @ApiOperation(value = "Delete a user", notes = "Eliminar usuario por username")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/delete/{username}")
    public Response deleteUser(@PathParam("username") String username) {

        User user = userDAO.getUser(username);
        if(userDAO.existsusername(username)){
            userDAO.deleteUserByUsername(username);
            inventoryDAO.deleteInventoryByUsername(username);
            return Response.status(201).entity(user).build();
        }
        return Response.status(404).build();
    }

    //Updateamos un usuario
    @PUT
    @ApiOperation(value = "Update a user", notes = "Updatear un usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class),
            @ApiResponse(code = 404, message = "Usuario no encontrado"),
            @ApiResponse(code = 406, message = "Usuario en uso"),
            @ApiResponse(code = 409, message = "Mail en uso"),
            @ApiResponse(code = 500, message = "Datos erroneos")
    })
    @Path("/update/{username}")
    public Response updateUser(@PathParam("username") String oldUsername, RegisterCredentials rCr) {

        User oldUser = userDAO.getUser(oldUsername);
        User changedUser = new User(rCr.getName(), rCr.getUsername(), rCr.getPassword(), rCr.getMail(), oldUser.getCoins());

        if (!userDAO.existsusername(oldUsername)) {
            return Response.status(404).build();
        } else {
            if (rCr.getName().isEmpty() && rCr.getPassword().isEmpty() && rCr.getUsername().isEmpty() && rCr.getMail().isEmpty())
                return Response.status(500).build();
            else {
                if (!oldUsername.equals(rCr.getUsername()) && userDAO.existsusername(rCr.getUsername()))
                    return Response.status(406).build();
                if (!oldUser.getMail().equals(rCr.getMail()) && userDAO.existsmail(rCr.getMail()))
                    return Response.status(409).build();
                else {
                    this.inventoryDAO.updateParameterByParameter("username", rCr.getUsername(), "username", oldUsername);
                    this.gameDAO.updateParameterByParameter("username", rCr.getUsername(), "username", oldUsername);

                    User newUser = userDAO.updateUserParameters(oldUser, changedUser);

                    return Response.status(201).entity(newUser).build();
                }

            }
        }
    }



    //Get de la cantidad de un objeto de un usuario
    @GET
    @ApiOperation(value = "Get de la cantidad de un objeto de un usuario", notes = "Get de la cantidad de un objeto de un usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Inventory.class, responseContainer = "Lista"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/userInventoryList/{username}/{NameItem}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserInventoryList(@PathParam("username") String username, @PathParam("NameItem") String NameItem) {

        if (userDAO.existsusername(username)) {
            Inventory i = null;
            //List<Inventory> inventoryList = null;
            try {
                i = inventoryDAO.getByParameter("NameItem", NameItem);

              //inventoryList = inventoryDAO.getInventoryListByUserName(username);

            } catch (Throwable t) {
                t.printStackTrace();
            }
            i.getQuantItem();
            //GenericEntity<List<Inventory>> entity = new GenericEntity<List<Inventory>>(inventoryList) {
            return Response.status(201).entity(i).build();
        } else {
            return Response.status(404).build();
        }

    }



}
