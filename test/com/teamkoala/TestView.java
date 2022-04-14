package com.teamkoala;

class TestView implements View {
    private final int numPlayers;

    public TestView(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    @Override
    public void showStartScreen() {}

    @Override
    public int getNumberOfPlayers() {
        return numPlayers;
    }

    @Override
    public int displayTurnStart(int player, String hand) {
        throw new IllegalStateException("Not implemented");
    }
}
