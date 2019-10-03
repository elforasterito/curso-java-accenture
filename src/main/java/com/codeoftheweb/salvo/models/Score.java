package com.codeoftheweb.salvo.models;

import com.codeoftheweb.salvo.models.Game;
import com.codeoftheweb.salvo.models.Player;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private double score;
    private Date finishDate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id")
    private Player player;

    public Score(){    }

    public Score(Game game, Player player, double score, Date finishDate){
        this.game = game;
        this.player = player;
        this.score = score;
        this.finishDate = finishDate;
    }

    public long getId() {
        return id;
    }

    public double getScore() {
        return score;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public Game getGame() {
        return game;
    }

    public Player getPlayer() {
        return player;
    }

    public Map<String, Object> makeScoresDTO(){
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("player", this.player.getId());
        dto.put("score", this.getScore());
        dto.put("finishDate", this.getFinishDate().toInstant().toEpochMilli());
        return dto;
    }
}
