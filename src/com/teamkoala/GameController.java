package com.teamkoala;

import java.util.*;
import java.util.logging.Logger;

/**
 * Controls the game view and uses the appropriate models.
 *
 * @version Lab 7
 */
public class GameController implements Controller {
    private final static Logger logger = Logger.getLogger("Koala-Golf");
    private final Scanner input = new Scanner(System.in);
    private final View view;
    private final Player[] players;
    private final int[] scores;
    private final Deck deck;

    private int activePlayer = 0;

    private int hole = 0;

    private final int holes;

    /**
     * Creates a new GameController from a given GameView and number of players.
     *
     * @param view GameView to use to display the game.
     * @param players Number of players to create for the game.
     */
    public GameController(View view, int players, int holes) {
        this.view = view;
        this.players = new Player[players];
        this.scores = new int[players];
        this.holes = holes;

        deck = new Deck(players);

        for (int i = 0; i < players; i++) {
            this.players[i] = new Player(deck);
            this.scores[i] = 0;
        }
    }

    /**
     * Advances to the next turn, for internal use.
     */
    private void nextTurn() {
        activePlayer++;
        activePlayer %= players.length;
    }

    /**
     * Checks if the player hand is fully flipped.
     */
    private boolean checkHand() {
        final Player player = players[activePlayer];

        for (PlayingCards[] row: player.hand)
            for (PlayingCards card: row)
                if (card.faceDown)
                    return false;

        return true;
    }

    /**
     * Ends the current hole and progresses.
     */
    private void nextHole() {
        hole++;

        getHoleNum();

        for (int i = 0; i < players.length; i++) {
            Player player = players[i];
            scores[i] = player.scoreHand();
            player.discardHand(deck);
        }

        deck.reset();

        activePlayer = hole % players.length;

        for (int i = 0; i < players.length; i++)
            players[(i + hole) % players.length] = new Player(deck);

    }

    /**
     * Getter to get and count the hole number upon the execution of nextHole().
     * @return
     */
    public static int getHoleNum(){
        int holeCounter = 0;
        holeCounter++;
        return holeCounter;
    }

    /**
     * Performs game play loop.
     * @return If the program should continue running.
     */
    @Override
    public boolean process() {
        while (hole < holes) { // Each iteration is a turn
            PlayingCards lastDiscard = deck.discardSize() != 0 ? deck.peekDiscard() : null;

            final Player player = players[activePlayer];

            view.displayTurnStart(activePlayer + 1, player.handAsString(), lastDiscard);

            PlayingCards drawn = null;
            boolean discard = false;
            boolean keep = true;

            try {
                switch (view.drawCard(deck.stockSize() > 0, deck.discardSize() > 0)) {
                    case 0 -> {
                        return false;
                    }
                    case 1 -> drawn = deck.drawCard();
                    case 2 -> {
                        drawn = deck.drawDiscard();
                        discard = true;
                    }
                }
            } catch (NoSuchElementException e) {
                logger.warning("View returned a value not allowed due to pile sizes.");
                return true;
            }

            if (drawn == null) {
                logger.warning("Impossible value returned from the view.");
                return true;
            }

            drawn.setFaceUp();

            if (!discard)
                keep = view.askKeep(drawn);

            if (keep) {
                final int index = view.askReplace(drawn);
                if (index > 5 || index < 0) {
                    logger.warning("Impossible value returned from the view.");
                    return true;
                }

                final int row = index / 3;
                final int col = index % 3;

                PlayingCards last = player.hand[row][col];
                player.hand[row][col] = drawn;

                last.setFaceUp();
                deck.discard(last);
            } else {
                deck.discard(drawn);

                while (true) {
                    final int index = view.askFlip(false);

                    if (index == -1)
                        break;

                    if (index > 5 || index < -1) {
                        logger.warning("Impossible value returned from the view.");
                        return true;
                    }

                    final int row = index / 3;
                    final int col = index % 3;

                    PlayingCards card = player.hand[row][col];

                    if (card.faceDown) {
                        player.hand[row][col].setFaceUp();
                        break;
                    }
                }
            }

            System.out.println("1: View scoreboard; 0: Continue");
            int temp = input.nextInt();
            if(temp == 1){
                System.out.println("The total number of holes: " + holes);
                view.displayScoreboard();
                displayScores(getPlayers());
                nextTurn();
            }
            else {
                // Most turns have a card flipped up, though not all.
                if (checkHand())
                    nextHole();
                else
                    nextTurn();
            }

        }
        // Displays the winner
        displayWinner(getPlayers());

        return true;
    }

    /**
     * Class to help sort scores
     */
    class SortbyScore implements Comparator<Player> {
        public int compare(Player a, Player b)
        {
            return a.scoreFaceCard() - b.scoreFaceCard();
        }
    }

    /**
     * Displays player's scores in ascending order if the user wants the scoreboard. Meant to be in CLIView but can't figure out how to implement it there.
     * @param players
     */
    public void displayScores(ArrayList<Player> players) {
        ArrayList<Player> temp = new ArrayList<Player>();

        for(int i = 0; i<players.size(); i++){
            temp.add(i, players.get(i));
        }

        temp.sort(new SortbyScore());

        System.out.println("The Scores are:");
        for(int i = 0; i < players.size(); i++){
            System.out.println("Player " + (players.indexOf(temp.get(i))+1) + "'s Score: " + temp.get(i).returnScore());
        }
    }

    /**
     * Converts players array to an arraylist
     * @return
     */
    public ArrayList<Player> getPlayers() {
        List<Player> pl = new ArrayList<>(Arrays.asList(players));
        return (ArrayList<Player>) pl;
    }

    /**
     * Displays the winner after a game is complete
     * @param players
     */
    void displayWinner(ArrayList<Player> players) {
        ArrayList<Player> temp = new ArrayList<Player>();
        for(int i = 0; i<players.size(); i++){
            temp.add(i, players.get(i));
        }
        temp.sort(new SortbyScore());
        System.out.println("The winner is: Player " + (players.indexOf(temp.get(0))+1));
    }
}
