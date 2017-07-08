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
   * set the selectionMode of containerPanel
   * 
   * @param containerPanel
   * @param selectionMode
   */
  public void setSelection(JPanel containerPanel, boolean selectionMode);

  /**
   * receive the current selection mode of a panel that holds BoxTextArea
   * 
   * @param containerPanel:
   *          the panel that holds BoxTextArea objects
   * @return the selection mode of that panel
   */
  public boolean getSelection(JPanel containerPanel);
}
