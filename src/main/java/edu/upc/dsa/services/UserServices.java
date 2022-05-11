package edu.upc.dsa.services;




import edu.upc.dsa.UserManager;
import edu.upc.dsa.UserManagerImpl;
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

    private UserManager manager;


    public UserServices() {
        this.manager = UserManagerImpl.getInstance();
        if (manager.userListSize() == 0) {
            User u1 = this.manager.addUser(new User("20297698P", "Esther", "12345", "EstheMC", "esther@gmail.com"));
            User u2 = this.manager.addUser(new User("08979711H", "Óscar", "Abcd", "ÓscarPL", "oscar@gmail.com"));

            Item i1 = new Item("Espada", "Para atacar", 50);
            Item i2 = new Item("Llave", "Abre una puerta", 100);

            u1.addItem(i1);
            u2.addItem(i2);
        }
    }

    //Login de usuario
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
    }

    //Registro de usuario
    @POST
    @ApiOperation(value = "Registrar nuevo usuario", notes = "Nombre y password")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class),
            @ApiResponse(code = 500, message = "Validation Error")
    })
    @Path("/añadir")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(User u) {
        User user = new User(u.getName(), u.getPassword(), u.getMail(), u.getUsername(), u.getId());
        if (user.getUsername().equals("") || user.getPassword().equals("")) {
            return Response.status(500).build();
        }
        for (User us : this.manager.getAllUsers()) {
            if (us.getUsername().equals(user.getUsername())) {
                return Response.status(500).build();
            }
        }
        user.setMail(u.getMail());
        user.setName(u.getName());
        user.setId(u.getId());
        this.manager.addUser(user);
        return Response.status(200).entity(user).build();
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
       List<User> users = this.manager.getAllUsers();
       GenericEntity<List<User>> entity = new GenericEntity<List<User>>(users){};
       return Response.status(201).entity(entity).build();
    }

    //Get lista de items de un usuario
    //Falta acabar de implementarla
    @GET
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
    }
    //Eliminar un usuario
    @DELETE
    @ApiOperation(value = "Delete a user", notes = "Eliminar usuario por username")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/delete/{username}")
    public Response deleteUser(@PathParam("username") String username) {

        User user = this.manager.getUser(username);
        if(user.getUsername() != null){
            manager.deleteUser(username);
            return Response.status(200).entity(user).build();
        }
        return Response.status(404).build();
    }

    @PUT
    @ApiOperation(value = "Update a user", notes = "Updatear un usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "Username no encontrado")
    })
    @Path("/update/{username}")
    public Response changePassword(User u) {
        User user = this.manager.changePassword(u);
        if(user.getUsername() == null){
            return Response.status(404).build();
        }
        else {
            return Response.status(201).build();
        }
    }

}
