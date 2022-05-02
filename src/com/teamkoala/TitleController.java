package com.teamkoala;

/**
 * Controls the title screen.
 *
 * @version Lab 6
 */
public class TitleController implements Controller {
    private final View view;

    /**
     * Constructs a TitleController.
     *
     * @param view The view with which to display.
     */
    public TitleController(View view) {
        this.view = view;
    }

    /**
     * Runs the title screen loop.
     *
     * @return If this program should continue running. Always false for TitleController.
     */
    @Override
    public boolean process() {
        boolean running = true;

        while (running) {
            view.showStartScreen();

            final int players = view.getNumberOfPlayers();

            if (players == 0)
                return false;

            final int holes = view.queryHoles();

            if (holes == 0)
                return false;

            running = new GameController(this.view, players, holes).process();
        }

        return false;
    }
}
