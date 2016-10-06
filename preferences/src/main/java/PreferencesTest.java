import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.prefs.*;

/**
 * Created by ScorpionOrange on 2016/10/02.
 */
public class PreferencesTest {
    public static void main(String[] args){
        EventQueue.invokeLater(() -> {
            PreferencesFrame frame = new PreferencesFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

/**
 * A frame that restores position and size from user preferences and updates the preferences upon exit.
 */
class PreferencesFrame extends JFrame{
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;

    public PreferencesFrame(){
        // get position, size, title from preferences
        Preferences root = Preferences.userRoot();
        final Preferences node = root.node("/com/horstmann/corejava");
        int left = node.getInt("left", 0);
        int top = node.getInt("top", 0);
        int width = node.getInt("width", DEFAULT_WIDTH);
        int height = node.getInt("height", DEFAULT_HEIGHT);
        setBounds(left, top, width, height);

        // if no title given, ask user
        String title = node.get("title", "");
        if(title.equals("")){
            title = JOptionPane.showInputDialog("Please supply a frame title:");
        }
        if(title == null){
            title = "";
        }
        setTitle(title);

        // set up file chooser that shows XML files
        final JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));

        // accept all files ending with .xml
        chooser.setFileFilter(new javax.swing.filechooser.FileFilter(){
            public boolean accept(File f){
                return f.getName().toLowerCase().endsWith(".xml") || f.isDirectory();
            }

            public String getDescription(){
                return "XML files";
            }
        });

        // set up menus
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu menu = new JMenu("File");
        menuBar.add(menu);

        JMenuItem exportItem = new JMenuItem("Export preferences");
        menu.add(exportItem);
        // normal mode
        /*
        exportItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if(chooser.showSaveDialog(PreferencesFrame.this) == JFileChooser.APPROVE_OPTION){
                    try{
                        OutputStream out = new FileOutputStream(chooser.getSelectedFile());
                        node.exportSubtree(out);
                        out.close();
                    }
                    catch (Exception exception){
                        exception.printStackTrace();                    }
                }
            }
        });
        */

        // lambda mode
        exportItem.addActionListener(event -> {
            if(chooser.showSaveDialog(PreferencesFrame.this) == JFileChooser.APPROVE_OPTION){
                try{
                    OutputStream out = new FileOutputStream(chooser.getSelectedFile());
                    node.exportSubtree(out);
                    out.close();
                }
                catch (Exception exception){
                    exception.printStackTrace();                    }
            }
        });

        JMenuItem importItem = new JMenuItem("Import preferences");
        menu.add(importItem);
        // normal mode
        /*
        importItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if(chooser.showOpenDialog(PreferencesFrame.this) == JFileChooser.APPROVE_OPTION){
                    try{
                        InputStream in = new FileInputStream(chooser.getSelectedFile());
                        Preferences.importPreferences(in);
                        in.close();
                    }
                    catch (Exception exception){
                        exception.printStackTrace();
                    }
                }
            }
        });
        */

        //lambda mode
        importItem.addActionListener(event -> {
            if(chooser.showOpenDialog(PreferencesFrame.this) == JFileChooser.APPROVE_OPTION){
                try{
                    InputStream in = new FileInputStream(chooser.getSelectedFile());
                    Preferences.importPreferences(in);
                    in.close();
                }
                catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        });

        JMenuItem exitItem = new JMenuItem("Exit");
        menu.add(exitItem);
        // normal mode
        /*
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                node.putInt("left", getX());
                node.putInt("top", getY());
                node.putInt("width", getWidth());
                node.putInt("height", getHeight());
                node.put("title", getTitle());
                System.exit(0);
            }
        });
        */

        // lambda mode
        exitItem.addActionListener(event -> {
            node.putInt("left", getX());
            node.putInt("top", getY());
            node.putInt("width", getWidth());
            node.putInt("height", getHeight());
            node.put("title", getTitle());
            System.exit(0);
        });
    }
}
