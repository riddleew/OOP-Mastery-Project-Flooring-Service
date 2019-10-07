/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumber.dao;

import com.sg.guessthenumber.entity.Game;
import java.util.List;

/**
 *
 * @author riddl
 */
public interface GameDao {
    List<Game> getAllGames();
    Game getGameById(int gameId);
    Game addGame(Game game);
    void updateGame(Game round);
}
