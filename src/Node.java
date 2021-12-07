import api.GeoLocation;
import api.NodeData;

public class Node implements NodeData {
    /**
     * Each node contains few fields:
     * location: An object that represent the location of the node by 3d point.
     * weight: A variable that is used in later functions, by default Initialized to Integer.MAX_VALUE(infinite).
     * info: A variable that is used in later functions, by default Initialized to "White".
     * tag: A variable that is used in later functions, by default Initialized to -1.
     * key: A unique key that is used as each node's ID.
     */
    private int key;
    private GeoLocation location;
    private double weight = Double.MAX_VALUE;
    private String info = "White";
    private int tag = -1;
    private static int uniqueKey = 0;

    /**
     * Constructor.
     * @param l - geo_Location.
     */
    public Node(GeoLocation l){
        this.key = uniqueKey++;
        this.location = l;
    }
    @Override
    public int getKey() {
        return 0;
    }

    @Override
    public GeoLocation getLocation() {
        return null;
    }

    @Override
    public void setLocation(GeoLocation p) {

    }

    @Override
    public double getWeight() {
        return 0;
    }

    @Override
    public void setWeight(double w) {

    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public void setInfo(String s) {

    }

    @Override
    public int getTag() {
        return 0;
    }

    @Override
    public void setTag(int t) {

    }
}
