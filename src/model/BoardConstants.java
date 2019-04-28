package model;

import java.awt.*;

public class BoardConstants {

    public static final int CELL_SIZE = 60;

    public static final Color BACKGROUND = new Color(255,255,255);

    public static final Color BORDER_COLOR = new Color(52, 55, 61);

    public static final Color CELL_COLOR_NORMAL = new Color(244, 248, 255);
    public static final Color CELL_COLOR_ERROR = new Color(255, 198, 198);

    public static final Color FONT_COLOR_NORMAL = Color.BLACK;
    public static final Color FONT_COLOR_AUTOMATICALLY_SOLVED = new Color(5, 135, 31);

    public static final Font FONT_EDITABLE = new Font("Arial", Font.PLAIN, 13);
    public static final Font FONT_UNEDITABLE = new Font("Arial Black", Font.BOLD, 13);
}
