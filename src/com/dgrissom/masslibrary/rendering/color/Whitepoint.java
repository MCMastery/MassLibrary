package com.dgrissom.masslibrary.rendering.color;

// https://en.wikipedia.org/wiki/White_point
public final class Whitepoint {
    // https://en.wikipedia.org/wiki/Standard_illuminant#White_points_of_standard_illuminants
    // we are using the 2 degree observer unless otherwise specified
    public static final XYYColor A = new XYYColor(0.44757, 0.40745);
    // B & C are obsolete, not considered to be CIE standard illuminants
    public static final XYYColor B = new XYYColor(0.34842, 0.35161);
    public static final XYYColor C = new XYYColor(0.31006, 0.31616);

    public static final XYYColor D50 = new XYYColor(0.34567, 0.35850);
    public static final XYYColor D55 = new XYYColor(0.33242, 0.34743);
    public static final XYYColor D65 = new XYYColor(0.31271, 0.32902);
    public static final XYYColor D75 = new XYYColor(0.29902, 0.31485);

    // represents all wavelengths equally
    public static final XYYColor E = new XYYColor(1 / 3.0, 1 / 3.0);

    // flourescent lighting
    public static final XYYColor F1 = new XYYColor(0.31310, 0.33727);
    public static final XYYColor F2 = new XYYColor(0.37208, 0.37529);
    public static final XYYColor F3 = new XYYColor(0.40910, 0.39430);
    public static final XYYColor F4 = new XYYColor(0.44018, 0.40329);
    public static final XYYColor F5 = new XYYColor(0.31379, 0.34531);
    public static final XYYColor F6 = new XYYColor(0.37790, 0.38835);
    public static final XYYColor F7 = new XYYColor(0.31292, 0.32933);
    public static final XYYColor F8 = new XYYColor(0.34588, 0.35875);
    public static final XYYColor F9 = new XYYColor(0.37417, 0.37281);
    public static final XYYColor F10 = new XYYColor(0.34609, 0.35986);
    public static final XYYColor F11 = new XYYColor(0.38052, 0.37713);
    public static final XYYColor F12 = new XYYColor(0.43695, 0.40441);

    //todo L series, for LED lighting. expected mid-2018

    private Whitepoint() {}
}
