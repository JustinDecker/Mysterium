package com.techelevator.dao;

import com.techelevator.model.*;
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

        int lastPsychicId = 0;
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
            for(Psychic psychic : gameStateDTO.getPsychicList()){
                if(prediction.getPlayerId() == psychic.getPlayerId()){
                    psychic.addPredictionToList(prediction);
                    break;
                }
            }
        }
        
        return gameStateDTO;
    }

    public void startNewGame(int ghostId, int psychicOneId, int psychicTwoId, int psychicThreeId){
        String sql = "delete from vision_player; " +
                "delete from player_prediction; " +
                "delete from murders; " +
                "delete from players; " +
                "update game_card set zone = 'deck'; " +
                "update visions set zone = 'deck'; " +
                "update games set night = 0, phase = 0, number_of_psychics = 3; " +
                "insert into players (player_id, role, game_id, psychic_level, remaining_guesses, investigation_phase, current_guess) " +
                "values (?, 'ghost', 1, 0, 0, 0, -1), (?, 'psychic', 1, 0, 4, 0, -1), (?, 'psychic', 1, 0, 4, 0, -1), (?, 'psychic', 1, 0, 4, 0, -1); " +
                "update game_card set zone = 'play' " +
                "where game_card_id in (select game_card_id from game_card where card_type_id = 1 order by RANDOM() limit 6); " +
                "update game_card set zone = 'play' " +
                "where game_card_id in (select game_card_id from game_card where card_type_id = 2 order by RANDOM() limit 6); " +
                "update game_card set zone = 'play' " +
                "where game_card_id in (select game_card_id from game_card where card_type_id = 3 order by RANDOM() limit 6);";

        jdbcTemplate.execute(sql);
    }
    
    public List<GameCard> generateGameCards(){
        String sql = "update game_card " +
                "set location = 'play' " +
                "where game_card_id in ( " +
                "select game_card_id " +
                "from game_card " +
                "where card_type_id = 1 " +
                "order by random() " +
                "limit 6); " +
                "update game_card " +
                "set location = 'play' " +
                "where game_card_id in ( " +
                "select game_card_id " +
                "from game_card " +
                "where card_type_id = 2 " +
                "order by random() " +
                "limit 6); " +
                "update game_card " +
                "set location = 'play' " +
                "where game_card_id in ( " +
                "select game_card_id " +
                "from game_card " +
                "where card_type_id = 3 " +
                "order by random() " +
                "limit 6);" +
                "select game_card_id, card_type_id, img_url from game_card " +
                "where location = 'play' " +
                "order by game_card_id;";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
        List<GameCard> gameCards = new ArrayList<>();
        while(rs.next()){
            gameCards.add(new GameCard(rs.getInt("game_card_id"), rs.getInt("card_type_id"), rs.getString("img_url")));
        }
        return gameCards;
    }
}
