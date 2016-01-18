package pl.edu.wat.wcy.pz.view;

import de.javasoft.plaf.synthetica.SyntheticaBlackEyeLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaLookAndFeel;
import net.miginfocom.swing.MigLayout;
import pl.edu.wat.wcy.pz.controller.Password;
import pl.edu.wat.wcy.pz.model.dao.UserDao;
import pl.edu.wat.wcy.pz.model.entities.accounts.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;


public class LoginWindow extends JFrame{
    private static LoginWindow instance = null;
    UserDao userDao = new UserDao();


    public int windowWidth = 400;
    public int windowHeight = 314;

    private JPanel mainPanel;

    private JTextField loginTextField;
    private JPasswordField passwordTextField;

    private JButton loginButton;
    private JButton registerButton;
    private JButton cancelButton;
    private int buttonsLength;

    private CustomPanel logoPanel;

    private JPanel loginAndCancelPanel;


    public static LoginWindow getInstance() {
        if (instance == null) {
            instance = new LoginWindow();
        }
        return instance;
    }

    public LoginWindow(){
        setIcon();
        setLookAndFeel();
        setMainWindowValues();
        setMainWindowLayout();
        setTitle("Login to Guitar Organizer");

        generateMainPanel();

        generateLogoPanel();


        JLabel loginLabel = new JLabel("Login:");
        JLabel passwordLabel = new JLabel("Password:");
        loginTextField = new JTextField(40);
        passwordTextField = new JPasswordField(40);
        generateLoginButton();
        generateRegisterButton();
        generateCancelButton();
        generateLoginAndCancelPanel();

        mainPanel.add(new JPanel(),"wrap");
        mainPanel.add(new JPanel());
        mainPanel.add(logoPanel, "wrap");
        mainPanel.add(new JPanel(),"wrap");
        mainPanel.add(loginLabel);
        mainPanel.add(loginTextField, "wrap");
        mainPanel.add(new JPanel(),"wrap");
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordTextField, "wrap");
        mainPanel.add(new JPanel(),"wrap");
        mainPanel.add(new JPanel());
        mainPanel.add(loginButton, "split 2");
        mainPanel.add(cancelButton, "wrap");
        mainPanel.add(new JPanel(),"wrap");
        mainPanel.add(new JPanel());
        mainPanel.add(registerButton, "span 2");


        add(mainPanel);

        getRootPane().setDefaultButton(loginButton);
        setVisible(true);
    }

    private void generateLoginAndCancelPanel() {
//        loginAndCancelPanel = new JPanel(new MigLayout());
//        loginAndCancelPanel.add(loginButton);
//        loginAndCancelPanel.add(cancelButton);
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

    private void generateLogoPanel() {
        logoPanel = new CustomPanel("src/images/logo.png");
        logoPanel.setPreferredSize(new Dimension(180,70));
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
        new RegisterDialog(this);
    }

    private void generateLoginButton() {
        loginButton = new JButton("Login");
        //loginButton.setPreferredSize(new Dimension(80,24));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    loginButtonClicked();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    private void loginButtonClicked() throws Exception {
        if(loginTextField.getText().length() < 2){
            JOptionPane.showMessageDialog(loginTextField, "Login is too short.");
        }else if(passwordTextField.getPassword().length < 2){
            JOptionPane.showMessageDialog(loginTextField, "Password is too short.");
        }else{
            char[] pass = passwordTextField.getPassword();
            String passString = new String(pass);
            java.util.List<User> users = userDao.findUsersWithLogin(loginTextField.getText());
            if (Password.check(passString, users.get(0).getPassword())){
                MainWindow.getInstance(users.get(0)).setVisible(true);
                dispose();
            }else{
                JOptionPane.showMessageDialog(loginTextField, "Wrong password.");
                passwordTextField.setText("");
            }

        }

    }

    private void generateMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new MigLayout());
    }

    private void setMainWindowValues() {
        setSize(windowWidth, windowHeight);
        centerWindow();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
    }

    private void centerWindow() {
        setLocationRelativeTo(null);
    }

    private void setMainWindowLayout() {
        //setLayout(new BorderLayout());
    }

    private void setLookAndFeel() {
        try {

            UIManager.removeAuxiliaryLookAndFeel(UIManager.getLookAndFeel());
            SyntheticaLookAndFeel.setWindowsDecorated(false);
            UIManager.setLookAndFeel(new SyntheticaBlackEyeLookAndFeel());
            SwingUtilities.updateComponentTreeUI(this);

        } catch (Exception ex) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setIcon() {
        ImageIcon img = new ImageIcon("src/images/guitarIcon.png");
        setIconImage(img.getImage());
    }
}
