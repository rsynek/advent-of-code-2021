package org.synek.adventofcode.day16;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

import org.synek.adventofcode.util.Util;

public class Day16Challenge {

    private static final String INPUT_RESOURCE = "input";

    private static final int PACKET_START = 0;

    private static final int PACKET_TYPE_VALUE_LITERAL = 4;

    private final Deque<OperatorPacket> packetDeque = new ArrayDeque<>();
    private final List<Packet> finishedPackets = new ArrayList<>();

    public static void main(String[] args) {
        String encodedMessage = readInput(INPUT_RESOURCE);
        String binaryString = hexToBinary(encodedMessage);
        System.out.println(binaryString);

        Day16Challenge challenge = new Day16Challenge();
        challenge.decode(binaryString);
        challenge.printPackets();

        // Challenge 1
        // System.out.println("Sum of versions: " + challenge.sumVersions());

        // Challenge 2
        System.out.println("Value is: " + challenge.computeValue());
    }

    private static String hexToBinary(String hexString) {
        hexString = hexString.replaceAll("0", "0000");
        hexString = hexString.replaceAll("1", "0001");
        hexString = hexString.replaceAll("2", "0010");
        hexString = hexString.replaceAll("3", "0011");
        hexString = hexString.replaceAll("4", "0100");
        hexString = hexString.replaceAll("5", "0101");
        hexString = hexString.replaceAll("6", "0110");
        hexString = hexString.replaceAll("7", "0111");
        hexString = hexString.replaceAll("8", "1000");
        hexString = hexString.replaceAll("9", "1001");
        hexString = hexString.replaceAll("A", "1010");
        hexString = hexString.replaceAll("B", "1011");
        hexString = hexString.replaceAll("C", "1100");
        hexString = hexString.replaceAll("D", "1101");
        hexString = hexString.replaceAll("E", "1110");
        hexString = hexString.replaceAll("F", "1111");
        return hexString;

    }

    private void printPackets() {
        finishedPackets.forEach(System.out::println);
    }

    private long sumVersions() {
        return finishedPackets.stream().mapToInt(Packet::getVersion).sum();
    }

    private long computeValue() {
        return finishedPackets.get(0).value();
    }

    private void decode(String binaryString) {
        if (binaryString.length() <= 3) { // padding bits
            return;
        }
        int version = Integer.parseInt(binaryString.substring(PACKET_START, Packet.HEADER_VERSION_LENGTH), 2);
        final int packetTypeStart = PACKET_START + Packet.HEADER_VERSION_LENGTH;
        final int packageTypeEnd = PACKET_START + Packet.HEADER_VERSION_LENGTH + Packet.HEADER_TYPE_LENGTH;
        int packetType = Integer.parseInt(binaryString.substring(packetTypeStart, packageTypeEnd), 2);
        String remainingBinaryString = binaryString.substring(packageTypeEnd);
        if (packetType == PACKET_TYPE_VALUE_LITERAL) {
            decodeLiteral(version, remainingBinaryString);
        } else {
            decodeOperator(version, packetType, remainingBinaryString);
        }
    }

    private void decodeLiteral(int version, String binaryString) {
        final int step = 5;
        StringBuilder sb = new StringBuilder();
        int i = 0;
        int stepCount = 0;
        for (; i <= binaryString.length() - step; i = i + step) {
            sb.append(binaryString, i + 1, i + step);
            stepCount++;
            if (binaryString.charAt(i) == '0') { // the last group
                break;
            }
        }
        final int contentLength = sb.length() + stepCount;
        LiteralPacket literalPacket = new LiteralPacket(version, contentLength, Long.parseLong(sb.toString(), 2));
        finishedPackets.add(literalPacket);
        boolean completed = packetFinished(literalPacket);
        if (!completed) {
            decode(binaryString.substring(contentLength));
        }
    }

    private void decodeOperator(int version, int type, String binaryString) {
        int lengthTypeId = Integer.parseInt(binaryString.substring(0, 1), 2);
        final int lengthStart = 1;
        int operatorPacketContentStart;
        if (lengthTypeId == OperatorPacket.LENGTH_TYPE_BITS) {
            String bitLengthBinaryString = binaryString.substring(lengthStart, lengthStart + OperatorPacketByBits.LENGTH_TYPE_BITS_LENGTH);
            int bitLength = Integer.parseInt(bitLengthBinaryString, 2);
            operatorPacketContentStart = 1 + OperatorPacketByBits.LENGTH_TYPE_BITS_LENGTH;
            packetDeque.push(new OperatorPacketByBits(version, type, bitLength));
        } else if (lengthTypeId == OperatorPacket.LENGTH_TYPE_SUBPACKETS) {
            int subPacketsCount = Integer.parseInt(binaryString.substring(lengthStart, lengthStart + OperatorPacketBySubPackets.LENGTH_TYPE_SUBPACKETS_LENGTH), 2);
            operatorPacketContentStart = 1 + OperatorPacketBySubPackets.LENGTH_TYPE_SUBPACKETS_LENGTH;
            packetDeque.push(new OperatorPacketBySubPackets(version, type, subPacketsCount));
        } else {
            throw new IllegalArgumentException("Unknown operator package (" + binaryString + ").");
        }

        decode(binaryString.substring(operatorPacketContentStart));
    }

    private boolean packetFinished(Packet packet) {
        if (packetDeque.isEmpty()) {
            Collections.reverse(finishedPackets);
            return true;
        }
        OperatorPacket parentOperatorPacket = packetDeque.peek();
        parentOperatorPacket.addSubPacket(packet);
        if (parentOperatorPacket.isCompleted()) {
            packetDeque.pop();
            finishedPackets.add(parentOperatorPacket);
            return packetFinished(parentOperatorPacket);
        }
        return false;
    }

    private static String readInput(String resource) {
        try {
            return Files.readString(Path.of(Util.classpathResourceURI(Day16Challenge.class, resource)));
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }
}
