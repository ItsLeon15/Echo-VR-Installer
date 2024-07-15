package bl00dy_c0d3_.echovr_installer.helpers;

import bl00dy_c0d3_.echovr_installer.ErrorDialog;
import bl00dy_c0d3_.echovr_installer.SpecialLabel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URL;

public class Helpers {
    public static @Nullable Image loadGUI(String imageName) {
        URL imageURL = Helpers.class.getClassLoader().getResource(imageName);
        return imageURL == null ? null : new ImageIcon(imageURL, imageName).getImage();
    }

    public static void centerFrame(@NotNull Window frame, int width, int height) {
        frame.setSize(width, height);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - frame.getWidth()) / 2;
        int y = (d.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);
    }

        public static void jsonFileChooser(SpecialLabel labelPcDownloadPath, JDialog outFrame) {
        FileDialog fd = new FileDialog((Frame) null, "Select a JSON file", FileDialog.LOAD);
        fd.setFile("*.json");
        fd.setVisible(true);

        String directory = fd.getDirectory();
        String filename = fd.getFile();

        if (filename != null && filename.endsWith(".json")) {
            String configPath = new File(directory, filename).getPath();
            labelPcDownloadPath.setText(configPath);
            outFrame.repaint();
        }
        else{
            new ErrorDialog().errorDialog(outFrame, "Wrong filetype provided", "Your provided file is not a config.json. Please check again!", 0);
        }
    }


    public static void pathFolderChooser(SpecialLabel labelPcDownloadPath, JDialog outFrame) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            String directory = chooser.getSelectedFile().getPath();
            labelPcDownloadPath.setText(directory);
            outFrame.repaint();
        }
    }

}
