package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.Model.Game.Table;

public interface GUIManager {
    public void editMessage(String message);
    public void showPattern(int ID);
    public void updateTable(Table table);
}
