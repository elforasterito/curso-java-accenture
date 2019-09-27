package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;


import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity

public class GamePlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private Date creationDate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="player_id")
    private Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="game_id")
    private Game game;

    @OneToMany(mappedBy = "gamePlayer", fetch = FetchType.EAGER)
    private Set<Ship> ships;

    public GamePlayer() {     }

    public GamePlayer(Date creationDate, Game game, Player player) {
        this.creationDate = creationDate;
        this.player = player;
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public long getId() {
        return id;
    }

    public Game getGame() {
        return game;
    }

    public Set<Ship> getShips() {
        return ships;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Map<String, Object> makeGamePlayersDTO() {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", this.getId());
        dto.put("player", this.getPlayer().makePlayerDTO());
        dto.put("ships", this.getAllShips(getShips()));
        return dto;
    }

     public Map<String, Object> makeShipDTO() {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", this.getGame().getId());
        dto.put("created", this.getGame().getCreationDate().toInstant().toEpochMilli());
        dto.put("gamePlayers", this.getGame().getAllGamePlayersDTO(getGame().getGamePlayers()));
        dto.put("ships", this.getAllShips(getShips()));
        return dto;
    }

    public List<Map<String, Object>> getAllShips(Set<Ship> ships) {
        return ships
                .stream()
                .map(ship -> ship.makeShipDTO())
                .collect(Collectors.toList());
    }
}
