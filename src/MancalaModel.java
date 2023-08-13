import java.util.*;
/**
 * CS151 Mancala Project Fall 2022 - MancalaModel
 * @author Buzz - Logan and Peter
 * @version 12/02/2022
 */


/**
 * This class represents the Mancala model.
 */
public class MancalaModel {
  private Player pA, pB;
  private char player;
  private ArrayList<Listener> listeners;
  private MancalaModel previousState;
  private int undos;

  /**
   * Constructor for objects of class Mancala
   * 
   * @param initialStones the initial stones of the player
   */
  public MancalaModel(int initialStones) {
    this.pA = new Player(initialStones);
    this.pB = new Player(initialStones);
    this.player = 'A';
    this.listeners = new ArrayList<Listener>();
    this.undos = 3;
  }

  private MancalaModel(MancalaModel other) {
    this.pA = Player.copy(other.pA);
    this.pB = Player.copy(other.pB);
    this.player = other.player;
    this.listeners = new ArrayList<Listener>(other.listeners);
    this.undos = other.undos - 1;
  }

  /**
   * Performs a turn in the mancala game (also works as mutator).
   * 
   * @param pit the index of the pit between 0 and 5 (inclusive)
   */
  public void doTurn(char player, int pit) throws Exception {
    if (player != this.player)
      throw new Exception("Wrong Player!");
    if (pit < 0 || pit > 5) {
      throw new IllegalArgumentException(pit + " is not in between 0 and 5 (inclusive).");
    }

    this.previousState = new MancalaModel(this);
    Player p = this.player == 'A' ? this.pA : this.pB;
    Player initialPlayer = p;
    int beads = p.getBeadsInPit(pit);
    p.setBeadsInPit(pit, 0);
    while (beads > 0) {
      if (++pit <= 5) {
        p.setBeadsInPit(pit, p.getBeadsInPit(pit) + 1);
        beads--;
        if (beads == 0 && p == initialPlayer && p.getBeadsInPit(pit) == 1) { // drop bead into own empty pit
          p = p == this.pA ? this.pB : this.pA; // swap player
          int original = p.getBeadsInPit(5 - pit); // pit on other side
          p.setBeadsInPit(5 - pit, 0); // empty pit
          initialPlayer.setBeadsInPit(pit, 0);
          initialPlayer.setMancala(initialPlayer.getMancala() + original + 1);
        }
        continue;
      }
      if (p == initialPlayer) {
        p.setMancala(p.getMancala() + 1);
        beads--;
      }
      if (beads == 0) {
        this.undos = 3;
        this.gameIsOver();
        this.updateView();
        return;
      }
      p = p == this.pA ? this.pB : this.pA;
      pit = -1;
    }
    this.undos = 3;
    this.player = this.player == 'A' ? 'B' : 'A';
    this.gameIsOver();
    this.updateView();
  }

  /**
   * Returns a boolean based on if the game is over.
   * 
   * @return true if the game is over, false otherwise
   */
  public boolean gameIsOver() {
    Player p;
    if (this.pA.emptyPits()) {
      p = this.pB;
    } else if (this.pB.emptyPits()) {
      p = this.pA;
    } else {
      return false;
    } 

    p.collectAll();
    return true;
  }

  /**
   * Getter for the current player whose turn it is.
   * 
   * @return the current player
   */
  public char getCurrentPlayer() {
    return this.player;
  }

  /**
   * Getter for player A.
   * 
   * @return player A
   */
  public Player getPlayerA() {
    return this.pA;
  }

  /**
   * Getter for player B.
   * 
   * @return player B
   */
  public Player getPlayerB() {
    return this.pB;
  }

  /**
   * Gets the winner of the game or an empty space if the game is ongoing.
   * 
   * @return 'A' or 'B' if the game is over, ' ' otherwise.
   */
  public char getWinner() {
    return this.gameIsOver() ? this.pA.getMancala() > this.pB.getMancala() ? 'A' : 'B' : ' ';
    // I'm so glad this is a school assignment
  }

  /**
   * Attaches a listener to the model
   * @param listener the listener
   */
  public void attach(Listener listener) {
    this.listeners.add(listener);
    this.updateView();
  }

  /**
   * updates the view with new information
   */
  private void updateView() {
    for (Listener l : this.listeners) {
      l.update(this);
    }
  }

  /**
   * gets the previous state of the board
   * @return: the previous state
   */
  public MancalaModel getPrevState() {
    if (this.previousState.undos >= 0)
      return previousState;
    return null;
  }
}
