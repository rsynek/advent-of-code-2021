package org.synek.adventofcode.day15;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph {

    private final Node source;
    private final Node target;

    public Graph(int [][] matrix) {
        Map<Integer, Node> nodeByIndex = new HashMap<>();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                int index = i * matrix[i].length + j;
                Node node = new Node(index, matrix[i][j]);
                nodeByIndex.put(index, node);
            }
        }


        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                int index = i * matrix[i].length + j;
                Node node = nodeByIndex.get(index);
                if (j > 0) {
                    int leftIndex = index - 1;
                    node.addDestination(nodeByIndex.get(leftIndex));
                }

                if (j < matrix[i].length - 1) {
                    int rightIndex = index + 1;
                    node.addDestination(nodeByIndex.get(rightIndex));
                }

                if (i > 0) {
                    int topIndex = index - matrix[i].length;
                    node.addDestination(nodeByIndex.get(topIndex));
                }

                if (i < matrix.length - 1) {
                    int bottomIndex = index + matrix[i].length;
                    node.addDestination(nodeByIndex.get(bottomIndex));
                }
            }
        }

        source = nodeByIndex.get(0);
        int targetIndex = matrix.length * matrix[0].length - 1;
        this.target = nodeByIndex.get(targetIndex);
    }

    public long shortestPath() {
        source.setDistance(0);

        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Map.Entry<Node, Integer> adjacencyPair:
                    currentNode.getNeighbours().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                Integer edgeWeight = adjacencyPair.getValue();
                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }

        List<Node> path = target.getShortestPath();
        return target.getDistance();
    }

    private Node getLowestDistanceNode(Set<Node> unsettledNodes) {
        Node lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Node node: unsettledNodes) {
            int nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    private void calculateMinimumDistance(Node evaluationNode, Integer edgeWeigh, Node sourceNode) {
        Integer sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }
}
