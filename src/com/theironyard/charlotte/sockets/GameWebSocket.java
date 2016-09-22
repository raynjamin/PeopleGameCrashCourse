package com.theironyard.charlotte.sockets;

import com.google.gson.Gson;
import com.theironyard.charlotte.models.Command;
import com.theironyard.charlotte.models.Game;
import com.theironyard.charlotte.models.Person;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;

@WebSocket
public class GameWebSocket {
    private static Game currentGame = new Game();

    @OnWebSocketConnect
    public void connected(Session session) {
        currentGame.setPlayer(session, new Person());
    }

    @OnWebSocketClose
    public void closed(Session session, int statusCode, String reason) {
        // deal with this later
    }

    @OnWebSocketMessage
    public void message(Session session, String message) throws IOException {
        Command c = new Gson().fromJson(message, Command.class);

        currentGame.setPlayer(session, c.getActor());

        if (c.getActor().isHost() && c.getCommand() == Command.CommandType.START_GAME) {
            currentGame.startGame();
        }
    }
}
