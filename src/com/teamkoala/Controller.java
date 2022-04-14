package com.teamkoala;

/**
 * Represents a controller.
 */
public interface Controller {
    /**
     * Performs the controller's cycle, i.e. displaying the title.
     *
     * @return If the program should continue running.
     */
    boolean process();
}
