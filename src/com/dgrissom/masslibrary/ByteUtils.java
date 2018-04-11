package com.dgrissom.masslibrary;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class ByteUtils {
    private ByteUtils() {}

    // gets three bytes and converts to a 32 bit int
    public static int toInt32(byte[] bytes, int startIndex, ByteOrder byteOrder) {
        ByteBuffer bb = ByteBuffer.wrap(bytes, startIndex, 4);
        bb.order(byteOrder);
        return bb.getInt();
    }
    // gets three bytes and converts to a (64 bit) double
    public static double toDouble(byte[] bytes, int startIndex, ByteOrder byteOrder) {
        ByteBuffer bb = ByteBuffer.wrap(bytes, startIndex, 8);
        bb.order(byteOrder);
        return bb.getDouble();
    }
}
