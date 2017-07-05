package mercurial;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
  
  // determines whether or not this box is selected
  private boolean selected;
  // the priority level of this task
  private int priorityLv;

  // appearance constants
  private static final int SLOT_WIDTH = 200;
  private static final int SLOT_HEIGHT = 100;
  private static final int FIELD_COL = 22;
  private static final int FIELD_ROW = 4;
  private static final int MAX_PRIORITY = 10;
  private static final int MIN_PRIORITY = 1;
  private static final Color PANEL_COLOR = new Color(51, 133, 255);
  private static final Color BOX_COLOR = new Color(255, 255, 55);
  private static final Dimension SLIDER_SIZE = new Dimension(300, 15);
  private static final Dimension SLIDER_PREFERRED_SIZE = new Dimension(300, 35);

  public BoxTextArea(JPanel mainPanel) {

    // instantiate components for a checkbox and corresponding text area
    this.setLayout(new BorderLayout());
    this.setSize(new Dimension(SLOT_WIDTH, SLOT_HEIGHT));
    this.setPreferredSize(new Dimension(SLOT_WIDTH, SLOT_HEIGHT));
    editable = new JTextArea(FIELD_ROW, FIELD_COL);
    checkbox = new JCheckBox();
    boxAreaPane = new JScrollPane(editable);
    prioritySlider = new JSlider(MIN_PRIORITY, MAX_PRIORITY);

    // adding check box and text area to the panel
    this.add(checkbox, BorderLayout.WEST);
    this.add(prioritySlider, BorderLayout.SOUTH);
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
    priorityLv = prioritySlider.getValue();

    checkbox.addItemListener(this);
    prioritySlider.addChangeListener(this);

    this.setBackground(PANEL_COLOR);
    editable.setBackground(BOX_COLOR);

    mainPanel.add(this);
  }

  /**
   * record the state of the check box and update slider status.
   * 
   * @param e: the event of selecting/deselecting a checkbox
   */
  @Override
  public void itemStateChanged(ItemEvent e) {

    if (e.getStateChange() == ItemEvent.SELECTED) {

      this.selected = true;
      this.prioritySlider.setVisible(true);
      this.prioritySlider.setEnabled(true);
      editable.setEditable(true);
      System.out.println("CAN EDIT");
    } else {
      
      this.selected = false;
      this.prioritySlider.setVisible(false);
      this.prioritySlider.setEnabled(false);
      editable.setEditable(false);
      System.out.println("CAN'T EDIT");
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
    if (e.getSource() instanceof JSlider) {
      this.priorityLv = source.getValue();
      System.out.println("Changing priority");
      System.out.println(priorityLv);
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
   */
  public void setSelected(boolean select) {
    this.selected = select;
    checkbox.setSelected(select);
  }

}
