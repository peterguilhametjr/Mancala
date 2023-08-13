/**
 * CS151 Mancala Project Fall 2022 - Player
 * @author Buzz - Logan and Peter
 * @version 12/02/2022
 */

public class Player {
  private static final int PITS = 6;

  private int mancala;
  private int[] pits;

  public Player(int initialStones) {
    this.pits = new int[PITS];
    for (int i = 0; i < this.pits.length; i++) {
      this.pits[i] = initialStones;
    }
    this.mancala = 0;
  }

  private Player(Player other) {
    this.pits = new int[PITS];
    for (int i = 0; i < this.pits.length; i++) {
      this.pits[i] = other.pits[i];
    }
    this.mancala = other.mancala;
  }

  /**
   * Gets the number of beads at the index
   * @param index 0 - 5 inclusive
   * @return
   */
  public int getBeadsInPit(int index) {
    return this.pits[index];
  }

  /**
   * Sets the umber of beads into the indexed pit
   * @param index: index
   * @param newBeads: beads attempting to be added to pit
   */
  public void setBeadsInPit(int index, int newBeads) {
    this.pits[index] = newBeads;
  }

  /**
   * gets the mancala
   * @return: mancala
   */
  public int getMancala() {
    return this.mancala;
  }

  /**
   * sets the mancala
   * @param newMancala: new mancala
   */
  public void setMancala(int newMancala) {
    this.mancala = newMancala;
  }

  /**
   * checks for empty pits
   * @return: boolean value
   */
  public boolean emptyPits() {
    for (int i : this.pits) {
      if (i != 0) {
        return false;
      }
    }
    return true;
  }

  /**
   * collects all marbles from left over pits when game is over
   */
  public void collectAll() {
    for (int i = 0; i < this.pits.length; i++) {
      this.mancala += this.pits[i];
      this.pits[i] = 0;
    }
  }

  /**
   * Returns a copy of this player
   * @param other the other player
   * @return a copy
   */
  public static Player copy(Player other) {
    return new Player(other);
  }
}
