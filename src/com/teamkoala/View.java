package com.teamkoala;

public interface View {
    void showStartScreen();
    int getNumberOfPlayers();
    int displayTurnStart(int player, String hand);
}
