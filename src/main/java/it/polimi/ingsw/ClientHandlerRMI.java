package it.polimi.ingsw;

import it.polimi.ingsw.Model.Cards.Patterns.PatternCard;
import it.polimi.ingsw.Model.Cards.PrivateObjectives.PrivateObjectiveCard;
import it.polimi.ingsw.Model.Game.Table;
import it.polimi.ingsw.client.RMI.RMIClientInterface;
import it.polimi.ingsw.exceptions.NetworkErrorException;
import it.polimi.ingsw.observer.Observable;

import java.rmi.RemoteException;

public class ClientHandlerRMI implements ClientHandler {
    private RMIClientInterface rmiClientInterface;

    public ClientHandlerRMI(RMIClientInterface rmiClientInterface) {
        this.rmiClientInterface = rmiClientInterface;
    }

    @Override
    public void displayGame(Table table) throws NetworkErrorException {
        try {
            rmiClientInterface.displayGame(table);
        } catch (RemoteException e) {
            throw new NetworkErrorException();
        }
    }

    @Override
    public void sendMessage(String message) throws NetworkErrorException {
        try {
            rmiClientInterface.send(message);
        } catch (RemoteException e) {
            throw new NetworkErrorException();
        }
    }

    @Override
    public void sendGameMessage(String message) throws NetworkErrorException {
        try {
            rmiClientInterface.send(message);
        } catch (RemoteException e) {
            throw new NetworkErrorException();
        }
    }

    @Override
    public void sendActiveTableElement(String element) throws NetworkErrorException {
        try {
            rmiClientInterface.sendActiveTableElement(element);
        } catch (RemoteException e) {
            throw new NetworkErrorException();
        }
    }

    @Override
    public void sendPatternCard(PatternCard patternCard) throws NetworkErrorException {
        try {
            rmiClientInterface.sendPatternCard(patternCard);
        } catch (RemoteException e) {
            throw new NetworkErrorException();
        }
    }

    @Override
    public void sendPrivateObjectiveCard(PrivateObjectiveCard privateObjectiveCard) throws NetworkErrorException {
        try {
            rmiClientInterface.sendPrivateObjectiveCard(privateObjectiveCard);
        } catch (RemoteException e) {
            throw new NetworkErrorException();
        }
    }

    @Override
    public void update(Observable o, String message) throws NetworkErrorException {
        try {
            rmiClientInterface.update(o, message);
        } catch (RemoteException e) {
            throw new NetworkErrorException();
        }
    }

    @Override
    public void check() throws NetworkErrorException {
        try {
            rmiClientInterface.checkConnection();
        } catch (RemoteException e) {
            throw new NetworkErrorException();
        }
    }
}
