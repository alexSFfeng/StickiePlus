package mainPlanner;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import calendarFunctions.MainCalendar;

/**
 * Application for self Organization, scheduling, goals,
 * planning, and internship finding. (Keep track of productivity)
 * 
 * @author Shanfeng Feng
 * @version 0.1
 * @since 2017-03-30
 */


public class SelfPlanner extends JFrame{

  private static final long serialVersionUID = -4801315076066931210L;

  // default frame status
  //private JFrame mainFrame;
  private static final int FRAME_WIDTH = 1000;
  private static final int FRAME_HEIGHT = 700;
  private static final int HEADER_FONT_SIZE = 16;
  private static TopUI topUI;
  private static LeftUI leftUI;
  
  /**
   * display the user interface
   */
  public void displayUI(){
    
    this.setPreferredSize(new Dimension(FRAME_WIDTH,FRAME_HEIGHT));
    this.setSize(this.getPreferredSize());
    this.setTitle("SelfPlanner");
    this.getContentPane().setBackground(Color.DARK_GRAY);
    this.setLeftUI();
    this.setTopUI();
    this.setCalendar();
    this.validate();
    
    // try to match system look and feel ui
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException e) {
    } catch (InstantiationException e) {
    } catch (IllegalAccessException e) {
    } catch (UnsupportedLookAndFeelException e) {
    }

    this.setVisible(true);
  }
  
  /**
   * Display and set up the upper UI
   */
  public void setTopUI() {
    
    topUI = new TopUI(this);
    
  }
  

  /**
   * Display and set up the left UI
   */
  public void setLeftUI() {
    
    leftUI = new LeftUI(this);
    
  }
  
  /**
   * Getter for the LeftUI
   * 
   * @return leftUI panel
   */
  public static LeftUI getLeftUI() {
    return leftUI;
  }

  /**
   * Getter for the TopUI
   * 
   * @return topUI panel
   */
  public static TopUI getTopUI() {
    return topUI;
  }

  /**
   * Display and set up the Main calendar
   */
  public void setCalendar() {

    MainCalendar mainCal = new MainCalendar(this);

  }

  /**
   * add a toolbar to a Jpanel (such toolbar is for add/remove tasks)
   * 
   * @param panel:
   *          the panel that contains the toolbar
   * @param addB:
   *          the add button
   * @param removeB:
   *          the remove button
   * @param selectAll:
   *          button to select all the boxes
   * @param sortHL:
   *          task priority high to low (Optional: use NULL if no sort)
   * @param sortLH:
   *          task priority low to high (Optional: use NULL if no sort)
   * @param toolbar:
   *          the toolbar containing the buttons and menu
   */
  public void addToolBars(JPanel panel, JButton addB, JButton removeB,
      JButton selectAll, JButton sortHL, JButton sortLH, JToolBar toolbar) {
    
    // adding buttons and menus to toolbar
    toolbar.add(addB);
    toolbar.add(removeB);
    toolbar.add(selectAll);

    if (sortHL != null) {
      toolbar.add(sortHL);
      sortHL.setBorderPainted(false);

    }
    if (sortLH != null) {
      toolbar.add(sortLH);
      sortLH.setBorderPainted(false);
    }

    toolbar.setFloatable(false);
    panel.add(toolbar,BorderLayout.SOUTH);
    
    // set JButtons background
    Font buttonFont = new Font(addB.getFont().getFontName(),
        Font.BOLD, 10);

    // button appearance
    addB.setBorderPainted(false);
    addB.setFont(buttonFont);
    removeB.setBorderPainted(false);
    selectAll.setBorderPainted(false);
  
  }
  
  /**
   * return the mainframe reference
   * 
   * @return the main frame of this program
   */
  public JFrame mainFrame(){
    return this;
  }
  
  /**
   * set the components of a container to the same color as the container
   * @param container: the container that holds all the component
   * @param grayscale: the color to be set
   */
  public void setComponentColor(Container container, int grayscale){
    
    container.setBackground(new Color(grayscale, grayscale, grayscale));
    
    for(Component c : container.getComponents()){
      
      c.setBackground(new Color(grayscale, grayscale, grayscale));

    }
    
  }
  /**
   * set the header label to be in bold font and header size
   * @param label: the JLabel to be set bold
   */
  public void setHeaderFont(JLabel label){
    
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
    
    SelfPlanner myApp = new SelfPlanner();
    myApp.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    myApp.displayUI();
    myApp.addWindowListener(new WindowAdapter() {

      @Override
      public void windowClosing(WindowEvent e) {

        // prompt user to decide whether to save or not
        int userChoice = JOptionPane.showConfirmDialog(null,
            "Do you want to save?", "Save Before Closing",
            JOptionPane.YES_NO_CANCEL_OPTION);

        // save before exit
        if (userChoice == JOptionPane.YES_OPTION) {
          leftUI.saveState();
          topUI.saveState();
          myApp.dispose();
        }

        // exit without saving
        if (userChoice == JOptionPane.NO_OPTION) {
          myApp.dispose();
        }

        if (userChoice == JOptionPane.CANCEL_OPTION) {
          // Do nothing, close pop up diaglog
        }

      }
    });
    
  }
  
}
