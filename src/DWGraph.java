import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;

import java.util.*;

public class DWGraph implements DirectedWeightedGraph {

    private HashMap<Integer, NodeData> nodes;
    private HashMap<Integer, HashMap<Integer, EdgeData>> edges;
   // private Iterator<EdgeData> EIter;
   // private Iterator<NodeData> NIter;
    private int MC;
    private int Node_size;
    private int Edge_size;


    public DWGraph(){
        this.nodes= new HashMap<Integer,NodeData>();
        this.edges = new HashMap<Integer,HashMap<Integer,EdgeData>>();
        this.MC = 0;
        this.Node_size = 0;
        this.Edge_size = 0;

    }

    public DWGraph(DirectedWeightedGraph Other_graph) {
        this.nodes = new HashMap<Integer,NodeData>();
        this.edges = new HashMap<Integer,HashMap<Integer,EdgeData>>();
        NCopy(Other_graph, this.nodes);
        ECopy(Other_graph, this.edges);
        this.Edge_size = Other_graph.edgeSize();
        this.Node_size = Other_graph.nodeSize();
    }

    private void NCopy(DirectedWeightedGraph other, HashMap nodes) {
        for (Iterator<NodeData> it = other.nodeIter(); it.hasNext(); ) {
            NodeData n = it.next();
            int key = n.getKey();
            nodes.put(key,n);
        }

    }

    private void ECopy(DirectedWeightedGraph other, HashMap edges) {
        int key;
        for (Iterator<NodeData> itN = other.nodeIter(); itN.hasNext(); ) {
            NodeData n = itN.next();
            key = n.getKey();
            for (Iterator<EdgeData> itE = other.edgeIter(key); itE.hasNext(); ) {
                EdgeData e = itE.next();
                EdgeData edge = new Edge(e);
                edges.put(e.getSrc(),edges.put(e.getDest(),e));
            }
        }

    }


    @Override
    public NodeData getNode(int key) {
        if (nodes.containsKey(key))
        {
            return nodes.get(key);
        }
        else
        {
            return null;
        }
    }

    @Override
    public EdgeData getEdge(int src, int dest) {
        if(nodes.containsKey(src)&& nodes.containsKey(dest)) {
            return edges.get(src).get(dest);
        }
            return null;
    }

    @Override
    public void addNode(NodeData n) {
        if (nodes.containsKey(n.getKey())) {
            return;
        }
        nodes.put(n.getKey(), (Node)n);
        edges.put(n.getKey(), new HashMap<Integer,EdgeData>());
        MC++;
        Node_size++;
    }

    @Override
    public void connect(int src, int dest, double w) {
        if (this.getEdge(src, dest) != null && edges.get(src).get(dest).getWeight() != w)
        {
            EdgeData e = new Edge(src, dest, w);
            edges.get(src).put(dest, e);
            MC++;
        }
        else if (nodes.containsKey(src) && nodes.containsKey(dest) && this.getEdge(src, dest) == null)
        {
           EdgeData e = new Edge(src, dest, w);
            edges.get(src).put(dest, e);
            MC++;
            Edge_size++;
        }
    }


    @Override
    public Iterator<NodeData> nodeIter() {
       return new Iterator<NodeData>() {
           int innerMC = MC;
           Iterator<NodeData> NIter = nodes.values().iterator();
           NodeData temp;

           @Override
            public boolean hasNext() {

               if (innerMC != MC) {
                   throw new RuntimeException("the graph has changed");
               }
                return NIter.hasNext();
            }

            @Override
            public NodeData next() {
                if (innerMC != MC) {
                    throw new RuntimeException("the graph has changed");
                }
               temp = NIter.next();
                return temp;
            }
           @Override
           public void remove() {
               if (innerMC != MC) {
                   throw new RuntimeException("the graph has changed");
               } else if (temp!=null){
                   removeNode(temp.getKey());
                   innerMC = MC;
                   NIter = nodes.values().iterator();
               }
           }
        };
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        return new Iterator<EdgeData>() {
            int innerMC = MC;
            Iterator <Integer> src = edges.keySet().iterator();
            Iterator<EdgeData> currNode = edgeIter(src.next());
             EdgeData currEdge;
             EdgeData temp;
             int currSrc = 0;

                @Override
                public boolean hasNext() {
                    if (innerMC != MC) {
                        throw new RuntimeException ("the graph has changed");
                    }
                    if (!currNode.hasNext())
                    {
                        if (!src.hasNext())
                        {
                            return false;
                        }
                        else
                        {
                            currSrc = src.next();
                            currNode = edgeIter(currSrc);
                        }
                    }
                    return currNode.hasNext();
                }

                @Override
                public EdgeData next() {
                    if (innerMC != getMC()) {
                        throw new RuntimeException ("the graph has changed");
                    }
                    temp = currEdge;
                    currEdge = currNode.next();
                    return currEdge;
                }

                @Override
                public void remove() {
                    if (innerMC != MC) {
                        throw new RuntimeException("the graph has changed");
                    }
                    if (currEdge!=null){
                        removeEdge(currEdge.getSrc(), currEdge.getDest());
                        innerMC = MC;
                        src = edges.keySet().iterator();
                        currNode = edgeIter(src.next());
                    }

                }
            };

        }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        return new Iterator<EdgeData>() {
            private int innerMC = MC;
            Iterator<EdgeData> EIter = edges.get(node_id).values().iterator();
            EdgeData temp ;
            @Override
            public boolean hasNext() {
                if (innerMC != MC) {
                    throw new RuntimeException("the graph has changed");
                }
                return EIter.hasNext();
            }

            @Override
            public EdgeData next() {
                if (innerMC != MC) {
                    throw new RuntimeException("the graph has changed");
                }
                temp = EIter.next();
                return temp;
            }

            @Override
            public void remove() {
                if (innerMC != MC) {
                    throw new RuntimeException("the graph has changed");
                }
                if (temp!=null){
                    removeEdge(temp.getSrc(), temp.getDest());
                    innerMC = MC;
                    EIter = edges.get(node_id).values().iterator();
                }
            }
        };
    }

    @Override
    public NodeData removeNode(int key) {
        NodeData temp = null;
        if(this.nodes.containsKey(key))
        {
            temp= nodes.get(key);
            int size = edges.get(key).size();
            edges.remove(key);
            Edge_size -= size;
            MC += size;
            Collection<Integer> c = edges.keySet();
            for (int i :c)
            {
                if(edges.get(i).containsKey(key))
                {
                    edges.get(i).remove(key);
                    Edge_size--;
                    MC ++;
                }
            }
            nodes.remove(key);
            Node_size--;
            MC++;
        }
        return temp;
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        EdgeData edge = edges.get(src).remove(dest);
        if(edge == null)
        {
            return null;
        }
        Edge_size--;
        MC++;
        return edge;
    }


    @Override
    public int nodeSize() {
        return this.Node_size;
    }

    @Override
    public int edgeSize() {
        return this.Edge_size;
    }


    @Override
    public int getMC() {
        return this.MC;
    }

    public static void main(String[] args) {
        DWGraph gr1 = new DWGraph();
        Node n1 = new Node(new Geo_Location(3,3,0));
        Node n2 = new Node(new Geo_Location(-4,-4,0));
        Node n3 = new Node(new Geo_Location(5,-7.5,0));
        Node n4 = new Node(new Geo_Location(-2.4,1.3,0));

        gr1.addNode(n1);
        gr1.addNode(n2);
        gr1.addNode(n3);
        gr1.addNode(n4);
        gr1.connect(0,1,3);
        gr1.connect(0,2,6);
        gr1.connect(0,3,9);
        gr1.connect(1,2,4);
        gr1.connect(1,0,4);
        System.out.println(gr1.nodeSize());
        for (Iterator<NodeData> itN = gr1.nodeIter(); itN.hasNext(); ) {
            NodeData n = itN.next();
            itN.remove();
        }
        System.out.println(gr1.nodeSize());
    }
}
