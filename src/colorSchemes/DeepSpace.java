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
public class DeepSpace extends JMenuItem implements ColorScheme {

  private String schemeName = "Deep Space (Default)";
  private static final Color leftUIShade = new Color(91, 91, 91);
  private static final Color leftUIBoxShade = Color.GRAY;
  private static final Color topUIShade = new Color(114, 114, 114);
  private static final Color calendarShade = new Color(100, 100, 100);

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
