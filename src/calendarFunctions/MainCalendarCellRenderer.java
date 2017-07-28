
package calendarFunctions;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
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

  // the calendar references and the UI that contains all the tasks
  private Calendar myCalendar, newCalendar;
  private LeftUI refUI;

  // the column of the calendar that is Saturday
  private static final int SATURDAY_COL = 6;

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
  @SuppressWarnings("deprecation")
  public Component getTableCellRendererComponent(JTable table, Object value,
                                                 boolean selected, boolean focused,
                                                 int row, int column){

    // remove all the components in this cell
    this.removeAll();

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
      dayLabel = new JLabel(String.valueOf(value));

      // panel that stores all the tasks on that day
      taskPanel = new JPanel();
      taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.Y_AXIS));
      taskPanel.setBackground(this.getBackground());

      // put corresponding tasks into the calendar cell
      Object[] taskArray = refUI.getAllTasks();

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
        }
      }
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
}
