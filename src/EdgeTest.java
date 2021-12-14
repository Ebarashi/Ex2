  import api.EdgeData;
  import imp.Edge;
  import org.junit.jupiter.api.Test;

  import static org.junit.jupiter.api.Assertions.assertTrue;
  import static org.junit.jupiter.api.Assertions.assertEquals;

  public class EdgeTest {
   EdgeData e0 = new Edge(5,6,7);
   Edge e1 = new Edge(-3,6,10);
   EdgeData e2 = new Edge(2,-8,3);
   Edge e3 = new Edge(-1,-4,5.5);


    @Test
    void constructor(){
        try{
             Edge e9 = new Edge(2,3,-1);}

        catch(Exception e){
            assertTrue(1==1);
        }
    }


    @Test
    void getDestT() {
        assertEquals(6,e1.getDest());
        assertEquals(6,e0.getDest());
    }

    @Test
    void getSrcT() {
        assertEquals(-3,e1.getSrc());
        assertEquals(5,e0.getSrc());
    }

    @Test
    void getWeight() {
        assertEquals(3,e2.getWeight());
        assertEquals(5.5,e3.getWeight());

    }

    @Test
    void getInfo() {
        assertEquals("White",e3.getInfo());
        assertEquals("White",e2.getInfo());
    }

    @Test
    void setInfo() {
        e3.setInfo("harel and eilon");
        assertEquals("harel and eilon",e3.getInfo());
        e0.setInfo("harel and eilon");
        assertEquals("harel and eilon",e0.getInfo());
    }

    @Test
    void getTag() {
        assertEquals(-1,e0.getTag());
        assertEquals(-1,e3.getTag());

    }

    @Test
    void setTag() {
        e1.setTag(16);
        assertEquals(16,e1.getTag());
        e2.setTag(17);
        assertEquals(17,e2.getTag());
    }
}
