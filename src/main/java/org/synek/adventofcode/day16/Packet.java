package org.synek.adventofcode.day16;

abstract class Packet {
    public static final int HEADER_VERSION_LENGTH = 3;
    public static final int HEADER_TYPE_LENGTH = 3;
    private final int version;

    public Packet(int version) {
        this.version = version;
    }

    public abstract int bitSize();

    protected int headerBitSize() {
        return HEADER_VERSION_LENGTH + HEADER_TYPE_LENGTH;
    }

    public int getVersion() {
        return version;
    }

    public abstract long value();
}
