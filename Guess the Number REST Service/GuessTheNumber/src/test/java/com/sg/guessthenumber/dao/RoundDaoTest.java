/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumber.dao;

import com.sg.guessthenumber.TestApplicationConfiguration;
import com.sg.guessthenumber.entity.Game;
import com.sg.guessthenumber.entity.Round;
import com.sg.guessthenumber.entity.Round;
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
public class RoundDaoTest {
    
    @Autowired
    RoundDao roundDao;
    
    @Autowired
    GameDao gameDao;
    
    public RoundDaoTest() {
    }
    
    @Test
    public void testAddGetGetAll() {
        int gameId = 1;
        
        Game game = new Game();
        game.setAnswer("5678");
        game.setFinished(false);
        game = gameDao.addGame(game);
        
        Round round = new Round();
        round.setGuess("1234");
        round.setResult("e:0:p:0");
        round.setGameId(gameId);
        roundDao.addRound(round);

        Round round2 = new Round();
        round2.setGuess("5678");
        round2.setResult("e:4:p:0");
        round2.setGameId(gameId);
        roundDao.addRound(round2);

        List<Round> rounds = roundDao.getAllRoundsByGameId(gameId);

        assertEquals(2, rounds.size());
        assertNotNull(round = roundDao.getRoundById(round.getRoundId()));
    }
}
