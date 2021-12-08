import api.GeoLocation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Geo_LocationTest {
    Geo_Location g0 = new Geo_Location(7,8,0.0);
    GeoLocation g1 = new Geo_Location(3.3,-3,0.0);
    Geo_Location g2 = new Geo_Location(-2,5,0.0);
    GeoLocation g3 = new Geo_Location(-9,-4.5,0.0);

    @Test
    void x() {
        assertEquals(3.3,g1.x());
        assertEquals(7,g0.x());
    }
    @Test
    void y() {
        assertEquals(-3,g1.y());
        assertEquals(8,g0.y());
    }
    @Test
    void z() {
        assertEquals(0.0,g3.z());
        assertEquals(0.0,g2.z());
    }
    @Test
    void distance() {
        //assuming z is 0
        double temp = Math.sqrt(Math.pow(g3.x()-g2.x(),2) + Math.pow(g3.y()-g2.y(), 2));
        assertEquals(temp ,g2.distance(g3));
    }

}
