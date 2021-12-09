import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.EdgeData;
import api.NodeData;
import com.google.gson.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class DWGAlgo implements DirectedWeightedGraphAlgorithms {

    private DirectedWeightedGraph g;

    public DWGAlgo() {
        this.g = new DWGraph();
    }

    @Override
    public void init(DirectedWeightedGraph g) {
        this.g = new DWGraph();
        Iterator <NodeData> NIter = g.nodeIter();
        while(NIter.hasNext()){
            this.g.addNode(NIter.next());
        }
        Iterator <EdgeData> EIter = g.edgeIter();
        while(EIter.hasNext()){
            EdgeData e = EIter.next();
            this.g.connect(e.getSrc(), e.getDest(), e.getWeight());
        }
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


    @Override
    public boolean isConnected() {
        if (this.g.nodeSize() == 0) {
            return true;
        }
        for (Iterator<NodeData> itN = g.nodeIter(); itN.hasNext(); ) {
            NodeData n = itN.next();
            boolean temp = this.BFS(n);
            resetTag();
            if (!temp) {
                return false;
            }
        }
        return true;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        resetInfo(); resetTag(); resetWeight();
        double dist = Dijkstra(this.g.getNode(src), this.g.getNode(dest));
        resetInfo(); resetTag(); resetWeight();
        if (dist == Integer.MAX_VALUE) {
            return -1;
        }
        return dist;
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        ArrayList <NodeData> al = new ArrayList<>();
        if (shortestPathDist(src, dest) == -1) {
            return null;
        }
        if (src == dest) {
            al.add(this.g.getNode(src));
            return al;
        }
        Dijkstra(this.g.getNode(src), this.g.getNode(dest));
        NodeData Nsrc = this.g.getNode(src);
        NodeData Ndest = this.g.getNode(dest);
        ArrayList <NodeData> reverseAL = new ArrayList<>();
        NodeData temp = Ndest;
        while (temp.getTag() != 0) {
            reverseAL.add(temp);
            temp = this.g.getNode(temp.getTag());
        }
       // NodeData[] arr = reverseAL.toArray(NodeData[]::new);
        al.add(Nsrc);
        for (int i = reverseAL.size()- 1; i >= 0; i--) {
            al.add(reverseAL.get(i));
        }
        resetInfo(); resetTag(); resetWeight();
        return al;
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

    private boolean BFS(NodeData n) {
        Queue<NodeData> q = new LinkedList<>();
        n.setTag(1);
        int counter = 1;
        q.add(n);
        while (!q.isEmpty()) {
            NodeData temp = q.peek();
            for (Iterator<EdgeData> itN = g.edgeIter(temp.getKey()); itN.hasNext(); ) {
                EdgeData next = itN.next();
                NodeData dest = this.g.getNode(next.getDest());
                if (dest.getTag() == 0) {
                    dest.setTag(1);
                    q.add(dest);
                    counter++;
                }
            }
        }
        return (counter == this.g.nodeSize());
    }

    private double Dijkstra(NodeData src, NodeData dest) {
        double shortest = Integer.MAX_VALUE;
        PriorityQueue<NodeData> pq = new PriorityQueue<>(this.g.nodeSize(), Comparator.comparingDouble(NodeData::getWeight));
        src.setWeight(0.0);
        pq.add(src);
        while (!pq.isEmpty()) {
            NodeData temp = pq.poll();
            for (Iterator<EdgeData> itE = g.edgeIter(temp.getKey()); itE.hasNext(); ) {
                EdgeData e = itE.next();
                NodeData n = this.g.getNode(e.getDest());
                if (Objects.equals(n.getInfo(), "White")) {
                    if (n.getWeight() > temp.getWeight() + e.getWeight()) {
                        n.setWeight(Math.min(n.getWeight(), temp.getWeight() + e.getWeight()));
                        n.setTag(temp.getKey());
                    }
                    pq.add(n);
                }
            }
            temp.setInfo("Black");
            if (temp.getKey() == dest.getKey()) {
                return temp.getWeight();
            }
        }
        return shortest;
    }

    private void resetTag() {
        for (Iterator<NodeData> itN = g.nodeIter(); itN.hasNext(); ) {
            NodeData n = itN.next();
            n.setTag(0);
        }

    }
    private void resetWeight() {
            for (Iterator<NodeData> itN = g.nodeIter(); itN.hasNext(); ) {
                NodeData n = itN.next();
                n.setWeight(Double.MAX_VALUE);
            }
        }

    private void resetInfo() {
        for (Iterator<NodeData> itN = g.nodeIter(); itN.hasNext();) {
            NodeData n = itN.next();
            n.setInfo("White");
        }
    }




}
