package colorSchemes;

import java.awt.Color;

/**
 * General color scheme
 * 
 * @author Shanfeng Feng
 * @since 2017-08-19
 * @version 0.1
 *
 */
public interface ColorScheme {

  public Color getLeftUIShade();

  public Color getLeftUIBoxShade();

  public Color getTopUIShade();

  public Color getCalendarShade();
  public String getSchemeName();

}
