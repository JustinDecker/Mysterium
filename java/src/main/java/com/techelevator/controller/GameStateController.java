package com.techelevator.controller;

import com.techelevator.dao.GameStateDao;
import com.techelevator.model.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;

@RestController
@CrossOrigin
public class GameStateController {

    private GameStateDao gameStateDao;

    public GameStateController(GameStateDao gameStateDao) {

        this.gameStateDao = gameStateDao;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/game", method = RequestMethod.GET)
    public GameStateDTO getGameStateByGameId(Principal principal){
        return gameStateDao.getGameStateByUsername(principal.getName());
    }
}