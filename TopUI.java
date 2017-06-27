package mercurial;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
  private JPanel topPanel, topLPanel, topMPanel, topRPanel, topLBotPanel,
                 topMBotPanel, topRBotPanel;
  private JMenu mercurial,file,view,event,goal,setting;
  private JLabel shortGoals, tasks, pastDues;
  private JPanel goalField, taskField, pastDueField;
  private JScrollPane goalPane, taskPane, duePane;
  private JToolBar goalBar, taskBar, dueBar;
  private JButton addButtonLeft, removeButtonLeft,addButtonRight, removeButtonRight,
                  addButtonMid, removeButtonMid;
  private JMenu sortLeft,sortMid,sortRight;

  // top ui constants for border and view port area
  private static final int BORDER_STROKE = 2;
  private static final int FIELD_COL = 100;
  private static final int FIELD_ROW = 4;

  // padding between each task box
  private static final int BORDER_PADDING = 10;
  private static final int BOX_PADDING = 30;
  private static final int TOP_GRAYSCALE = 114;

  // labels
  private static final String ADD_GOAL = "add goal boxField";
  private static final String REMOVE_GOAL = "remove goal boxField";
  private static final String ADD_TASK = "add task boxField";
  private static final String REMOVE_TASK = "remove task boxField";
  private static final String ADD_PASTDUE = "add due boxField";
  private static final String REMOVE_PASTDUE = "remove due boxField";
  
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
    topLBotPanel = new JPanel(new BorderLayout());
    topMBotPanel = new JPanel(new BorderLayout());
    topRBotPanel = new JPanel(new BorderLayout());


    // instantiating JButtons and JToolBars
    goalBar = new JToolBar();
    addButtonLeft = new JButton("+");
    addButtonLeft.setActionCommand(ADD_GOAL);
    removeButtonLeft = new JButton("-");
    removeButtonLeft.setActionCommand(REMOVE_GOAL);
    sortLeft = new JMenu("SORT");
    
    taskBar = new JToolBar();
    addButtonMid = new JButton("+");
    addButtonMid.setActionCommand(ADD_TASK);
    removeButtonMid = new JButton("-");
    removeButtonMid.setActionCommand(REMOVE_TASK);
    sortMid = new JMenu("SORT");
    
    dueBar = new JToolBar();
    addButtonRight = new JButton("+");
    addButtonRight.setActionCommand(ADD_PASTDUE);
    removeButtonRight = new JButton("-");
    removeButtonRight.setActionCommand(REMOVE_PASTDUE);
    sortRight = new JMenu("SORT");
    
    // add this UI as listener to the buttons.
    addButtonLeft.addActionListener(this);
    addButtonMid.addActionListener(this);
    addButtonRight.addActionListener(this);
    removeButtonLeft.addActionListener(this);
    removeButtonMid.addActionListener(this);
    removeButtonRight.addActionListener(this);
    
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
    goalField.setPreferredSize(new Dimension(FIELD_ROW, FIELD_COL));
    taskField.setPreferredSize(new Dimension(FIELD_ROW, FIELD_COL));
    pastDueField.setPreferredSize(new Dimension(FIELD_ROW, FIELD_COL));

    // scrollable panel
    goalPane = new JScrollPane(goalField);
    taskPane = new JScrollPane(taskField);
    duePane = new JScrollPane(pastDueField);

    // set up the border for top ui scroll panes
    goalPane
        .setBorder(BorderFactory.createLineBorder(Color.BLACK, BORDER_STROKE));
    taskPane
        .setBorder(BorderFactory.createLineBorder(Color.BLACK, BORDER_STROKE));
    duePane
        .setBorder(BorderFactory.createLineBorder(Color.BLACK, BORDER_STROKE));
    
    // add components to top panels
    topLPanel.add(shortGoals,BorderLayout.NORTH);
    topLPanel.add(topLBotPanel,BorderLayout.SOUTH);
    topLPanel.add(goalPane);
    topMPanel.add(tasks,BorderLayout.NORTH);
    topMPanel.add(topMBotPanel,BorderLayout.SOUTH);
    topMPanel.add(taskPane);
    topRPanel.add(pastDues,BorderLayout.NORTH);
    topRPanel.add(topRBotPanel,BorderLayout.SOUTH);
    topRPanel.add(duePane);
    
    
    // add toolbars to each panel
    container.addToolBars(topLBotPanel, addButtonLeft, removeButtonLeft
                     ,sortLeft, goalBar);
    container.addToolBars(topMBotPanel, addButtonMid, removeButtonMid
                     ,sortMid, taskBar);
    container.addToolBars(topRBotPanel, addButtonRight, removeButtonRight
                     ,sortRight, dueBar);
    
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
    container.setComponentColor(topLBotPanel, TOP_GRAYSCALE);
    container.setComponentColor(topMBotPanel, TOP_GRAYSCALE);
    container.setComponentColor(topRBotPanel, TOP_GRAYSCALE);
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
    
    if (e.getActionCommand().equals(ADD_GOAL)) {

    }

    if (e.getActionCommand().equals(ADD_TASK)) {

    }

    if (e.getActionCommand().equals(ADD_PASTDUE)) {

    }

    if (e.getActionCommand().equals(REMOVE_GOAL)) {

    }

    if (e.getActionCommand().equals(REMOVE_TASK)) {

    }

    if (e.getActionCommand().equals(REMOVE_PASTDUE)) {

    }
  }
  
  /*
   * 
   * set text field wrap around functionality goalField.setWrapStyleWord(true);
   * goalField.setLineWrap(true); taskField.setWrapStyleWord(true);
   * taskField.setLineWrap(true); pastDueField.setWrapStyleWord(true);
   * pastDueField.setLineWrap(true);
   * 
   * 
   * // text fields restrictions and make them scrollable goalField = new
   * JTextArea(FIELD_ROW,FIELD_COL); taskField = new
   * JTextArea(FIELD_ROW,FIELD_COL); pastDueField = new
   * JTextArea(FIELD_ROW,FIELD_COL); // the big fields are not editable.
   * goalField.setEditable(false); taskField.setEditable(false);
   * pastDueField.setEditable(false);
   */

}
