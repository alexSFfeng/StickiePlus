package mercurial;

import java.util.Comparator;

/**
 * Sort BoxTextArea in reverse (lower priority level goes first)
 * 
 * @author Shanfeng Feng
 * @since 2017-07-05
 * @version 0.1
 */
public class BoxReverseComparator implements Comparator {

  /**
   * compare two BoxTextArea object base on priority level higher priority level
   * should go first
   * 
   * @param o1:
   *          the first BoxTextArea
   * @param o2:
   *          the second BoxTextArea
   * @return -1 if o1 < o2, 1 if o1 > o2, 0 if o1 == o2
   */
  @Override
  public int compare(Object o1, Object o2) {

    if (((BoxTextArea) o1).getPriorityLv() > ((BoxTextArea) o2)
        .getPriorityLv()) {
      return 1;
    } else if (((BoxTextArea) o1).getPriorityLv() < ((BoxTextArea) o2)
        .getPriorityLv()) {
      return -1;
    }

    return 0;
  }

}
