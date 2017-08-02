package mercurial;

import javax.swing.JPanel;

/**
 * Each main panel in the main frame is a main UI_Panel
 * 
 * @author Shanfeng Feng
 * @since 2017-07-06
 * @version 0.1
 *
 */
public interface UI_Panel {

  /**
   * set the selectionMode of a panel that holds BoxTextArea objects
   * 
   * @param containerPanel:
   *          the panel that holds the BoxTextArea objects
   * @param selectionMode:
   *          the selection Mode to be set for this panel
   */
  public void setSelection(JPanel containerPanel, boolean selectionMode);


}
