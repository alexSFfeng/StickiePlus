package calendarFunctions;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * The main calendar that reveals
 * 
 * @author Xelafe
 *
 */
public class MainCalendar extends JPanel {

  // top main container panel
  private JPanel topContainel, topYearPanel, topMonthPanel;
  private JButton nextYearButton, prevYearButtoon, nextMonButton, prevMonButton;
  private JLabel yearLabel, monLabel;

  // main grid panel
  private JPanel daysPanel;

  private static final Color CALENDAR_COLOR = new Color(100, 100, 100);
  /**
   * add the main calendar to the main frame
   * 
   * @param mainFrame:
   *          the main window of the programe
   */
  public MainCalendar(JFrame mainFrame) {

    this.setBackground(CALENDAR_COLOR);
    mainFrame.add(this);

  }

}
