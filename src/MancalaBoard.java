import java.awt.event.*;
import java.awt.*;
/**
 * CS151 Mancala Project Fall 2022 - MancalaBoard
 * @author Buzz - Logan and Peter
 * @version 12/02/2022
 */

/**
 * Abstract class for the mancala boards
 *
 */
public abstract class MancalaBoard implements MouseListener {
  public static final int BOARD_WIDTH = 1000;
  public static final int BOARD_HEIGHT = 500;

  public static final int COLS = 8;
  public static final int ROWS = 2;
  public static final int XGAP = 10;
  public static final int YGAP = 30;

  public abstract void draw(Graphics2D g2);

  public abstract void update(MancalaModel m);
}
