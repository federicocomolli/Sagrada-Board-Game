package it.polimi.ingsw.Model.Cards.toolcard;


import it.polimi.ingsw.Model.Game.*;
import it.polimi.ingsw.Model.effects.Effect;
import it.polimi.ingsw.exceptions.ImpossibleMoveException;
import it.polimi.ingsw.exceptions.NotValidInputException;
import it.polimi.ingsw.exceptions.RollBackException;

import java.io.Serializable;
import java.util.ArrayList;

import static it.polimi.ingsw.Model.Game.Game.INVALID_MOVE_BY_PLAYER;

public class ToolCard implements Serializable {

    private static final int INITIAL_PRICE = 1;
    private static final int ORDINARY_PRICE = 2;
    private int ID;
    private String name;
    private String description;
    private int numOfTokens;
    private int price;
    private ArrayList<Integer> usableInTurns;
    private ArrayList<Integer> movesLeft;
    private ArrayList<Effect> effects;
    private ArrayList<Integer> stops;
    private int index;
    private int stopIndex;
    private int stop;

    public ToolCard(int ID, String name, String description, ArrayList<Integer> usableInTurns, ArrayList<Integer> movesLeft) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.numOfTokens = 0;
        this.price = INITIAL_PRICE;
        this.usableInTurns = usableInTurns;
        this.movesLeft = movesLeft;
        this.effects = new ArrayList<>();
        this.index = 0;
        this.stopIndex = 0;
        this.stop = 0;
        this.stops = new ArrayList<>();
    }

    public void useToolCard(ArrayList<String> commands, Table table, Round round) {
        boolean result = true;
        while (index<effects.size() && index<stop && result) {
            try {
                if (effects.get(index).applyEffect(commands, table, round)) {
                    if (index +1<effects.size()) {
                        effects.get(index +1).explainEffect(table, round);
                    }
                    index++;
                } else {
                    effects.get(index).explainEffect(table, round);
                    result = false;
                }
            } catch (RollBackException e) {
                table.notifyObservers(INVALID_MOVE_BY_PLAYER + round.getCurrentPlayer().getName() +
                        ":\n" + e.getMessage());
                index--;
                effects.get(index).explainEffect(table, round);
                result = false;
            } catch (ImpossibleMoveException e) {
                table.notifyObservers(INVALID_MOVE_BY_PLAYER + round.getCurrentPlayer().getName() +
                        ":\n" + e.getMessage());
                index = effects.size();
            }
        }

        if (result == false) {
            return;
        }
        if (index == stop) {
            nextStop();
        }
        if(index == effects.size()) {
            resetToolCard(table, round);
        }
    }

    public void resetToolCard(Table table, Round round) {
        index = 0;
        stopIndex = 0;
        stop = 0;
        round.getPlayerTurn(0).setToolCardActive(false);
        round.getPlayerTurn(0).setToolCardUsed(true);
        round.getPlayerTurn(0).getOriginCoordinates().clear();
        round.getPlayerTurn(0).setColorRoundTrack(null);
        table.removeActiveToolCard();
        Dice dice = table.getActiveDice();
        table.setActiveDice(null);
        if (dice != null) {
            table.getDraftPool().add(dice);
        }
    }

    /*private void nextStop() {
        while (stopIndex<effects.size() && !effects.get(stopIndex).isStop()) {
            stopIndex++;
        }
        if (stopIndex<effects.size()) {
            stopIndex++;
            stop = stopIndex-1;
        } else {
            stop = stopIndex;
        }
    }*/

    public void nextStop() {
        if (stopIndex+1<stops.size()) {
            stopIndex++;
            stop = stops.get(stopIndex);
        }
    }

    public void generateStops(ArrayList<Effect> effects) {
        this.stops = new ArrayList<>();
        for (int i = 0; i < effects.size(); i++) {
            if (effects.get(i).isStop()) {
                this.stops.add(i);
            }
        }
        this.stops.add(effects.size());
        this.stopIndex = 0;
        if (!stops.isEmpty()) {
        this.stop = this.stops.get(0);
        }
    }

    public int getCommandsLenght() {
        return effects.get(index).getCommandsLenght();
    }

    public ArrayList<Effect> getEffects() {
        return effects;
    }

    public void setEffects(ArrayList<Effect> effects) {
        this.effects = effects;
        generateStops(effects);
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumOfTokens() {
        return numOfTokens;
    }

    public void setNumOfTokens(int numOfTokens) {
        this.numOfTokens = numOfTokens;
    }

    public ArrayList<Integer> getUsableInTurns() {
        return usableInTurns;
    }

    public void setUsableInTurns(ArrayList<Integer> usableInTurns) {
        this.usableInTurns = usableInTurns;
    }

    public ArrayList<Integer> getMovesLeft() {
        return movesLeft;
    }

    public void setMovesLeft(ArrayList<Integer> movesLeft) {
        this.movesLeft = movesLeft;
    }

    public boolean payTokens(Player player) {
        if (numOfTokens>0) {
            price = ORDINARY_PRICE;
        }
        if (player.getNumOfTokens() >= price) {
            numOfTokens += price;
            try {
                player.setNumOfTokens(player.getNumOfTokens()-price);
            } catch (NotValidInputException e) {

            }
            return true;
        }
        return false;
    }

    public boolean useAllowed(PlayerTurn turn) {
        return usableInTurns.contains(turn.getTurnNumber())
                && movesLeft.contains(turn.getMovesLeft())
                && !turn.isToolCardUsed();
    }

    @Override
    public String toString() {
        return  "\nID: " + ID + "\n" +
                "Name: " + name + "\n" +
                "Description: " + description + "\n" +
                "Number Of Tokens: " + numOfTokens + "\n" +
                "Price: " + price + "\n";
    }

    public void dump() {
        System.out.println(toString());
    }
}
