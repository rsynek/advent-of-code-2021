package org.synek.adventofcode.day16;

public class OperatorPacketByBits extends OperatorPacket {

    public static final int LENGTH_TYPE_BITS_LENGTH = 15;

    private final int expectedBitCount;

    public OperatorPacketByBits(int version, int type, int bitCount) {
        super(version, type);
        this.expectedBitCount = bitCount;
    }

    @Override
    public boolean isCompleted() {
        return expectedBitCount == subPacketsBitSize();
    }

    @Override
    public int bitSize() {
        return super.bitSize() + LENGTH_TYPE_BITS_LENGTH;
    }

    @Override
    public String toString() {
        return "OperatorPacketByBits{" +
                "expectedBitCount=" + expectedBitCount +
                '}';
    }
}
