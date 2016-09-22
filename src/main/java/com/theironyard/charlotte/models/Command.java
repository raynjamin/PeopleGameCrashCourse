package com.theironyard.charlotte.models;

/**
 * Created by Ben on 9/21/16.
 */
public class Command {
    public enum CommandType {
        MESSAGE,
        REGISTER,
        START_GAME,
        END_GAME
    }

    private Person actor;
    private CommandType command;

    public Person getActor() {
        return actor;
    }

    public void setActor(Person actor) {
        this.actor = actor;
    }

    public CommandType getCommand() {
        return command;
    }

    public void setCommand(CommandType command) {
        this.command = command;
    }
}
