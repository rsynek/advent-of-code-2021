package org.synek.adventofcode.day12;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph {

    private final Node start;

    public Graph(List<String> edges) {
        Set<Node> nodes = new HashSet<>();
        Map<String, Node> nameToNode = new HashMap<>();
        for (String edge : edges) {
            String[] nodePair = edge.split("-");
            String leftNodeName = nodePair[0];
            String rightNodeName = nodePair[1];

            Node left = nameToNode.computeIfAbsent(leftNodeName, Node::new);
            Node right = nameToNode.computeIfAbsent(rightNodeName, Node::new);
            left.addAdjacentNode(right);
            right.addAdjacentNode(left);
            nodes.add(left);
            nodes.add(right);
        }
        start = nameToNode.get("start");
        for (Node node : nodes) {
            node.getAdjacentNodes().remove(start);
            if (node.isEnd()) {
                node.getAdjacentNodes().clear();
            }
        }
    }

    public int printAllPaths() {
        Node startNode = start;

        Set<Node> visitedOnce = new HashSet<>();
        Set<Node> visitedTwice = new HashSet<>();
        List<Node> pathList = new ArrayList<>();
        pathList.add(startNode);
        Set<List<Node>> allPaths = new HashSet<>();
        printAllPathsRecursive(startNode, visitedOnce, visitedTwice, pathList, allPaths);
        allPaths.forEach(System.out::println);
        return allPaths.size();
    }

    private void printAllPathsRecursive(Node node, Set<Node> visitedOnce, Set<Node> visitedTwice, List<Node> pathList, Set<List<Node>> allPaths) {
        if (node.isEnd()) {
            allPaths.add(new ArrayList<>(pathList));
            return;
        }

        if (node.isSmall()) {
            if (visitedOnce.contains(node)) {
                visitedTwice.add(node);
            }
            visitedOnce.add(node);
        }

        for (Node neighbour : node.getAdjacentNodes()) {
            if (!visitedOnce.contains(neighbour) || visitedTwice.isEmpty()) {
                pathList.add(neighbour);
                printAllPathsRecursive(neighbour, visitedOnce, visitedTwice, pathList, allPaths);
                int lastIndex = pathList.lastIndexOf(neighbour);
                if (lastIndex >= 0) {
                    pathList.remove(lastIndex);
                }
            }
        }

        if (visitedTwice.contains(node)) {
            visitedTwice.remove(node);
        } else {
            visitedOnce.remove(node);
        }
    }

    private static class Node {
        private final String name;
        private final boolean small;
        private final List<Node> adjacent = new ArrayList<>();

        Node(String name) {
            this.name = name;
            small = name.toLowerCase().equals(name);
        }

        boolean isEnd() {
            return name.equals("end");
        }

        boolean isSmall() {
            return small;
        }

        void addAdjacentNode(Node node) {
            adjacent.add(node);
        }

        List<Node> getAdjacentNodes() {
            return adjacent;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
