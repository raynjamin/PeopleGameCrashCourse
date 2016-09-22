package com.theironyard.charlotte.models;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.Iterator;
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

    public void changeTurn(Person a, Person b, String message) {
        if (a.isTurn()) {
            a.setOutput(message);
            a.setTurn(false);

            if (b != null) {
                b.setInput(message);
                b.setTurn(true);
            }
        }
    }

    public void receiveMessage(Session session, String message) {
        Iterator<Map.Entry<Session, Person>> iter = players.entrySet().iterator();

        while (iter.hasNext()) {
            Map.Entry<Session, Person> current = iter.next();

            if (current.getKey().equals(session)) {
                Map.Entry<Session, Person> nextGuy = null;

                while (iter.hasNext()) {
                    Map.Entry<Session, Person> temp = iter.next();

                    if (!temp.getValue().isHost()) {
                        nextGuy = temp;
                        break;
                    }
                }

                changeTurn(current.getValue(), nextGuy.getValue(), message);
                break;
            }
        }

        broadcastGameStatus();
    }

    public void endGame() {
        players.values().forEach(p -> {
            p.resetFields();
        });

        broadcastGameStatus();
    }

    public void setPlayer(Session session, Person player) {
        players.put(session, player);

        broadcastGameStatus();
    }

    public void startGame(Person actor) {
        if (actor.isHost()) {
            Optional<Person> person = players.values().stream().filter(p -> !p.isTurn() && !p.isHost()).findFirst();

            if (person.isPresent()) {
                person.get().setInput(actor.getOutput());
                person.get().setTurn(true);
            }

            broadcastGameStatus();
        }
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
