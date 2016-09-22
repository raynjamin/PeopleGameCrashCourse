package com.theironyard.charlotte.models;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by Ben on 9/21/16.
 */
public class Game {
    private Map<Session, Person> players = new ConcurrentHashMap<>();

    public List<Person> playerStatus() {
        return players.values().stream().collect(Collectors.toList());
    }

    public void setPlayer(Session session, Person player) {
        players.put(session, player);

        broadcastGameStatus();
    }

    public void startGame() {
        Optional<Person> person = players.values().stream().filter(p -> !p.isTurn() && !p.isHost()).findFirst();

        if (person.isPresent()) {
            person.get().setTurn(true);
        }

        broadcastGameStatus();
    }

    private void broadcastGameStatus() {
        players.entrySet().forEach(e -> {
            Session session = e.getKey();

            try {
                session.getRemote().sendString(new Gson().toJson(playerStatus()));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}
