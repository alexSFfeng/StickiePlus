package colorSchemes;

import java.awt.Color;

import javax.swing.JMenuItem;

/**
 * Deep Space color scheme
 * 
 * @author Xelafe
 * @since 2017-08-19
 * @version 0.1
 */
public class GrassField extends JMenuItem implements ColorScheme {

  private String schemeName = "Grass Field";
  private static final Color leftUIShade = new Color(124, 252, 0);
  private static final Color leftUIBoxShade = new Color(102, 205, 0);
  private static final Color topUIShade = new Color(188, 238, 104);
  private static final Color calendarShade = new Color(118, 238, 0);

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
