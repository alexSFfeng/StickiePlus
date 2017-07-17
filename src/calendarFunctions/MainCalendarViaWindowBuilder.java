package calendarFunctions;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 * Fast look and feel development
 * 
 * @author Xelafe
 *
 */
public class MainCalendarViaWindowBuilder extends JPanel {

  /**
   * Create the panel.
   */
  public MainCalendarViaWindowBuilder() {
    setLayout(new BorderLayout(0, 0));

    JPanel panel = new JPanel();
    add(panel);
    panel.setLayout(null);

    JPanel panel_3 = new JPanel();
    panel_3.setBounds(6, 121, 774, 66);
    panel.add(panel_3);
    panel_3.setBorder(new LineBorder(new Color(0, 0, 0)));
    panel_3.setLayout(null);

    JButton btnMonthJuly = new JButton("September");
    btnMonthJuly.setBounds(304, 16, 164, 41);
    btnMonthJuly.setFont(new Font("Marker Felt", Font.BOLD, 31));
    panel_3.add(btnMonthJuly);

    JButton button_1 = new JButton("<");
    button_1.setFont(new Font("Marker Felt", Font.BOLD, 29));
    button_1.setBounds(6, 16, 164, 41);
    panel_3.add(button_1);

    JButton button_2 = new JButton(">");
    button_2.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
      }
    });
    button_2.setFont(new Font("Marker Felt", Font.BOLD, 29));
    button_2.setBounds(604, 16, 164, 41);
    panel_3.add(button_2);

    JPanel panel_2 = new JPanel();
    panel_2.setBounds(6, 6, 774, 111);
    panel.add(panel_2);
    panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
    panel_2.setLayout(null);

    JButton btnNewButton = new JButton("<<");
    btnNewButton.setFont(new Font("Marker Felt", Font.BOLD, 29));
    btnNewButton.setBounds(6, 21, 168, 69);
    panel_2.add(btnNewButton);

    JButton button = new JButton(">>");
    button.setFont(new Font("Marker Felt", Font.BOLD, 29));
    button.setBounds(600, 21, 168, 69);
    panel_2.add(button);

    JButton btnNewButton_1 = new JButton("YEAR: 2017");
    btnNewButton_1.setFont(new Font("Marker Felt", Font.BOLD, 39));
    btnNewButton_1.setBounds(254, 21, 264, 69);
    panel_2.add(btnNewButton_1);

  }
}
