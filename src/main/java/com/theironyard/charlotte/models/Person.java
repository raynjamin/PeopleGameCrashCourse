package com.theironyard.charlotte.models;

/**
 * Created by Ben on 9/21/16.
 */
public class Person {
    private static int lastId = 0;
    private int id;
    private String username;
    private String input;
    private String output;
    private boolean host;
    private boolean turn;

    public Person() {
        id = lastId++;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public boolean isHost() {
        return host;
    }

    public void setHost(boolean host) {
        this.host = host;
    }

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }
}
