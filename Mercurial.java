package mercurial;

import java.util.*;
import java.lang.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

/**
 * Application for self Organization, scheduling, goals,
 * planning, and internship finding. (Keep track of productivity)
 * 
 * @author Shanfeng Feng
 * @version 0.1
 * @since 2017-03-30
 */
public class Mercurial{

  // default frame status
  private JFrame mainFrame;
  private static final int FRAME_WIDTH = 1000;
  private static final int FRAME_HEIGHT = 700;
  private static final int HEADER_FONT_SIZE = 16;
  
  // top portion of the UI
  private JMenuBar menuBar;
  private JPanel topPanel, topLPanel, topMPanel, topRPanel;
  private JMenu mercurial,file,view,event,goal,setting;
  private JLabel shortGoals, tasks, pastDues;
  private JTextArea goalField, taskField, pastDueFields;
  private JScrollPane goalPane, taskPane, duePane;
  private JToolBar goalBar, taskBar, dueBar;
  private static final int FIELD_COL = 25;
  private static final int FIELD_ROW = 4;
  private static final int BORDER_PADDING = 10;
  private static final int TOP_GRAYSCALE = 114;
  private static final int ALPHA_VALUE = 200;
  
  // left portion of the UI
  private JPanel leftPanel,leftPanelTop,leftPanelBot,leftPanelBotTop;
  private JScrollPane leftScroll;
  private JLabel myProductivity;
  private JToolBar addRemoveBar;
  private JButton addButtonLP, removeButtonLP;
  private JMenu sortLP;
  private static final int LEFT_GRAYSCALE = 91;
  
  public Mercurial(){
    mainFrame = new JFrame();
  }
  /**
   * display the user interface
   */
  public void displayUI(){
    
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.setPreferredSize(new Dimension(FRAME_WIDTH,FRAME_HEIGHT));
    mainFrame.setSize(mainFrame.getPreferredSize());
    mainFrame.setTitle("Mercurial");
    mainFrame.getContentPane().setBackground(Color.DARK_GRAY);
    this.setTopUI();
    this.setLeftUI();
    
    mainFrame.setVisible(true);
  }
  
  /**
   * TODO add toolbars for check box functionality
   * Set up the top panel user interface
   */
  private void setTopUI(){
    
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
    mainFrame.setJMenuBar(menuBar);
    
    // initialize top panel interface
    topPanel = new JPanel();
    topPanel.setLayout(new BoxLayout(topPanel,BoxLayout.X_AXIS));
    topLPanel = new JPanel(new BorderLayout());
    topMPanel = new JPanel(new BorderLayout());
    topRPanel = new JPanel(new BorderLayout());
    
    // labels for panels and the corresponding text fields
    shortGoals = new JLabel("Today's Goals: ");
    tasks = new JLabel("Daily Tasks: ");
    pastDues = new JLabel("Past dues: ");
    this.setHeaderFont(shortGoals);
    this.setHeaderFont(tasks);
    this.setHeaderFont(pastDues);
    
    // text fields restrictions and make them scrollable
    goalField = new JTextArea(FIELD_ROW,FIELD_COL);
    taskField = new JTextArea(FIELD_ROW,FIELD_COL);
    goalPane = new JScrollPane(goalField);
    taskPane = new JScrollPane(taskField);
    
    // add components to top panels
    topLPanel.add(shortGoals,BorderLayout.NORTH);
    topLPanel.add(goalPane);
    topMPanel.add(tasks,BorderLayout.NORTH);
    topMPanel.add(taskPane);
    
    // set text field wrap around functionality
    
    goalField.setWrapStyleWord(true);
    goalField.setLineWrap(true);
    taskField.setWrapStyleWord(true);
    taskField.setLineWrap(true);
    
    // TODO Magic Numbers !!!
    topPanel.add(Box.createRigidArea(new Dimension(15,0)));
    topPanel.add(topLPanel);
    topPanel.add(Box.createRigidArea(new Dimension(30,0)));
    topPanel.add(topMPanel);
    topPanel.add(Box.createRigidArea(new Dimension(400,0)));
    topPanel.add(topRPanel);
    
    // add padding to the topPanel && background color
    topPanel.setBorder(new EmptyBorder(BORDER_PADDING,0,BORDER_PADDING,0));
    topPanel.setBackground(new Color(TOP_GRAYSCALE,TOP_GRAYSCALE,
                                     TOP_GRAYSCALE,ALPHA_VALUE));
    mainFrame.add(topPanel,BorderLayout.NORTH);
  }
  
  /**
   * TODO toolbar for check box functionality and productivity gauge
   * set up the left user interface
   */
  private void setLeftUI(){
    
    // left panel initializations
    leftPanel = new JPanel(new BorderLayout());
    leftPanelTop = new JPanel();
    leftPanelBot = new JPanel(new BorderLayout());
    leftPanelBotTop = new JPanel();
    leftScroll = new JScrollPane(leftPanelBotTop);
    myProductivity = new JLabel("Priority Tasks/Remainders: ");
    this.setHeaderFont(myProductivity);
    
    // components for left bottom panel
    addRemoveBar = new JToolBar();
    addButtonLP = new JButton("+");
    removeButtonLP = new JButton("-");
    sortLP = new JMenu("sort");
    
    // adding buttons to toolbar
    addRemoveBar.add(addButtonLP);
    addRemoveBar.add(removeButtonLP);
    addRemoveBar.add(sortLP);
    addRemoveBar.setFloatable(false);
    leftPanelBot.add(addRemoveBar,BorderLayout.SOUTH);
    leftPanelBot.add(leftPanelBotTop);
    
    leftPanelTop.add(myProductivity);
    leftPanel.add(leftPanelTop,BorderLayout.NORTH);
    leftPanel.add(leftPanelBot);
    
    // color settings
    leftPanel.setBorder(new EmptyBorder(BORDER_PADDING,BORDER_PADDING,
                                        BORDER_PADDING,BORDER_PADDING));
    
    this.setComponentColor(leftPanel, LEFT_GRAYSCALE);
    this.setComponentColor(leftPanelBot, LEFT_GRAYSCALE);
    this.setComponentColor(addRemoveBar, LEFT_GRAYSCALE);
    
    // set JButtons background
    Font buttonFont = new Font(addButtonLP.getFont().getFontName(),
                               Font.BOLD,12);
    addButtonLP.setOpaque(true);
    addButtonLP.setBorderPainted(false);
    addButtonLP.setFont(buttonFont);
    removeButtonLP.setOpaque(true);
    removeButtonLP.setBorderPainted(false);
    
    mainFrame.add(leftPanel,BorderLayout.WEST);
  }
  
  public JFrame mainFrame(){
    return mainFrame;
  }
  
  /**
   * set the components of a container to the same color as the container
   * @param container: the container that holds all the component
   * @param grayscale: the color to be set
   */
  private void setComponentColor(Container container, int grayscale){
    
    container.setBackground(new Color(grayscale, grayscale, grayscale));
    
    for(Component c : container.getComponents()){
      
      c.setBackground(new Color(grayscale, grayscale, grayscale));

    }
    
  }
  /**
   * set the header label to be in bold font and header size
   * @param label: the JLabel to be set bold
   */
  private void setHeaderFont(JLabel label){
    
    Font labelFont = label.getFont();
    Font boldFont = new Font(labelFont.getFontName(),
                             Font.BOLD,HEADER_FONT_SIZE);
    label.setFont(boldFont);
  }

  /**
   * runs the program in default
   * @param args: command line arguments
   */
  public static void main(String [] args){
    
    Mercurial myApp = new Mercurial();
    myApp.displayUI();
    
  }
  
}
