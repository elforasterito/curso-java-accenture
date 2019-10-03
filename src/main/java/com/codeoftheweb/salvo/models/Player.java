package com.codeoftheweb.salvo.models;

import org.hibernate.annotations.GenericGenerator;


import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Entity
public class Player {
    private String userName;
    private String password;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER)
    Set<GamePlayer> gamePlayers;

    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER)
    Set<Score> scores;

    public Player() { }

    public Player(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public Set<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }

    public Set<Score> getScores() {
        return scores;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String, Object> makePlayerDTO(){
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", this.getId());
        dto.put("email", this.getUserName());
        return dto;
    }

    public Map<String, Object> getPlayerScoreDTO(){
        Map<String, Object> dto = new LinkedHashMap<>();
        Map<String, Object> score = new LinkedHashMap<>();
        dto.put("id", this.getId());
        dto.put("email", this.getUserName());
        dto.put("score", score);
            score.put("total", this.getTotalScore());
            score.put("won", this.getAllWon());
            score.put("lost", this.getAllLost());
            score.put("tied", this.getAllTied());
        return dto;
    }
    public Double getTotalScore(){
        return this.getAllWon() * 1.0D + this.getAllTied() * 0.5D;
    }

    public Long getAllWon(){
        return this.getScores().stream().filter(score -> score.getScore() == 1.0D).count();
    }

    public Long getAllLost(){
        return this.getScores().stream().filter(score -> score.getScore() == 0).count();
    }

    public Long getAllTied(){
        return this.getScores().stream().filter(score -> score.getScore() == 0.5D).count();
    }


}
