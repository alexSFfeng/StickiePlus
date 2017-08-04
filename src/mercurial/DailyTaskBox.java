package mercurial;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.Serializable;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * A typical task box consist of check box, textField, and drop down time
 * deadline
 * 
 * @author Shanfeng Feng
 * @version 0.1
 * @since 2017-08-01
 */
public class DailyTaskBox extends JPanel
    implements FocusListener, ItemListener, Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -837945227972515380L;
  
  // components that makes up this box object
  private JCheckBox checkBox;
  private JTextField taskField;
  private JComboBox<String> timeCombo;
  private boolean selected;

  private static final int NUM_HOURS_HALF_DAY = 12;
  private static final Dimension BOX_DIMENSION = new Dimension(500, 30);
  private static final Color FOCUS_COLOR = new Color(229, 195, 27);

  /**
   * Ctor to make the box and add to targetPanel
   * 
   * @param targetPanel:
   *          the JPanel that would contain all the dailytask boxes
   */
  public DailyTaskBox(JPanel targetPanel) {

    // initializing instances
    checkBox = new JCheckBox();
    taskField = new JTextField();
    timeCombo = new JComboBox();
    selected = false;

    /*------------- combo box setting and population--------------*/
    timeCombo.setEditable(false);

    timeCombo.addItem("Set Time");
    timeCombo.addItem("12:00 AM");
    timeCombo.addItem("12:30 AM");
    for (int i = 1; i < NUM_HOURS_HALF_DAY; i++) {
      timeCombo.addItem(i + ":00 AM");
      timeCombo.addItem(i + ":30 AM");
    }

    timeCombo.addItem("12:00 PM");
    timeCombo.addItem("12:30 PM");
    for (int i = 1; i < NUM_HOURS_HALF_DAY; i++) {
      timeCombo.addItem(i + ":00" + "PM");
      timeCombo.addItem(i + ":30 PM");
    }

    /*--------------------task field setting -----------------------*/
    taskField.setBackground(FOCUS_COLOR);
    taskField.addFocusListener(this);

    /*-----------------this panel appearance and setting ----------*/
    this.setMaximumSize(BOX_DIMENSION);
    this.setSize(BOX_DIMENSION);

    /*---------------adding components to the main container--------*/
    this.setLayout(new BorderLayout());
    this.add(checkBox, BorderLayout.WEST);
    this.add(timeCombo, BorderLayout.EAST);
    this.add(taskField);

    this.setBackground(targetPanel.getBackground());
    targetPanel.add(this);
  }

  /**
   * When object receives focus text field becomes editable
   * 
   * @param e:
   *          the focus event invoking the call
   */
  @Override
  public void focusGained(FocusEvent e) {
    taskField.setEditable(true);
    taskField.setBackground(Color.WHITE);
  }

  /**
   * When object receives focus text field becomes non-editable
   * 
   * @param e:
   *          the focus event invoking the call
   */
  @Override
  public void focusLost(FocusEvent e) {
    taskField.setEditable(false);
    taskField.setBackground(FOCUS_COLOR);
  }

  /**
   * Checkbox changing according to user selection
   * 
   * @param e:
   *          the box checking event that invoked the call
   */
  @Override
  public void itemStateChanged(ItemEvent e) {
    // slider is only available for adjustment when box is selected
    if (e.getStateChange() == ItemEvent.SELECTED) {
      this.selected = true;
    } else {
      this.selected = false;
    }
  }

  /**
   * Setter for selection boolean
   * 
   * @param select:
   *          the boolean value to be set
   */
  public void setSelected(boolean select) {
    selected = select;
    checkBox.setSelected(select);
  }

  /**
   * Getter for the selection boolean
   * 
   * @return the current status of the selection boolean
   */
  public boolean isSelected() {
    return selected;
  }

  /**
   * Getter for the order index to sort
   * 
   * @return the current order index based on the selected time
   */
  public int getOrderId() {
    return timeCombo.getSelectedIndex();
  }

}
