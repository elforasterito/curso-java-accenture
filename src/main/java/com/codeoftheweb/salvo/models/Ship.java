package com.codeoftheweb.salvo.models;

import com.codeoftheweb.salvo.models.GamePlayer;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

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
        dto.put("locations", this.getShipLocation());
        return dto;
    }

}
