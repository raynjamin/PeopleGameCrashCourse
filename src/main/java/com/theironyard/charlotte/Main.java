package com.theironyard.charlotte;

import com.theironyard.charlotte.sockets.GameWebSocket;

import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {
        port(Integer.valueOf(System.getenv("PORT"));

        webSocket("/people-game", GameWebSocket.class);

        init();

        before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));
    }
}
