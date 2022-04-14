package com.teamkoala;

/**
 * Simple TestView that either does nothing or errors if a result is required.
 *
 * @version Lab 6
 */
class TestView implements View {
    @Override
    public void showStartScreen() {}

    @Override
    public int getNumberOfPlayers() {
       throw new IllegalStateException("TestView does not use this method");
    }

    @Override
    public int displayTurnStart(int player, String hand) {
        throw new IllegalStateException("TestView does not use this method");
    }
}
