package com.teamkoala;

/**
 * Holds driver for the golf application.
 *
 * @version Lab 6
 */
public class Main {

    /**
     * Entry point for golf application.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        CLIView view = new CLIView();
        TitleController ctrl = new TitleController(view);

        while (ctrl.process()) {}
    }
}
