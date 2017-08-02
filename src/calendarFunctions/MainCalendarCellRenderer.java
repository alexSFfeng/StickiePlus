
package calendarFunctions;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

import mercurial.BoxTextArea;
import mercurial.LeftUI;

/**
 * Renderer for main calendar cell display. Each cell will have a label for the
 * corresponding date and a panel that contains all the tasks due on that date
 * 
 * @author Shanfeng Feng
 * @version 0.1
 * @since 2017-07-24
 *
 */
public class MainCalendarCellRenderer extends JPanel implements TableCellRenderer{

  // Components that makes up the contents of each cell
  private JLabel dayLabel;
  private JPanel taskPanel;

  // Panel and pop up window for displaying tasks if selected
  private JPanel displayPanel;
  private JFrame newWindow;
  private JScrollPane displayPane;

  // the calendar references and the UI that contains all the tasks
  private Calendar myCalendar, newCalendar;
  private LeftUI refUI;

  // the column of the calendar that is Saturday
  private static final int SATURDAY_COL = 6;

  // pop up window size
  private static final int WINDOW_WIDTH = 650;
  private static final int WINDOW_HEIGHT = 300;
  private static final int DISPLAY_WIDTH = 180;
  private static final int DISPLAY_HEIGHT = 200;

  /**
   * Ctor for initializing the main calendar components
   * 
   * @param realCalendar
   *          - the calendar that contains the real date
   * @param currentCalendar
   *          - the calendar that contains the date in the application (can be
   *          modified by user via buttons)
   * @param allTaskUI
   *          - the UI panel that contains all the tasks specified
   */
  public MainCalendarCellRenderer(Calendar realCalendar,
      Calendar currentCalendar, LeftUI allTaskUI) {

    // link the references
    this.setLayout(new BorderLayout());
    myCalendar = realCalendar;
    newCalendar = currentCalendar;
    refUI = allTaskUI;

  }

  /**
   * Cell Renderer for the table (the main calendar)
   * 
   * @param table
   *          - the table that will serve as the calendar's day table
   * @param value
   *          - the day of the month
   * @param selected
   *          - whether this cell is selected or not
   * @param focused
   *          - whether this cell is focused or not
   * @param row
   *          - the row index of this cell
   * @param column
   *          - the column index of this cell
   * @return - this panel that contains all the components.
   */
  public Component getTableCellRendererComponent(JTable table, Object value,
                                                 boolean selected, boolean focused,
                                                 int row, int column){

    // remove all the components in this cell
    this.removeAll();

    // obtaining all the tasks created by user
    Object[] taskArray = refUI.getAllTasks();
    dayLabel = new JLabel();
    taskPanel = new JPanel();
    newWindow = new JFrame();
    displayPanel = new JPanel();
    displayPane = new JScrollPane(displayPanel);

    /*----------------- selection / deselection colors --------------------*/
    if (selected) {
      this.setBackground(table.getSelectionBackground());
    } else {
      // setting weekend days background
      if (column == 0 || column == SATURDAY_COL) {
        this.setBackground(Color.WHITE);
      }
      // setting weekdays background
      else {
        this.setBackground(MainCalendar.CALENDAR_COLOR);
      }

      // setting today's background
      if (value != null) {
        this.setDaysBackground((Integer) value);
      }
    }

    // make the new objects if the date is a valid day of the month
    if (value != null) {

      // label for the day of the month
      dayLabel.setText(String.valueOf(value));

      // panel that stores all the tasks on that day (short description)
      taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.Y_AXIS));
      taskPanel.setBackground(this.getBackground());

      // The pop up window components for displaying the detailed tasks
      newCalendar.set(Calendar.DATE, (Integer) value);
      newWindow.setTitle("Task on "
          + new SimpleDateFormat("yyyy-MM-dd").format(newCalendar.getTime()));
      displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.X_AXIS));

      // search the Array for tasks that should be put into this date cell
      for (int i = 0; i < taskArray.length; i++) {

        // setting up for date comparison
        Date taskDueDate = ((BoxTextArea) taskArray[i]).getFullDate();
        Calendar tempCalendar = Calendar.getInstance();
        tempCalendar.setTime(taskDueDate);
        int priorityLv = ((BoxTextArea) taskArray[i]).getPriorityLv();

        // if there is a task due on this date, add a corresponding label
        if (tempCalendar.get(Calendar.YEAR) == newCalendar.get(Calendar.YEAR)
            && tempCalendar.get(Calendar.MONTH) == newCalendar
                .get(Calendar.MONTH)
            && tempCalendar.get(Calendar.DATE) == (Integer) value) {

          // adding the label and setting the corresponding colors
          JLabel newLabel = new JLabel();
          // different display if the task box is empty
          if (((BoxTextArea) taskArray[i]).isBoxEmpty()) {
            newLabel.setText("Lv: " + priorityLv + " EMPTY!!?   ");
          } else {
            newLabel.setText("Lv: " + priorityLv + " "
                + ((BoxTextArea) taskArray[i]).getTextRep());
          }

          // label appearances
          newLabel
              .setBackground(((BoxTextArea) taskArray[i]).getPriorityColor());
          newLabel.setOpaque(true);
          newLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
          taskPanel.add(newLabel);

          this.setUpDisplayPanel((BoxTextArea) taskArray[i]);
        }
      } // end of for loop searching for corresponding tasks

      /*
       * if (displayPanel.getComponentCount() != 0 && selected && !popUpExist) {
       * newWindow.add(displayPane); newWindow.setSize(new
       * Dimension(WINDOW_WIDTH, WINDOW_HEIGHT)); newWindow.setVisible(true);
       * popUpExist = true; newWindow.addWindowListener(new WindowAdapter() {
       * public void windowClosing(WindowEvent e) { popUpExist = false;
       * table.clearSelection(); } }); }
       */

      // add the components into the cell panel
      this.add(dayLabel, BorderLayout.NORTH);
      this.add(taskPanel);

    }
    return this;
  }
  
  /**
   * Give "today" a special color on the calendar
   * 
   * @param value
   *          - the day of the month passed in for this cell
   */
  private void setDaysBackground(int value) {

    // the actual real life date
    int realDay = myCalendar.get(Calendar.DAY_OF_MONTH);
    int realMonth = myCalendar.get(Calendar.MONTH);
    int realYear = myCalendar.get(Calendar.YEAR);

    // compare real life date with this cell's date
    if (realDay == value
        && realMonth == newCalendar.get(Calendar.MONTH)
        && realYear == newCalendar.get(Calendar.YEAR)) {

      this.setBackground(Color.ORANGE);
      taskPanel.setBackground(Color.ORANGE);
    }
  }

  /**
   * Setup a panel that display all the tasks due on the day specified by this
   * cell
   * 
   * @param task:
   *          the task box that is contained in this cell
   */
  private void setUpDisplayPanel(BoxTextArea task) {

    // getting the task text into the container
    JTextArea taskClone;
    if (task.getTextRep().length() != 0) {
      taskClone = new JTextArea(task.getTextRep());
    } else {
      taskClone = new JTextArea("EMPTY !!!!!?");
    }

    taskClone.setPreferredSize(new Dimension(DISPLAY_WIDTH, DISPLAY_HEIGHT));
    taskClone.setSize(new Dimension(DISPLAY_WIDTH, DISPLAY_HEIGHT));

    // the wrap around functionality of the text area
    taskClone.setWrapStyleWord(true);
    taskClone.setLineWrap(true);
    taskClone.insert("PRIORITY LEVEL: " + task.getPriorityLv() + "\n", 0);
    taskClone.setBackground(task.getPriorityColor());
    taskClone.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    taskClone.setEditable(false);
    displayPanel.add(taskClone);

  }

  /**
   * Create the pop up window that displays all the tasks on THIS day
   * 
   * @param table
   *          - the table that holds the table of days
   */
  public void createPopUp(JTable table) {

    // display the panel of tasks only if there are tasks on that day
    if (displayPanel.getComponentCount() != 0) {
      newWindow.add(displayPane);
      newWindow.setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
      newWindow.setVisible(true);
    }
  }
}
