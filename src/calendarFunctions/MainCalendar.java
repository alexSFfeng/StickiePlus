package calendarFunctions;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.Box;
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
  private JPanel topContainel, topYearPanel, topMonthPanel, monthSubPanel,
      yearSubPanel;
  private JButton nextYearButton, prevYearButton, nextMonButton, prevMonButton,
      yearButton, monthButton;

  // appearance constants
  private static final int PANEL_WIDTH = 200;
  private static final int PANEL_HEIGHT = 120;
  private static final int NXT_PRV_FONT = 29;
  private static final int MON_FONT = 31;
  private static final int YEAR_FONT = 35;

  // padding in the top panel for better appearance
  private static final int BUTTON_PADDING = 175;
  private static final int BORDER_STROKE = 2;
  private static final int MON_WIDTH = 280;
  private static final int MON_HEIGHT = 41;
  private static final int YEAR_WIDTH = 180;
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
    topYearPanel = new JPanel(new BorderLayout());
    topYearPanel
        .setBorder(BorderFactory.createLineBorder(Color.BLACK, BORDER_STROKE));

    // making the year buttons
    nextYearButton = new JButton(">>");
    nextYearButton.setFont(new Font("Marker Felt", Font.BOLD, NXT_PRV_FONT));
    prevYearButton = new JButton("<<");
    prevYearButton.setFont(new Font("Marker Felt", Font.BOLD, NXT_PRV_FONT));
    yearButton = new JButton("Year: " + currentCal.get(Calendar.YEAR));
    yearButton.setFont(new Font("Marker Felt", Font.BOLD, YEAR_FONT));
    yearButton.setPreferredSize(new Dimension(YEAR_WIDTH, YEAR_HEIGHT));

    // create rigid area
    yearSubPanel = new JPanel(new BorderLayout());
    yearSubPanel.add(Box.createRigidArea(new Dimension(BUTTON_PADDING,0)),BorderLayout.WEST);
    yearSubPanel.add(Box.createRigidArea(new Dimension(BUTTON_PADDING,0)),BorderLayout.EAST);
    yearSubPanel.add(yearButton);
    
    // adding the button to the year panel
    topYearPanel.add(prevYearButton, BorderLayout.WEST);
    topYearPanel.add(nextYearButton, BorderLayout.EAST);
    topYearPanel.add(yearSubPanel);

    /*---------- setting up the month panel ----------------*/
    topMonthPanel = new JPanel(new BorderLayout());
    topMonthPanel
        .setBorder(BorderFactory.createLineBorder(Color.BLACK, BORDER_STROKE));

    // making the month buttons
    nextMonButton = new JButton(">");
    nextMonButton.setFont(new Font("Marker Felt", Font.BOLD, NXT_PRV_FONT));
    prevMonButton = new JButton("<");
    prevMonButton.setFont(new Font("Marker Felt", Font.BOLD, NXT_PRV_FONT));
    monthButton = new JButton(MONS_OF_YEAR[currentCal.get(Calendar.MONTH)]);
    monthButton.setFont(new Font("Marker Felt", Font.BOLD, MON_FONT));
    monthButton.setPreferredSize(new Dimension(MON_WIDTH, MON_HEIGHT));
    today = new JButton("Today");
    today.setFont(new Font("Marker Felt", Font.ITALIC, 29));
    today.setPreferredSize(new Dimension(BUTTON_PADDING, MON_HEIGHT));

    // create rigid area
    monthSubPanel = new JPanel(new BorderLayout());
    monthSubPanel.add(Box.createRigidArea(new Dimension(BUTTON_PADDING, 0)),
        BorderLayout.WEST);
    monthSubPanel.add(today,
        BorderLayout.EAST);
    monthSubPanel.add(monthButton);

    // adding the button to the year panel
    topMonthPanel.add(prevMonButton, BorderLayout.WEST);
    topMonthPanel.add(nextMonButton, BorderLayout.EAST);
    topMonthPanel.add(monthSubPanel);

    // adding the year and month panel together to the top
    topContainel.setPreferredSize(new Dimension(400, 90));
    topContainel.add(topMonthPanel, BorderLayout.SOUTH);
    topContainel.add(topYearPanel);

    this.add(topContainel, BorderLayout.NORTH);

    this.setBackground(CALENDAR_COLOR);
    mainFrame.add(this);

  }

}
