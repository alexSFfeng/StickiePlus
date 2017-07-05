package mercurial;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;
/**
 * Panels for the program: tool bars, info display
 * 
 * @author Shanfeng Feng
 * @version 0.1
 * @since 2017-03-30
 *
 */
public class TopUI implements ActionListener {

  // top portion of the UI
  private JMenuBar menuBar;
  private JPanel topPanel, topLPanel, topMPanel, topRPanel;
  private JMenu mercurial,file,view,event,goal,setting;
  private JLabel shortGoals, tasks, pastDues;

  // the three top UI tasks container
  private JPanel goalField, taskField, pastDueField;
  private JScrollPane goalPane, taskPane, duePane;
  private JToolBar goalBar, taskBar, dueBar;
  private JButton addButtonLeft, removeButtonLeft, selectButtonLeft,
      addButtonRight, removeButtonRight, selectButtonRight, addButtonMid,
      removeButtonMid, selectButtonMid;
  private JButton sortLHighToLow, sortLLowToHigh, sortMHighToLow,
      sortMLowToHigh, sortRHighToLow, sortRLowToHigh;

  // top ui constants for border and view port area
  private static final int BORDER_STROKE = 2;
  private static final int FIELD_COL = 100;
  private static final int FIELD_ROW = 4;

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
  private static final String ADD_PASTDUE = "add due boxField";
  private static final String REMOVE_PASTDUE = "remove due boxField";
  private static final String SELECT_DUES = "select all dues";
  private static final String HIGH_LOW = "SORT HIGH TO LOW";
  private static final String LOW_HIGH = "SORT LOW TO HIGH";
  
  /**
   * Ctor for connecting TOP UI with mainframe
   * @param container: the frame to connect with
   */
  public TopUI(Mercurial container){
    
    // initialize menu options
    menuBar = new JMenuBar();
    mercurial = new JMenu("Mercurial");
    file = new JMenu("File");
    view = new JMenu("View");
    event = new JMenu("Event");
    goal = new JMenu("Goal");
    setting = new JMenu("Setting");
    
    // add related buttons to menuBar
    menuBar.add(mercurial);
    menuBar.add(file);
    menuBar.add(view);
    menuBar.add(event);
    menuBar.add(goal);
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
    addButtonLeft = new JButton("+");
    addButtonLeft.setActionCommand(ADD_GOAL);
    removeButtonLeft = new JButton("-");
    removeButtonLeft.setActionCommand(REMOVE_GOAL);
    selectButtonLeft = new JButton("Select All");
    selectButtonLeft.setActionCommand(SELECT_GOALS);
    sortLHighToLow = new JButton("Sort: ^");
    sortLLowToHigh = new JButton("Sort: v");
    
    /*------------------------TASKS---------------------------*/
    taskBar = new JToolBar();
    addButtonMid = new JButton("+");
    addButtonMid.setActionCommand(ADD_TASK);
    removeButtonMid = new JButton("-");
    removeButtonMid.setActionCommand(REMOVE_TASK);
    selectButtonMid = new JButton("Select All");
    selectButtonMid.setActionCommand(SELECT_TASKS);
    sortMHighToLow = new JButton("Sort: ^");
    sortMLowToHigh = new JButton("Sort: v");
    
    /*------------------------PAST DUES----------------------*/
    dueBar = new JToolBar();
    addButtonRight = new JButton("+");
    addButtonRight.setActionCommand(ADD_PASTDUE);
    removeButtonRight = new JButton("-");
    removeButtonRight.setActionCommand(REMOVE_PASTDUE);
    selectButtonRight = new JButton("Select All");
    selectButtonRight.setActionCommand(SELECT_DUES);
    sortRHighToLow = new JButton("Sort: ^");
    sortRLowToHigh = new JButton("Sort: v");
    
    // add this UI as listener to the buttons.
    addButtonLeft.addActionListener(this);
    addButtonMid.addActionListener(this);
    addButtonRight.addActionListener(this);
    removeButtonLeft.addActionListener(this);
    removeButtonMid.addActionListener(this);
    removeButtonRight.addActionListener(this);
    selectButtonLeft.addActionListener(this);
    selectButtonMid.addActionListener(this);
    selectButtonRight.addActionListener(this);
    
    // sort button event handling
    sortLHighToLow.addActionListener(new SortGoals());
    sortLHighToLow.setActionCommand(HIGH_LOW);
    sortLLowToHigh.addActionListener(new SortGoals());
    sortLLowToHigh.setActionCommand(LOW_HIGH);

    sortRHighToLow.addActionListener(new SortDues());
    sortRHighToLow.setActionCommand(HIGH_LOW);
    sortRLowToHigh.addActionListener(new SortDues());
    sortRLowToHigh.setActionCommand(LOW_HIGH);

    sortMHighToLow.addActionListener(new SortTasks());
    sortMHighToLow.setActionCommand(HIGH_LOW);
    sortMLowToHigh.addActionListener(new SortTasks());
    sortMLowToHigh.setActionCommand(LOW_HIGH);

    // labels for panels and the corresponding text fields
    shortGoals = new JLabel("  Today's Goals: ");
    tasks = new JLabel("  Daily Tasks: ");
    pastDues = new JLabel("  Past Dues: ");
    container.setHeaderFont(shortGoals);
    container.setHeaderFont(tasks);
    container.setHeaderFont(pastDues);
    
    // Panels for storing tasks and goals
    goalField = new JPanel();
    taskField = new JPanel();
    pastDueField = new JPanel();
    // layout management
    goalField.setLayout(new BoxLayout(goalField, BoxLayout.Y_AXIS));
    taskField.setLayout(new BoxLayout(taskField, BoxLayout.Y_AXIS));
    pastDueField.setLayout(new BoxLayout(pastDueField, BoxLayout.Y_AXIS));

    // scrollable panel
    goalPane = new JScrollPane(goalField);
    taskPane = new JScrollPane(taskField);
    duePane = new JScrollPane(pastDueField);

    // Panel fixed size
    goalPane.setPreferredSize(new Dimension(FIELD_ROW, FIELD_COL));
    taskPane.setPreferredSize(new Dimension(FIELD_ROW, FIELD_COL));
    duePane.setPreferredSize(new Dimension(FIELD_ROW, FIELD_COL));

    // set up the border for top ui scroll panes
    goalPane
        .setBorder(BorderFactory.createLineBorder(Color.BLACK, BORDER_STROKE));
    taskPane
        .setBorder(BorderFactory.createLineBorder(Color.BLACK, BORDER_STROKE));
    duePane
        .setBorder(BorderFactory.createLineBorder(Color.BLACK, BORDER_STROKE));
    
    // add toolbars to each panel
    container.addToolBars(topLPanel, addButtonLeft, removeButtonLeft,
        selectButtonLeft, sortLHighToLow, sortLLowToHigh, goalBar);
    container.addToolBars(topMPanel, addButtonMid, removeButtonMid,
        selectButtonMid, sortMHighToLow, sortMLowToHigh, taskBar);
    container.addToolBars(topRPanel, addButtonRight, removeButtonRight,
        selectButtonRight, sortRHighToLow, sortRLowToHigh, dueBar);
    
    // add components to top panels
    topLPanel.add(shortGoals,BorderLayout.NORTH);
    topLPanel.add(goalPane);
    topMPanel.add(tasks,BorderLayout.NORTH);
    topMPanel.add(taskPane);
    topRPanel.add(pastDues,BorderLayout.NORTH);
    topRPanel.add(duePane);
    
    
    // layout paddings
    topPanel.add(Box.createRigidArea(new Dimension(BOX_PADDING,0)));
    topPanel.add(topLPanel);
    topPanel.add(Box.createRigidArea(new Dimension(BOX_PADDING,0)));
    topPanel.add(topMPanel);
    topPanel.add(Box.createRigidArea(new Dimension(BOX_PADDING,0)));
    topPanel.add(topRPanel);
    topPanel.add(Box.createRigidArea(new Dimension(BOX_PADDING,0)));
    
    // add padding to the topPanel && background color
    topPanel.setBorder(new EmptyBorder(BORDER_PADDING,0,BORDER_PADDING,0));
    
    container.setComponentColor(topPanel, TOP_GRAYSCALE);
    container.setComponentColor(goalField, TOP_GRAYSCALE);
    container.setComponentColor(taskField, TOP_GRAYSCALE);
    container.setComponentColor(pastDueField, TOP_GRAYSCALE);
    container.setComponentColor(goalBar, TOP_GRAYSCALE);
    container.setComponentColor(taskBar, TOP_GRAYSCALE);
    container.setComponentColor(dueBar, TOP_GRAYSCALE);
    
    container.add(topPanel,BorderLayout.NORTH);
    
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

    if (e.getActionCommand().equals(ADD_GOAL)) {
      addBoxTextArea(goalField);
    }

    if (e.getActionCommand().equals(ADD_TASK)) {
      addBoxTextArea(taskField);
    }
    if (e.getActionCommand().equals(ADD_PASTDUE)) {
      addBoxTextArea(pastDueField);
    }

    /*-----------------------REMOVING----------------------*/

    if (e.getActionCommand().equals(REMOVE_GOAL)) {
      removeBoxTextArea(goalField);
    }

    if (e.getActionCommand().equals(REMOVE_TASK)) {
      removeBoxTextArea(taskField);
    }

    if (e.getActionCommand().equals(REMOVE_PASTDUE)) {
      removeBoxTextArea(pastDueField);
    }

    /*-----------------------SELECTING----------------------*/

    if (e.getActionCommand().equals(SELECT_GOALS)) {
      selectAllBox(goalField);
    }

    if (e.getActionCommand().equals(SELECT_TASKS)) {
      selectAllBox(taskField);
    }

    if (e.getActionCommand().equals(SELECT_DUES)) {
      selectAllBox(pastDueField);
    }

  }

  /*-------------------PRIVATE CLASS SORT BUTTON HANDLING-----------------*/

  private class SortGoals implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      sortBox(e, goalField);
    }

  }

  private class SortTasks implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      sortBox(e, taskField);
    }

  }

  private class SortDues implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      sortBox(e, pastDueField);
    }

  }
  
  private void addBoxTextArea(JPanel targetPanel) {

    new BoxTextArea(targetPanel);
    targetPanel.revalidate();

  }

  private void removeBoxTextArea(JPanel targetPanel) {

    // remove all the selected boxes
    for (int i = targetPanel.getComponents().length - 1; i >= 0; i--) {

      if (((BoxTextArea) targetPanel.getComponent(i)).isSelected()) {
        targetPanel.remove(i);
        targetPanel.revalidate();
      }

    }

    // since last validate doesn't not show real time visual update, needs
    // repaint
    targetPanel.repaint();

  }

  private void selectAllBox(JPanel targetPanel) {

    for (int i = 0; i < targetPanel.getComponentCount(); i++) {
      ((BoxTextArea) (targetPanel.getComponent(i))).setSelected(true);
    }

  }

  private void sortBox(ActionEvent e, JPanel targetPanel) {

    if (e.getActionCommand() == HIGH_LOW) {
      Arrays.sort(targetPanel.getComponents(), new BoxComparator());
    } else if (e.getActionCommand() == LOW_HIGH) {
      Arrays.sort(targetPanel.getComponents(), new BoxReverseComparator());
    }

    targetPanel.revalidate();
    targetPanel.repaint();
  }

}
