import api.DirectedWeightedGraph;
import api.NodeData;
import imp.DWGAlgo;
import imp.DWGraph;
import imp.Geo_Location;
import imp.Node;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DWGAlgoTest {

    @Test
    void init() {
        DWGAlgo gA = new DWGAlgo();
    }

    @Test
    void getGraph() {
        DWGAlgo gA = new DWGAlgo();
        gA.load("data\\G3.json");
        DirectedWeightedGraph graph;
        graph = gA.getGraph();
        assertTrue(graph==gA.getGraph());

    }

    @Test
    void copy() {
        DWGAlgo gA = new DWGAlgo();
        DirectedWeightedGraph copy;
        copy =  gA.copy();
        assertFalse(copy==gA);
    }

    @Test
    void isConnected() {
        long start = new Date().getTime();
        DWGAlgo gA = new DWGAlgo();
        gA.load("data\\10000Nodes.json");
        assertEquals(true,gA.isConnected());
        long end = new Date().getTime();
        double time = (end - start) / 1000.0;
        assertTrue(time<1);


    }

    @Test
    void shortestPathDist() {
        DWGAlgo gA = new DWGAlgo();
        gA.load("data\\G3.json");
        Assertions.assertEquals(1.7420530403455134, gA.shortestPathDist(15, 40));
    }


    @Test
    void shortestPath() {
        Node n1 = new Node(new Geo_Location(3, 3, 0));
        Node n2 = new Node(new Geo_Location(-4, -4, 0));
        Node n3 = new Node(new Geo_Location(5, -7.5, 0));
        Node n4 = new Node(new Geo_Location(-2.4, 1.3, 0));

        DWGAlgo gr = new DWGAlgo();
        DWGraph gr1 = new DWGraph();
        gr1.addNode(n1);
        gr1.addNode(n2);
        gr1.addNode(n3);
        gr1.connect(0, 1, 3);
        gr1.connect(1, 2, 4);
        gr.setG(gr1);
        List<NodeData> e = new ArrayList<>();
        e.add(n1);
        e.add(n2);
        e.add(n3);
        List<NodeData> ans = new ArrayList<>();
        ans = gr.shortestPath(0, 2);
        assertEquals(e, ans);

    }

    @Test
    void center() {
        long start = new Date().getTime();
        DWGAlgo g = new DWGAlgo();
        DirectedWeightedGraph graph;

        g.load("data/G1.json");
        graph = g.getGraph();
        assertEquals(graph.getNode(8), g.center());

        g.load("data/G2.json");
        graph = g.getGraph();
        assertEquals(graph.getNode(0), g.center());

        g.load("data/G3.json");
        graph = g.getGraph();
        assertEquals(graph.getNode(40), g.center());

        g.load("data/1000Nodes.json");
        graph = g.getGraph();
        assertEquals(graph.getNode(362), g.center());

        g.load("data/10000Nodes.json");
        graph = g.getGraph();
        assertEquals(graph.getNode(3846), g.center());
        long end = new Date().getTime();
        double time = (end - start) / 1000.0;
        System.out.println(time);
        assertTrue(time < 400);


    }


    @Test
    void tsp() {
        ArrayList<NodeData> cities = new ArrayList<NodeData>();
        DWGAlgo gr = new DWGAlgo();
        gr.load("data\\G1.json");
        DirectedWeightedGraph g = gr.getGraph();
        cities.add(g.getNode(0));
        cities.add(g.getNode(1));
        cities.add(g.getNode(2));
        cities.add(g.getNode(3));
        assertEquals(cities,gr.tsp(cities));
        List <NodeData> other_cities = new ArrayList<>();
        for(int i=0;i<6;i++){
           other_cities.add(g.getNode(i));
        }
        List <NodeData> ans = gr.tsp(other_cities);
        assertEquals(other_cities.get(1).getKey(), ans.get(1).getKey());

        gr.load("data\\1000Nodes.json");
        DirectedWeightedGraph g1 = gr.getGraph();
        other_cities.clear();
        for (Iterator<NodeData> i = g1.nodeIter(); i.hasNext();){
            NodeData temp = i.next();
            other_cities.add(temp);
        }

        long start = new Date().getTime();
        gr.tsp(other_cities);
        long end = new Date().getTime();
        double time = (end - start) / 1000.0;
        assertTrue(time < 600);


    }


    @Test
    void save() {
        DWGAlgo g = new DWGAlgo();
        g.load("data\\G1.json");
        boolean b =  g.save("data\\bb.json");
        assertTrue(b);


    }

    @Test
    void load() {
        DWGAlgo gA = new DWGAlgo();
        boolean ans = gA.load("data\\G3.json");
        assertTrue(ans);

    }

}