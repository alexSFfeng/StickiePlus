package mercurial;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * A due date for each individual tasks
 * 
 * @author Shanfeng Feng
 * @since 2017-07-11
 * @version 0.1
 *
 */
public class DueDate extends Date {

  /**
   * print out the text representation of the due date
   */
  @Override
  public String toString() {
    return "Due on: " + new SimpleDateFormat("MM/dd/yyyy").format(this);
  }

}
