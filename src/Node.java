import api.GeoLocation;
import api.NodeData;

public class Node implements NodeData {
    private int key;
    private GeoLocation location;
    private double weight =Integer.MAX_VALUE;
    private String info = "White";
    private int tag = 0;
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
}
