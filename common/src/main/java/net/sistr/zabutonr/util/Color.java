package net.sistr.zabutonr.util;

//色の並びは1.20.2以降の色付きブロックの並びに準ずる。
public enum Color {
    WHITE("white", 0),
    LIGHT_GRAY("light_gray", 1),
    GRAY("gray", 2),
    BLACK("black", 3),
    BROWN("brown", 4),
    RED("red", 5),
    ORANGE("orange", 6),
    YELLOW("yellow", 7),
    LIME("lime", 8),
    GREEN("green", 9),
    CYAN("cyan", 10),
    LIGHT_BLUE("light_blue", 11),
    BLUE("blue", 12),
    PURPLE("purple", 13),
    MAGENTA("magenta", 14),
    PINK("pink", 15);
    //valuesでもいいが、順序を明確にするため独自で定義する。
    public static final Color[] colors;
    public final String name;
    public final int id;

    Color(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public static Color fromId(int id) {
        if (id > colors.length) {
            return WHITE;
        }
        return colors[id];
    }

    static {
        colors = new Color[16];
        for (Color color : Color.values()) {
            colors[color.id] = color;
        }
    }
}
