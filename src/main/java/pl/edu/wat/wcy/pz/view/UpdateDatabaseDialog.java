package pl.edu.wat.wcy.pz.view;

import net.miginfocom.swing.MigLayout;
import pl.edu.wat.wcy.pz.controller.MP3Parser;
import pl.edu.wat.wcy.pz.model.dao.FolderDao;
import pl.edu.wat.wcy.pz.model.dao.SongDao;
import pl.edu.wat.wcy.pz.model.entities.music.Folder;
import pl.edu.wat.wcy.pz.model.entities.music.Song;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.*;
import java.util.List;


public class UpdateDatabaseDialog extends JDialog {
    private MainWindow mainWindow;

    private SongDao songDao = new SongDao();

    private JLabel label;
    private JScrollPane scrollPane;
    private JTextPane textPane;
    private final int textFieldCount = 20;
    private java.util.List<JTextField> textFieldList = new ArrayList<>(textFieldCount);
    private JButton okButton;
    private JButton cancelButton;
    private JPanel buttonsPanel;
    private ArrayList<String> mp3Paths = new ArrayList();

    public UpdateDatabaseDialog(MainWindow mainWindow) {
        super(mainWindow, "Update Database", true);
        this.mainWindow = mainWindow;

        setMyValues();
        setMyLayout();

        generateLabel("Directories to scan:");
        add(label, "wrap");

        generateTextPane();

        generateScrollPaneForTextArea();
        add(scrollPane, "wrap");

        generateButtonsPanel();
        add(buttonsPanel);

        setVisible(true);
    }

    private void generateButtonsPanel() {
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new MigLayout());

        okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateFolders();
                generateMp3Paths();
                addSongsAndArtistsToDatabase();
                mainWindow.songsPanel.revalidateMe();
                mainWindow.artistsPanel.revalidateMe();

                dispose();
            }
        });
        buttonsPanel.add(okButton);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        buttonsPanel.add(cancelButton);
    }

    private void generateMp3Paths() {
        FolderDao folderDao = new FolderDao();
        List<Folder> folderList = folderDao.findAll();
        File f;
        for (Folder folder : folderList) {
            if (!folder.getPath().equals("")){
                f = new File(folder.getPath());
                getMp3s(f);
            }
        }
    }


    private void addSongsAndArtistsToDatabase() {
        MP3Parser parser = new MP3Parser();
        for (String mp3Path : mp3Paths) {
            parser.addSongAndArtistToDatabase(mp3Path);
        }
    }

    private void drawMp3Paths() {
        for (String mp3Path : mp3Paths) {
            System.out.println(mp3Path);
        }
    }

    void getMp3s(File f) {
        File[] files;
        if (f.isDirectory() && (files = f.listFiles()) != null) {
            for (File file : files) {
                getMp3s(file);
            }
        }
        else {
            String path = f.getPath();
            if (path.substring(path.length()-4, path.length()).equals(".mp3")) {
                if(!songAlreadyExists(f.getPath())){
                    mp3Paths.add(f.getPath());
                }
            }
        }
    }

    private boolean songAlreadyExists(String path) {
        List<Song> resultList = songDao.findSongsWithPath(path);
        if(resultList.size()==0){
            return false;
        }
        return true;
    }


    private void updateFolders() {
        //saveFolder();

        deleteAllFolders();
        saveFolders();
        generateSongs();
    }

    private void saveFolder() {
        FolderDao folderDao = new FolderDao();
        Folder folder = new Folder();
        folder.setPath("cps");
        folderDao.create(folder);
    }

    private void generateSongs() {

    }

    private void saveFolders() {
        FolderDao folderDao = new FolderDao();
        Folder folder;
        for (JTextField textField : textFieldList) {
            folder = new Folder();
            folder.setPath(textField.getText());
            folderDao.create(folder);
        }
    }

    private void deleteAllFolders() {
        FolderDao folderDao = new FolderDao();
        List<Folder> folderList = folderDao.findAll();
        for (Folder folder : folderList) {
            folderDao.delete(folder.getId());
        }
    }

    private void generateTextPane() {
        textPane = new JTextPane();
        textPane.setPreferredSize(new Dimension(340, 400));
        textPane.setEditable(false);
        setTextAreaContent();
    }

    private void setTextAreaContent() {
        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet attr = new SimpleAttributeSet();
        for (int i = 0; i < 20;i++ )
        {
            try {
                generateTextField();
                textPane.insertComponent(textFieldList.get(i));
                textPane.insertComponent(new ChooseFileButton(textFieldList.get(i)));
                textPane.setCaretPosition(textPane.getDocument().getLength());
                doc.insertString(doc.getLength(), "\n", attr );
                textPane.insertComponent(new JSeparator());
                doc.insertString(doc.getLength(), "\n", attr );
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
        setTextFieldsTexts();
    }

    private void setTextFieldsTexts() {
        FolderDao folderDao = new FolderDao();
        List<Folder> folderList = folderDao.findAll();
        int i = 0;
        for (Folder folder : folderList) {
            System.out.println(folder.getPath());
            textFieldList.get(i).setText(folder.getPath());
            i++;
        }
    }

    private void generateTextField() {
        JTextField textField = new JTextField(10);
        textField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        textFieldList.add(textField);
    }

    private void generateScrollPaneForTextArea() {
        scrollPane = new JScrollPane(textPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    }

    private void generateLabel(String s) {
        label = new JLabel(s);
    }

    private void setMyLayout() {
        setLayout(new MigLayout());
    }

    private void setMyValues() {
        setSize(400,500);
        setLocationRelativeTo(null);
        setResizable(false);
    }
}
