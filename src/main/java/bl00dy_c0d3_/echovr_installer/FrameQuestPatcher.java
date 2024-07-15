package bl00dy_c0d3_.echovr_installer;

import com.frostwire.jlibtorrent.SessionManager;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static bl00dy_c0d3_.echovr_installer.helpers.Helpers.*;
import static bl00dy_c0d3_.echovr_installer.helpers.LabelHelper.createSpecialLabel;

public class FrameQuestPatcher extends JDialog {
    private static final int FRAME_WIDTH = 1280;
    private static final int FRAME_HEIGHT = 720;
    private static final String DEFAULT_PATH = "C:\\EchoVR";
    private final Path targetPath = Paths.get(System.getProperty("java.io.tmpdir"), "echo/");
    private SpecialTextfield textfieldQuestPatchLink;
    private SpecialLabel labelConfigPath;
    private SpecialCheckBox checkBoxConfig;
    private Downloader downloader = null;
    private TorrentDownload downloader2 = null;

    public FrameQuestPatcher() {
        initComponents();
        this.setVisible(true);
    }

    JDialog outframe = this;

    @Override
    public void dispose() {
        if (downloader != null) {
            downloader.cancelDownload();
        }
        if (downloader2 != null) {
            downloader2.cancelDownload();
        }
        super.dispose();
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setIconImage(loadGUI("icon.png"));
        setTitle("Echo VR Installer v0.3");

        setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        setModal(true);
        setContentPane(createContentPane());

        pack();
        centerFrame(this, FRAME_WIDTH, FRAME_HEIGHT);
    }

    private @NotNull JPanel createContentPane() {
        Background back = new Background("echo-vr-cierra.png");
        back.setLayout(null);

        addSpecialLabels(back);
        addSpecialHyperlinks(back);
        addImages(back);
        addTextFields(back);
        addButtons(back);
        addCheckBoxes(back);

        return back;
    }

    private void handleDownloadButtonClick() {
        if (textfieldQuestPatchLink.getText().matches("https://tmpfiles.org.*")) {
            JOptionPane.showMessageDialog(null, "The Download will start after pressing OK. Please wait for both files to be done!", "Download started", JOptionPane.INFORMATION_MESSAGE);
            downloader = new Downloader();
            String fixedURL = textfieldQuestPatchLink.getText().replace("org", "org/dl");
            downloader.startDownload(fixedURL, targetPath.toString(), "personilizedechoapk.apk", labelQuestProgress2, this, 2);
            SessionManager sessionManager = new SessionManager();
            sessionManager.start();
            downloader2 = new TorrentDownload(sessionManager);
            downloader2.startDownload("p2pFiles/obb.torrent", targetPath.toString(), "main.4987566.com.readyatdawn.r15.obb", labelQuestProgress3, this, null, 2);
        } else {
            new ErrorDialog().errorDialog(this, "Wrong URL provided", "Your provided Download Link is wrong. Please check!", 0);
        }
    }

    private void handleChooseConfigClick() {
        jsonFileChooser(labelConfigPath, outframe);
    }

    private void handlePatchingButtonClick() {
        String apkfileName;
        if (checkBoxConfig.isSelected()) {
            File f = new File(targetPath + "/personilizedechoapk.apk");
            if (f.exists() && !f.isDirectory()) {
                PatchAPK patchAPK = new PatchAPK();
                if (!patchAPK.patchAPK(targetPath.toString(), "personilizedechoapk.apk", labelConfigPath.getText(), labelConfigPath, this)) {
                    return;
                }
            } else {
                new ErrorDialog().errorDialog(this, "Echo not found", "Echo wasn't found. Please use the Download Button first", 2);
                return;
            }
            apkfileName = "changedConfig-aligned-debugSigned.apk";
        } else {
            apkfileName = "personilizedechoapk.apk";
        }
        InstallerQuest installtoQuest = new InstallerQuest();
        installtoQuest.installAPK(targetPath.toString(), apkfileName, "main.4987566.com.readyatdawn.r15.obb", labelQuestProgress4, this);
    }

    private void addBackgroundImage(@NotNull JPanel back, String imagePath, int x, int y, int width, int height) {
        Background image = new Background(imagePath);
        image.setLocation(x, y);
        image.setSize(width, height);
        image.setVisible(true);
        back.add(image);
    }

    SpecialLabel labelQuestProgress2 = new SpecialLabel(" 0%", 15);
    SpecialLabel labelQuestProgress3 = new SpecialLabel(" 0%", 15);
    SpecialLabel labelQuestProgress4 = new SpecialLabel("Not started yet", 18);

    private void addSpecialLabels(@NotNull JPanel back) {
        back.add(createSpecialLabel("1. Join the Echo VR Patcher Discord Server:", 16, 40, 40));
        back.add(createSpecialLabel("2. React to the message on Discord", 16, 40, 135));
        back.add(createSpecialLabel("by clicking on the smiley:", 16, 40, 165));

        back.add(createSpecialLabel("3. You will receive a private Message from the", 16, 40, 335));
        back.add(createSpecialLabel("\"EchoSignUp\" Bot. Right Click on the blue URL ", 16, 40, 365));
        back.add(createSpecialLabel("and select Copy Link. NOT COPY MESSAGE LINK!", 16, 40, 395));

        back.add(createSpecialLabel("4. Paste the link with CTRL-V:", 16, 582, 40));
        back.add(createSpecialLabel("5. Start the Download Process:", 16, 582, 170));
        back.add(createSpecialLabel("5(a). Optional config.json. Don't use if you don't need to:", 16, 582, 295));
        back.add(createSpecialLabel("6. After the Download above is finished, start this button:", 16, 582, 490));
        back.add(createSpecialLabel("Progress = ", 17, 810, 210, new Dimension(130, 38), Color.BLACK, Color.WHITE));

        String configPath = "Optional: Choose config.json on the button above";
        labelConfigPath = createSpecialLabel(configPath, 14, 582, 455, new Dimension(600, 25), Color.BLACK, Color.WHITE);
        back.add(labelConfigPath);

        labelQuestProgress2 = createSpecialLabel(null, 17, 940, 210, new Dimension(130, 19), Color.BLACK, Color.WHITE, SwingConstants.LEFT);
        labelQuestProgress3 = createSpecialLabel(null, 17, 940, 229, new Dimension(130, 19), Color.BLACK, Color.WHITE, SwingConstants.LEFT);
        labelQuestProgress4 = createSpecialLabel(null, 17, 885, 587, new Dimension(300, 50), Color.BLACK, Color.WHITE, SwingConstants.LEFT);

        back.add(labelQuestProgress3);
        back.add(labelQuestProgress2);
        back.add(labelQuestProgress4);
    }

    private void addSpecialHyperlinks(@NotNull JPanel back) {
        back.add(new SpecialHyperlink(40, 95, "Click on me to join the Echo VR Patcher Discord-Server", "https://discord.gg/bMpsva6fmA", 14));
    }

    private void addImages(JPanel back) {
        addBackgroundImage(back, "quest_react.png", 40, 215, 182, 108);
        addBackgroundImage(back, "copy_linkQuest.png", 40, 465, 279, 177);
    }

    private void addTextFields(@NotNull JPanel back) {
        textfieldQuestPatchLink = new SpecialTextfield();
        textfieldQuestPatchLink.specialTextfield(630, 30, 582, 87, 13);
        back.add(textfieldQuestPatchLink);
    }

    private void addButtons(JPanel back) {
        addStartDownloadButton(back);
        addChooseConfigButton(back);
        addStartPatchingButton(back);
    }

    private void addCheckBoxes(@NotNull JPanel back) {
        checkBoxConfig = new SpecialCheckBox("Check this to use the custom config", 17);
        checkBoxConfig.setSize(500, 30);
        checkBoxConfig.setLocation(582, 525);
        checkBoxConfig.setOpaque(true);
        checkBoxConfig.setBackground(new Color(50, 50, 50));
        back.add(checkBoxConfig);
    }

    private void addStartDownloadButton(@NotNull JPanel back) {
        SpecialButton questStartDownload = new SpecialButton("Start Download", "button_up_middle.png", "button_down_middle.png", "button_highlighted_middle.png", 18);
        questStartDownload.setLocation(582, 210);
        questStartDownload.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent event) {
                handleDownloadButtonClick();
            }
        });
        back.add(questStartDownload);
    }


    private void addChooseConfigButton(@NotNull JPanel back) {
        SpecialButton chooseConfig = new SpecialButton("OPTIONAL CONFIG", "button_up_middle.png", "button_down_middle.png", "button_highlighted_middle.png", 15);
        chooseConfig.setLocation(582, 405);
        chooseConfig.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent event) {
                handleChooseConfigClick();
            }
        });
        back.add(chooseConfig);
    }

    private void addStartPatchingButton(@NotNull JPanel back) {
        SpecialButton pcStartPatch = new SpecialButton("Start patching", "button_up.png", "button_down.png", "button_highlighted.png", 18);
        pcStartPatch.setLocation(585, 587);
        pcStartPatch.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent event) {
                handlePatchingButtonClick();
            }
        });
        back.add(pcStartPatch);
    }
}
