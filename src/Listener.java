/**
 * CS151 Mancala Project Fall 2022.
 * @author Buzz - Logan and Peter
 * @version 12/2/2022
 */

/**
 * Java view components that implement this interface update.
 */
public interface Listener {
  /**
   * Updates according to the MancalaModel
   * @param m the model
   */
  public void update(MancalaModel m);
}
