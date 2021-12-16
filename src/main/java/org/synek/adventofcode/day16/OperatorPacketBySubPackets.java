package org.synek.adventofcode.day16;

public class OperatorPacketBySubPackets extends OperatorPacket {

    public static final int LENGTH_TYPE_SUBPACKETS_LENGTH = 11;

    private final int expectedSubPacketCount;

    public OperatorPacketBySubPackets(int version, int type, int subPacketCount) {
        super(version, type);
        this.expectedSubPacketCount = subPacketCount;
    }

    @Override
    public boolean isCompleted() {
        return expectedSubPacketCount == subPacketsCount();
    }

    @Override
    public int bitSize() {
        return super.bitSize() + LENGTH_TYPE_SUBPACKETS_LENGTH;
    }

    @Override
    public String toString() {
        return "OperatorPacketBySubPackets{" +
                "expectedSubPacketCount=" + expectedSubPacketCount +
                '}';
    }
}
