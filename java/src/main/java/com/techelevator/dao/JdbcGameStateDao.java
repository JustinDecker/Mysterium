package com.techelevator.dao;

import com.techelevator.model.GameStateDTO;
import com.techelevator.model.Prediction;
import com.techelevator.model.Psychic;
import com.techelevator.model.Vision;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcGameStateDao implements GameStateDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcGameStateDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public GameStateDTO getGameStateByUsername(String username) {
        
        String sql = "select games.game_id, night, phase, users.username, players.player_id, psychic_level, remaining_guesses, investigation_phase, current_guess, vision_player.vision_id, visions.img_url " +
                "from games " +
                "join players on players.game_id = games.game_id " +
                "join users on users.user_id = players.player_id " +
                "join vision_player on vision_player.player_id = players.player_id " +
                "join visions on vision_player.vision_id = visions.vision_id " +
                "where players.role = 'psychic' and games.game_id in ( " +
                "select games.game_id " +
                "from games " +
                "join players on players.game_id = games.game_id " +
                "join users on users.user_id = players.player_id " +
                "where users.username = ? " +
                ") " +
                "order by player_id";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
        
        return mapRowSetToGameStateDTO(results);
    }

    private GameStateDTO mapRowSetToGameStateDTO(SqlRowSet rs){
        GameStateDTO gameStateDTO = new GameStateDTO();
        List<Psychic> psychicList = new ArrayList<>();

        int lastPsychicId = 0;
        int psychicIndex = 0;
        int currentId = 0;
        Psychic psychic = new Psychic();
        while(rs.next()) {
            currentId = rs.getInt("player_id");
            int phase = rs.getInt("phase");
            if(currentId != lastPsychicId){
                //new psychic found, populate into Psychic psychic
                if(lastPsychicId != 0){
                    gameStateDTO.addPsychicToList(psychic);
                }
                psychic = new Psychic();
                lastPsychicId = currentId;
                psychic.setPlayerId(currentId);
                psychic.setUsername(rs.getString("username"));
                psychic.setPsychicLevel(rs.getInt("psychic_level"));
                psychic.setRemainingGuesses(rs.getInt("remaining_guesses"));
                psychic.setInvestigationPhase(rs.getInt("investigation_phase"));
            }

            if (phase >= 4){
                //get the visions
                psychic.addVisionToList(new Vision(rs.getInt("vision_id"), "player", rs.getString("img_url")));
                if(phase >= 5){
                    psychic.setCurrentGuess(rs.getInt("current_guess"));
                }
            }
            // to make a GameStateDTO you must make a psychic
            // to make a Psychic you must make a List<Vision> and List<Prediction>
            // but you only need visions if phase >= 4
            // and you only need current_guess if phase >= 5
            // and you only need predictions if phase == 6 
            // you can check gameStateDTO.getPhase() to find out
        }
        gameStateDTO.addPsychicToList(psychic);
        rs.last();
        gameStateDTO.setGameId(rs.getInt("game_id"));
        gameStateDTO.setNight(rs.getInt("night"));
        gameStateDTO.setPhase(rs.getInt("phase"));
        if(gameStateDTO.getPhase() == 6){
            //get the predictions
            gameStateDTO = getPredictions(gameStateDTO);
        }
        return gameStateDTO;
    }
    
    private GameStateDTO getPredictions(GameStateDTO gameStateDTO){
        String sql = "select player_prediction.player_id, foreign_player_id, prediction " +
                "from player_prediction " +
                "join players on players.player_id = player_prediction.player_id " +
                "join games on players.game_id = games.game_id " +
                "where games.game_id = ? " +
                "order by player_id";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, gameStateDTO.getGameId());

        while(results.next()){
            Prediction prediction = new Prediction(results.getInt("player_id"), results.getInt("foreign_player_id"), results.getBoolean("prediction"));
            //idk, add it to the psychic it belongs to from here
            //for each over the psychics
            for(Psychic psychic : gameStateDTO.getPsychicList()){
                if(prediction.getPlayerId() == psychic.getPlayerId()){
                    psychic.addPredictionToList(prediction);
                    break;
                }
            }
        }
        
        return gameStateDTO;
    }
}
