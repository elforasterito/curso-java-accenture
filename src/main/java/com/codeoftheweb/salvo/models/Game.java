package com.codeoftheweb.salvo.models;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.Authentication;


import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @OneToMany(mappedBy = "game", fetch = FetchType.EAGER)
    Set<GamePlayer> gamePlayers;

    @OneToMany(mappedBy = "game", fetch = FetchType.EAGER)
    Set<Score> scores;

    private Date creationDate;
    public Game() { }
    public Game(Date creationDate){
        this.creationDate = creationDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public long getId() {
        return id;
    }

    public Set<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }

    public Set<Score> getScores() {
        return scores;
    }

    public Map<String, Object> gameDTO(){
        Map<String, Object> dto = new LinkedHashMap<String, Object>();

            dto.put("id", this.getId());
            dto.put("created", this.getCreationDate().toInstant().toEpochMilli());
            dto.put("gamePlayers", this.getAllGamePlayers());
            dto.put("scores", this.getAllScores());
            return dto;
    }
    public List<Map<String, Object>> getAllGamePlayers() {
        return this.getGamePlayers()
                .stream()
                .map(gamePlayer -> gamePlayer.makeGamePlayersDTO())
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> getAllScores() {
        return this.getScores()
                .stream()
                .map(score -> score.makeScoresDTO())
                .collect(Collectors.toList());
    }
}
