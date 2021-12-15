package org.synek.adventofcode.day15;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Node {
    private final int index;
    private final int cost;
    private List<Node> shortestPath = new LinkedList<>();
    private Integer distance = Integer.MAX_VALUE;
    Map<Node, Integer> neighbours = new HashMap<>();

    public Node(int index, int cost) {
        this.index = index;
        this.cost = cost;
    }

    public void addDestination(Node destination) {
        neighbours.put(destination, destination.getCost());
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Map<Node, Integer> getNeighbours() {
        return neighbours;
    }

    public List<Node> getShortestPath() {
        return shortestPath;
    }

    public void setShortestPath(List<Node> shortestPath) {
        this.shortestPath = shortestPath;
    }

    public int getIndex() {
        return index;
    }

    public int getCost() {
        return cost;
    }
}
