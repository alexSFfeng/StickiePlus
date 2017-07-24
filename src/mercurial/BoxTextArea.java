package mercurial;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import calendarFunctions.JDatePicker;

/**
 * check boxes that holds goal, task, and pastDue informations
 * A check box should have a text area (scrollable) next to it
 * Under it should be a slider from 1 - 10 for priority level.
 * 
 * @author Shanfeng Feng
 * @version 0.1
 * @since 2017-04-10
 *
 */
public class BoxTextArea extends JPanel
    implements ItemListener, ChangeListener {

  // components of a boxTextArea
  private JTextArea editable;
  private JCheckBox checkbox;
  private JScrollPane boxAreaPane;
  private JSlider prioritySlider;
  
  // due date component group
  private JPanel duePanel;
  private JLabel dueSign;
  private JDatePicker dueDatePicker;

  // the UI_Panel that this BoxTextArea object belongs to
  private UI_Panel refPanel;

  // determines whether or not this box is selected
  private boolean selected;
  // the priority level of this task
  private int priorityLv;

  // appearance constants
  private static final int SLOT_WIDTH = 200;
  private static final int SLOT_HEIGHT = 150;
  private static final int FIELD_COL = 22;
  private static final int FIELD_ROW = 4;
  private static final int MAX_PRIORITY = 10;
  private static final int MIN_PRIORITY = 1;
  private static final Dimension SLIDER_SIZE = new Dimension(300, 15);
  private static final Dimension SLIDER_PREFERRED_SIZE = new Dimension(300, 35);

  // box color background
  private static final Color PANEL_COLOR = Color.GRAY;

  // default priority 4-7 Box color (YELLOW) Caution
  private static final Color BOX_COLOR = new Color(255, 255, 55);
  private static final int CAUTION_THRESHOLD_LOW = 4;
  private static final int CAUTION_THRESHOLD_HIGH = 7;
  // low priority 1-3 Box color (Green) Safe
  private static final Color SAFE_COLOR = new Color(54, 237, 88);
  // high priority 8-10 Box color (Red) Danger
  private static final Color HIGH_PRIORITY_COLOR = new Color(239, 19, 19);

  /**
   * Ctor for initializing a BoxTextArea object and responsible for adding
   * listeners and initializing appearance
   * 
   * @param mainPanel:
   *          the panel to add this object to
   */
  public BoxTextArea(JPanel mainPanel, UI_Panel containerUI) {

    // instantiate components for a checkbox and corresponding text area
    this.setLayout(new BorderLayout());
    this.setSize(new Dimension(SLOT_WIDTH, SLOT_HEIGHT));
    this.setPreferredSize(new Dimension(SLOT_WIDTH, SLOT_HEIGHT));
    editable = new JTextArea(FIELD_ROW, FIELD_COL);
    checkbox = new JCheckBox();
    boxAreaPane = new JScrollPane(editable);
    prioritySlider = new JSlider(MIN_PRIORITY, MAX_PRIORITY);

    // due date component initialization
    dueDatePicker = new JDatePicker();
    dueSign = new JLabel("Due on");
    dueSign.setForeground(Color.red);
    duePanel = new JPanel(new BorderLayout());
    dueDatePicker.setDate(new Date());

    // button appearance
    duePanel.setBackground(Color.GRAY);
    dueDatePicker.setBackground(Color.GRAY);

    // putting together due component group
    duePanel.add(dueSign, BorderLayout.WEST);
    duePanel.add(dueDatePicker);

    // adding check box and text area to the panel
    this.add(checkbox, BorderLayout.WEST);
    this.add(prioritySlider, BorderLayout.SOUTH);
    this.add(duePanel, BorderLayout.NORTH);
    this.add(boxAreaPane);

    // the wrap around functionality of the text area
    editable.setWrapStyleWord(true);
    editable.setLineWrap(true);
    editable.setEditable(true);

    // slider appearance.
    prioritySlider.setMajorTickSpacing(1);
    prioritySlider.setPaintTicks(true);
    prioritySlider.setPaintLabels(true);
    prioritySlider.setSize(SLIDER_SIZE);
    prioritySlider.setPreferredSize(SLIDER_PREFERRED_SIZE);
    prioritySlider.setVisible(false);
    prioritySlider.setSnapToTicks(true);
    priorityLv = prioritySlider.getValue();

    // event handling listener
    checkbox.addItemListener(this);
    prioritySlider.addChangeListener(this);

    this.setBackground(PANEL_COLOR);
    editable.setBackground(BOX_COLOR);

    // store the reference to the UI_Panel that contains this object
    refPanel = containerUI;

    mainPanel.add(this);
  }

  /**
   * record the state of the check box and update slider status.
   * 
   * @param e: the event of selecting/deselecting a checkbox
   */
  @Override
  public void itemStateChanged(ItemEvent e) {

    // slider is only available for adjustment when box is selected
    if (e.getStateChange() == ItemEvent.SELECTED) {

      this.selected = true;
      this.prioritySlider.setVisible(true);
      this.prioritySlider.setEnabled(true);

    } else {
      
      this.selected = false;
      this.prioritySlider.setVisible(false);
      this.prioritySlider.setEnabled(false);

    }

  }

  /**
   * record the change to the priority slider and update priority level
   * 
   * @param e:the change event to the slider
   */
  @Override
  public void stateChanged(ChangeEvent e) {

    JSlider source = (JSlider) e.getSource();
    // check if it is the JSlider that is has changed
    if (e.getSource() instanceof JSlider) {
      this.priorityLv = source.getValue();

      // low priority color
      if (priorityLv < CAUTION_THRESHOLD_LOW) {
        editable.setBackground(SAFE_COLOR);
      }
      // high priority color
      else if (priorityLv > CAUTION_THRESHOLD_HIGH) {
        editable.setBackground(HIGH_PRIORITY_COLOR);
      }
      // default priority color
      else {
        editable.setBackground(BOX_COLOR);
      }
    }
    
  }
  
  /**
   * Get the priority level of this task
   * 
   * @return the priority level of the this task
   */
  public int getPriorityLv(){
    return this.priorityLv;
  }
  
  /**
   * Get the current selected status of the boxTextArea
   * 
   * @return the current selection status
   */
  public boolean isSelected(){
    return this.selected;
  }

  /**
   * Set the selected status of the boxTextArea (toggle effect)
   * 
   * @param select:
   *          the selection status to be set
   */
  public void setSelected(boolean select) {
    this.selected = select;
    checkbox.setSelected(select);
  }

  /**
   * Get the date selected by the user
   * 
   * @return the Date object that holds the due date selected
   */
  public Date getDate() {

    return dueDatePicker.getDate();

  }

}
