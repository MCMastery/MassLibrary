package com.dgrissom.masslibrary.gis.record;

import com.dgrissom.masslibrary.gis.ShapeType;

public class NullRecord implements Record {
    @Override
    public ShapeType getShapeType() {
        return ShapeType.NULL;
    }
}
