package com.teamkoala;

public abstract class View {
    abstract void showStartScreen();
    abstract int getNumberOfPlayers();
    abstract int displayTurnStart(int playerTurn, String playerHand);

}
