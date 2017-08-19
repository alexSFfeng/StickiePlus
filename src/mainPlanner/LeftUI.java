package mainPlanner;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import calendarFunctions.MainCalendar;
import colorSchemes.ColorScheme;
import colorSchemes.DeepSpace;

/**
 * Left side UI configurations for Mercurial
 * 
 * @author Shanfeng Feng
 * @version 0.1
 * @since 2017-04-10
 *
 */
public class LeftUI implements UI_Panel {

  // left portion of the UI
  private JPanel leftPanel,leftPanelTop,leftPanelBot,leftPanelBotTop;
  private JScrollPane leftScroll;
  private JLabel myProductivity;
  private JToolBar addRemoveBar;
  private JButton addButtonLP, removeButtonLP, selectButton;
  private JButton sortHighToLow, sortLowToHigh;
  private boolean selectedAll;
  private ColorScheme currentScheme;

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
  public LeftUI (StickiePlus frame){
    
    // left panel initializations
    leftPanel = new JPanel(new BorderLayout());
    leftPanelTop = new JPanel();
    leftPanelBot = new JPanel(new BorderLayout());
    leftPanelBotTop = new JPanel();
    leftPanelBotTop.setLayout(new BoxLayout(leftPanelBotTop, BoxLayout.Y_AXIS));
    leftScroll = new JScrollPane(leftPanelBotTop);
    myProductivity = new JLabel("  Future Goals and Tasks:   ");
    myProductivity.setBorder(BorderFactory
        .createEtchedBorder(EtchedBorder.LOWERED, Color.GRAY, Color.BLACK));
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
    
    this.setScheme(currentScheme = new DeepSpace());

    frame.add(leftPanel,BorderLayout.WEST);
    
    // listen to button actions
    addButtonLP.addActionListener(new AddButton());
    removeButtonLP.addActionListener(new RemoveButton());
    selectButton.addActionListener(new SelectButton());
    sortHighToLow.addActionListener(new SortHighToLow());
    sortLowToHigh.addActionListener(new SortLowToHigh());

    // load the previously save state of this UI_Panel
    this.loadState();

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
      new BoxTextArea(leftPanelBotTop, true);
      leftPanelBotTop.revalidate();
      MainCalendar.refreshCalendar(MainCalendar.getProgMonth(),
          MainCalendar.getProgYear());
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

      // no component left; select all boolean becomes false;
      if (leftPanelBotTop.getComponentCount() == 0) {
        selectButton.setText("Select All");
        selectedAll = false;
      }
      // since last validate doesn't not show real time visual update, needs
      // repaint
      leftPanelBotTop.repaint();

      // refresh the calendar to remain in sync
      MainCalendar.refreshCalendar(MainCalendar.getProgMonth(),
          MainCalendar.getProgYear());

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

      setSelection(leftPanelBotTop, selectedAll);
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
      MainCalendar.refreshCalendar(MainCalendar.getProgMonth(),
          MainCalendar.getProgYear());
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
      MainCalendar.refreshCalendar(MainCalendar.getProgMonth(),
          MainCalendar.getProgYear());

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
   * Set whole UI color
   */
  public void setScheme(ColorScheme scheme) {

    currentScheme = scheme;

    // panel colors
    StickiePlus.setComponentColor(leftPanel, scheme.getLeftUIShade());
    StickiePlus.setComponentColor(leftPanelBot, scheme.getLeftUIShade());
    StickiePlus.setComponentColor(leftPanelBotTop, scheme.getLeftUIShade());
    StickiePlus.setComponentColor(addRemoveBar, scheme.getLeftUIShade());

    // box colors
    for (int i = 0; i < leftPanelBotTop.getComponentCount(); i++) {
      ((BoxTextArea) leftPanelBotTop.getComponent(i)).setScheme(scheme);
    }

  }

  /**
   * get the current color scheme
   * 
   * @return the current color scheme
   */
  public ColorScheme getScheme() {
    return currentScheme;
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
  public void setSelection(JPanel containerPanel, boolean selectAllMode) {

    if (containerPanel.getComponentCount() > 0) {

      selectedAll = selectAllMode;

      if (selectedAll) {
        selectButton.setText("Deselect All");
      } else {
        selectButton.setText("Select All");
      }

    }

  }

  /**
   * Return an array of all the tasks in the leftUI containerPanel
   * 
   * @return the array of assigned tasks
   */
  public Object[] getAllTasks() {

    return leftPanelBotTop.getComponents();

  }

  /**
   * Save the current state of the left UI by serializing all the box text area
   * objects
   */
  @Override
  public void saveState() {

    try {

      // create the file for saving and open stream to save data
      File leftUiDateFile = new File("leftUIBoxes.ser");
      FileOutputStream fileOut = new FileOutputStream(leftUiDateFile, false);
      ObjectOutputStream objOut = new ObjectOutputStream(fileOut);

      // write the array of components (boxes) into file
      objOut.writeObject(leftPanelBotTop.getComponents());

      // save the selected scheme
      objOut.writeObject(currentScheme);

      // close stream
      objOut.close();
      fileOut.close();
    } catch (IOException e) {
      System.err.println("Failure to write to file?");
      e.printStackTrace();
    }

  }

  /**
   * Load all the serialized objects that belongs to this UI.
   */
  @Override
  public void loadState() {

    Object[] prevBoxes = null;
    try {

      // open the data file and the input stream
      FileInputStream fileIn = new FileInputStream("leftUIBoxes.ser");
      ObjectInputStream objIn = new ObjectInputStream(fileIn);

      // read in the array of stored objects
      prevBoxes = (Object[]) objIn.readObject();

      // reload scheme
      this.setScheme((ColorScheme) objIn.readObject());

      // close stream
      objIn.close();
      fileIn.close();

      // add the box objects back to the panel
      for (int i = 0; prevBoxes != null && i < prevBoxes.length; i++) {
        leftPanelBotTop.add((BoxTextArea) prevBoxes[i]);
      }

    } catch (FileNotFoundException e) {
      // normal, first time opening
    } catch (IOException e) {
      System.err.println("Failure to read leftUI data file.");
      e.printStackTrace();
      return;
    } catch (ClassNotFoundException c) {
      c.printStackTrace();
      return;
    }

  }

}
