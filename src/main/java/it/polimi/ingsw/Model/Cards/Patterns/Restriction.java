package it.polimi.ingsw.Model.Cards.Patterns;

import it.polimi.ingsw.Model.Game.Color;

import java.io.Serializable;

public enum Restriction implements Serializable {
    WHITE("\u001B[37m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    ONE("\u2680"),
    TWO("\u2681"),
    THREE("\u2682"),
    FOUR("\u2683"),
    FIVE("\u2684"),
    SIX("\u2685");

    public static final String RESET = "\u001B[0m";
    private String escape;

    Restriction(String escape) {
        this.escape = escape;
    }

    public String escape() {
        return escape;
    }


    public boolean compare(String value) {
        return this.escape.equals(value);
    }

    public boolean compare(Color color) {
        return this.escape.equals(color.escape());
    }


}
