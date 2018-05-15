package it.polimi.ingsw.Model.Game;

import it.polimi.ingsw.Model.Game.Dice;
import it.polimi.ingsw.Model.Game.DiceBag;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.Model.Game.Round;
import it.polimi.ingsw.Model.Cards.Patterns.PatternDeck;
import it.polimi.ingsw.exceptions.NotValidInputException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TestRound {
    @Test
    void TwoTurnsInARowTest(){
        ArrayList<Player> players= new ArrayList<>();
        int index=0;
        DiceBag diceBag= new DiceBag();
        ArrayList<ArrayList<Dice>> roundTrack= new ArrayList<>(10);
        PatternDeck deck = new PatternDeck();
        for(int i=0; i<4; i++){
            players.add(new Player("Player"+(i+1)));
            try {
                players.get(i).setPatternCard(deck.getPatternCard(i+1));
            } catch (NotValidInputException e) {
                e.printStackTrace();
            }
        }
        Round round=new Round(players, index, diceBag, roundTrack);

        try {
            round.twoTurnsInARow(players.get(0));
        } catch (NotValidInputException e) {
            e.printStackTrace();
        }
        assertEquals(players.get(0), round.getPlayerTurn().get(0).getPlayer());
        assertEquals(players.get(0), round.getPlayerTurn().get(1).getPlayer());
        try {
            round.twoTurnsInARow(players.get(1));
        } catch (NotValidInputException e) {
            e.printStackTrace();
        }
        assertEquals(players.get(1), round.getPlayerTurn().get(2).getPlayer());
        assertEquals(players.get(1), round.getPlayerTurn().get(3).getPlayer());
        try {
            round.twoTurnsInARow(players.get(2));
        } catch (NotValidInputException e) {
            e.printStackTrace();
        }
        assertEquals(players.get(2), round.getPlayerTurn().get(4).getPlayer());
        assertEquals(players.get(2), round.getPlayerTurn().get(5).getPlayer());

        assertEquals(players.get(3), round.getPlayerTurn().get(6).getPlayer());
        assertEquals(players.get(3), round.getPlayerTurn().get(7).getPlayer());
        try {
            round.twoTurnsInARow(players.get(3));
        } catch (NotValidInputException e) {
            e.printStackTrace();
        }

        String temp = round.toString();
        round.dump();
        assertEquals(players.get(3), round.getPlayerTurn().get(6).getPlayer());
        assertEquals(players.get(3), round.getPlayerTurn().get(7).getPlayer());
    }
}