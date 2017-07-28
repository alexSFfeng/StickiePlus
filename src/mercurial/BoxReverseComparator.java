package mercurial;

import java.util.Comparator;
import java.util.Date;

/**
 * Sort BoxTextArea in reverse (lower priority level goes first)
 * 
 * @author Shanfeng Feng
 * @since 2017-07-05
 * @version 0.1
 */
public class BoxReverseComparator implements Comparator {

  /**
   * This is the reverse comparator, later deadlines would be put at the top
   * compare two BoxTextArea object base on priority level lower priority level
   * should go first sort all the boxes; selected boxes comes before deselected
   * boxes, If two boxes shares the same selection status, the will be compared
   * base on the following rule
   * 
   * @param o1:
   *          the first BoxTextArea
   * @param o2:
   *          the second BoxTextArea
   * @return -1 if o1 < o2, 1 if o1 > o2, 0 if o1 == o2
   */
  @SuppressWarnings("deprecation")
  @Override
  public int compare(Object o1, Object o2) {

    if (((BoxTextArea) o1).isSelected() == ((BoxTextArea) o2).isSelected()) {

      /*-----------LATER DEADLINE COMES FIRST REGARDLESS OF PRIORITY---*/
      Date o1Date = ((BoxTextArea) o1).getFullDate();
      Date o2Date = ((BoxTextArea) o2).getFullDate();

      // o1's date comes after o2's date
      if (o1Date.getYear() > o2Date.getYear()
          && o1Date.getMonth() > o2Date.getMonth()
          && o1Date.getDate() > o2Date.getDate()) {
        return -1;
      }
      // o1's date comes before o2's date
      else if (o1Date.getYear() < o2Date.getYear()
          && o1Date.getMonth() < o2Date.getMonth()
          && o1Date.getDate() < o2Date.getDate()) {
        return 1;
      }
      // same deadline, check priority
      else {
        // higher priority will be lower in the list
        if (((BoxTextArea) o1).getPriorityLv() > ((BoxTextArea) o2)
          .getPriorityLv()) {
          return 1;
        }
        // lower priority will be higher in the list
        else if (((BoxTextArea) o1).getPriorityLv() < ((BoxTextArea) o2)
            .getPriorityLv()) {
          return -1;
        }

        // no change in positioning if two object have same priority
        return 0;
      }
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

}
