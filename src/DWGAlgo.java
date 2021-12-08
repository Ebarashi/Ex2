import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.EdgeData;
import api.NodeData;
import com.google.gson.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

public class DWGAlgo implements DirectedWeightedGraphAlgorithms {

    private DirectedWeightedGraph g;

    public DWGAlgo() {
        this.g = new DWGraph();
    }

    @Override
    public void init(DirectedWeightedGraph g) {
        this.g = g;
    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return this.g;
    }

    @Override
    public DirectedWeightedGraph copy() {
        return new DWGraph(this.g);
    }


    public void DFS(DirectedWeightedGraph g, NodeData u) {
        u.setTag(1); //color is grey
        Iterator<EdgeData> eIterator = g.edgeIter(u.getKey());
        while (eIterator.hasNext()) {
            EdgeData vEdge = eIterator.next();
            int vKey = vEdge.getDest();
            Node v = (Node) g.getNode(vKey);
            if (v.getTag() == 0) {
                DFS(g, v);
            }
        }
        u.setTag(2);  //color is black
    }

    //receives a graph and returns the transpose of that graph
    public DirectedWeightedGraph Transpose(DirectedWeightedGraph g) {
        DirectedWeightedGraph gt = new DWGraph(g);
        Iterator<EdgeData> edgeIt = g.edgeIter();
        while (edgeIt.hasNext()) {
            EdgeData eIt = edgeIt.next();
            //  gt.removeEdge(eIt.getSrc(), eIt.getDest());
            gt.connect(eIt.getDest(), eIt.getSrc(), eIt.getWeight());
        }
        return gt;
    }

    @Override
    public boolean isConnected() {
        NodeData n = g.getNode(0); //any node to start
        DFS(g, n);
        Iterator<NodeData> nIterator = g.nodeIter(); //goes through all nodes in graph
        while (nIterator.hasNext()) {
            NodeData gNode = nIterator.next();
            if (gNode.getTag() == 0) //color of node is white, so hasn't been touched
            {
                return false; //not connected
            }
        }
        Iterator<NodeData> nIterator2 = g.nodeIter(); //goes through all nodes in graph
        while (nIterator2.hasNext()) {
            NodeData gNode2 = nIterator2.next();
            gNode2.setTag(0); //resets to zero
        }
        DirectedWeightedGraph gt = Transpose((DirectedWeightedGraph) g); //tranposes the graph
        DFS(gt, n); //dfs again
        Iterator<NodeData> gtIterator = gt.nodeIter(); //goes through all nodes in graph
        while (gtIterator.hasNext()) {
            NodeData gtNode = gtIterator.next();
            if (gtNode.getTag() == 0) //color of node is white, so hasn't been touched
            {
                return false; //not connected
            }
        }
        return true;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        return 0;
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        return null;
    }

    @Override
    public NodeData center() {
        return null;
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        return null;
    }

    @Override
    public boolean save(String file) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            gson.toJson(g, new FileWriter("saveG"));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean load(String file) {
        JsonObject myJsonObject;
        String fileData;
        DirectedWeightedGraph newG = new DWGraph();
        try {
            fileData = new String(Files.readAllBytes(Paths.get(file)));

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        myJsonObject = JsonParser.parseString(fileData).getAsJsonObject();

        JsonArray NodesArray = myJsonObject.getAsJsonArray("Nodes");
        for (JsonElement jsonNode : NodesArray) {
            String[] locationArray = jsonNode.getAsJsonObject().get("pos").getAsString().split(",");
            NodeData temp = new Node(jsonNode.getAsJsonObject().get("id").getAsInt());
            if (locationArray.length > 0)
                temp.setLocation(new Geo_Location(Double.parseDouble(locationArray[0]), Double.parseDouble(locationArray[1])
                         ,Double.parseDouble(locationArray[2])));
            newG.addNode(temp);
        }

        JsonArray EdgesArray = myJsonObject.getAsJsonArray("Edges");
        for (JsonElement jsonEdge : EdgesArray) {
            int src = jsonEdge.getAsJsonObject().get("src").getAsInt();
            int dest = jsonEdge.getAsJsonObject().get("dest").getAsInt();
            double w = jsonEdge.getAsJsonObject().get("w").getAsDouble();
            newG.connect(src, dest, w);
        }
        init(newG);
        return true;

    }
}
