package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Entity
public class Salvo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gamePlayer_id")
    private GamePlayer gamePlayer;

    private int turn;

    @Column(name="salvoLocation")
    @ElementCollection
    Set<String> salvoLocation;

    public Salvo(){    }

    public Salvo(int turn, GamePlayer gamePlayer, Set<String> salvoLocation){
        this.turn = turn;
        this.gamePlayer = gamePlayer;
        this.salvoLocation = salvoLocation;
    }

    public long getId() {
        return id;
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public int getTurn() {
        return turn;
    }

    public Set<String> getSalvoLocation() {
        return salvoLocation;
    }

    public Map<String, Object> makeSalvoDTO() {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("turn", this.getTurn());
        dto.put("player", this.getGamePlayer().getPlayer().getId());
        dto.put("salvoLocations", this.getSalvoLocation());
        return dto;
    }

}
