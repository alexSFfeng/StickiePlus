
package mercurial;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * cell definitions for JList of boxTextArea
 * 
 * @author Shanfeng Feng
 * @since 2017- 06 - 28
 * @version 0.1
 *
 */
public class boxTextCellRender extends boxTextArea implements 
                               ListCellRenderer<boxTextArea>{

  @Override
  public Component getListCellRendererComponent(
      JList<? extends boxTextArea> list, boxTextArea value, int index,
      boolean isSelected, boolean cellHasFocus) {
    
    this.setSelected(isSelected);
    if (isSelected) {
      setForeground(list.getSelectionForeground());
      setBackground(list.getSelectionBackground());
    } else {
      setForeground(list.getForeground());
      setBackground(list.getBackground());
    }
    return this;
  }

  
}
