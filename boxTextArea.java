package mercurial;

import java.awt.BorderLayout;
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
 * 
 * @author Shanfeng Feng
 * @version 0.1
 * @since 2017-04-10
 *
 */
public class boxTextArea implements ItemListener, ChangeListener {

  private JTextArea editable;
  private JCheckBox checkbox;
  private JScrollPane boxAreaPane;
  private JPanel boxPanel;
  private JSlider prioritySlider;
  
  private boolean selected;
  private int priorityLv;

  private static final int FIELD_COL = 22;
  private static final int FIELD_ROW = 5;
  private static final int MAX_PRIORITY = 10;
  private static final int MIN_PRIORITY = 1;

  public boxTextArea() {

    // instantiate components for a checkbox and corresponding text area
    boxPanel = new JPanel(new BorderLayout());
    editable = new JTextArea(FIELD_COL, FIELD_ROW);
    checkbox = new JCheckBox();
    boxAreaPane = new JScrollPane(editable);
    prioritySlider = new JSlider(MIN_PRIORITY, MAX_PRIORITY);

    boxPanel.add(checkbox, BorderLayout.WEST);
    boxPanel.add(prioritySlider, BorderLayout.SOUTH);
    boxPanel.add(boxAreaPane);

    editable.setWrapStyleWord(true);
    editable.setLineWrap(true);

    prioritySlider.setMajorTickSpacing(1);
    prioritySlider.setPaintTicks(true);
    prioritySlider.setPaintLabels(true);

    checkbox.addItemListener(this);
    prioritySlider.addChangeListener(this);

  }

  /**
   * record the state of the check box and update slider status.
   * 
   * @param e:
   *          the event of selecting/deselecting a checkbox
   */
  @Override
  public void itemStateChanged(ItemEvent e) {

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
   * @param e:
   *          the change event to the slider
   */
  @Override
  public void stateChanged(ChangeEvent e) {

    JSlider source = (JSlider) e.getSource();
    if (e.getSource() instanceof JSlider) {
      priorityLv = source.getValue();
    }
    
  }

}
