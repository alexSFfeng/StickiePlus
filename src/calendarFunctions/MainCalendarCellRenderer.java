
package calendarFunctions;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import mercurial.LeftUI;

/**
 * Renderer for main calendar cell display
 * @author Shanfeng Feng
 * @version 0.1
 * @since 2017-07-24
 *
 */
public class MainCalendarCellRenderer extends JPanel implements TableCellRenderer{

  private JScrollPane displayPane;
  private JLabel dayLabel;
  private JPanel taskPanel;

  private Calendar myCalendar, newCalendar;
  private LeftUI refUI;

  private static final int SATURDAY_COL = 6;

  public MainCalendarCellRenderer(Calendar realCalendar,
      Calendar currentCalendar, LeftUI allTaskUI) {

    this.setLayout(new BorderLayout());
    myCalendar = realCalendar;
    newCalendar = currentCalendar;
    refUI = allTaskUI;

  }
  public Component getTableCellRendererComponent(JTable table, Object value,
                                                 boolean selected, boolean focused,
                                                 int row, int column){
    this.removeAll();

    // selection colors
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

    if (value != null) {
      dayLabel = new JLabel(String.valueOf(value));
      taskPanel = new JPanel();
      taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.Y_AXIS));
      taskPanel.setBackground(this.getBackground());
      this.add(dayLabel, BorderLayout.NORTH);
      displayPane = new JScrollPane(taskPanel);
      displayPane.setBackground(MainCalendar.CALENDAR_COLOR);
      displayPane.setBorder(BorderFactory.createEmptyBorder());
      this.add(displayPane);
    }
    return this;
  }
  
  private void setDaysBackground(int value) {

    int realDay = myCalendar.get(Calendar.DAY_OF_MONTH);
    int realMonth = myCalendar.get(Calendar.MONTH);
    int realYear = myCalendar.get(Calendar.YEAR);

    System.out.println("Day = " + realDay);
    System.out.println("Month = " + realMonth);
    if (realDay == value
        && realMonth == newCalendar.get(Calendar.MONTH)
        && realYear == newCalendar.get(Calendar.YEAR)) {

      this.setBackground(Color.RED);
      taskPanel.setBackground(Color.RED);
    }
  }
}
