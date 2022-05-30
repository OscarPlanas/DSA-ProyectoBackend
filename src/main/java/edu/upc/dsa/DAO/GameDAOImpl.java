package edu.upc.dsa.DAO;

import edu.upc.dsa.UserManagerImpl;
import edu.upc.dsa.models.Game;
import edu.upc.dsa.models.User;
import edu.upc.dsa.DAO.FactorySession;

import java.sql.Connection;
import java.util.List;

public class GameDAOImpl implements GameDAO{
    private static GameDAO instance;
    private final Session session;

    private GameDAOImpl(){
        session = FactorySession.openSession();
    }

    public static GameDAO getInstance(){
        if (instance==null){
            instance = new GameDAOImpl();
        }
        return instance;
    }
    @Override
    public boolean addGame(Game game) {
        return session.create(game);
    }
    @Override
    public List<Game> getAllGames(){
        return ((List) session.queryObjects(Game.class));
    }

}
