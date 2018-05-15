package it.polimi.ingsw.Model.Game;

import it.polimi.ingsw.Model.Cards.Patterns.PatternDeck;
import it.polimi.ingsw.exceptions.*;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerTurn {
    private ArrayList<Dice> drawPool;
    private Player player;
    private ActionPerformed actionPerformed;

    public PlayerTurn(Player player, ArrayList<Dice> drawPool) {
        this.drawPool=drawPool;
        this.player = player;
        this.actionPerformed = ActionPerformed.NOTHING;
    }

    public Player getPlayer() {
        return player;
    }

    public void setActionPerformed(ActionPerformed actionPerformed) {
        this.actionPerformed = actionPerformed;
    }

    public ActionPerformed getActionPerformed() {
        return actionPerformed;
    }

    public void move() {

        Logger logger = Logger.getLogger(PatternDeck.class.getName());

        //Observer mossa normale
        int index=1;
        int row=1;
        int col=1;
        try {
            player.getWindowFrame().setDice(row, col, drawPool.remove(index));
        } catch (MismatchedRestrictionException e) {
            logger.log(Level.SEVERE, "context", e);
        } catch (InvalidNeighboursException e) {
            logger.log(Level.SEVERE, "context", e);
        } catch (InvalidFirstMoveException e) {
            logger.log(Level.SEVERE, "context", e);
        } catch (OccupiedCellException e) {
            logger.log(Level.SEVERE, "context", e);
        }

        //Observer mossa toolcard

        //Observer salta turno

        //Observer timer 90 secondi

    }

    public void payTokens(int toPay) throws  NotValidInputException{
        player.setNumOfTokens(player.getNumOfTokens()-toPay);
    }

    public void useToolCard() {

    }

    @Override
    public String toString(){
        String string="DrawPool:\n";
        if(drawPool==null)
            string=string.concat("NOT ADDED YET");
        else
            for(Dice dice: drawPool)
                string=string.concat(dice.toString());
        string=string.concat("\nPlayer: "+player.getName());
        string=string.concat("\nAction Performed: "+actionPerformed);
        return string;
    }

    public void dump(){
        System.out.println(toString());
    }


}