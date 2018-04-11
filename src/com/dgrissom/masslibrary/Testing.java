package com.dgrissom.masslibrary;

import java.io.IOException;

public final class Testing {
    private Testing() {}

    public static void main(String[] args) throws IOException {

//        Image polygonsAnd = new Image(800, 800, false);
//        Polygon p1 = RegularPolygon.fromVertices(3, new Point2d(300, 250), 250);
//        Polygon p2 = RegularPolygon.fromVertices(7, new Point2d(400, 350), 200);
//        polygonsAnd.draw(p1, RGBColor.WHITE);
//        polygonsAnd.draw(p2, RGBColor.WHITE);
//        PointSet2d ps1 = Random.pointSet2d(50, 100, 100, 500, 500);
//        PointSet2d ps2 = Random.pointSet2d(50, 300, 300, 700, 700);
//        Polygon poly1 = ps1.convexHull();
//        Polygon poly2 = ps2.convexHull();

//        for (Point2d p1 : ps1)
//            polygonsAnd.fill(new Circle2d(p1, 2), RGBColor.WHITE);
//        for (Point2d p2 : ps2)
//            polygonsAnd.fill(new Circle2d(p2, 2), RGBColor.WHITE);

//        polygonsAnd.draw(poly1, RGBColor.WHITE);
//        polygonsAnd.draw(poly2, RGBColor.WHITE);
//        polygonsAnd.fill(PolygonBooleanOperations.and(poly1, poly2, polygonsAnd), RGBColor.WHITE);
//        polygonsAnd.save(new File("renderings/polygons/and.png"));


//        Image points = new Image(800, 800, false);
//        PointSet2d ps = new PointSet2d();
//        for (int i = 0; i < 50; i++) {
//            Point2d p = Random.point2d(200, 200, 600, 600);
//            ps.add(p);
//            points.fill(new Circle2d(p, 2), RGBColor.WHITE);
//        }
//        points.draw(ps.convexHull(), RGBColor.WHITE);
//        for (int i = 0; i < 3; i++) {
//            ps.add(Random.point2d(0, 0, 800, 800));
//        }
//        Triangle2d t = new Triangle2d(ps.get(0), ps.get(1), ps.get(2));
//        points.draw(t, RGBColor.WHITE);
//        points.draw(t.circumcircle(), RGBColor.WHITE);
//        points.draw(t.incircle(), RGBColor.WHITE);
//        points.save(new File("renderings/point-set.png"));

//        DataSet ds = new DataSet(1, 2, 3, 4, 5, 6, 8, 9);
//        System.out.println(ds.median());


//        Image complex = new Image(1000, 1000, false);
//        for (double a = -10; a < 10; a += 10 / complex.getWidth()) {
//            for (double b = -10; b < 10; b += 10 / complex.getHeight()) {
//                Point2d p = new Point2d(a, b).multiply(100).add(500);
//                ComplexNumber cn = new ComplexNumber(a, b);
//                cn = cn.sin();
//                complex.draw(p, cn.toHSL());
//            }
//        }
//        complex.save(new File("renderings/complex-plane.png"));

//        Image isometric = new Image(800, 800, false);
//        IsometricProjection ip = new IsometricProjection();
//        CompositeTransform cp = new CompositeTransform(new Scale2d(800, 800), new Translate2d(400, 400));
//
//        List<Face> faces = new ArrayList<>();
//        for (double x = -0.5; x <= 0.5; x += 0.1) {
//            for (double z = -1; z <= 0.5; z += 0.1) {
//                faces.addAll(Cuboid.cube(new Point3d(x, 0.5, z), 0.1).faces());
//            }
//        }
//        for (Face face : faces) {
//            List<Point2d> p2dVertices = new ArrayList<>();
//            for (Point3d p3d : face.getVertices()) {
//                Point2d p2d = p3d.transform(ip).toPoint2d().transform(cp);
//                p2dVertices.add(p2d);
//            }
//            Polygon polygon = Polygon.fromVertices(p2dVertices);
//            Point2d centroid = polygon.centroid();
//            Rectangle2d bounds = polygon.boundingBox();
//            RGBColor color = Random.rgb();
//            for (int x = (int) bounds.left(); x < (int) bounds.right(); x++) {
//                for (int y = (int) bounds.left(); y < (int) bounds.right(); y++) {
//                    if (Intersections2d.contains(polygon, new Point2d(x, y), bounds, centroid, false, 0)) {
//                        isometric.draw(new Point2d(x, y), color);
//                    }
//                }
//            }
//            isometric.fill(Polygon.fromVertices(p2dVertices), Random.rgb());
//        }
//
//        isometric.save(new File("renderings/isometric-2.png"));


//        USShapefile.load();
//        Image image = new Image(1600, 800, false);
//
//        for (Ring ring : USShapefile.polygonRecord().rings()) {
//            image.fill(ring.transform(new CompositeTransform(new Scale2d(20, -20),
//                    new Translate2d(2850, 1200))), RGBColor.WHITE);
//        }
//        image.save(new File("renderings/gis/us.png"));

//        Image image = new Image(800, 800, false);
//        for (double sigma = 0; sigma < 2; sigma += 10e-2) {
//            for (double tau = 0; tau < 2; tau += 10e-4) {
//                BipolarCoordinates bc = new BipolarCoordinates(sigma, tau, 0.1);
//                Point2d point = bc.toCartesian().multiply(1, -1).multiply(800, 800).add(400, 400);
//                image.draw(point, RGBColor.WHITE);
//            }
//        }
//        image.save(new File("renderings/bipolar-coordinates.png"));

//        XYZColor a = new XYZColor(10, 20, 60);
//        XYZColor b = new XYZColor(56, 20, 80);
//        Image i = new Image(800, 800);
//        i.fill(new Rectangle2d(0, 0, 600, 800), a);
//        i.fill(new Rectangle2d(200, 0, 600, 800), b);
//        i.fill(new Rectangle2d(400, 0, 400, 800), XYYColor.mix(a, b));
//        i.save(new File("renderings/color/mix.png"));

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

//        LabColor a = new LabColor(50, 20, -128);
//        LabColor b = new LabColor(20, 60, -100);
//        System.out.println("DeltaE76: " + a.deltaE76(b));
//        System.out.println("DeltaE94: " + a.deltaE94(b));
//        System.out.println("DeltaE2000: " + a.deltaE2000(b));

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
