import api.GeoLocation;
import api.NodeData;

public class Node implements NodeData ,Comparable<Node> {

    private int key;
    private GeoLocation location;
    private double weight =Double.MAX_VALUE;
    private String info = "White";
    private int tag = -1;
    private static int StartKeys = 0;


    public Node(GeoLocation l){
        this.key = StartKeys++;
        this.location = l;
    }
    public Node(GeoLocation g, int id) {
        this.key = id;
        this.location = g;
    }
    //deep copy constructor
    public Node(NodeData other) {
        this.key = other.getKey();
        this.location = new Geo_Location(other.getLocation());
        this.weight = other.getWeight();
        this.info = other.getInfo();
        this.tag = other.getTag();
    }

    public Node(int id) {
        this.key = id;
    }

    @Override
    public int getKey() {return this.key;}

    @Override
    public GeoLocation getLocation() {return this.location;}

    @Override
    public void setLocation(GeoLocation p) {this.location=new Geo_Location(p);}

    @Override
    public double getWeight() {return this.weight;}

    @Override
    public void setWeight(double weight) {this.weight=weight;}

    @Override
    public String getInfo() {return this.info;}

    @Override
    public void setInfo(String str) {this.info=str;}

    @Override
    public int getTag() {return this.tag;}

    @Override
    public void setTag(int t) {this.tag=t;}

    @Override
    public int compareTo(Node o) {
        return Double.compare(this.getWeight(),o.getWeight());
    }

    @Override
    public String toString() {
        return "Node{" +
                "key=" + key +
//                ", weight=" + weight +
//                ", info='" + info + '\'' +
//                ", tag=" + tag +
                '}';
    }
}
