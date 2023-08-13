
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.*;
/**
 * CS151 Mancala Project Fall 2022 - BoardOne
 * @author Buzz - Logan and Peter
 * @version 12/02/2022
 */

/**
 * Creates the first board if chosen from the frame class
 *
 */
public class BoardOne extends MancalaBoard {
	private MancalaModel m;
	private final int x;
	private final int y;
	private final int width;
	private final int height;
	private final int cols;
	private final int xgap;
	private final int ygap;
	private final int rows;
	private HashMap<Integer, Pit> pitListA;
	private HashMap<Integer, Pit> pitListB;
	private Pit mancalaA, mancalaB;

	public BoardOne(MancalaFrame frame) {
		this.x = frame.getWidth() / 2 - BOARD_WIDTH / 2;
		this.y = frame.getHeight() / 2 - BOARD_HEIGHT / 2;
		this.width = BOARD_WIDTH;
		this.height = BOARD_HEIGHT;
		this.cols = COLS;
		this.xgap = XGAP;
		this.ygap = YGAP;
		this.rows = ROWS;

		this.pitListA = new HashMap<Integer, Pit>();
		this.pitListB = new HashMap<Integer, Pit>();
		this.mancalaB = new Pit(this.x + this.xgap, y + ygap, width / cols - xgap * 2, height - ygap * 2);
		this.mancalaA = new Pit(width + x - width / cols + this.xgap, y + ygap, width / cols - xgap * 2,
				height - ygap * 2);

		this.initBoard();
	}

	/**
	 * draws all details of the board
	 */
	@Override
	public void draw(Graphics2D g2) {
		Rectangle2D.Double base = new Rectangle2D.Double(x, y, width, height);
		for (Pit p : this.pitListA.values()) {
			p.draw(g2);
		}
		for (Pit p : this.pitListB.values()) {
			p.draw(g2);
		}
		this.mancalaA.draw(g2);
		this.mancalaB.draw(g2);
		g2.draw(base);

		g2.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		g2.drawString("A1", x + width / 8 + 5 * xgap, y + height + 30);
		g2.drawString("A2", x + 2 * width / 8 + 5 * xgap, y + height + 30);
		g2.drawString("A3", x + 3 * width / 8 + 5 * xgap, y + height + 30);
		g2.drawString("A4", x + 4 * width / 8 + 5 * xgap, y + height + 30);
		g2.drawString("A5", x + 5 * width / 8 + 5 * xgap, y + height + 30);
		g2.drawString("A6", x + 6 * width / 8 + 5 * xgap, y + height + 30);
		g2.drawString("-->", x + 6 * width / 8 + 2 * (5 * xgap), y + height + 30);

		g2.drawString("<--", x + width / 8, y - 20);
		g2.drawString("B6", x + width / 8 + 5 * xgap, y - 20);
		g2.drawString("B5", x + 2 * width / 8 + 5 * xgap, y - 20);
		g2.drawString("B4", x + 3 * width / 8 + 5 * xgap, y - 20);
		g2.drawString("B3", x + 4 * width / 8 + 5 * xgap, y - 20);
		g2.drawString("B2", x + 5 * width / 8 + 5 * xgap, y - 20);
		g2.drawString("B1", x + 6 * width / 8 + 5 * xgap, y - 20);

		AffineTransform at = new AffineTransform();
		at.setToRotation(Math.toRadians(270), x, y);
		g2.setTransform(at);
		g2.drawString("Mancala B", x - height / 2 - (g2.getFontMetrics(g2.getFont()).stringWidth("Mancala B")) / 2, y);

		AffineTransform at2 = new AffineTransform();
		at2.setToRotation(Math.toRadians(90), x + width, y);
		g2.setTransform(at2);
		g2.drawString("Mancala A", x + width + height / 2 - (g2.getFontMetrics(g2.getFont()).stringWidth("Mancala A")) / 2, y);
	}

	/**
	 * initializes the board
	 */
	private void initBoard() {
		int incrementA = 1;
		int decrementB = 6;
		for (int i = x; i < width + x; i += width / cols) { // Draw circles
			for (int j = y; j < height + y; j += height / rows) {
				if (i == x || i + width / cols == width + x)
					continue;
				Pit pit = new Pit(i + xgap, j + ygap, width / cols - xgap * 2, height / rows - ygap * 2);
				if (j == y + height / rows) {
					pitListA.put(incrementA, pit);
					incrementA++;
				} else {
					pitListB.put(decrementB, pit);
					decrementB--;
				}
			}
		}
	}

	/**
	 * updates board from model
	 */
	public void update(MancalaModel m) {
		this.m = m;
		Player a = m.getPlayerA();
		Player b = m.getPlayerB();
		for (int key : pitListA.keySet()) {
			pitListA.get(key).setBeads(a.getBeadsInPit(key - 1));
			pitListB.get(key).setBeads(b.getBeadsInPit(key - 1));
		}
		this.mancalaA.setBeads(a.getMancala());
		this.mancalaB.setBeads(b.getMancala());
	}

	/**
	 * controller method that changes the amount of marbles in each pit when clicked on during the proper turn
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		try {
			for (int key : pitListA.keySet()) {
				if (pitListA.get(key).contains(e.getPoint()))
					this.m.doTurn('A', key - 1);
				else if (pitListB.get(key).contains(e.getPoint()))
					this.m.doTurn('B', key - 1);
			}
		} catch (Exception exc) {
			System.out.println("Wrong turn" + this.m.getCurrentPlayer() + "'s turn");
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
