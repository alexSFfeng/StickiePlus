package mainPlanner;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

/**
 * Comparator for BoxTextArea objects
 * 
 * @author Shanfeng Feng
 * @since 2017-07-05
 * @version 0.1
 *
 */
public class BoxComparator implements Comparator {

  /**
   * First compare the two object base on their deadlines (closer should come
   * first) compare two BoxTextArea object base on priority level higher
   * priority level should go first; selected boxes comes before deselected
   * boxes, If two boxes shares the same selection status, the will be compared
   * base on the following rule
   * 
   * @param o1:
   *          the first BoxTextArea
   * @param o2:
   *          the second BoxTextArea
   * @return -1 if o1 > o2, 1 if o1 < o2, 0 if o1 == o2
   */
  @SuppressWarnings("deprecation")
  @Override
  public int compare(Object o1, Object o2) {

    // proceed to priority check if both boxes share same status
    if (((BoxTextArea) o1).isSelected() == ((BoxTextArea) o2).isSelected()) {

      /*-----------CLOSER DEADLINE COMES FIRST REGARDLESS OF PRIORITY---*/
      Date o1Date = trimDate(((BoxTextArea) o1).getFullDate());
      Date o2Date = trimDate(((BoxTextArea) o2).getFullDate());

      // o1's date comes after o2's date
      if (o1Date.after(o2Date)) {
        return 1;
      }
      // o1's date comes before o2's date
      else if (o1Date.before(o2Date)) {
        return -1;
      }
      // same deadline, check priority
      else {
        // lower priority will return 1, result in lower in the list
        if (((BoxTextArea) o1).getPriorityLv() < ((BoxTextArea) o2)
            .getPriorityLv()) {
          return 1;
        }
        // higher priority will return -1, result in higer in the list
        else if (((BoxTextArea) o1).getPriorityLv() > ((BoxTextArea) o2)
          .getPriorityLv()) {
          return -1;
        }
      }

    return 0;
    }
    // o1 selected, return -1 means it should be higher in the list
    else if (((BoxTextArea) o1).isSelected() == true) {
      return -1;
    }
    // return 1, because o1 should be lower in the list (unselected)
    else {
      return 1;
    }
  }

  /**
   * Trim the Date information down to Month,Day,Year By setting seconds, hour,
   * minutes, millisecond to zero
   * 
   * @param targetDate:
   *          the date to be modified
   * @return the new date after trimming
   */
  private Date trimDate(Date targetDate) {
    Calendar newCal = Calendar.getInstance();
    newCal.setTime(targetDate);
    newCal.set(Calendar.HOUR_OF_DAY, 0);
    newCal.set(Calendar.MINUTE, 0);
    newCal.set(Calendar.SECOND, 0);
    newCal.set(Calendar.MILLISECOND, 0);
    return newCal.getTime();
  }
}
