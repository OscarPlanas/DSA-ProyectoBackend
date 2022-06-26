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
import sun.awt.dnd.SunDropTargetContextPeer;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

@Api(value = "/game", description = "Endpoint to Game Service")
@Path("/game")
public class GameServices {
    private GameDAO gameDAO;
    private UserDAO userDAO;

    public GameServices() {
        this.gameDAO = GameDAOImpl.getInstance();
        this.userDAO = UserDAOImpl.getInstance();
    }

    //Añadimos partida
    @POST
    @ApiOperation(value = "New game", notes = "New game")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Game.class),
            @ApiResponse(code = 411, message = "User does not exist")
    })
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addGame(Game g) {
        Game game = new Game(g.getUsername(), g.getCoins(), g.getPoints());

        if (userDAO.existsusername(g.getUsername())){
            gameDAO.addGame(game);
            return Response.status(201).entity(game).build();
        } else {
            return Response.status(411).build();
        }

    }


    //Get all para todos los jugadores ordenados(ranking)
    @GET
    @ApiOperation(value = "Get ranking", notes = "Get ranking")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Game.class, responseContainer = "Lista"),
            @ApiResponse(code = 404, message = "Lista no encontrada")
    })
    @Path("/rankingAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRankingAll() {
        //List<Game> list = this.gameDAO.getAllGames();
        List<Game> list = this.gameDAO.orderGamesByPoints();

        GenericEntity<List<Game>> entity = new GenericEntity<List<Game>>(list){};

        if(list.size() > 0){
            return Response.status(201).entity(entity).build();
        }
        return Response.status(404).entity(entity).build();
    }
    //Get de partidas de un usuario
    @GET
    @ApiOperation(value = "Get games of a user", notes = "Get games of a user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Game.class, responseContainer = "Lista"),
            @ApiResponse(code = 404, message = "Lista no encontrada")
    })
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRankingUser(@PathParam("username") String username) {

        List<Game> list = this.gameDAO.getAllGamesByUsername(username);
        
        Collections.sort(list, new Comparator<Game>() {
            @Override
            public int compare(Game g1, Game g2) {
                return Integer.compare(g1.getCoins(), g2.getCoins());
            }
        });
        Collections.reverse(list);

        GenericEntity<List<Game>> entity = new GenericEntity<List<Game>>(list){};

        if(list.size() > 0){
            return Response.status(201).entity(entity).build();
        }
        return Response.status(404).entity(entity).build();
    }
}
