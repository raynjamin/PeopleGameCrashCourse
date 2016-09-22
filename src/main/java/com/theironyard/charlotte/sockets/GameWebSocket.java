package com.theironyard.charlotte.sockets;

import com.google.gson.Gson;
import com.theironyard.charlotte.models.Command;
import com.theironyard.charlotte.models.Game;
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
    }

    @OnWebSocketClose
    public void closed(Session session, int statusCode, String reason) {
        // deal with this later
        currentGame.removePlayer(session);
    }

    @OnWebSocketMessage
    public void message(Session session, String message) throws IOException {
        System.out.printf("Received Message: %s\n", message);

        Command c = new Gson().fromJson(message, Command.class);

        switch (c.getCommand()) {
            case REGISTER:
                currentGame.setPlayer(session, c.getActor());
                break;
            case START_GAME:
                currentGame.startGame(c.getActor());
                break;
            case END_GAME:
                 currentGame.endGame();
                break;
            case MESSAGE:
                currentGame.receiveMessage(session, message);
                break;
        }
    }
}
