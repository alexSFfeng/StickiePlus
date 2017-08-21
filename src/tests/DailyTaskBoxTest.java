package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.swing.JPanel;

import org.junit.Before;
import org.junit.Test;

import mainPlanner.DailyTaskBox;

/**
 * Test method functionality of DailyTaskBox class
 * 
 * @author Shanfeng Feng
 * @since 2017-08-21
 * @version 0.1
 *
 */
public class DailyTaskBoxTest {

  private DailyTaskBox boxWithNoText, boxWithText;
  private JPanel containerPanel;

  /**
   * Initial testing conditions
   * 
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception {

    containerPanel = new JPanel();

    // 1-arg ctor to make box with no text and no selected time
    boxWithNoText = new DailyTaskBox(containerPanel);

    // 3-arg ctor to make box with initial text and selected time
    boxWithText = new DailyTaskBox(containerPanel, "Has Text", 12);

    // selected id = 12 which is 5:30AM
  }

  /**
   * Test isSelected function of DailyTaskBox objects
   */
  @Test
  public void testIsSelected() {

    // all boxes are not selected when they are first created
    assertTrue(!boxWithNoText.isSelected());
    assertTrue(!boxWithText.isSelected());

  }

  /**
   * Test setSelected function of DailyTaskBox objects
   */
  @Test
  public void testSetSelected() {

    // select the boxes
    boxWithNoText.setSelected(true);
    boxWithText.setSelected(true);

    // boxes are now selected
    assertTrue(boxWithNoText.isSelected());
    assertTrue(boxWithText.isSelected());
  }

  /**
   * Test getOrderId functionality of DailyTaskBox objects
   */
  @Test
  public void testGetOrderId() {

    // should return the relative index for the selected time slot in the time
    // combo box
    assertEquals(boxWithNoText.getOrderId(), 0);
    assertEquals(boxWithText.getOrderId(), 12);

  }

  /**
   * Test getTaskText functionality of DailyTaskBox objects
   */
  @Test
  public void testGetTaskText() {

    // should return the text currently inside the box
    assertEquals(boxWithNoText.getTaskText(), "");
    assertEquals(boxWithText.getTaskText(), "Has Text");

  }

}
