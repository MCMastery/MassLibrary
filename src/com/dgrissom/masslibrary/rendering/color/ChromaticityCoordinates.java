package com.dgrissom.masslibrary.rendering.color;

import com.dgrissom.masslibrary.Formatted;
import com.dgrissom.masslibrary.ObjectFormatter;

// https://en.wikipedia.org/wiki/CIE_1931_color_space#CIE_xy_chromaticity_diagram_and_the_CIE_xyY_color_space
// represents a point on a chromaticity locus
public class ChromaticityCoordinates {
    // center of the locus
    public static final ChromaticityCoordinates EPICENTER = new ChromaticityCoordinates(0.3320, 0.1858);

    @Formatted
    private final double x, y;

    public ChromaticityCoordinates(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return this.x;
    }
    public ChromaticityCoordinates setX(double x) {
        return new ChromaticityCoordinates(x, this.y);
    }
    public double getY() {
        return this.y;
    }
    public ChromaticityCoordinates setY(double y) {
        return new ChromaticityCoordinates(this.x, y);
    }

    // correlated color temperature
    // https://en.wikipedia.org/wiki/Color_temperature#Approximation
    // approximation
    public double cct() {
        double n = (this.x - EPICENTER.getX()) / (this.y - EPICENTER.getY());
        return -449 * n * n * n + 3525 * n * n - 6823.3 * n + 5520.33;
    }
    // https://en.wikipedia.org/wiki/Planckian_locus#Approximation
    // from correlated color temperature
    // approximation
    public static ChromaticityCoordinates from(double cct) {
        if (cct < 1667 || cct > 25000)
            throw new IllegalArgumentException("cct out of range [1667, 25000]");

        double x, y;
        if (1667 <= cct && cct <= 4000) {
            x = -0.2661239 * (Math.pow(10, 9) / (cct * cct * cct)) - 0.2343580 * (Math.pow(10, 6) / (cct * cct))
                    + 0.8776956 * (Math.pow(10, 3) / cct) + 0.179910;
        } else {
            x = -3.0258469 * (Math.pow(10, 9) / (cct * cct * cct)) + 2.1070379 * (Math.pow(10, 6) / (cct * cct))
                    + 0.2226347 * (Math.pow(10, 3) / cct) + 0.240390;
        }

        if (1667 <= cct && cct <= 2222)
            y = -1.1063814 * x * x * x - 1.34811020 * x * x + 2.18555832 * x - 0.20219683;
        else if (2222 <= cct && cct <= 4000)
            y = -0.9549476 * x * x * x - 1.37418593 * x * x + 2.09137015 * x - 0.16748867;
        else
            y = 3.0817580 * x * x * x - 5.87338670 * x * x + 3.75112997 * x - 0.37001483;
        return new ChromaticityCoordinates(x, y);
    }

    @Override
    public String toString() {
        return ObjectFormatter.format(this);
    }
}
