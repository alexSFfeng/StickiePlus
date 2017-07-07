package mercurial;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;

/**
 * Left side UI configurations for Mercurial
 * 
 * @author Shanfeng Feng
 * @version 0.1
 * @since 2017-04-10
 *
 */
public class LeftUI {

  // left portion of the UI
  private JPanel leftPanel,leftPanelTop,leftPanelBot,leftPanelBotTop;
  private JScrollPane leftScroll;
  private JLabel myProductivity;
  private JToolBar addRemoveBar;
  private JButton addButtonLP, removeButtonLP, selectButton;
  private JButton sortHighToLow, sortLowToHigh;
  private boolean selectedAll;

  // appearance constants
  private static final int BORDER_STROKE = 3;
  private static final int LEFT_GRAYSCALE = 91;
  private static final int BORDER_PADDING = 10;
  
  /**
   * Ctor for initializing the Left user interface and adding it to the main
   * frame
   * 
   * @param frame:
   *          the main frame of the program
   */
  public LeftUI (Mercurial frame){
    
    // left panel initializations
    leftPanel = new JPanel(new BorderLayout());
    leftPanelTop = new JPanel();
    leftPanelBot = new JPanel(new BorderLayout());
    leftPanelBotTop = new JPanel();
    leftPanelBotTop.setLayout(new BoxLayout(leftPanelBotTop, BoxLayout.Y_AXIS));
    leftScroll = new JScrollPane(leftPanelBotTop);
    myProductivity = new JLabel("Priority Tasks/Reminders: ");
    frame.setHeaderFont(myProductivity);
    
    // initial selection status is false (selectAll button hasn't been clicked)
    selectedAll = false;
    // scroll pane modifications
    leftScroll
        .setBorder(BorderFactory.createLineBorder(Color.BLACK, BORDER_STROKE));

    // components for left bottom panel
    addRemoveBar = new JToolBar();
    addButtonLP = new JButton("+");
    removeButtonLP = new JButton("-");
    selectButton = new JButton("Select All");
    sortHighToLow = new JButton("Sort: ^");
    sortLowToHigh = new JButton("Sort: v");

    // adding components to frame
    frame.addToolBars(leftPanelBot, addButtonLP, removeButtonLP, selectButton,
        sortHighToLow, sortLowToHigh, addRemoveBar);
    leftPanelBot.add(leftScroll);
    
    leftPanelTop.add(myProductivity);
    leftPanel.add(leftPanelTop,BorderLayout.NORTH);
    leftPanel.add(leftPanelBot);
    
    // color settings
    leftPanel.setBorder(new EmptyBorder(BORDER_PADDING,BORDER_PADDING,
                                        BORDER_PADDING,BORDER_PADDING));
    
    frame.setComponentColor(leftPanel, LEFT_GRAYSCALE);
    frame.setComponentColor(leftPanelBot, LEFT_GRAYSCALE);
    frame.setComponentColor(leftPanelBotTop, LEFT_GRAYSCALE);
    frame.setComponentColor(addRemoveBar, LEFT_GRAYSCALE);
    
    frame.add(leftPanel,BorderLayout.WEST);
    
    // listen to button actions
    addButtonLP.addActionListener(new AddButton());
    removeButtonLP.addActionListener(new RemoveButton());
    selectButton.addActionListener(new SelectButton());
    sortHighToLow.addActionListener(new SortHighToLow());
    sortLowToHigh.addActionListener(new SortLowToHigh());

  }

  /**
   * class that listens to the add button
   * 
   * @author Shanfeng Feng
   * @since 2017-07-06
   * @version 0.1
   *
   */
  private class AddButton implements ActionListener {

    /**
     * add a BoxTextArea to the target panel
     * 
     * @param e:
     *          the event that invoked the call
     */
    @Override
    public void actionPerformed(ActionEvent e) {
      new BoxTextArea(leftPanelBotTop);
      leftPanelBotTop.revalidate();
    }
  }

  /**
   * class that listens to the remove button
   * 
   * @author Shanfeng Feng
   * @since 2017-07-06
   * @version 0.1
   *
   */
  private class RemoveButton implements ActionListener {

    /**
     * event handling when the button was pressed, should remove all the
     * selected BoxTextArea objects
     * 
     * @param e:
     *          the event that invoked the call
     */
    @Override
    public void actionPerformed(ActionEvent e) {

      // remove all the selected boxes
      for (int i = leftPanelBotTop.getComponents().length - 1; i >= 0; i--) {

        if (((BoxTextArea) leftPanelBotTop.getComponent(i)).isSelected()) {
          leftPanelBotTop.remove(i);
          leftPanelBotTop.revalidate();
        }

      }

      // since last validate doesn't not show real time visual update, needs
      // repaint
      leftPanelBotTop.repaint();
    }

  }

  /**
   * class that listens to the selectButton
   * 
   * @author Shanfeng Feng
   * @since 2017-07-06
   * @version 0.1
   *
   */
  private class SelectButton implements ActionListener {

    /**
     * event handling when Select button was clicked, selects all boxes or
     * deselect all boxes based on the previous status of the boolean
     * selectedAll
     * 
     * @param e:
     *          the event that invoked the call
     */
    @Override
    public void actionPerformed(ActionEvent e) {
      // toggle boolean each time button is called
      selectedAll = !selectedAll;

      // select or deselect all boxes
      for (int i = 0; i < leftPanelBotTop.getComponentCount(); i++) {
        ((BoxTextArea) (leftPanelBotTop.getComponent(i)))
            .setSelected(selectedAll);
      }
    }
  }

  /**
   * Class that listens to sort ^ button
   * 
   * @author Shanfeng Feng
   * @since 2017-07-06
   * @version 0.1
   *
   */
  private class SortHighToLow implements ActionListener {

    /**
     * event handling when sorting button was clicked sorts all the BoxTextArea
     * objects in the panel base on higher priority coming first
     * 
     * @param e:
     *          the event that invoked the call
     */
    @SuppressWarnings("unchecked")
    @Override
    public void actionPerformed(ActionEvent e) {

      Component[] boxArray = leftPanelBotTop.getComponents();
      Arrays.sort(boxArray, new BoxComparator());

      // relist all the BoxTextArea objects in the target panel
      relistBoxes(boxArray, leftPanelBotTop);

    }

  }

  /**
   * Class that listens to sort v button
   * 
   * @author Shanfeng Feng
   * @since 2017-07-06
   * @version 0.1
   *
   */
  private class SortLowToHigh implements ActionListener {

    /**
     * event handling when sorting button was clicked sorts all the BoxTextArea
     * objects in the panel base on lower priority coming first
     * 
     * @param e:
     *          the event that invoked the call
     */
    @SuppressWarnings("unchecked")
    @Override
    public void actionPerformed(ActionEvent e) {

      Component[] boxArray = leftPanelBotTop.getComponents();
      Arrays.sort(boxArray, new BoxReverseComparator());

      // relist all the BoxTextArea objects after sorting
      relistBoxes(boxArray, leftPanelBotTop);

    }

  }

  /**
   * relist all the BoxTextArea objects in a given panel using a sorted array of
   * all those objects
   * 
   * @param boxArray:
   *          the sorted array of all the BoxTextArea object
   * @param targetPanel:
   *          the panel being act upon
   */
  private void relistBoxes(Component[] boxArray, JPanel targetPanel) {

    targetPanel.removeAll();

    // add boxes back in desired order
    for (int i = 0; i < boxArray.length; i++) {
      targetPanel.add(boxArray[i]);
    }

    targetPanel.revalidate();
    targetPanel.repaint();
  }

  /**
   * Sets the selection status of the selectButton; if a box was manually
   * selected by user, the selectedAll boolean should be set to false so that
   * when user click selectButton afterwards, it would be in selection mode
   * rather than deselection mode.
   * 
   * @param selectAllMode:
   *          the selection mode to be set
   */
  public void setSelection(boolean selectAllMode) {
    selectedAll = selectAllMode;
  }
}
