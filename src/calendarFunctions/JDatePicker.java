package calendarFunctions;

import java.awt.BorderLayout;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

import javax.swing.JPanel;

/**
 * @author Xelafe
 *
 */
public class JDatePicker extends JPanel implements Serializable {

  private JDateChooser myChooser;
  private Date previousDate;

  public JDatePicker(){
    this.setLayout(new BorderLayout());
    myChooser = new JDateChooser();
    this.add(myChooser);
  }
  
  private void writeObject(ObjectOutputStream out) throws IOException {

    out.writeObject(myChooser.getDate());
  }

  private void readObject(ObjectInputStream in) throws IOException {
    try {
      System.out.println("Before");
      this.myChooser = new JDateChooser();
      System.out.println("After");
      previousDate = (Date) in.readObject();
      myChooser.setDate(previousDate);
      this.add(myChooser);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void setDate(Date newDate) {
    myChooser.setDate(newDate);
  }

  public Date getDate() {
    return myChooser.getDate();
  }
}
