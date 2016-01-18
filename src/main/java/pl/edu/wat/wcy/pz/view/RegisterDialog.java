package pl.edu.wat.wcy.pz.view;


import net.miginfocom.swing.MigLayout;
import pl.edu.wat.wcy.pz.controller.Password;
import pl.edu.wat.wcy.pz.model.dao.UserDao;
import pl.edu.wat.wcy.pz.model.entities.accounts.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class RegisterDialog extends JDialog{
    private UserDao userDao;

    private JButton registerButton;
    private JButton cancelButton;

    private JTextField accountTextField;
    private JPasswordField accountPasswordField;
    private JPasswordField repeatPasswordField;

    public RegisterDialog(LoginWindow loginWindow) {
        super(loginWindow, "Register to Guitarist Organizer", true);
        userDao = new UserDao();
        setMyValues();
        setMyLayout();

        JPanel panel = new JPanel(new MigLayout());
        JLabel accountLabel = new JLabel("Account name");
        accountTextField = new JTextField(40);

        JLabel passwordLabel = new JLabel("Password");
        accountPasswordField = new JPasswordField(40);

        JLabel repeatPaswordLabel = new JLabel("Repeat Password");
        repeatPasswordField = new JPasswordField(40);

        generateRegisterButton();
        generateCancelButton();

        panel.add(new JPanel(),"wrap");
        panel.add(accountLabel,"wrap");
        panel.add(accountTextField,"wrap");
        panel.add(new JPanel(),"wrap");
        panel.add(accountPasswordField, "wrap");
        panel.add(passwordLabel, "wrap");
        panel.add(accountPasswordField, "wrap");
        panel.add(new JPanel(),"wrap");
        panel.add(repeatPaswordLabel, "wrap");
        panel.add(repeatPasswordField, "wrap");
        panel.add(new JPanel(),"wrap");
        panel.add(new JPanel(),"wrap");
        panel.add(registerButton, "split 2");
        panel.add(cancelButton);


        add(panel);
        setVisible(true);
    }

    private void generateCancelButton() {
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void generateRegisterButton() {
        registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerButtonClicked();
            }
        });
    }

    private void registerButtonClicked() {
        if(accountTextField.getText().length() < 2) {
            JOptionPane.showMessageDialog(accountTextField, "Login is too short.");
        }else if(userAlreadyExists()){
            JOptionPane.showMessageDialog(accountTextField, "The username is already taken.");
        }else if(accountPasswordField.getPassword().length < 2){
            JOptionPane.showMessageDialog(accountPasswordField, "Password is too short.");
        }else if(!Objects.equals(Arrays.toString(accountPasswordField.getPassword()), Arrays.toString(repeatPasswordField.getPassword()))){
            JOptionPane.showMessageDialog(accountPasswordField, "Given passwords are different.");
            accountPasswordField.setText("");
            repeatPasswordField.setText("");
        }else{
            addUser();
            JOptionPane.showMessageDialog(accountTextField, "Registration was successful. Now log in to your new account.");
            dispose();
        }
    }

    private boolean userAlreadyExists() {
        List<User> users = userDao.findUsersWithLogin(accountTextField.getText());
        if(users.size() == 0){
            return false;
        }
        return true;
    }

    private void addUser() {
        userDao = new UserDao();
        User user = new User();
        user.setLogin(accountTextField.getText());

        char[] pass = accountPasswordField.getPassword();
        String passString = new String(pass);
        try {
            user.setPassword(Password.getSaltedHash(passString));
        } catch (Exception e) {
            e.printStackTrace();
        }
        userDao.create(user);
    }

    private void setMyLayout() {
        setLayout(new MigLayout());
    }

    private void setMyValues() {
        setSize(380,300);
        setLocationRelativeTo(null);
        setResizable(false);
    }
}
