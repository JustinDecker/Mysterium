package com.techelevator.dao;

import com.techelevator.model.GameStateDTO;
import com.techelevator.model.Psychic;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;
import java.util.List;

public class JdbcGameStateDao implements GameStateDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcGameStateDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public GameStateDTO getGameStateByGameId(int gameId) {
        
        String sql = "select night, phase, players.player_id, psychic_level, guesses_left, investigation_phase, current_guess, vision_player.vision_id, visions.img_url " +
                "from games " +
                "join players on players.game_id = games.game_id " +
                "join vision_player on vision_player.player_id = players.player_id " +
                "join visions on vision_player.vision_id = visions.vision_id " +
                "where players.role = 'psychic' and games.game_id = ? " +
                "order by player_id";
        SqlRowSet visions = jdbcTemplate.queryForRowSet(sql, gameId);
        
        return mapRowSetToGameStateDTO(visions);
    }

    private GameStateDTO mapRowSetToGameStateDTO(SqlRowSet rs){
        if(rs.next()) {
            GameStateDTO gameStateDTO = new GameStateDTO();
            List<Psychic> psychicList = new ArrayList<>();
            
            gameStateDTO.setNight(rs.getInt("night"));
            gameStateDTO.setPhase(rs.getInt("phase"));
            
            Psychic psychic = new Psychic();
            
            // to make a GameStateDTO you must make a psychic
            // to make a Psychic you must make a List<Vision> and List<Prediction>
            // but you only need current_guess if phase >= 5
            // and you only need visions if phase >= 4
            // and you only need predictions if phase == 6 
            // you can check gameStateDTO.getPhase() to find out
            return gameStateDTO;
        }
        else {
            return null;
        }
    }
    
    private Psychic mapRowToPsychic(SqlRowSet rs){
        
        return null;
    }
}
