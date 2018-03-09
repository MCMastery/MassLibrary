package com.dgrissom.masslibrary.math.geom.r2.polygon;

// https://en.wikipedia.org/wiki/Schläfli_symbol
public final class SchlafliSymbol {
    // s >= 2, otherwise we assume we don't have a s
    // p = # of vertices
    // s is # of points skipped when drawing lines from each vertex

    // sidenote: {30/15}, for example, has the same effect as {30/45}.
    private final int p, s;

    // for regular polygons
    public SchlafliSymbol(int p) {
        this(p, 1);
    }
    public SchlafliSymbol(int p, int s) {
        this.p = p;
        this.s = s;
    }

    public int getP() {
        return this.p;
    }
    public int getS() {
        return this.s;
    }

    @Override
    public String toString() {
        return "{" + this.p + (this.s < 2 ? "" : "/" + this.s) + "}";
    }
}
