package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.swing.JPanel;

import org.junit.Before;
import org.junit.Test;

import mainPlanner.BoxTextArea;
import mainPlanner.StickiePlus;

/**
 * Unit tester for methods in BoxTextArea class
 * 
 * @author Shanfeng Feng
 * @since 2017-08-21
 * @version 0.1
 *
 */
public class BoxTextAreaTest {

  private BoxTextArea boxLowPriority, boxHighPriority, boxDefaultPriority;
  private BoxTextArea boxNotPickableHigh, boxNotPickableLow;
  private JPanel containerPanel;

  /**
   * Default initial testing state
   * 
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception {

    StickiePlus testApp = new StickiePlus();
    testApp.testSetUp();

    containerPanel = new JPanel();

    // initialize all the due date pickable boxes
    boxLowPriority = new BoxTextArea(containerPanel, true);
    boxHighPriority = new BoxTextArea(containerPanel, true);
    boxDefaultPriority = new BoxTextArea(containerPanel, true);

    // initialize all the due date not-pickable boxes as copy (Deep cpy ctor)
    boxNotPickableHigh = new BoxTextArea(boxHighPriority, containerPanel,
        false);
    boxNotPickableLow = new BoxTextArea(boxLowPriority, containerPanel, false);

    // change priority levels
    // setting priority level
    boxLowPriority.setPriorityLv(1);
    boxHighPriority.setPriorityLv(10);
    boxDefaultPriority.setPriorityLv(5);
    boxNotPickableHigh.setPriorityLv(8);
    boxNotPickableLow.setPriorityLv(3);

  }

  /**
   * Test get priority method
   */
  @Test
  public void testGetPriority() {

    // the priority level should be the same as what was set in setup
    assertEquals(boxLowPriority.getPriorityLv(), 3);
    assertEquals(boxHighPriority.getPriorityLv(), 8);
    assertEquals(boxDefaultPriority.getPriorityLv(), 5);

    // these priority level should be the same as their copy since,
    // copy ctor sync both boxes' priority slider and text information
    assertEquals(boxNotPickableHigh.getPriorityLv(),
        boxHighPriority.getPriorityLv());
    assertEquals(boxNotPickableLow.getPriorityLv(),
        boxLowPriority.getPriorityLv());

  }

  /**
   * Test set priority method
   */
  @Test
  public void testSetPriority() {

    // setting priority level
    boxLowPriority.setPriorityLv(1);
    boxHighPriority.setPriorityLv(1);
    boxDefaultPriority.setPriorityLv(1);
    boxNotPickableHigh.setPriorityLv(1);
    boxNotPickableLow.setPriorityLv(1);

    // the priority level should now all be 1
    assertEquals(boxLowPriority.getPriorityLv(), 1);
    assertEquals(boxHighPriority.getPriorityLv(), 1);
    assertEquals(boxDefaultPriority.getPriorityLv(), 1);
    assertEquals(boxNotPickableHigh.getPriorityLv(), 1);
    assertEquals(boxNotPickableLow.getPriorityLv(), 1);
  }

  /**
   * Test color change functionality of BoxTextArea objects
   */
  @Test
  public void testGetPriorityColor() {

    // default color if priority level is between 4 and 7 inclusive
    assertEquals(boxDefaultPriority.getPriorityColor(),
        BoxTextArea.DEFAULT_COLOR);

    // safe color if priority level is lower than 4
    assertEquals(boxNotPickableLow.getPriorityColor(), BoxTextArea.SAFE_COLOR);
    assertEquals(boxLowPriority.getPriorityColor(), BoxTextArea.SAFE_COLOR);

    // dangerous color if priority level is higher than 7
    assertEquals(boxHighPriority.getPriorityColor(),
        BoxTextArea.HIGH_PRIORITY_COLOR);
    assertEquals(boxNotPickableHigh.getPriorityColor(),
        BoxTextArea.HIGH_PRIORITY_COLOR);

  }

  /**
   * Test isSelected function of BoxTextArea objects
   */
  @Test
  public void testIsSelected() {

    // all boxes are default to be not selected when created
    assertTrue(!boxLowPriority.isSelected());
    assertTrue(!boxDefaultPriority.isSelected());
    assertTrue(!boxHighPriority.isSelected());
    assertTrue(!boxNotPickableHigh.isSelected());
    assertTrue(!boxNotPickableLow.isSelected());

  }

  /**
   * Test setSelected function of BoxTextArea objects Note that selection status
   * are not shared between copy boxes and its relative source box
   */
  @Test
  public void testSetSelected() {

    // setting the selection status of the three boxes
    boxLowPriority.setSelected(true);
    boxDefaultPriority.setSelected(true);
    boxHighPriority.setSelected(true);

    // the three boxes should be selected
    assertTrue(boxLowPriority.isSelected());
    assertTrue(boxDefaultPriority.isSelected());
    assertTrue(boxHighPriority.isSelected());

    // the copy boxes are NOT, because selection status are not shared
    assertTrue(!boxNotPickableHigh.isSelected());
    assertTrue(!boxNotPickableLow.isSelected());

  }

  /**
   * Test isBoxEmpty method of the BoxTextArea object
   */
  @Test
  public void testIsBoxEmpty() {

    // empty means there are no text inside the box
    assertTrue(boxLowPriority.isBoxEmpty());
    assertTrue(boxDefaultPriority.isBoxEmpty());
    assertTrue(boxHighPriority.isBoxEmpty());
    assertTrue(boxNotPickableHigh.isBoxEmpty());
    assertTrue(boxNotPickableLow.isBoxEmpty());

  }

  /**
   * Test appendText method of the BoxTextArea object
   */
  @Test
  public void testAppendText() {

    // append some text to box objects
    boxLowPriority.appendText("HI, how are you?");
    boxDefaultPriority
        .appendText("Testers should written before write actual code");
    boxHighPriority.appendText("Sorry, got lazy....");

    /*
     * the three boxes should not be empty. However, the copy boxes are empty
     * because text synchronization between copy and source box only happens
     * when text is append by user focusing the text area and typing text into
     * it
     */
    assertTrue(!boxLowPriority.isBoxEmpty());
    assertTrue(!boxDefaultPriority.isBoxEmpty());
    assertTrue(!boxHighPriority.isBoxEmpty());

    // copy still remains empty!!
    assertTrue(boxNotPickableHigh.isBoxEmpty());
    assertTrue(boxNotPickableLow.isBoxEmpty());

  }

  /**
   * Test getTextRep method of BoxTextArea object
   */
  @Test
  public void testGetTextRep() {

    // append some text to box objects
    boxLowPriority.appendText("HI, how are you?");
    boxDefaultPriority
        .appendText("Testers should be written before actual code");
    boxHighPriority.appendText("Sorry, got lazy....");

    assertEquals("HI, how are you?", boxLowPriority.getTextRep());
    assertEquals("Testers should be written before actual code",
        boxDefaultPriority.getTextRep());
    assertEquals("Sorry, got lazy....", boxHighPriority.getTextRep());
    assertEquals("", boxNotPickableLow.getTextRep());
    assertEquals("", boxNotPickableHigh.getTextRep());
  }

}
