package mercurial;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JToolBar;

/**
 * Application for self Organization, scheduling, goals,
 * planning, and internship finding. (Keep track of productivity)
 * 
 * @author Shanfeng Feng
 * @version 0.1
 * @since 2017-03-30
 */

// TODO need to implement actionListener for button action
public class Mercurial extends JFrame{

  // default frame status
  //private JFrame mainFrame;
  private static final int FRAME_WIDTH = 1000;
  private static final int FRAME_HEIGHT = 700;
  private static final int HEADER_FONT_SIZE = 16;
  
  /**
   * display the user interface
   */
  public void displayUI(){
    
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setPreferredSize(new Dimension(FRAME_WIDTH,FRAME_HEIGHT));
    this.setSize(this.getPreferredSize());
    this.setTitle("Mercurial");
    this.getContentPane().setBackground(Color.DARK_GRAY);
    this.setTopUI();
    this.setLeftUI();
    this.validate();
    
    this.setVisible(true);
  }
  
  /**
   * TODO add toolbars for check box functionality
   * Set up the top panel user interface
   */
  private void setTopUI(){
    
    TopUI topUI = new TopUI(this);
    
  }
  
  /**
   * TODO toolbar for check box functionality and productivity gauge
   * set up the left user interface
   */
  private void setLeftUI(){
    
    LeftUI leftUI = new LeftUI(this);
    
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
   * @param sort:
   *          the sort menu (optional)
   * @param toolbar:
   *          the toolbar containing the buttons and menu
   */
  public void addToolBars(JPanel panel, JButton addB, JButton removeB,
      JButton selectAll, JMenu sort, JToolBar toolbar) {
    
    // adding buttons and menus to toolbar
    toolbar.add(addB);
    toolbar.add(removeB);
    toolbar.add(selectAll);
    if (sort != null) {
      toolbar.add(sort);
    }
    toolbar.setFloatable(false);
    panel.add(toolbar,BorderLayout.SOUTH);
    
    // set JButtons background
    Font buttonFont = new Font(addB.getFont().getFontName(),
        Font.BOLD, 10);

    // button appearance
    addB.setOpaque(true);
    addB.setBorderPainted(false);
    addB.setFont(buttonFont);
    removeB.setOpaque(true);
    removeB.setBorderPainted(false);
    selectAll.setOpaque(true);
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
    
    Mercurial myApp = new Mercurial();
    myApp.setDefaultCloseOperation(EXIT_ON_CLOSE);
    myApp.displayUI();
    
  }
  
}
