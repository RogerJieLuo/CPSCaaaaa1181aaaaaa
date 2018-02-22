package week7.lecture.example2;

import week2.BankAccount;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InvestmentViewer {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        JButton button = new JButton("Add Interest");
        BankAccount ba = new BankAccount(100);
        JLabel label = new JLabel("Balance account: " + ba.getBalance());
        JPanel panel = new JPanel();

        panel.add(button);
        panel.add(label);

        frame.add(panel);

        /**
         * an inner class object created inside a static method can only access static class variables
         */
        class AddInterestListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                // in the inner class, the ba is defined as final, not able to change ba
                // so can't do ba = new BankAccount();
                double interest = ba.getBalance() * 10 / 100;
                ba.deposit(interest);
                label.setText("Balance account: " + ba.getBalance());
            }
        }

        ActionListener listener = new AddInterestListener();
        button.addActionListener(listener);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}
