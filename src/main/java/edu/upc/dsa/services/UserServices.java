package edu.upc.dsa.services;




import edu.upc.dsa.DAO.UserDAO;
import edu.upc.dsa.DAO.UserDAOImpl;
import edu.upc.dsa.models.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/user", description = "Endpoint to User Service")
@Path("/user")
public class UserServices {

    private UserDAO userDAO;


    public UserServices() {
        this.userDAO = UserDAOImpl.getInstance();
        /*if (userDAO.userListSize() == 0) {
            this.userDAO.addUser("Esther", "EstheMC", "12345", "esther@gmail.com");
            this.userDAO.addUser("Oscar", "Oscar", "123", "oscar@gmail.com");

            Item i1 = new Item("Espada", "Para atacar", 50);
            Item i2 = new Item("Llave", "Abre una puerta", 100);

            //u1.addItem(i1);
            //u2.addItem(i2);
        }*/
    }
    /*//Login de usuario
    @POST
    @ApiOperation(value = "Login usuario", notes = "Password")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class),
            @ApiResponse(code = 500, message = "Validation Error"),
            @ApiResponse(code = 404, message = "User not found")

    })

   @Path("/User/{username}/{password}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response userLogIn(@PathParam("username") String username, @PathParam("password") String password) {
        User u = this.manager.getUser(username);

        if (u == null) {
            return Response.status(404).build();
        } else if (u.getPassword().equals(password)) {
            this.manager.userLogIn(username, password);
            return Response.status(201).entity(u).build();
        } else
            return Response.status(500).build();
    }*/
    //Login de usuario
    @POST
    @ApiOperation(value = "Login usuario", notes = "Password")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class),
            @ApiResponse(code = 404, message = "User not found")

    })

    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response userLogIn(CredentialsLogIn credLogin) {
        User u = this.userDAO.getUser(credLogin.getUsername());

        if (u != null) {
            return Response.status(201).entity(u).build();
        } else
            return Response.status(404).build();
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
            u.setMail(u.getMail());
            u.setName(u.getName());
            this.userDAO.addUser(u.getName(), u.getUsername(), u.getPassword(), u.getMail());
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
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/delete/{username}")
    public Response deleteUser(@PathParam("username") String username) {

        User user = this.userDAO.getUser(username);
        if(user.getUsername() != null){
            userDAO.deleteUser(username);
            return Response.status(200).entity(user).build();
        }
        return Response.status(404).build();
    }

    /*@PUT
    @ApiOperation(value = "Update a user", notes = "Updatear un usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "Username no encontrado")
    })
    @Path("/update/{username}")
    public Response changeName(User u) {
        User user = this.userDAO.changeName(u);
        if(user.getUsername() == null){
            return Response.status(404).build();
        }
        else {
            return Response.status(201).build();
        }
    }*/



}
