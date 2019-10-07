/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumber.dao;

import com.sg.guessthenumber.TestApplicationConfiguration;
import com.sg.guessthenumber.entity.Game;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author EricR
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class GameDaoDBTest {

    @Autowired
    GameDao gameDao;

    public GameDaoDBTest() {
    }

    @Test
    public void testGetAllGames() {
        Game game = new Game();
        game.setAnswer("1234");
        game.setFinished(false);
        gameDao.addGame(game);

        Game game2 = new Game();
        game2.setAnswer("5678");
        game2.setFinished(false);
        gameDao.addGame(game2);

        List<Game> games = gameDao.getAllGames();

        assertEquals(2, games.size());
        assertTrue(games.contains(game));
        assertTrue(games.contains(game2));
    }

    @Test
    public void testAddGetGame() {
        Game game = new Game();
        game.setAnswer("1234");
        game.setFinished(false);
        game = gameDao.addGame(game);

        Game fromDao = gameDao.getGameById(game.getGameId());

        assertEquals(game, fromDao);
    }

    @Test
    public void testUpdateGame() {
        Game game = new Game();
        game.setAnswer("1234");
        game.setFinished(false);
        game = gameDao.addGame(game);

        Game fromDao = gameDao.getGameById(game.getGameId());

        assertEquals(game, fromDao);

        game.setFinished(true);

        gameDao.updateGame(game);

        assertNotEquals(game, fromDao);

        fromDao = gameDao.getGameById(game.getGameId());

        assertEquals(game, fromDao);
    }
}
