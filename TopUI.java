package mercurial;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
/**
 * Panels for the program: tool bars, info display
 * 
 * @author Shanfeng Feng
 * @version 0.1
 * @since 2017-03-30
 *
 */
public class TopUI {

  // top portion of the UI
  private JMenuBar menuBar;
  private JPanel topPanel, topLPanel, topMPanel, topRPanel, topLBotPanel,
                 topMBotPanel, topRBotPanel;
  private JMenu mercurial,file,view,event,goal,setting;
  private JLabel shortGoals, tasks, pastDues;
  private JTextArea goalField, taskField, pastDueField;
  private JScrollPane goalPane, taskPane, duePane;
  private JToolBar goalBar, taskBar, dueBar;
  private JButton addButtonLeft, removeButtonLeft,addButtonRight, removeButtonRight,
                  addButtonMid, removeButtonMid;
  private JMenu sortLeft,sortMid,sortRight;
  private static final int FIELD_COL = 25;
  private static final int FIELD_ROW = 4;
  private static final int BORDER_PADDING = 10;
  private static final int BOX_PADDING = 30;
  private static final int TOP_GRAYSCALE = 114;
  
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
    removeButtonLeft = new JButton("-");
    sortLeft = new JMenu("sort");
    
    taskBar = new JToolBar();
    addButtonMid = new JButton("+");
    removeButtonMid = new JButton("-");
    sortMid = new JMenu("sort");
    
    dueBar = new JToolBar();
    addButtonRight = new JButton("+");
    removeButtonRight = new JButton("-");
    sortRight = new JMenu("sort");
    
    
    // labels for panels and the corresponding text fields
    shortGoals = new JLabel("  Today's Goals: ");
    tasks = new JLabel("  Daily Tasks: ");
    pastDues = new JLabel("  Past Dues: ");
    container.setHeaderFont(shortGoals);
    container.setHeaderFont(tasks);
    container.setHeaderFont(pastDues);
    
    // text fields restrictions and make them scrollable
    goalField = new JTextArea(FIELD_ROW,FIELD_COL);
    taskField = new JTextArea(FIELD_ROW,FIELD_COL);
    pastDueField = new JTextArea(FIELD_ROW,FIELD_COL);
    // the big fields are not editable.
    goalField.setEditable(false);
    taskField.setEditable(false);
    pastDueField.setEditable(false);
    goalPane = new JScrollPane(goalField);
    goalPane.setBackground(Color.BLACK);
    taskPane = new JScrollPane(taskField);
    taskPane.setBackground(Color.BLACK);
    duePane = new JScrollPane(pastDueField);
    duePane.setBackground(Color.BLACK);
    
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
    
    // set text field wrap around functionality
    goalField.setWrapStyleWord(true);
    goalField.setLineWrap(true);
    taskField.setWrapStyleWord(true);
    taskField.setLineWrap(true);
    pastDueField.setWrapStyleWord(true);
    pastDueField.setLineWrap(true);
    
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
  
}
