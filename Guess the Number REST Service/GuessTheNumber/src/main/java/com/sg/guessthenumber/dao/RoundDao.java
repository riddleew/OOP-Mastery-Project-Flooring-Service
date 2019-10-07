/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumber.dao;

import com.sg.guessthenumber.entity.Game;
import com.sg.guessthenumber.entity.Round;
import java.util.List;

/**
 *
 * @author riddl
 */

public interface RoundDao {
    
    List<Round> getAllRoundsByGameId(int gameId);
    Round getRoundById(int roundId);
    Round addRound(Round round);
}
