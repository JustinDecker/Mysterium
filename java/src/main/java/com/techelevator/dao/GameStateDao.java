package com.techelevator.dao;

import com.techelevator.model.GameStateDTO;

public interface GameStateDao {

    GameStateDTO getGameStateByUsername(String username);
}
