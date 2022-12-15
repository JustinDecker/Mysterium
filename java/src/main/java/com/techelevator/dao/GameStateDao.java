package com.techelevator.dao;

import com.techelevator.model.GameCard;
import com.techelevator.model.GameStateDTO;

import java.util.List;

public interface GameStateDao {

    GameStateDTO getGameStateByUsername(String username);

    List<GameCard> generateGameCards();

    void startNewGame(int ghostId, int psychicOneId, int psychicTwoId, int psychicThreeId);
}
