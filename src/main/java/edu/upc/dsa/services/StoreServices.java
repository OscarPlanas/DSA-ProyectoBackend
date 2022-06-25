package edu.upc.dsa.services;


import edu.upc.dsa.DAO.*;
import edu.upc.dsa.UserManager;
import edu.upc.dsa.UserManagerImpl;
import edu.upc.dsa.ItemManagerImpl;
import edu.upc.dsa.ItemManager;
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

@Api(value = "/store", description = "Endpoint to User Service")
@Path("/store")
public class StoreServices {

    final static Logger logger = Logger.getLogger(SessionImpl.class);

    private ItemDAO manager;
    private UserDAO userManager;
    private InventoryDAO inventoryManager;
    public StoreServices() {
        this.manager = ItemDAOImpl.getInstance();
        this.userManager = UserDAOImpl.getInstance();
        this.inventoryManager = InventoryDAOImpl.getInstance();

    }

    //Get de la lista de items
    @GET
    @ApiOperation(value = "Get de todos los items", notes = "Get todos los items")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Item.class, responseContainer = "Lista"),
    })
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllItems() {
        List<Item> listaitems = this.manager.getAllItems();
        GenericEntity<List<Item>> entity = new GenericEntity<List<Item>>(listaitems){};
        return Response.status(201).entity(entity).build();
    }

    //Get de un item
    @PUT
    @ApiOperation(value = "Get un item", notes = "Get un item")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Item.class, responseContainer = "Item"),
    })
    @Path("/buy")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItem(Inventory i) {
        Item item = this.manager.getItemByName(i.getNameItem());
        User user = this.userManager.getUser(i.getUsername());
        int coinstotales = user.getCoins() - (item.getCoins()*i.getQuantItem());
        if(coinstotales < 0){
            return Response.status(413).build();
        }
        else {
            Inventory inven = this.inventoryManager.getByTwoParameters("username", i.getUsername(), "NameItem", item.getName());
            inven.setQuantItem(inven.getQuantItem() + i.getQuantItem());
            logger.info(inven.getUsername() + " " + inven.getNameItem() + " " + inven.getQuantItem());
            this.inventoryManager.updateItemQuantityByUserNameAndItemName(inven.getQuantItem(), i.getUsername(), i.getNameItem());
            this.userManager.updateUserCoinsByUsername(coinstotales, i.getUsername());
            return Response.status(201).build();
        }
    }

    @PUT
    @ApiOperation(value = "Use an item", notes = "Use an item")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Item.class, responseContainer = "Item"),
            @ApiResponse(code = 406, message = "Don't have any quantity"),
    })
    @Path("/use/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response useItem(@PathParam("username") String username, String nameitem) {

        Inventory in = this.inventoryManager.getByTwoParameters("username", username, "NameItem", nameitem);
        if(in.getQuantItem()>0){
            in.setQuantItem(in.getQuantItem() - 1);
            this.inventoryManager.updateItemQuantityByUserNameAndItemName(in.getQuantItem(), username, nameitem);
            return Response.status(201).build();
        }
        else{
            return Response.status(406).build();
        }

    }

    //Get de la lista de items ordenados por precio
    /*@GET
    @ApiOperation(value = "Get de todos los items por precio", notes = "Get todos los items por precio")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Item.class, responseContainer = "Lista"),
            @ApiResponse(code = 404, message = "Lista no encontrada")
    })
    @Path("/getOrdenado")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItemsPorPrecio() {
        List<Item> listaitemsprecio = this.manager.getItemsPorPrecio();
        GenericEntity<List<Item>> entity = new GenericEntity<List<Item>>(listaitemsprecio){};

        if(listaitemsprecio.size() > 0){
            return Response.status(201).entity(entity).build();
        }
        return Response.status(404).entity(entity).build();
    }*/
}
