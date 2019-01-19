package me.deftware.client.framework.utils;

import java.awt.*;

public class ColorUtil {

    public static Color convertCharToColor(String character) {
        switch (character) {
            case "0":
                return Color.black;
            case "1":
                return new Color(0, 0, 170);
            case "2":
                return new Color(0, 170, 0);
            case "3":
                return new Color(0, 170, 170);
            case "4":
                return new Color(170, 0, 0);
            case "5":
                return new Color(170, 0, 170);
            case "6":
                return new Color(255, 170, 0);
            case "7":
                return new Color(170, 170, 170);
            case "8":
                return new Color(85, 85, 85);
            case "9":
                return new Color(85, 85, 255);
            case "a":
                return new Color(85, 255, 85);
            case "b":
                return new Color(85, 255, 255);
            case "c":
                return new Color(255, 85, 85);
            case "d":
                return new Color(255, 85, 255);
            case "e":
                return new Color(255, 255, 85);
            case "f":
                return Color.white;
        }
        return Color.white;
    }
}
