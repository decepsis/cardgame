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
    public void displayTurnStart(int player, String hand, PlayingCards lastDiscard) {}

    @Override
    public int drawCard(boolean stockHasCards, boolean discardHasCards) {
        throw new IllegalStateException("TestView does not use this method");
    }

    @Override
    public int askReplace(PlayingCards drawn) {
        throw new IllegalStateException("TestView does not use this method");
    }

    @Override
    public boolean askKeep(PlayingCards drawn) {
        throw new IllegalStateException("TestView does not use this method");
    }

    @Override
    public int askFlip(boolean wasFlipped) {
        throw new IllegalStateException("TestView does not use this method");
    }

    @Override
    public void displayScoreboard() {

    }
}
