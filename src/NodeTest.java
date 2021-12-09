import api.GeoLocation;
import api.NodeData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NodeTest {
    Geo_Location g0 = new Geo_Location(7,8,0.0);
    GeoLocation g1 = new Geo_Location(3,-3,0.0);
    Geo_Location g2 = new Geo_Location(-2,5,0.0);
    GeoLocation g3 = new Geo_Location(-9,-4.5,0.0);

    Node n0 = new Node(g0);
    Node n1 = new Node(g1);
    NodeData n2 = new Node(g2);
    NodeData n4 = new Node(g3);


    @Test
    void getTag() {
        assertEquals(0, n2.getTag());
        assertEquals(0, n0.getTag());
    }
    @Test
    void getLocation() {
        assertEquals(g0, n0.getLocation());
        assertEquals(g2, n2.getLocation());

    }
    @Test
    void setLocation() {
        n0.setLocation(g2);
        assertEquals(-2, n0.getLocation().x());
        assertEquals(5, n0.getLocation().y());
        assertEquals(0, n0.getLocation().z());

        n2.setLocation(g3);
        assertEquals(-9, n2.getLocation().x());
        assertEquals(-4.5, n2.getLocation().y());
        assertEquals(0, n2.getLocation().z());
    }
    @Test
    void getInfo() {
        assertEquals("White", n2.getInfo());
        assertEquals("White", n0.getInfo());
     }
    @Test
    void setInfo() {
        n2.setInfo("harel and eilon");
        assertEquals("harel and eilon", n2.getInfo());
        n0.setInfo("harel and eilon");
        assertEquals("harel and eilon", n0.getInfo());
    }
    @Test
    void setTag() {
        n4.setTag(21);
        assertEquals(21,n4.getTag());
        n1.setTag(18);
        assertEquals(18,n1.getTag());
    }




}
