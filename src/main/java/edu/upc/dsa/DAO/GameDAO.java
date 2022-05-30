package edu.upc.dsa.DAO;

import edu.upc.dsa.models.User;
import edu.upc.dsa.models.Game;

import java.util.List;

public interface GameDAO {

    boolean addGame(Game game);

    List<Game> getAllGames();
    //List<Game> getAllGamesByUserName(String username);
    //List<Game> orderGamesByPoints();

    //Game getGameByUsername(String username);
    //Game getByParameter(String parameter, Object value);

    //boolean delete(Game game);

}
