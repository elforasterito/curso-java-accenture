package com.codeoftheweb.salvo.models;

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

    @OneToMany(mappedBy = "gamePlayer", fetch = FetchType.EAGER)
    private Set<Salvo> salvos;

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

    public Set<Salvo> getSalvos() {
        return salvos;
    }

    public Map<String, Object> makeGamePlayersDTO() {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", this.getId());
        dto.put("player", this.getPlayer().makePlayerDTO());
        return dto;
    }

    public Map<String, Object> makePlayerShipsDTO() {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", this.getGame().getId());
        dto.put("created", this.getGame().getCreationDate().toInstant().toEpochMilli());
        dto.put("gamePlayers", this.getGame().getAllGamePlayers());
        dto.put("ships", this.getAllShips());
        dto.put("salvoes", this.getAllSalvos(
                this.getGame().getGamePlayers().stream()
                        .flatMap(gamePlayer -> gamePlayer.getSalvos().stream())
                        .collect(Collectors.toSet())
        ));
        return dto;
    }

    public List<Map<String, Object>> getAllShips() {
        return this.getShips()
                .stream()
                .map(ship -> ship.ShipDTO())
                .collect(Collectors.toList());
    }
    public List<Map<String, Object>> getAllSalvos(Set<Salvo> salvos) {
        return salvos
                .stream()
                .map(salvo -> salvo.makeSalvoDTO())
                .collect(Collectors.toList());
    }
}