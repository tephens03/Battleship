package miscellaneous;

import java.awt.Color;

public class Misc {
        public static final char[] ALPHABET = { '|', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                        'N',
                        'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
        public static final Color UNSELECTED_COLOR = new Color(37, 38, 67);
        public static final Color[] PLAYER_COLOR = { UNSELECTED_COLOR, new Color(27, 57, 64), new Color(51, 51, 51) };
        public static final Color[] COMPUTER_COLOR = { UNSELECTED_COLOR, new Color(69, 30, 62), new Color(51, 51, 51) };
        public static final Color MISSED_COLOR = new Color(120, 193, 243);
        public static final Color HIT_COLOR = new Color(239, 98, 98);
        public static final Color DESTROYED_COLOR = new Color(0, 28, 48);
        public static final Color SHIP_COLOR = Color.GRAY;
        public static final Color HOVER_COLOR = new Color(47,160,138);
        public static final Color OVER_RANGED_COLOR = new Color(140,26,35);
        public static final Color WHITE = Color.WHITE;
        public static final int WIDTH = 600;
        public static final int HEIGHT = 600;
        public static final String[] LANGUAGE = { "English", "French", "Portuguese" };
        public static final String[] DIMENSION = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" };

}