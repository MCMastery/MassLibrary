package com.dgrissom.masslibrary.gis;

import com.dgrissom.masslibrary.gis.record.PolygonRecord;

import java.io.File;
import java.io.IOException;

public class USShapefile extends Shapefile {
    // taken from https://www.census.gov/geo/maps-data/data/cbf/cbf_nation.html
    private static final String PATH = "gis/us/cb_2016_us_nation_20m.shp";
    private static Shapefile shapefile;

    private USShapefile() {}

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
