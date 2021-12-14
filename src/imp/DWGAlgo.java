package imp;

import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.EdgeData;
import api.NodeData;
import com.google.gson.*;

import java.io.File;
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

    public void setG(DirectedWeightedGraph x) {
        this.g = x;
    }

    @Override
    public void init(DirectedWeightedGraph g) {
        this.g = new DWGraph();
        Iterator<NodeData> NIter = g.nodeIter();
        while (NIter.hasNext()) {
            this.g.addNode(NIter.next());
        }
        Iterator<EdgeData> EIter = g.edgeIter();
        while (EIter.hasNext()) {
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

    /**
     * We will use bfs algorithm which checks if it is possible to reach from any vertex to any vertex.
     * for a specific vertex if the whole graph is painted black and so is the converse graph with the same vertex
     * then the graph isConnected
     * * @return true if and only if (iff) there is a valid path from each node to each
     */

    @Override
    public boolean isConnected() {
        if (g.nodeSize() == 0) {
            return true;
        }
        Iterator<NodeData> Niter = g.nodeIter();
        NodeData n = Niter.next();
        BFS(n, g);
        while (Niter.hasNext()) {
            NodeData temp1 = Niter.next();
            if (temp1.getInfo().equals("White")) {
                return false;
            }
        }
        originAll();
        DirectedWeightedGraph c = converse(g);
        BFS(n, c);
        for (Iterator<NodeData> Citer = c.nodeIter(); Citer.hasNext(); ) {
            NodeData temp2 = Citer.next();
            if (temp2.getInfo().equals("White")) {
                return false;
            }
        }
        return true;
    }

    /**
     * This function uses a Dijkstra algorithm which gives the shortest path between source
     * and destination
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return the length of the shortest path between src to dest
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        originAll();
        double dist = Dijkstra(this.g.getNode(src), this.g.getNode(dest));
        originAll();
        if (dist == Double.MAX_VALUE) {
            return -1;
        }
        return dist;
    }

    /**
     * Computes the shortest path between src to dest - as an ordered List of nodes
     * This function uses  Dijkstra Algorithm.
     * The algorithm initializes each vertex from whom it came and then we extract while running backwards all the ancestors of the vertices
     * then return the list in the correct order
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        ArrayList<NodeData> al = new ArrayList<>();
        if (shortestPathDist(src, dest) == -1) {
            return null;
        }
        if (src == dest) {
            al.add(this.g.getNode(dest));
            return al;
        }
        originAll();
        Dijkstra(this.g.getNode(src), this.g.getNode(dest));
        NodeData Nsrc = this.g.getNode(src);
        NodeData Ndest = this.g.getNode(dest);
        ArrayList<NodeData> reverseAL = new ArrayList<>();
        NodeData temp = Ndest;
        while (temp.getTag() != -1) {
            reverseAL.add(temp);
            temp = this.g.getNode(temp.getTag());
        }
        al.add(Nsrc);
        for (int i = reverseAL.size() - 1; i >= 0; i--) {
            al.add(reverseAL.get(i));
        }
        originAll();
        return al;
    }

    /**
     * This function uses a Dijkstra-algorithm.
     * We will run with a vertex that does not exist.
     * the algorithm initialize all the vertices' weights to the shortest way to them from the source.
     * we find the longest way to a target vertex out of the shortest.
     * from all the longest path find the minimum out of it and that will be the center.
     *
     * @return the Node data to which the max shortest path to all the other nodes is minimized.
     */
    @Override
    public NodeData center() {
        NodeData center = null;
        Node magic = new Node(-1);
        double distAns = Double.MAX_VALUE;
        for (Iterator<NodeData> itN = g.nodeIter(); itN.hasNext(); ) {
            NodeData n = itN.next();
            originAll();
            Dijkstra(n, magic);
            double distTemp = Double.MIN_VALUE;
            for (Iterator<NodeData> it = g.nodeIter(); it.hasNext(); ) {
                NodeData k = it.next();
                if (k.getWeight() > distTemp)
                    distTemp = k.getWeight();
            }
            if (distTemp < distAns) {
                distAns = distTemp;
                center = n;
            }
        }
        return center;
    }

    /**
     * This function uses shortestPathDist & shortestPath
     * The function run from thr first vertex on the list and examines the shortest path to the rest of the vertices.
     * we calculate the list_path from the first to the vertex we reached in the shortest path from the first.
     * then we continue with that vertex and so on.
     * in each level we Add the path to the al_list.
     *
     * @param cities
     * @return a list of consecutive nodes which go over all the nodes in cities.
     */

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        ArrayList<NodeData> al = new ArrayList();
        List<NodeData> path;
        ArrayList<NodeData> copy = new ArrayList();

        for (NodeData c : cities) {
            copy.add(c);
        }
        NodeData first = copy.get(0);
        copy.remove(first);
        al.add(first);
        NodeData Ntemp = null;
        while (!copy.isEmpty()) {
            double best = Double.MAX_VALUE;
            for (NodeData n : copy) {
                double temp = shortestPathDist(first.getKey(), n.getKey());
                if (temp < best) {
                    best = temp;
                    Ntemp = n;
                }
            }
            path = shortestPath(first.getKey(), Ntemp.getKey());
            for (int i = 1; i < path.size(); i++) {
                al.add(path.get(i));
            }
            //al.add(Ntemp);
            copy.remove(Ntemp);
            first = Ntemp;
        }
        return al;
    }
//        ArrayList<NodeData> al = new ArrayList();
//        ArrayList<NodeData> copy = new ArrayList();
//        for (NodeData c : cities) {
//            copy.add(c);
//        }
//        NodeData first = copy.get(0);
//        copy.remove(first);
//        al.add(first);
//        NodeData Ntemp = null;
//        while (!copy.isEmpty()){
//            double best = Double.MAX_VALUE;
//            for (NodeData n : copy) {
//                double temp = shortestPathDist(first.getKey(), n.getKey());
//                if (temp < best) {
//                    best = temp;
//                    Ntemp = n;
//                }
//
//            }
//            al.add(Ntemp);
//            copy.remove(Ntemp);
//            first = Ntemp;
//        }
//        return al;
//    }


    @Override
    public boolean save(String file) {
        JsonObject jsonObject = new JsonObject();
        JsonArray nodes = new JsonArray();
        JsonArray edges = new JsonArray();
        for (Iterator<NodeData> itN = g.nodeIter(); itN.hasNext(); ) {
            NodeData n = itN.next();
            JsonObject Ntemp = new JsonObject();
            if (n.getLocation() == null) {
                Ntemp.addProperty("pos", ",,");
            } else {
                String location = n.getLocation().x() + "," + n.getLocation().y() + "," + n.getLocation().z();
                Ntemp.addProperty("pos", location);
            }
            Ntemp.addProperty("id", n.getKey());
            nodes.add(Ntemp);
        }

        for (Iterator<NodeData> itN = g.nodeIter(); itN.hasNext(); ) {
            NodeData n = itN.next();
            for (Iterator<EdgeData> itE = g.edgeIter(n.getKey()); itE.hasNext(); ) {
                EdgeData e = itE.next();
                JsonObject Etemp = new JsonObject();
                Etemp.addProperty("src", e.getSrc());
                Etemp.addProperty("w", e.getWeight());
                Etemp.addProperty("dest", e.getDest());
                edges.add(Etemp);
            }
        }

        jsonObject.add("Edges", edges);
        jsonObject.add("Nodes", nodes);


        try {
            File f = new File(file);
            Gson gson = new Gson();
            FileWriter fileWriter = new FileWriter(f);
            fileWriter.write(gson.toJson(jsonObject));
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    @Override
    public boolean load(String file) {
        JsonObject JO;
        String data;
        DirectedWeightedGraph graph = new DWGraph();
        try {
            data = new String(Files.readAllBytes(Paths.get(file)));

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        JO = JsonParser.parseString(data).getAsJsonObject();

        JsonArray nodes = JO.getAsJsonArray("Nodes");
        for (JsonElement n : nodes) {
            String[] locationData = n.getAsJsonObject().get("pos").getAsString().split(",");
            NodeData temp = new Node(n.getAsJsonObject().get("id").getAsInt());
            if (locationData.length > 0)
                temp.setLocation(new Geo_Location(Double.parseDouble(locationData[0]), Double.parseDouble(locationData[1])
                        , Double.parseDouble(locationData[2])));
            graph.addNode(temp);
        }

        JsonArray edges = JO.getAsJsonArray("Edges");
        for (JsonElement e : edges) {
            int src = e.getAsJsonObject().get("src").getAsInt();
            int dest = e.getAsJsonObject().get("dest").getAsInt();
            double w = e.getAsJsonObject().get("w").getAsDouble();
            graph.connect(src, dest, w);
        }
        init(graph);
        return true;
    }

    /**
     * @param n
     * @param graph
     * @return true iff we can reach from the given node to all the graph nodes
     */
    private boolean BFS(NodeData n, DirectedWeightedGraph graph) {
        Queue<NodeData> q = new LinkedList<>();
        n.setInfo("Black");
        int count = 1;
        q.add(n);
        while (!q.isEmpty()) {
            NodeData temp = q.poll();
            for (Iterator<EdgeData> itN = graph.edgeIter(temp.getKey()); itN.hasNext(); ) {
                EdgeData next = itN.next();
                NodeData dest = graph.getNode(next.getDest());
                if (dest.getInfo().equals("White")) {
                    dest.setInfo("Black");
                    q.add(dest);
                    count++;
                }
            }
        }
        return (count == graph.nodeSize());
    }

    /**
     * This algorithm gets a source and a destination and returns the short way between them.
     * The algorithm does this while going through all the vertices as long as we have not visited them
     * and the edges associated with each vertex.
     * for each vertex we initialized its weight to be the shortest way to reach it from the src and the tag to be from whom we reached it
     * when we finished with a vertex we painted it black.
     * we implemented the algo we learned at algorithms course
     * @param src
     * @param dest
     * @return the shortest path from stc to dest
     */
    private double Dijkstra(NodeData src, NodeData dest) {
        double shortest = Double.MAX_VALUE;
        PriorityQueue<NodeData> pq = new PriorityQueue<>(this.g.nodeSize(), Comparator.comparingDouble(NodeData::getWeight));
        src.setWeight(0.0);
        pq.add(src);
        while (!pq.isEmpty()) {
            NodeData temp = pq.poll();
            if (temp.getInfo().equals("White")) {
                temp.setInfo("Black");
                if (temp.getKey() == dest.getKey()) {
                    return temp.getWeight();
                }
                for (Iterator<EdgeData> itE = g.edgeIter(temp.getKey()); itE.hasNext(); ) {
                    EdgeData e = itE.next();
                    NodeData n = this.g.getNode(e.getDest());
                    if (n.getInfo().equals("White")) {
                        if (temp.getWeight() + e.getWeight() < n.getWeight()) {
                            n.setWeight(temp.getWeight() + e.getWeight());
                            n.setTag(temp.getKey());
                        }
                        pq.add(n);
                    }
                }
//                temp.setInfo("Black");
//                if (temp.getKey() == dest.getKey()) {
//                    return temp.getWeight();
//                }
            }
        }
        return shortest;
    }

    /**
     * init all the fields to default
     */
    private void originAll() {
        for (Iterator<NodeData> itN = g.nodeIter(); itN.hasNext(); ) {
            NodeData n = itN.next();
            n.setInfo("White");
            n.setWeight(Integer.MAX_VALUE);
            n.setTag(-1);
        }
    }

    /**
     * @param graph
     * @return a converse graph of the given graph
     */
    private DirectedWeightedGraph converse(DirectedWeightedGraph graph) {
        DirectedWeightedGraph converseG = new DWGraph();
        Iterator<NodeData> Niter = graph.nodeIter();
        while (Niter.hasNext()) {
            NodeData n = Niter.next();
            NodeData newN = new Node(n);
            converseG.addNode(newN);
        }
        Iterator<EdgeData> Eiter = graph.edgeIter();
        while (Eiter.hasNext()) {
            EdgeData e = Eiter.next();
            converseG.connect(e.getDest(), e.getSrc(), e.getWeight());
        }
        return converseG;
    }


}

