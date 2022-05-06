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

@Api(value = "/")
@Path("/")
public class UserServices {

    private UserManager manager;


    public UserServices() {
        this.manager = UserManagerImpl.getInstance();
    }

    @GET
    @ApiOperation(value = "get all Users ordered by last name and name")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class, responseContainer="List"),
    })
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {

        List<User> users = this.manager.getUserList();

        GenericEntity<List<User>> entity = new GenericEntity<List<User>>(users) {};
        return Response.status(201).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "get all Items ordered by price")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Item.class, responseContainer="List"),
    })
    @Path("/items")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItems() {

        List<Item> items = this.manager.getListObjectsByPrice();

        GenericEntity<List<Item>> entity = new GenericEntity<List<Item>>(items) {};
        return Response.status(201).entity(entity).build();
    }

    @POST
    @ApiOperation(value = "register a User")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response=User.class),
            @ApiResponse(code = 500, message = "Validation Error")

    })

    @Path("/user")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newUser(User u) {
        int res = manager.userRegister(u.getId(),u.getName(),u.getLastName(),u.getPassword(),u.getBornDate(),u.getMail());
        if (res == -1)  return Response.status(500).entity(u).build();
        return Response.status(201).entity(u).build();
    }

    @POST
    @ApiOperation(value = "add Item")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response=Item.class),

    })

    @Path("/item")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newItem(Item item) {
        manager.addItem(item.getName(),item.getCoins(), item.getDescription());
        return Response.status(201).entity(item).build();
    }

    @PUT
    @ApiOperation(value = "User Buys a Item")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "User not found"),
            @ApiResponse(code = 403, message = "User needs more coins")
    })
    @Path("/sells")
    public Response buyItem(String mail, String itemID) {

        int res = manager.itemBuyByUser(mail,itemID);

        if (res == -1) return Response.status(404).build();
        if (res==-2) return Response.status(403).build();
        return Response.status(201).build();
    }

    @GET
    @ApiOperation(value = "get Items by user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Item.class, responseContainer="List"),
    })
    @Path("/items")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItemsByUser(User u) {

        List<String> items = this.manager.getItemsByUser(u.getMail());

        GenericEntity<List<String>> entity = new GenericEntity<List<String>>(items) {};
        return Response.status(201).entity(entity).build();
    }

}
