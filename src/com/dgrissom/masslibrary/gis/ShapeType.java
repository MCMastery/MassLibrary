package com.dgrissom.masslibrary.gis;

// https://en.wikipedia.org/wiki/Shapefile
public enum ShapeType {
    NULL(0, "Null"),
    POINT(1, "Point"),
    POLYLINE(3, "Polyline"),
    POLYGON(5, "Polygon"),
    MULTI_POINT(8, "MultiPoint"),
    POINT_Z(11, "PointZ"),
    POLYLINE_Z(13, "PolylineZ"),
    POLYGON_Z(15, "PolygonZ"),
    MULTI_POINT_Z(18, "MultiPointZ"),
    POINT_M(21, "PointM"),
    POLYLINE_M(23, "PolylineM"),
    POLYGON_M(25, "PolygonM"),
    MULTI_POINT_M(28, "MultiPointM"),
    MULTI_PATCH(31, "MultiPatch");

    private final int id;
    private final String name;

    ShapeType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }

    public static ShapeType fromId(int id) {
        for (ShapeType shapeType : values())
            if (shapeType.getId() == id)
                return shapeType;
        throw new IllegalArgumentException("no ShapeType with id " + id);
    }
}
