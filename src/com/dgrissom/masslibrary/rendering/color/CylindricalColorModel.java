package com.dgrissom.masslibrary.rendering.color;

// https://en.wikipedia.org/wiki/HSL_and_HSV
public abstract class CylindricalColorModel implements Color {
    // [0, 360]
    public abstract double hue();
    public abstract double chroma();
    abstract RGBColor matchLightness(RGBColor rgb);

    // https://en.wikipedia.org/wiki/HSL_and_HSV#From_HSV
    @Override
    public RGBColor toRGB(ColorSpace colorSpace) {
        double h = hue();
        double c = chroma();
        double hPrime = h / 60;
        double x = c * (1 - Math.abs(hPrime % 2 - 1));
        RGBColor rgb;
        if (0 <= hPrime && hPrime <= 1)
            rgb = new RGBColor(c, x, 0);
        else if (1 <= hPrime && hPrime <= 2)
            rgb = new RGBColor(x, c, 0);
        else if (2 <= hPrime && hPrime <= 3)
            rgb = new RGBColor(0, c, x);
        else if (3 <= hPrime && hPrime <= 4)
            rgb = new RGBColor(0, x, c);
        else if (4 <= hPrime && hPrime <= 5)
            rgb = new RGBColor(x, 0, c);
        else if (5 <= hPrime && hPrime <= 6)
            rgb = new RGBColor(c, 0, x);
        else
            throw new IllegalArgumentException("hue is outside range [0, 360]");
        return matchLightness(rgb);
    }
}
