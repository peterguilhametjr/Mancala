import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
/**
 * CS151 Mancala Project Fall 2022 - MancalaFrame
 * @author Buzz - Logan and Peter
 * @version 12/02/2022
 */

/**
 * The JFrame for the mancala board, includes controller method too
 *
 */
public class MancalaFrame extends JFrame implements Listener {
  private JButton chooseBoard1, chooseBoard2;
  private JPanel northPanel;
  private JLabel prompt;
  private JTextField input;
  private MancalaBoard selectedBoard;
  private MancalaModel model;

  public MancalaFrame() {
    this.setExtendedState(JFrame.MAXIMIZED_BOTH);

    JButton quit = new JButton("Quit");
    quit.addActionListener(event -> {
      this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    });

    this.add(northPanel(), BorderLayout.NORTH);
    this.add(southPanel(), BorderLayout.SOUTH);

    this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    this.prompt = new JLabel("Please enter number of starting pits.");
  }
  
  /**
   * Paints the frame (allows for repaint)
   */
  @Override
  public void paint(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    super.paint(g2);
    if (this.selectedBoard != null)
      this.selectedBoard.draw(g2);
  }
  
  /**
   * updates the JFrame when necessary 
   */
  @Override
  public void update(MancalaModel m) {
    if (m.gameIsOver()) {
      this.northPanel.add(new JLabel("Congratulations " + this.model.getWinner() + "!"));
      this.revalidate();
    }
    this.selectedBoard.update(m);
    this.prompt.setText(m.getCurrentPlayer() + "'s turn.");
    this.repaint();
  }

  /**
   * creates the redo button and text field to choose 3 or 4 starting marbles
   * @return: the JPanel with the redo button and textfield
   */
  private JPanel southPanel() {
    JPanel p = new JPanel();
    p.setLayout(new GridLayout());

    JButton redo = new JButton("Redo");
    redo.addActionListener(event -> {
      if (this.model.getPrevState() != null) {
        this.model = this.model.getPrevState();
        this.update(this.model);
        this.repaint();
        this.revalidate();
      }
    });

    this.input = new JTextField();
    input.addActionListener(event -> {
      this.model = new MancalaModel(Integer.parseInt(this.input.getText()));
      model.attach(this);
      p.remove(input);
      this.prompt.setText("A's turn.");
      this.revalidate();
    });
    input.setEnabled(false);

    p.add(redo);
    p.add(input);
    return p;
  }

  /**
   * creates a north JPanel that holds the quit button, and the buttons to choose board 1 or board 2
   * @return: JPanel
   */
  private JPanel northPanel() {
    JPanel p = new JPanel();

    JButton quit = new JButton("Quit");
    quit.addActionListener(event -> {
      this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    });

    this.chooseBoard1 = new JButton("Board One");
    chooseBoard1.addActionListener(selectedButton(1));

    this.chooseBoard2 = new JButton("Board Two");
    chooseBoard2.addActionListener(selectedButton(2));

    p.add(quit);
    p.add(this.chooseBoard1);
    p.add(this.chooseBoard2);

    this.northPanel = p;
    return p;
  }

  /**
   * controller method that carrys out different actions depending on the selected button
   * @param button: differentiates the buttons with different ints
   * @return: evt
   */
  private ActionListener selectedButton(int button) {
    return evt -> {
      if (button == 1)
        selectedBoard = new BoardOne(this);
      else if (button == 2)
        selectedBoard = new BoardTwo(this);
      else
        throw new Error("Button must be 1 or 2");
      this.addMouseListener(selectedBoard);
      this.northPanel.remove(chooseBoard1);
      this.northPanel.remove(chooseBoard2);
      this.input.setEnabled(true);
      this.northPanel.add(this.prompt);
      this.repaint();
      this.revalidate();
    };
  }

}
