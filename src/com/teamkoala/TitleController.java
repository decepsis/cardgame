package com.teamkoala;

/**
 * Controls the title screen.
 *
 * @version Lab 6
 */
public class TitleController implements Controller {
    private final View view;

    public TitleController(View view) {
        this.view = view;
    }

    @Override
    public boolean process() {
        boolean running = true;

        while (running) {
            view.showStartScreen();

            final int players = view.getNumberOfPlayers();

            if (players == 0)
                return false;

            running = new GameController(this.view, players).process();
        }

        return false;
    }
}
