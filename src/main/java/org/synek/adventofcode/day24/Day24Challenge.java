package org.synek.adventofcode.day24;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.synek.adventofcode.util.Util;

public class Day24Challenge {
    private static final String INPUT_RESOURCE = "input";

    public static void main(String[] args) {
        List<Instruction> instructions = parseInstructions(INPUT_RESOURCE);

        final long start = 11111111111111L;
        final long end = 99999999999999L;
        for (long value = end; value >= start; value--) {
            Alu alu = new Alu(toDigits(value));
            instructions.forEach(alu::processInstruction);
            if (alu.isValidMonad()) {
                System.out.println(value);
                break;
            }
        }
    }

    private static int[] toDigits(long number) {
        String str = Long.toString(number);
        int[] digits = new int[str.length()];
        for (int i = 0; i < str.length(); i++) {
            digits[i] = str.charAt(i) - '0';
        }
        return digits;
    }

    private static List<Instruction> parseInstructions(String resource) {
        try {
            return Files.readAllLines(Path.of(Util.classpathResourceURI(Day24Challenge.class, resource)))
                    .stream()
                    .map(Instruction::parse)
                    .toList();
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }

    private static class Alu {
        private Register w = new Register();
        private Register x = new Register();
        private Register y = new Register();
        private Register z = new Register();

        private final int[] inputs;
        private int index = 0;

        public Alu(int[] inputs) {
            this.inputs = inputs;
        }

        void processInstruction(Instruction instruction) {
            String opcode = instruction.opcode();
            Register firstOperand = register(instruction.operands()[0]);
            Operand secondOperand = null;
            if (instruction.operands().length == 2) {
                String operand = instruction.operands()[1];
                if (operand.equals("w") || operand.equals("x") || operand.equals("y") || operand.equals("z")) {
                    secondOperand = register(operand);
                } else {
                    secondOperand = new Literal(Long.parseLong(operand));
                }
            }
            if (opcode.equals("inp")) {
                inp(firstOperand);
            } else if (opcode.equals("add")) {
                add(firstOperand, secondOperand);
            } else if (opcode.equals("mul")) {
                mul(firstOperand, secondOperand);
            } else if (opcode.equals("div")) {
                div(firstOperand, secondOperand);
            } else if (opcode.equals("mod")) {
                mod(firstOperand, secondOperand);
            } else if (opcode.equals("eql")) {
                eql(firstOperand, secondOperand);
            } else {
                throw new IllegalArgumentException("Unknown opcode (" + opcode + ").");
            }
        }

        private Register register(String registerCode) {
            return switch (registerCode) {
                case "w" -> w;
                case "x" -> x;
                case "y" -> y;
                case "z" -> z;
                default -> throw new IllegalArgumentException("Unknown register code (" + registerCode + ").");
            };
        }
        
        private long readInput() {
            return inputs[index++];
        }
        
        void inp(Register target) {
            target.setValue(readInput());
        }

        void add(Register target, Operand operand) {
            long result = target.getValue() + operand.getValue();
            target.setValue(result);
        }

        void mul(Register target, Operand operand) {
            long result = target.getValue() * operand.getValue();
            target.setValue(result);
        }

        void div(Register target, Operand operand) {
            long result = Math.floorDiv(target.getValue(), operand.getValue());
            target.setValue(result);
        }

        void mod(Register target, Operand operand) {
            long result = target.getValue() % operand.getValue();
            target.setValue(result);
        }

        void eql(Register target, Operand operand) {
            long result = target.getValue() == operand.getValue() ? 1 : 0;
            target.setValue(result);
        }

        public boolean isValidMonad() {
            return z.getValue() == 0;
        }
    }


    private static class Operand {
        protected long value;

        Operand(long value) {
            this.value = value;
        }

        public long getValue() {
            return value;
        }
    }

    private static class Literal extends Operand {
        public Literal(long value) {
            super(value);
        }
    }

    private static class Register extends Operand {

        public Register() {
            super(0);
        }

        Register(long value) {
            super(value);
        }

        public void setValue(long value) {
            this.value = value;
        }
    }

    record Instruction(String opcode, String ... operands) {
        static Instruction parse(String line) {
            String[] opcodeAndOperands = line.split(" ");
            if (opcodeAndOperands.length == 2) {
                return new Instruction(opcodeAndOperands[0], opcodeAndOperands[1]);
            } else {
                return new Instruction(opcodeAndOperands[0], opcodeAndOperands[1], opcodeAndOperands[2]);
            }
        }
    }
}
