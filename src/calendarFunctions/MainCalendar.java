package calendarFunctions;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import mercurial.Mercurial;


/**
 * The main calendar window that displays a calendar that could go +/- 10 years
 * away from current date
 * 
 * Source of help:
 * http://www.dreamincode.net/forums/topic/25042-creating-a-calendar-viewer-application/
 * 
 * @author Shanfeng Feng
 * @version 0.1
 * @since 2017-07-10
 *
 */
public class MainCalendar extends JPanel {

  // top main container panel
  private JPanel topContainel, topYearPanel, topMonthPanel, monthSubPanel,
      yearSubPanel;
  private static JButton nextYearButton, prevYearButton, nextMonButton,
      prevMonButton;
  private static JButton yearButton, monthButton, today, refresh;
  private JPopupMenu yearMenu, monthMenu;

  private Mercurial frameRef;

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
  private static final String[] DAYS_OF_WEEK = { "SUN", "MON", "TUE", "WED",
      "THU", "FRI", "SAT" };
  private static final String[] MONS_OF_YEAR = { "January", "February", "March",
      "April", "May", "June", "July", "August", "September", "October",
      "November",
      "December" };

  // main grid ( a table of the days displayed in the scrollpane)
  private static DefaultTableModel tableModel;
  private static JTable calendarTable;
  private JScrollPane daysPane;

  // the build in calendar being used
  private static Calendar realCal;
  private static int realYear;
  private int realMonth;
  private static int programYear;
  private static int programMonth;

  private static final int NUM_ROWS = 6;
  private static final int NUM_COLS = 7;
  private static final int ROW_HEIGHT = 65;

  public static final Color CALENDAR_COLOR = new Color(100, 100, 100);
  private static final int YEAR_LIMIT = 10;
  private static final int CALENDAR_OFFSET = 2;
  
  /**
   * add the main calendar to the main frame
   * 
   * @param mainFrame:
   *          the main window of the program
   */
  public MainCalendar(Mercurial mainFrame) {

    // storing reference to the mainFrame
    frameRef = mainFrame;

    // initialize the calendar for local time and date
    realCal = Calendar.getInstance();
    realYear = realCal.get(Calendar.YEAR);
    realMonth = realCal.get(Calendar.MONTH);

    // the program's month and year (can be modified by user)
    programMonth = realMonth;
    programYear = realYear;

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
    yearButton = new JButton("Year: " + realYear);
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
    monthButton = new JButton(MONS_OF_YEAR[realMonth]);
    monthButton.setFont(new Font("Marker Felt", Font.BOLD, MON_FONT));
    monthButton.setPreferredSize(new Dimension(MON_WIDTH, MON_HEIGHT));

    // utility buttons (jumping back to today and refreshing the calendar)
    today = new JButton("Today");
    today.setFont(new Font("Marker Felt", Font.ITALIC, 29));
    today.setPreferredSize(new Dimension(BUTTON_PADDING, MON_HEIGHT));
    today.setForeground(Color.ORANGE);

    refresh = new JButton("Refresh");
    refresh.setFont(new Font("Marker Felt", Font.ITALIC, 29));
    refresh.setPreferredSize(new Dimension(BUTTON_PADDING, MON_HEIGHT));
    refresh.setForeground(Color.CYAN);

    // create rigid area
    monthSubPanel = new JPanel(new BorderLayout());
    monthSubPanel.add(refresh, BorderLayout.WEST);
    monthSubPanel.add(today, BorderLayout.EAST);
    monthSubPanel.add(monthButton);

    // adding the button to the month panel
    topMonthPanel.add(prevMonButton, BorderLayout.WEST);
    topMonthPanel.add(nextMonButton, BorderLayout.EAST);
    topMonthPanel.add(monthSubPanel);

    // adding the year and month panel together to the top panel
    topContainel.setPreferredSize(new Dimension(400, 90));
    topContainel.add(topMonthPanel, BorderLayout.SOUTH);
    topContainel.add(topYearPanel);

    /*----------------------------Days part of the calendar----------*/

    // table is selectable but not double-click editable
    tableModel = new DefaultTableModel() {

      @Override
      public boolean isCellEditable(int row, int col) {
        return false;
      }
    };

    calendarTable = new JTable(tableModel);
    daysPane = new JScrollPane(calendarTable);

    // each column of the table is a day of the week
    for (int i = 0; i < DAYS_OF_WEEK.length; i++) {
      tableModel.addColumn(DAYS_OF_WEEK[i]);
    }

    // headers cannot be reordered or resized
    calendarTable.getTableHeader().setResizingAllowed(false);
    calendarTable.getTableHeader().setReorderingAllowed(false);

    // set table selection mode
    calendarTable.setColumnSelectionAllowed(true);
    calendarTable.setRowSelectionAllowed(true);
    calendarTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    // table appearance
    tableModel.setRowCount(NUM_ROWS);
    tableModel.setColumnCount(NUM_COLS);
    calendarTable.setRowHeight(ROW_HEIGHT);
    calendarTable.setBackground(CALENDAR_COLOR);

    /*-----------------------BUTTON EVENT LISTENERS-------------------------*/
    nextYearButton.addActionListener(new NextYearListener());
    prevYearButton.addActionListener(new PrevYearListener());
    prevMonButton.addActionListener(new PrevMonListener());
    nextMonButton.addActionListener(new NextMonListener());
    yearButton.addActionListener(new YearListener());
    monthButton.addActionListener(new MonthListener());
    today.addActionListener(new TodayListener());
    refresh.addActionListener(new RefreshListener());

    /*----------------------Popup Menu initialization ---------------------*/
    yearMenu = new JPopupMenu("YEARS");
    monthMenu = new JPopupMenu("MONTHS");

    ActionListener yearItemListener = new YearItemListener();
    ActionListener monthItemListener = new MonItemListener();
    // adding years to the menu
    for (int i = realYear - YEAR_LIMIT; i <= realYear + YEAR_LIMIT; i++) {
      JMenuItem newItem = new JMenuItem(String.valueOf(i));
      yearMenu.add(newItem);
      newItem.addActionListener(yearItemListener);
    }

    // adding months to the box
    for (int i = 0; i <= Calendar.DECEMBER; i++) {
      JMenuItem newItem = new JMenuItem(MONS_OF_YEAR[i]);
      newItem.setActionCommand(String.valueOf(i));
      monthMenu.add(newItem);
      newItem.addActionListener(monthItemListener);
    }

    yearButton.setComponentPopupMenu(yearMenu);
    monthButton.setComponentPopupMenu(monthMenu);

    // adding calendar components to the panel
    this.add(topContainel, BorderLayout.NORTH);
    this.add(daysPane);

    this.setBackground(CALENDAR_COLOR);

    refreshCalendar(realMonth, realYear);

    // doublic click cell to display all task on that day
    calendarTable.addMouseListener(new MouseAdapter() {

      // mouse clicked twice triggers event
      // (mousePressed cuz mouse click is on 1 same pixel)
      public void mousePressed(MouseEvent e) {

        if (e.getClickCount() == 2) {

          // obtain the column && row being clicked on
          JTable target = (JTable) e.getSource();
          int row = target.rowAtPoint(e.getPoint());
          int column = target.columnAtPoint(e.getPoint());
          // get the component renderered in that cell
          Component comp = calendarTable.prepareRenderer(
              calendarTable.getCellRenderer(row, column), row, column);

          // use that component's instances to create the pop up panel
          ((MainCalendarCellRenderer) comp).createPopUp(calendarTable);
        }

      }
    });

    mainFrame.add(this);

  }

  /*-------------------------Button Listener Inner Classes----------------*/

  /**
   * Event handling for next year button >>
   * 
   * @author Shanfeng Feng
   * 
   */
  private class NextYearListener implements ActionListener {

    /**
     * Increment the program year by 1 and refresh the whole calendar
     * 
     * @param e:
     *          the action event that invoked this call (button click)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
      programYear++;
      MainCalendar.refreshCalendar(programMonth, programYear);
    }

  }

  /**
   * Event handling for next month button >
   * 
   * @author Shanfeng Feng
   * 
   */
  private class NextMonListener implements ActionListener {

    /**
     * increment the program month and readjust program year if necessary
     * 
     * @param e:
     *          the action event that invoked this call (button click)
     */
    @Override
    public void actionPerformed(ActionEvent e) {

      // go into the next year if program at the last month
      if (programMonth == Calendar.DECEMBER) {
        programMonth = Calendar.JANUARY;
        programYear++;
      }
      // just increment month by one
      else {
        programMonth++;
      }

      MainCalendar.refreshCalendar(programMonth, programYear);
    }

  }

  /**
   * Event handling for previous year button <<
   * 
   * @author Shanfeng Feng
   * 
   */
  private class PrevYearListener implements ActionListener {

    /**
     * Decrement program year and refresh the calendar
     * 
     * @param e:
     *          the action event that invoked this call (button click)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
      programYear--;
      MainCalendar.refreshCalendar(programMonth, programYear);
    }

  }

  /**
   * Event handling for previous month button <
   * 
   * @author Shanfeng Feng
   * 
   */
  private class PrevMonListener implements ActionListener {

    /**
     * decrement the program month and readjust program year if needed
     * 
     * @param e:
     *          the action event that invoked this call (button click)
     */
    @Override
    public void actionPerformed(ActionEvent e) {

      // go into the previous year if program at the first month
      if (programMonth == Calendar.JANUARY) {
        programMonth = Calendar.NOVEMBER;
        programYear--;
      } else {
        programMonth--;
      }

      MainCalendar.refreshCalendar(programMonth, programYear);
    }

  }

  /**
   * Event handling for today button
   * 
   * @author Shanfeng Feng
   * 
   */
  private class TodayListener implements ActionListener {

    /**
     * Change program year/month to today's real date
     * 
     * @param e:
     *          the action event that invoked this call (button click)
     */
    @Override
    public void actionPerformed(ActionEvent e) {

      programYear = realYear;
      programMonth = realMonth;
      MainCalendar.refreshCalendar(programMonth, programYear);

    }
  }

  /**
   * Event handling for refresh button
   * 
   * @author Shanfeng Feng
   * 
   */
  private class RefreshListener implements ActionListener {

    /**
     * Allows user to update the calendar manually
     * 
     * @param e:
     *          the action event that invoked this call (button click)
     */
    @Override
    public void actionPerformed(ActionEvent e) {

      MainCalendar.refreshCalendar(programMonth, programYear);

    }
  }

  /**
   * Event handling for year button
   * 
   * @author Shanfeng Feng
   * 
   */
  private class YearListener implements ActionListener {

    /**
     * Allow users to manually change the year +/- 10
     * 
     * @param e:
     *          the action event that invoked this call (button click)
     */
    @Override
    public void actionPerformed(ActionEvent e) {

      yearMenu.show((Component) e.getSource(), 0, yearButton.getHeight());

    }
  }

  /**
   * Event handling for month button
   * 
   * @author Shanfeng Feng
   * 
   */
  private class MonthListener implements ActionListener {

    /**
     * Allow users to manually change the program month
     * 
     * @param e:
     *          the action event that invoked this call (button click)
     */
    @Override
    public void actionPerformed(ActionEvent e) {

      monthMenu.show((Component) e.getSource(), 0,
          monthButton.getHeight());

    }
  }

  /**
   * Event handling for month menu items
   * 
   * @author Shanfeng Feng
   * 
   */
  private class MonItemListener implements ActionListener {

    /**
     * Selection of a month menu item would change program month to the selected
     * month
     * 
     * @param e:
     *          the action event that invoked this call
     */
    @Override
    public void actionPerformed(ActionEvent e) {

      programMonth = Integer.parseInt(e.getActionCommand());
      monthButton.setText(MONS_OF_YEAR[programMonth]);
      MainCalendar.refreshCalendar(programMonth, programYear);
    }

  }

  /**
   * Event handling for year menu items
   * 
   * @author Shanfeng Feng
   * 
   */
  private class YearItemListener implements ActionListener {

    /**
     * Selection of a year menu item would change program year to the selected
     * month
     * 
     * @param e:
     *          the action event that invoked this call
     */
    @Override
    public void actionPerformed(ActionEvent e) {

      programYear = Integer.parseInt(((JMenuItem) e.getSource()).getText());
      yearButton.setText("Year: " + programYear);
      MainCalendar.refreshCalendar(programMonth, programYear);

    }

  }
  /**
   * Helper method to refresh the calendar display when buttons were clicked
   * 
   * @param targetMonth
   *          - the new month being moved to
   * @param targetYear
   *          - the new year being moved to
   * @param frameReference
   *          - the frame being used to display the calendar and holds the UI
   */
  public static void refreshCalendar(int targetMonth, int targetYear) {

    /*-----------------------BUTTON ENABLE STATUS---------------------*/

    // enable all the buttons first
    nextYearButton.setEnabled(true);
    prevYearButton.setEnabled(true);
    nextMonButton.setEnabled(true);
    prevMonButton.setEnabled(true);

    // month or year too far back (lower bound)
    if (targetMonth == 0 && targetYear <= realYear - YEAR_LIMIT) {
      prevYearButton.setEnabled(false);
      prevMonButton.setEnabled(false);
    } else if (targetYear <= realYear - YEAR_LIMIT) {
      prevYearButton.setEnabled(false);
    }

    // month or year too far ahead (upper bound)
    if (targetMonth == Calendar.DECEMBER
        && targetYear >= realYear + YEAR_LIMIT) {
      nextYearButton.setEnabled(false);
      nextMonButton.setEnabled(false);
    } else if (targetYear >= realYear + YEAR_LIMIT) {
      nextYearButton.setEnabled(false);
    }

    // update the year and task button text
    yearButton.setText("Year " + targetYear);
    monthButton.setText(MONS_OF_YEAR[targetMonth]);

    /*-----------------------UPDATE THE CALENDAR DISPLAY--------------*/
    Calendar tempCalendar = Calendar.getInstance();
    tempCalendar.set(targetYear, targetMonth, 1);
    int numOfDaysInMonth = tempCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    int startOfMon = tempCalendar.get(Calendar.DAY_OF_WEEK);

    // clean out the table first
    for (int i = 0; i < NUM_ROWS; i++) {
      for (int j = 0; j < NUM_COLS; j++) {
        tableModel.setValueAt(null, i, j);
      }
    }

    // print out the day of the month onto the calendar
    for (int i = 1; i <= numOfDaysInMonth; i++) {
      int row = (i + startOfMon - CALENDAR_OFFSET) / DAYS_OF_WEEK.length;
      int column = (i + startOfMon - CALENDAR_OFFSET) % DAYS_OF_WEEK.length;
      tableModel.setValueAt(i, row, column);
    }

    // apply renderer
    calendarTable.setDefaultRenderer(calendarTable.getColumnClass(0),
        new MainCalendarCellRenderer(realCal, tempCalendar,
            Mercurial.getLeftUI()));

    updateRowHeight();

  }

  /**
   * Update row height of the calendar base on the maximum height of the
   * components inside the cell
   */
  private static void updateRowHeight() {

    // Update the row height base on the number of components in JPanel
    for (int row = 0; row < NUM_ROWS; row++) {
      int rowHeight = calendarTable.getRowHeight();

      /*
       * get the height of the renderer cell and readjust height base on the
       * height of the height of the components inside the cell
       */
      for (int column = 0; column < NUM_COLS; column++) {
        Component comp = calendarTable.prepareRenderer(
            calendarTable.getCellRenderer(row, column), row, column);
        rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
      }

      // set the height for the whole row
      calendarTable.setRowHeight(row, rowHeight);
    }

  }

  /**
   * Getter for program year
   * 
   * @return program year
   */
  public static int getProgYear() {
    return programYear;
  }

  /**
   * Getter for program month
   * 
   * @return program month
   */
  public static int getProgMonth() {
    return programMonth;
  }
}
