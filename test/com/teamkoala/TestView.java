package com.teamkoala;

class TestView implements View {
    static class TraceException extends RuntimeException {}

    private final int numPlayers;
    private final boolean throwOnGame;

    public TestView(int numPlayers, boolean throwOnGame) {
        this.numPlayers = numPlayers;
        this.throwOnGame = throwOnGame;
    }

    @Override
    public void showStartScreen() {}

    @Override
    public int getNumberOfPlayers() {
        return numPlayers;
    }

    @Override
    public int displayTurnStart(int player, String hand) {
        if (throwOnGame)
            throw new TraceException();

        throw new IllegalStateException("Not implemented");
    }
}
