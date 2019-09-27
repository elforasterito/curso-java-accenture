package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Ship {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private String type;
    @Column(name = "shipLocation")
    @ElementCollection
    Set<String> shipLocation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gamePlayer_id")
    private GamePlayer gamePlayer;

    public Ship(){    }

    public Ship(GamePlayer gamePlayer, String type, Set<String> shipLocation){
        this.type = type;
        this.shipLocation = shipLocation;
        this.gamePlayer = gamePlayer;
    }

    public long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Set<String> getShipLocation() {
        return shipLocation;
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public Map<String, Object> ShipDTO() {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("type", this.getType());
        dto.put("shipLocations", this.getShipLocation());
        return dto;
    }
}
