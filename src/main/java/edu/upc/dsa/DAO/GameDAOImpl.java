package edu.upc.dsa.DAO;

import edu.upc.dsa.UserManagerImpl;
import edu.upc.dsa.models.Item;
import edu.upc.dsa.models.Game;
import edu.upc.dsa.models.User;
import edu.upc.dsa.models.Inventory;

import edu.upc.dsa.DAO.Session;
import edu.upc.dsa.DAO.FactorySession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
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
        try {
            return session.create(game);

        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Game> getAllGames(){
        return ((List) session.queryObjects(Game.class));
    }

    @Override
    public List<Game> getAllGamesByUsername(String userName) {
        return ((List) session.queryObjectsByParameter(Game.class, "userName", userName));
    }

    public List<Game> orderGamesByPoints() {
        return ((List) session.orderObjectsByParameter(Game.class,"points"));
    }
    @Override
    public Game getByParameter(String parameter, Object value) {
        return ((Game) session.getByParameter(Game.class, parameter, value));
    }
    @Override
    public Game getGameByUsername(String userName) {
        return ((Game) session.getByParameter(Game.class, "userName", userName));
    }

    @Override
    public boolean existsUsername(String userName) {
        if (session.getParameterByParameter(Game.class, "userName", "userName", userName) == null)
            return false;
        else
            return true;
    }

    @Override
    public boolean update(Game game) {
        return session.update(game);
    }

    @Override
    public boolean updateByParameter(Game game, String parameter, Object value) {
        return session.updateByParameter(game, parameter, value);
    }

    @Override
    public boolean updateUsername(String oldName, User newUser) {
        return session.updateParameterByParameter(Game.class,"userName", newUser.getName(), "userName", oldName);
    }

    @Override
    public boolean updateEnergyByUsername(int energy, String userName) {
        return session.updateParameterByParameter(Game.class, "energy", energy, "userName", userName);
    }

    @Override
    public boolean updatePointsByUsername(int points, String userName) {
        return session.updateParameterByParameter(Game.class, "points", points, "userName", userName);
    }


    @Override
    public boolean updateParameterByParameter(String parameter, Object parameterValue, String byParameter, Object byParameterValue) {
        return session.updateParameterByParameter(Game.class, parameter, parameterValue, byParameter, byParameterValue);
    }

    @Override
    public void delete(Game game) {
        session.delete(game);
    }

    @Override
    public boolean deleteByParameter(String parameter, Object value) {
        return session.deleteByParameter(Game.class, parameter, value);
    }

}
