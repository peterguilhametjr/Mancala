import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * CS151 Mancala Project Fall 2022 - Pit
 * @author Buzz - Logan and Peter
 * @version 12/02/2022
 */

/**
 * Pit class that draws the pit and stores the amount of beads per pit for easier control when manipulating
 * beads around the board. 
 *
 */
public class Pit {

	private int x, y, width, height, beads;
	private Ellipse2D.Double pit;

	public Pit(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.beads = -1;
	}

	/**
	 * draws the ellipse of the current pit
	 * @param g2: graphics of frame
	 */
	public void draw(Graphics2D g2) {
		pit = new Ellipse2D.Double(x, y, width, height);
		g2.draw(pit);
		int position = 0;
		int levels = 0;
		int width = this.width / 8;
		int height = this.height / 20;
		int y = this.y + this.height / 2 - height / 2;
		if (beads > 0) {
			for(int i = 0; i < beads; i++) {
				if(i % 7 == 0) {
					position = 0;
					levels++;
					y = this.y + this.height / 2 - height / 2 + levels * height;
				}
				int x = this.x + 10 + width*position;
				g2.drawOval(x, y, width, height);
				position++;
				
			}
			g2.setFont(new Font("Comic Sans", Font.PLAIN, 32) );
			g2.drawString(String.valueOf(beads), (int) (this.x + this.width / 2), y + 40);
		}

	}
	
	/**
	 * sets amount of beads
	 * @param beads: number of beads
	 */
	public void setBeads(int beads) {
		this.beads = beads;
	}

	/**
	 * checks if the current pit contains mouse point
	 * @param p: the mouse point
	 * @return: boolean value
	 */
	public boolean contains(Point p) {
		if (pit.contains(p)) {
			return true;
		}
		return false;
	}

	/**
	 * gets the width of pit
	 * @return: the width of pit
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * gets the height of pit
	 * @return: the height of pit
	 */
	public int getHeight() {
		return this.height;
	}

	/**
	 * gets the x val of pit
	 * @return: x val of pit
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * gets the y val of pit
	 * @return: y val of pit
	 */
	public int getY() {
		return this.y;
	}
}
