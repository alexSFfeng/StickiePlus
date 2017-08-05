package mainPlanner;

import java.util.Comparator;

/**
 * Compares daily tasks base on their selected time
 * 
 * @author Shanfeng Feng
 * @version 0.1
 * @since 2017-08-02
 *
 */
public class DailyTaskComparator implements Comparator {

  /**
   * Compare the task base on selected time Earlier time (lower ID) would be
   * higher on the list
   * 
   * @param o1:
   *          the first task box
   * @param o2:
   *          the second task box
   * @return -1 if o1 comes before o2, 1 if o1 comes after o2 0 if two are the
   *         same.
   */
  @Override
  public int compare(Object o1, Object o2) {

    // lower ID comes first on the list
    if (((DailyTaskBox) o1).getOrderId() < ((DailyTaskBox) o2).getOrderId()) {
      return -1;
    } else if (((DailyTaskBox) o1).getOrderId() > ((DailyTaskBox) o2)
        .getOrderId()) {
      return 1;
    }

    return 0;
  }

}
