package calendarFunctions;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * The main calendar window builder tool
 * 
 * 
 * @author Xelafe
 *
 */
public class MainCalendar extends JPanel {

  // top main container panel
  private JPanel topContainel, topYearPanel, topMonthPanel;
  private JButton nextYearButton, prevYearButton, nextMonButton, prevMonButton,
      yearButton, monthButton;

  // appearance constants
  private static final int PANEL_WIDTH = 200;
  private static final int PANEL_HEIGHT = 120;

  // padding in the top panel for better appearance
  private static final int BORDER_PADDING = 13;
  private static final int BUTTON_PADDING = 175;
  private static final int BORDER_STROKE = 2;

  private static final int MON_WIDTH = 280;
  private static final int MON_HEIGHT = 41;
  private static final int YEAR_WIDTH = 280;
  private static final int YEAR_HEIGHT = 55;

  // Calendar constant strings
  private static final String[] DAYS_OF_WEEK = { "MON", "TUE", "WED", "THU",
      "FRI", "SAT", "SUN" };
  private static final String[] MONS_OF_YEAR = { "January", "February", "March",
      "April", "May", "June", "July", "August", "September", "November",
      "December" };

  // bot panel component
  private JButton today;

  // main grid panel
  private JPanel daysPanel;

  private Calendar currentCal;

  private static final Color CALENDAR_COLOR = new Color(100, 100, 100);
  
  /**
   * add the main calendar to the main frame
   * 
   * @param mainFrame:
   *          the main window of the program
   */
  @SuppressWarnings("deprecation")
  public MainCalendar(JFrame mainFrame) {

    // initialize the calendar for local time and date
    currentCal = Calendar.getInstance();

    /*--------- setting up the top layout for year and month GUI-----------------*/
    this.setLayout(new BorderLayout());
    topContainel = new JPanel(new BorderLayout());
    topContainel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
    topContainel.setBackground(CALENDAR_COLOR);

    /*--------- setting up the year panel---------------------*/
    topYearPanel = new JPanel();
    topYearPanel.setLayout(new BoxLayout(topYearPanel, BoxLayout.X_AXIS));
    topYearPanel
        .setBorder(BorderFactory.createLineBorder(Color.BLACK, BORDER_STROKE));

    // making the year buttons
    nextYearButton = new JButton(">>");
    nextYearButton.setFont(new Font("Marker Felt", Font.BOLD, 29));
    prevYearButton = new JButton("<<");
    prevYearButton.setFont(new Font("Marker Felt", Font.BOLD, 29));
    yearButton = new JButton("Year: " + currentCal.get(Calendar.YEAR));
    yearButton.setFont(new Font("Marker Felt", Font.BOLD, 35));
    yearButton.setPreferredSize(new Dimension(YEAR_WIDTH, YEAR_HEIGHT));

    // adding the button to the year panel
    topYearPanel.add(Box.createRigidArea(new Dimension(BORDER_PADDING, 0)));
    topYearPanel.add(prevYearButton);
    topYearPanel.add(Box.createRigidArea(new Dimension(BUTTON_PADDING, 0)));
    topYearPanel.add(yearButton);
    topYearPanel.add(Box.createRigidArea(new Dimension(BUTTON_PADDING, 0)));
    topYearPanel.add(nextYearButton);
    topYearPanel.add(Box.createRigidArea(new Dimension(BORDER_PADDING, 0)));

    /*---------- setting up the month panel ----------------*/
    topMonthPanel = new JPanel();
    topMonthPanel.setLayout(new BoxLayout(topMonthPanel, BoxLayout.X_AXIS));
    topMonthPanel
        .setBorder(BorderFactory.createLineBorder(Color.BLACK, BORDER_STROKE));

    // making the month buttons
    nextMonButton = new JButton(">");
    nextMonButton.setFont(new Font("Marker Felt", Font.BOLD, 29));
    prevMonButton = new JButton("<");
    prevMonButton.setFont(new Font("Marker Felt", Font.BOLD, 29));
    monthButton = new JButton(MONS_OF_YEAR[currentCal.get(Calendar.MONTH)]);
    monthButton.setFont(new Font("Marker Felt", Font.BOLD, 31));
    monthButton.setPreferredSize(new Dimension(MON_WIDTH, MON_HEIGHT));

    // adding the button to the year panel
    topMonthPanel.add(Box.createRigidArea(new Dimension(BORDER_PADDING, 0)));
    topMonthPanel.add(prevMonButton);
    topMonthPanel.add(Box.createRigidArea(new Dimension(BUTTON_PADDING, 0)));
    topMonthPanel.add(monthButton);
    topMonthPanel.add(Box.createRigidArea(new Dimension(BUTTON_PADDING, 0)));
    topMonthPanel.add(nextMonButton);
    topMonthPanel.add(Box.createRigidArea(new Dimension(BORDER_PADDING, 0)));

    // adding the year and month panel together to the top
    topContainel.setPreferredSize(new Dimension(400, 90));
    topContainel.add(topMonthPanel, BorderLayout.SOUTH);
    topContainel.add(topYearPanel);

    this.add(topContainel, BorderLayout.NORTH);

    this.setBackground(CALENDAR_COLOR);
    mainFrame.add(this);

  }

}
