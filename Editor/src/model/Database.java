package model;

import java.io.Serializable;

public class Database implements Serializable {

    private String name;
    private String path;
    private String backgroundColor;
    private String date;
    private String time;
    private String fontBold;
    private String fontItalic;
    private String fontSize;
    private String fontColor;

    public Database(String name, String path, String backgroundColor, String date, String time,
                    String fontBold, String fontItalic, String fontSize, String fontColor) {
        this.name = name;
        this.path = path;
        this.backgroundColor = backgroundColor;
        this.date = date;
        this.time = time;
        this.fontBold = fontBold;
        this.fontItalic = fontItalic;
        this.fontSize = fontSize;
        this.fontColor = fontColor;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String text) {
        this.path = text;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFontBold() {
        return fontBold;
    }

    public void setFontBold(String fontBold) {
        this.fontBold = fontBold;
    }

    public String getFontItalic() {
        return fontItalic;
    }

    public void setFontItalic(String fontItalic) {
        this.fontItalic = fontItalic;
    }

    public String getFontSize() {
        return fontSize;
    }

    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }
}

