package org.synek.adventofcode.day16;

public class LiteralPacket extends Packet {

    private final long value;
    private final int length;

    public LiteralPacket(int version, int length, long value) {
        super(version);
        this.value = value;
        this.length = length;
    }

    @Override
    public long value() {
        return value;
    }

    @Override
    public int bitSize() {
        return headerBitSize() + length;
    }

    @Override
    public String toString() {
        return "LiteralPacket{" +
                "value=" + value +
                ", length=" + length +
                '}';
    }
}
