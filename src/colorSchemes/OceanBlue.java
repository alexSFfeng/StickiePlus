package colorSchemes;

import java.awt.Color;

import javax.swing.JMenuItem;

/**
 * Ocean blue color scheme
 * 
 * @author Xelafe
 * @since 2017-08-19
 * @version 0.1
 */
public class OceanBlue extends JMenuItem implements ColorScheme {

  private String schemeName = "Ocean Blue";
  private static final Color leftUIShade = new Color(0, 103, 165);
  private static final Color leftUIBoxShade = new Color(0, 151, 241);
  private static final Color topUIShade = new Color(12, 164, 255);
  private static final Color calendarShade = new Color(0, 72, 190);

  @Override
  public String getSchemeName() {
    return schemeName;
  }

  @Override
  public Color getLeftUIShade() {
    return leftUIShade;
  }

  @Override
  public Color getLeftUIBoxShade() {
    return leftUIBoxShade;
  }

  @Override
  public Color getTopUIShade() {
    return topUIShade;
  }

  @Override
  public Color getCalendarShade() {
    return calendarShade;
  }

}