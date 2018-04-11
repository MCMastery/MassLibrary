package com.dgrissom.masslibrary.gis;

import com.dgrissom.masslibrary.gis.record.PolygonRecord;

import java.io.File;
import java.io.IOException;

public class CanadaShapefile {
    // http://www12.statcan.gc.ca/census-recensement/2011/geo/bound-limit/bound-limit-2016-eng.cfm
    private static final String PATH = "gis/canada/lpr_000b16a_e.shp";
    private static Shapefile shapefile;

    private CanadaShapefile() {}

    // parses it if necessary
    public static Shapefile load() throws IOException {
        if (shapefile != null)
            return shapefile;

        ShapefileParser sfp = new ShapefileParser(new File(PATH));
        shapefile = sfp.parse();
        return shapefile;
    }

    public static PolygonRecord polygonRecord() {
        return (PolygonRecord) shapefile.getRecords().get(0);
    }
}
