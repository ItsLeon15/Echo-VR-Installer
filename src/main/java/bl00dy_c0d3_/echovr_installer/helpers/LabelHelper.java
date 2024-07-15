package bl00dy_c0d3_.echovr_installer.helpers;

import bl00dy_c0d3_.echovr_installer.SpecialLabel;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public class LabelHelper {
    @Contract("_, _ -> new")
    public static @NotNull SpecialLabel createSpecialLabel(String text, int fontSize) {
        return new SpecialLabel(text, fontSize);
    }

    public static @NotNull SpecialLabel createSpecialLabel(@Nullable String text, int fontSize, int x, int y) {
        SpecialLabel label = createSpecialLabel(text, fontSize);
        label.setLocation(x, y);
        return label;
    }

    public static @NotNull SpecialLabel createSpecialLabel(String text, int fontSize, int x, int y, Dimension size, Color foreground, @NotNull Color background) {
        SpecialLabel label = createSpecialLabel(text, fontSize, x, y);
        label.setSize(size);
        label.setForeground(foreground);
        label.setBackground(new Color(background.getRed(), background.getGreen(), background.getBlue(), 200));
        return label;
    }

    public static @NotNull SpecialLabel createSpecialLabel(String text, int fontSize, int x, int y, Dimension size, Color foreground, @NotNull Color background, int horizontalAlignment) {
        SpecialLabel label = createSpecialLabel(text, fontSize, x, y, size, foreground, background);
        label.setHorizontalAlignment(horizontalAlignment);
        return label;
    }
}
