package week7.lecture;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EndingListener implements ActionListener{
    // if needs other parameter used in the action, put them in the constructor
    @Override
    public void actionPerformed(ActionEvent e) {
        // ususally don't put i/o output here
        System.out.println(e.paramString());
        System.out.println(new java.sql.Timestamp(e.getWhen()));
        System.out.println("\nbye\n");
        System.exit(0);
    }
}
