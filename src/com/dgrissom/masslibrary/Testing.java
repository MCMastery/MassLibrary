package com.dgrissom.masslibrary;

import com.dgrissom.masslibrary.rendering.color.LabColor;

import java.io.IOException;

public final class Testing {
    private Testing() {}

    public static void main(String[] args) throws IOException {
//        Image testImage = Image.load(new File("renderings/test-2.jpg"));
//        for (int x = 0; x < testImage.getWidth(); x++) {
//            for (int y = 0; y < testImage.getHeight(); y++) {
//                RGBColor rgb = testImage.getColor(x, y);
//                HSLColor hsl = rgb.toHSL();
////                hsl = hsl.setH(GeneralMath.clamp(hsl.getH() - 30, 0, 360));
//                hsl = hsl.setS(hsl.getS() * 1.25);
//                testImage.draw(new Point2d(x, y), hsl);
//            }
//
//            String percent = String.valueOf((int) Math.round((double) x / testImage.getWidth() * 100));
//            System.out.println(percent + "% done");
//        }
//        testImage.save(new File("renderings/color/test-2-shift.png"));

        LabColor a = new LabColor(50, 20, -128);
        LabColor b = new LabColor(20, 60, -100);
        System.out.println("DeltaE76: " + a.deltaE76(b));
        System.out.println("DeltaE94: " + a.deltaE94(b));
        System.out.println("DeltaE2000: " + a.deltaE2000(b));

//        Image hsvColors = new Image(800, 800, false);
//        Circle2d circle = new Circle2d(hsvColors.getWidth() / 2, hsvColors.getHeight() / 2,
//                Math.min(hsvColors.getWidth(), hsvColors.getHeight()) / 3);
//        for (double hue = 0; hue < 360; hue += 360 / 10e3d) {
//            PolarCoordinates polar = circle.point(Math.toRadians(hue));
//            for (double saturation = 0; saturation < 1; saturation += 10e-4) {
//                polar = polar.setMagnitude(saturation * circle.getRadius());
//                Point2d point = polar.toCartesian();
//                HSVColor hsv = new HSVColor(hue, saturation, 1);
//                hsvColors.draw(point, hsv);
//            }
//        }
//        hsvColors.save(new File("renderings/color/hsv.png"));

//        Image xyyColors = new Image(800, 800, false);
//        xyyColors.setColorSpace(ColorSpace.SRGB);
//        Polygon gamut = xyyColors.getColorSpace().gamut();
//        Rectangle2d boundingBox = gamut.boundingBox();
//        Point2d centroid = gamut.centroid();
//
//        for (double x = 0; x < 1; x += 1.0 / xyyColors.getWidth()) {
//            for (double y = 0; y < 1; y += 1.0 / xyyColors.getHeight()) {
//                if (!Intersections2d.contains(gamut, new Point2d(x, y), boundingBox, centroid, false, 0))
//                    continue;
//
//                XYYColor xyy = new XYYColor(x, y, 1);
//                double imgX = x * xyyColors.getWidth();
//                double imgY = y * xyyColors.getHeight() * -1 + xyyColors.getHeight();
//                xyyColors.draw(new Point2d(imgX, imgY), xyy);
//            }
//        }
//        int imgX = (int) Math.round(xyyColors.getColorSpace().getWhitepoint().x() * xyyColors.getWidth());
//        int imgY = (int) Math.round(xyyColors.getColorSpace().getWhitepoint().y() * xyyColors.getHeight() * -1 + xyyColors.getHeight());
//        xyyColors.draw(new Point2d(imgX, imgY), RGBColor.BLACK);
//        xyyColors.save(new File("renderings/color/color-spaces/srgb.png"));
    }
}
