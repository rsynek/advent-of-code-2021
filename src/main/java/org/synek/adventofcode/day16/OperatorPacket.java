package org.synek.adventofcode.day16;

import java.util.ArrayList;
import java.util.List;

abstract class OperatorPacket extends Packet {
    public static final int LENGTH_TYPE_BITS = 0;
    public static final int LENGTH_TYPE_SUBPACKETS = 1;

    final int type;
    private final List<Packet> subPackets = new ArrayList<>();

    protected OperatorPacket(int version, int type) {
        super(version);
        this.type = type;
    }

    public void addSubPacket(Packet subPacket) {
        subPackets.add(subPacket);
    }

    public abstract boolean isCompleted();

    protected int subPacketsCount() {
        return subPackets.size();
    }

    protected int subPacketsBitSize() {
        return subPackets.stream().mapToInt(Packet::bitSize).sum();
    }

    @Override
    public int bitSize() {
        return headerBitSize() + 1 + subPacketsBitSize();
    }

    @Override
    public long value() {
        return switch (type) {
            case 0 -> applySum();
            case 1 -> applyProduct();
            case 2 -> applyMin();
            case 3 -> applyMax();
            case 5 -> applyGreaterThan();
            case 6 -> applyLessThan();
            case 7 -> applyEqual();
            default -> throw new IllegalStateException("Unsupported operator type (" + type + ")");
        };
    }

    private long applySum() {
        return subPackets.stream().mapToLong(Packet::value).sum();
    }

    private long applyProduct() {
        return subPackets.stream().mapToLong(Packet::value).reduce(1, (a, b) -> a * b);
    }

    private long applyMin() {
        return subPackets.stream().mapToLong(Packet::value).min().getAsLong();
    }

    private long applyMax() {
        return subPackets.stream().mapToLong(Packet::value).max().getAsLong();
    }

    private long applyGreaterThan() {
        if (subPackets.size() != 2) {
            throw new IllegalStateException("GreaterThan should have only 2 sub-packets, but has (" + subPackets.size() + ")");
        }

        return subPackets.get(0).value() > subPackets.get(1).value() ? 1 : 0;
    }

    private long applyLessThan() {
        if (subPackets.size() != 2) {
            throw new IllegalStateException("LessThan should have only 2 sub-packets, but has (" + subPackets.size() + ")");
        }

        return subPackets.get(0).value() < subPackets.get(1).value() ? 1 : 0;
    }

    private long applyEqual() {
        if (subPackets.size() != 2) {
            throw new IllegalStateException("Equal should have only 2 sub-packets, but has (" + subPackets.size() + ")");
        }

        return subPackets.get(0).value() == subPackets.get(1).value() ? 1 : 0;
    }
}
