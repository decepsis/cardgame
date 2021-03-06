package com.teamkoala;

import java.util.*;

/**
 * CLIView which extends the View class to contain user inputs and outputs
 *
 * @version Lab 7
 */
public class CLIView implements View {
    private final Scanner input = new Scanner(System.in);

    /**
     * Shows the title screen.
     */
    @Override
    public void showStartScreen() {
        System.out.println("~~~~~~~~~~~~Welcome to the game!~~~~~~~~~~~~");
    }

    /**
     * Prompts for the number of players.
     *
     * @return Number of players or 0 for exit.
     */
    @Override
    public int getNumberOfPlayers(){
        int num = -1;
        while (num == -1) {
            System.out.print("Enter the number of players(min. 2 & max. 8) or 0 to exit: ");

            int num1 = input.nextInt();
            if ((num1 >= 2 && num1 <= 8) || num1 == 0) {
                num = num1;
            } else {
                System.out.println("Please enter valid number of players.");
            }
        }

        return num;
    }

    /**
     * Query the user for the number of holes to play.
     *
     * @return Number of holes.
     */
    public int queryHoles() {
        int num = -1;
        while (num == -1) {
            System.out.print("Enter the number of holes to play: ");

            int num1 = input.nextInt();
            if (num1 >= 0) {
                num = num1;
            } else {
                System.out.println("Please enter a positive or zero number of holes.");
            }
        }

        return num;
    }

    /**
     * Displays current player's turn, their current hand and the last discarded card
     * @param playerTurn Number of the current player.
     * @param playerHand String version of the player's hand.
     * @param lastDiscard Last discarded card or null if there is none.
     */
    //Story 4
    @Override
    public void displayTurnStart(int playerTurn, String playerHand, PlayingCards lastDiscard) {
        System.out.println("Player " + playerTurn + "'s turn.\nPlayer's hand: " + playerHand);

        if (lastDiscard != null)
            System.out.print("Last discarded card: " + lastDiscard + "\n");
        else
            System.out.println("Discard is empty");
    }

    /**
     * Asks the player where to draw a card from.
     *
     * @param stockHasCards If the stock pile has cards (can be drawn.)
     * @param discardHasCards If the discard pile has cards (can be drawn.)
     * @return 1 for stock, 2 for discard, 0 for exit.
     */
    // Story 5
    @Override
    public int drawCard(boolean stockHasCards, boolean discardHasCards) {
        int temp = 0;

        if (stockHasCards && !discardHasCards) {
            System.out.print("Press 1 to draw a card from the stock pile or 0 to exit: ");

            temp = input.nextInt();
            while (temp != 1 && temp != 0) {
                System.out.println("Please only input 1 or 0");
                temp = input.nextInt();
            }
        } else if (!stockHasCards && discardHasCards) {
            System.out.print("Press 2 to draw the last discarded card or 0 to exit: ");

            temp = input.nextInt();
            while (temp != 2 && temp != 0) {
                System.out.println("Please only input 2 or 0");
                temp = input.nextInt();
            }
        } else if (!stockHasCards)
            System.out.println("There are no more cards left, we have to exit, sorry.");
        else {
            System.out.println("Press 1 to draw a card from the stock pile, 2 to draw the last discarded card, or 0 to exit.");
            temp = input.nextInt();
            while (temp != 1 && temp != 2 && temp != 0) {
                System.out.println("Please only input 1, 2, or 0");
                temp = input.nextInt();
            }
        }

        return temp;
    }

    /**
     * Asks the player which card they want to replace.
     *
     * @param drawn Card that was drawn.
     * @return 0-based index of the hand.
     */
    @Override
    public boolean askKeep(PlayingCards drawn) {
        System.out.println("The card drawn from the deck is: " + drawn);
        System.out.print("Press 1 to keep the card or 2 to discard it: ");

        int temp = input.nextInt();
        while (temp != 1 && temp != 2) {
            System.out.println("Please only input 1 or 2.");
            temp = input.nextInt();
        }

        return temp == 1;
    }

    /**
     * Asks the player if they want to keep the card.
     *
     * @param drawn Unused, as we don't print here.
     * @return If the player keeps the card.
     */
    @Override
    public int askReplace(PlayingCards drawn) {
        System.out.println("Select the row and column of the card you want replaced.");
        System.out.print("Row: ");
        int row = input.nextInt();

        while (row > 2 || row < 1) {
            System.out.println("Please select a row between 1 and 2.");
            row = input.nextInt();
        }

        System.out.print("Column: ");
        int col = input.nextInt();

        while (col > 3 || col < 1) {
            System.out.println("Please select a column between 1 and 3.");
            col = input.nextInt();
        }

        return (col - 1) + ((row - 1) *  3);
    }

    /**
     * Asks the player if they want to flip a card over.
     *
     * @return Card the player would like to flip over or -1.
     */
    @Override
    public int askFlip(boolean wasFlipped) {
        if (wasFlipped)
            System.out.println("That card is already flipped.");

        System.out.println("You may flip a card over. Enter the row or 0 to skip:");

        int row = input.nextInt();
        while (row > 2 || row < 0) {
            System.out.println("Please select a row between 1 and 2.");
            row = input.nextInt();
        }

        if (row == 0)
            return -1;

        System.out.println("Column: ");
        int col = input.nextInt();

        while (col > 3 || col < 1) {
            System.out.println("Please select a column between 1 and 3.");
            col = input.nextInt();
        }

        return (col - 1) + ((row - 1) *  3);
    }

    /**
     * Asks the player if they want to view the scoreboard.
     *
     * @return If the player wants to view the scoreboard.
     */
    @Override
    public boolean viewScoreboard() {
        System.out.println("1: View scoreboard; 0: Continue");

        while (true) {
            int temp = input.nextInt();

            if (temp == 1 || temp == 0)
                return temp == 1;
        }
    }

    /**
     * Shows the current scoreboard
     *
     * @param players Current players
     */
    @Override
    public void showScoreboard(Player[] players, int holes, int hole) {
        System.out.println("The current hole is: " + hole);
        System.out.println("The total number of holes: " + holes);

        List<Player> asList = Arrays.asList(players);
        ArrayList<Player> temp = new ArrayList<>();
        Collections.addAll(temp, players);

        temp.sort(Comparator.comparingInt(Player::scoreFaceCard));

        System.out.println("The Scores are:");
        for(int i = 0; i < players.length; i++){
            System.out.println("Player " + (asList.indexOf(temp.get(i))+1) + "'s Score: " + temp.get(i).scoreFaceCard());
        }
    }

    /**
     * Displays the winner after a game is complete
     * @param scores The final scores.
     */
    @Override
    public void displayWinner(int[] scores) {
        ArrayList<Integer> asList = new ArrayList<>();
        ArrayList<Integer> temp = new ArrayList<>();

        for (int score : scores) {
            temp.add(score);
            asList.add(score);
        }

        temp.sort(Comparator.naturalOrder());
        System.out.println("The winner is: Player " + (asList.indexOf(temp.get(0))+1));
        System.out.println("~~~~~~~~~~~~End of game!~~~~~~~~~~~~");
    }
}
