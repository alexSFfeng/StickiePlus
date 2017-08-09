package mainPlanner;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
/**
 * Panels for the program: tool bars, info display
 * 
 * @author Shanfeng Feng
 * @version 0.1
 * @since 2017-03-30
 *
 */
public class TopUI implements ActionListener, UI_Panel {

  // top portion of the UI
  private JMenuBar menuBar;
  private JPanel topPanel, topLPanel, topMPanel, topRPanel;
  private JMenu stickiePlus, file, view, event, goal, setting;
  private JLabel shortGoals, tasks, memo;

  // the three top UI tasks container
  private static JPanel goalField;
  private JPanel taskField;
  private JScrollPane goalPane, taskPane, memoPane;
  private JTextArea memoArea;
  private JToolBar goalBar, taskBar;
  private JButton selectButtonLeft,
      addButtonMid, removeButtonMid, selectButtonMid, sortDailyTasks;
  private JButton sortLHighToLow, sortLLowToHigh;

  // top ui constants for border and view port area
  private static final int BORDER_STROKE = 2;

  // appearance constants
  private static final int SLOT_WIDTH = 200;
  private static final int SLOT_HEIGHT = 200;

  // padding between each task box
  private static final int BORDER_PADDING = 10;
  private static final int BOX_PADDING = 30;
  private static final int TOP_GRAYSCALE = 114;

  // constant strings
  private static final String ADD_GOAL = "add goal boxField";
  private static final String REMOVE_GOAL = "remove goal boxField";
  private static final String SELECT_GOALS = "select all goals";
  private static final String ADD_TASK = "add task boxField";
  private static final String REMOVE_TASK = "remove task boxField";
  private static final String SELECT_TASKS = "select all tasks";
  private static final String HIGH_LOW = "SORT HIGH TO LOW";
  private static final String LOW_HIGH = "SORT LOW TO HIGH";
  private static final String SORT_TASKS = "SORT TASK";
  
  // boolean for keeping track of selection status (selectAll button)
  private boolean selectAllGoals, selectAllTasks;

  /**
   * Ctor for connecting TOP UI with mainframe
   * @param container: the frame to connect with
   */
  public TopUI(StickiePlus container){
    
    // initialize menu options
    menuBar = new JMenuBar();
    stickiePlus = new JMenu("Stickie+");
    setting = new JMenu("Setting");
    
    // initial selection status is false (select all button hasn't been clicked
    selectAllGoals = selectAllTasks = false;

    // add related buttons to menuBar
    menuBar.add(stickiePlus);
    menuBar.add(setting);
    container.setJMenuBar(menuBar);
    
    // declaring panel objects
    topPanel = new JPanel();
    topPanel.setLayout(new BoxLayout(topPanel,BoxLayout.X_AXIS));
    topLPanel = new JPanel(new BorderLayout());
    topMPanel = new JPanel(new BorderLayout());
    topRPanel = new JPanel(new BorderLayout());

    // instantiating JButtons and JToolBars

    /*-------------------------GOALS--------------------------*/
    goalBar = new JToolBar();
    selectButtonLeft = new JButton("Select All");
    selectButtonLeft.setActionCommand(SELECT_GOALS);
    sortLHighToLow = new JButton("Sort: ^");
    sortLLowToHigh = new JButton("Sort: v");
    sortDailyTasks = new JButton("Sort");
    
    /*------------------------TASKS---------------------------*/
    taskBar = new JToolBar();
    addButtonMid = new JButton("+");
    addButtonMid.setActionCommand(ADD_TASK);
    removeButtonMid = new JButton("-");
    removeButtonMid.setActionCommand(REMOVE_TASK);
    selectButtonMid = new JButton("Select All");
    selectButtonMid.setActionCommand(SELECT_TASKS);

    
    /*---------- add this UI as listener to the buttons.------------*/
    addButtonMid.addActionListener(this);
    removeButtonMid.addActionListener(this);
    selectButtonLeft.addActionListener(this);
    selectButtonMid.addActionListener(this);
    
    /*----------- sort button event handling ------------------------*/
    sortLHighToLow.addActionListener(new SortBoxes());
    sortLHighToLow.setActionCommand(HIGH_LOW);
    sortLHighToLow.setToolTipText("Sort by high priority");
    sortLLowToHigh.addActionListener(new SortBoxes());
    sortLLowToHigh.setActionCommand(LOW_HIGH);
    sortLLowToHigh.setToolTipText("Sort by low priority");
    sortDailyTasks.addActionListener(new SortTasks());
    sortDailyTasks.setActionCommand(SORT_TASKS);

    /*----------- labels for panels and the corresponding text fields-------*/
    shortGoals = new JLabel("  Today's Goals: ");
    shortGoals.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED,
        Color.GRAY, Color.BLACK));
    tasks = new JLabel("  Daily Tasks: ");
    tasks.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED,
        Color.GRAY, Color.BLACK));
    memo = new JLabel("  Memo/Note:  ");
    memo.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED,
        Color.GRAY, Color.BLACK));

    // setting the header labels
    container.setHeaderFont(shortGoals);
    container.setHeaderFont(tasks);
    container.setHeaderFont(memo);
    
    /*----------- Panels for storing tasks and goals ----------------------*/
    goalField = new JPanel();
    taskField = new JPanel();
    memoArea = new JTextArea();

    memoArea.setWrapStyleWord(true);
    memoArea.setLineWrap(true);

    // layout management
    goalField.setLayout(new BoxLayout(goalField, BoxLayout.Y_AXIS));
    taskField.setLayout(new BoxLayout(taskField, BoxLayout.Y_AXIS));

    /*----------------- scrollable panel -----------------------------*/
    goalPane = new JScrollPane(goalField);
    taskPane = new JScrollPane(taskField);
    memoPane = new JScrollPane(memoArea);

    /*------------------- Panel fixed size---------------------------*/
    topLPanel.setPreferredSize(new Dimension(SLOT_WIDTH, SLOT_HEIGHT));
    topRPanel.setPreferredSize(new Dimension(SLOT_WIDTH, SLOT_HEIGHT));
    topMPanel.setPreferredSize(new Dimension(SLOT_WIDTH, SLOT_HEIGHT));


    /*------------------ set up the border for top ui scroll panes-----------*/
    goalPane
        .setBorder(BorderFactory.createLineBorder(Color.BLACK, BORDER_STROKE));
    taskPane
        .setBorder(BorderFactory.createLineBorder(Color.BLACK, BORDER_STROKE));
    memoPane
        .setBorder(BorderFactory.createLineBorder(Color.BLACK, BORDER_STROKE));
    
    // add toolbars to each panel
    container.addToolBars(topLPanel, null, null,
        selectButtonLeft, sortLHighToLow, sortLLowToHigh, goalBar);
    container.addToolBars(topMPanel, addButtonMid, removeButtonMid,
        selectButtonMid, sortDailyTasks, null, taskBar);
    
    /*---------------- add components to top panels ----------------------*/
    topLPanel.add(shortGoals,BorderLayout.NORTH);
    topLPanel.add(goalPane);
    topMPanel.add(tasks,BorderLayout.NORTH);
    topMPanel.add(taskPane);
    topRPanel.add(memo, BorderLayout.NORTH);
    topRPanel.add(memoPane);
    
    
    /*--------------------------- layout paddings------------------------*/
    topPanel.add(Box.createRigidArea(new Dimension(BOX_PADDING,0)));
    topPanel.add(topLPanel);
    topPanel.add(Box.createRigidArea(new Dimension(BOX_PADDING,0)));
    topPanel.add(topMPanel);
    topPanel.add(Box.createRigidArea(new Dimension(BOX_PADDING,0)));
    topPanel.add(topRPanel);
    topPanel.add(Box.createRigidArea(new Dimension(BOX_PADDING,0)));
    
    /*----------- add padding to the topPanel && background color-------*/
    topPanel.setBorder(new EmptyBorder(BORDER_PADDING,0,BORDER_PADDING,0));
    container.setComponentColor(topPanel, TOP_GRAYSCALE);
    container.setComponentColor(goalField, TOP_GRAYSCALE);
    container.setComponentColor(taskField, TOP_GRAYSCALE);
    container.setComponentColor(memoPane, TOP_GRAYSCALE);
    container.setComponentColor(goalBar, TOP_GRAYSCALE);
    container.setComponentColor(taskBar, TOP_GRAYSCALE);
    
    container.add(topPanel,BorderLayout.NORTH);

    /*---------------LOAD TODAY'S TASKS)-------------*/
    loadTodayGoals(StickiePlus.getLeftUI().getAllTasks());
    this.loadState();
  }

  /**
   * Controls what happen when add/remove buttons are clicked for each text
   * pane.
   * 
   * @param e:
   *          the button clicked event to be tracked
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    
    /*----------------------ADDING--------------------------*/

    if (e.getActionCommand().equals(ADD_TASK)) {
      new DailyTaskBox(taskField);
      taskField.revalidate();
    }


    /*-----------------------REMOVING----------------------*/

    if (e.getActionCommand().equals(REMOVE_TASK)) {
      removeBox(taskField);
    }

    /*-----------------------SELECTING----------------------*/

    if (e.getActionCommand().equals(SELECT_GOALS)) {
      selectAllBox(goalField, selectAllGoals = !selectAllGoals);
      setSelection(goalField, selectAllGoals);
    }

    if (e.getActionCommand().equals(SELECT_TASKS)) {
      selectDailyTask(taskField, selectAllTasks = !selectAllTasks);
      setSelection(taskField, selectAllTasks);
    }

  }

  /*-------------------PRIVATE CLASS SORT BUTTON HANDLING-----------------*/

  /**
   * Sorting all the today's goals (event handling for sort button)
   * 
   * @author Shanfeng Feng
   * @since 2017-07-05
   * @version 0.1
   */
  private class SortBoxes implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      sortBox(e, goalField);
    }

  }

  /**
   * Sorting all the daily tasks (event handling for sort button)
   * 
   * @author Shanfeng Feng
   * @since 2017-07-05
   * @version 0.1
   */
  private class SortTasks implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      sortBox(e, taskField);
    }

  }

  /**
   * remove a box object from a target Panel
   * 
   * @param targetPanel:
   *          the panel that holds the BoxTextArea to be removed
   */
  private void removeBox(JPanel targetPanel) {

    // remove all the selected daily tasks
    if (targetPanel == taskField) {
      for (int i = targetPanel.getComponents().length - 1; i >= 0; i--) {

        // if the box is selected then remove
        if (((DailyTaskBox) targetPanel.getComponent(i)).isSelected()) {
          targetPanel.remove(i);
          targetPanel.revalidate();
        }

      }

      // if after no boxes left, select button update text and the
      // boolean for selecting all tasks becomes false
      if (targetPanel.getComponentCount() == 0) {
        toggleSelectButton(selectButtonMid, selectAllTasks = false);
      }
    }

    // since last validate doesn't not show real time visual update, needs
    // repaint
    targetPanel.repaint();

  }

  /**
   * Select / deselect all the BoxTextArea objects in a specified panel
   * 
   * @param targetPanel:
   *          the panel that's being act upon
   * @param selectStatus:
   *          tells if this function is a select or deselect call
   */
  private void selectAllBox(JPanel targetPanel, boolean selectStatus) {

    for (int i = 0; i < targetPanel.getComponentCount(); i++) {
      ((BoxTextArea) (targetPanel.getComponent(i))).setSelected(selectStatus);
    }

  }

  /**
   * Select / deselect all the DailyTaskBox objects in a specified panel
   * 
   * @param targetPanel:
   *          the panel that's being act upon
   * @param selectStatus:
   *          tells if this function is a select or deselect call
   */
  private void selectDailyTask(JPanel targetPanel, boolean selectStatus) {

    for (int i = 0; i < targetPanel.getComponentCount(); i++) {
      ((DailyTaskBox) (targetPanel.getComponent(i))).setSelected(selectStatus);
    }

  }

  /**
   * Sort all the BoxTextArea objects in a specified panel
   * 
   * @param e:
   *          the button event that invoked the call
   * @param targetPanel:
   *          the panel that holds the BoxTextArea objects
   */
  @SuppressWarnings("unchecked")
  private void sortBox(ActionEvent e, JPanel targetPanel) {

    // obtain the array of BoxTextArea objects in that panel
    Component[] boxArray = targetPanel.getComponents();

    // sort base on high to low priority
    if (e.getActionCommand() == HIGH_LOW) {
      Arrays.sort(boxArray, new BoxComparator());
    }
    // sort base on low to high priority
    else if (e.getActionCommand() == LOW_HIGH) {
      Arrays.sort(boxArray, new BoxReverseComparator());
    }
    else if (e.getActionCommand() == SORT_TASKS) {
      Arrays.sort(boxArray, new DailyTaskComparator());
    }

    // remove all the boxes
    targetPanel.removeAll();

    // add boxes back in desired order
    for (int i = 0; i < boxArray.length; i++) {
      targetPanel.add(boxArray[i]);
    }

    targetPanel.revalidate();
    targetPanel.repaint();

  }

  /**
   * Set the selection status for all the boxes in the target panel
   * 
   * @param containerPanel:
   *          the panel to be act upon
   * @param selectionMode:
   *          the selection status that would be set
   */
  @Override
  public void setSelection(JPanel containerPanel, boolean selectionMode) {

    // selection is only available for setting if there are boxes
    if (containerPanel.getComponentCount() > 0) {

      // updating the selection status of today's goal panel (select/deselect
      // all)
      if (containerPanel == goalField) {
        selectAllGoals = selectionMode;
        toggleSelectButton(selectButtonLeft, selectionMode);
      }
      // updating the selection status of daily task panel (select/deselect all
      else if (containerPanel == taskField) {
        selectAllTasks = selectionMode;
        toggleSelectButton(selectButtonMid, selectionMode);
      }

    }
  }

  /**
   * Toggles the text display for the select All button (select all or deselect
   * all)
   * 
   * @param targetButton:
   *          the button to change text
   * @param selectionMode:
   *          the selection mode that determines the text
   */
  private void toggleSelectButton(JButton targetButton, boolean selectionMode) {

    if (selectionMode) {
      targetButton.setText("Deselect All");
    } else {
      targetButton.setText("Select All");
    }

  }

  /**
   * Load all of today's Tasks
   * 
   * @param goals:
   *          the list of boxes to be added to today's tasks
   */
  public static void loadTodayGoals(Object[] goals) {

    // nothing has been initialized yet
    if (goals == null) {
      return;
    }

    goalField.removeAll();
    goalField.repaint();

    // preparing for due date comparison
    Calendar tempCalendar = Calendar.getInstance();
    int realYear = tempCalendar.get(Calendar.YEAR);
    int realMonth = tempCalendar.get(Calendar.MONTH);
    int realDay = tempCalendar.get(Calendar.DATE);

    // add back in the box to the panel if it matches the date
    for (int i = 0; i < goals.length; i++) {

      tempCalendar.setTime(((BoxTextArea) goals[i]).getFullDate());
      // if the date fits today, then
      if (tempCalendar.get(Calendar.YEAR) == realYear
          && tempCalendar.get(Calendar.MONTH) == realMonth
          && tempCalendar.get(Calendar.DATE) == realDay) {
        new BoxTextArea(((BoxTextArea) goals[i]), goalField, false);

      }
    }

    goalField.revalidate();

  }

  /**
   * Serialize the daily task field and Memo field and save the state into a
   * data file
   */
  @Override
  public void saveState() {

    try {

      // open file and streams for writing data (Overwrite previous file)
      File topUiDateFile = new File("topUIBoxes.ser");
      FileOutputStream fileOut = new FileOutputStream(topUiDateFile, false);
      ObjectOutputStream objOut = new ObjectOutputStream(fileOut);

      // preparing objects to store task box data
      int numTasks = taskField.getComponentCount();
      Object[] taskBoxes = taskField.getComponents();
      Integer[] timeId = new Integer[numTasks];
      String[] taskText = new String[numTasks];

      // recording in task box selected time and task info
      for (int i = 0; i < numTasks; i++) {
        timeId[i] = ((DailyTaskBox) taskBoxes[i]).getOrderId();
        taskText[i] = ((DailyTaskBox) taskBoxes[i]).getTaskText();
      }

      // writing the data into the file
      objOut.writeObject(timeId);
      objOut.writeObject(taskText);

      // writing the memo text into file
      objOut.writeObject(memoArea.getText());

      // closing stream
      objOut.close();
      fileOut.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  /**
   * Load the previously saved state by reading data file
   */
  @Override
  public void loadState() {

    // array of information to be read back
    Object[] boxesTime = null;
    Object[] boxesText = null;

    try {

      // creating the input streams for reading info
      FileInputStream fileIn = new FileInputStream("topUIBoxes.ser");
      ObjectInputStream objIn = new ObjectInputStream(fileIn);

      // initializing data storage
      boxesTime = (Object[]) objIn.readObject();
      boxesText = (Object[]) objIn.readObject();

      // reconstruct task boxes base on the data last saved
      for (int i = 0; boxesTime != null && boxesText != null
          && i < boxesTime.length; i++) {

        new DailyTaskBox(taskField, (String) boxesText[i], (int) boxesTime[i]);

      }

      // reconstruct memoArea's text
      this.memoArea.append((String) objIn.readObject());

      // close streams
      objIn.close();
      fileIn.close();


    } catch (FileNotFoundException e) {
      // normal, first time opening
    } catch (IOException e) {
      System.err.println("Failure to read topUI data file.");
      e.printStackTrace();
      return;
    } catch (ClassNotFoundException c) {
      c.printStackTrace();
      return;
    }

  }

}
