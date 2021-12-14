import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;



//there is a problam with juint because each test pass by itself but when we run them as a set some of them fails
class DWGraphTest {
    Node  n1;
    Node n2 ;
    Node n3 ;
    Node n4 ;
    DirectedWeightedGraph gr1 ;
    DWGraph gr2;
    DWGraph test;

    @BeforeEach
     void add_nodes_edges(){
        n1 = new Node(new Geo_Location(3,3,0));
        n2 = new Node(new Geo_Location(-4,-4,0));
        n3 = new Node(new Geo_Location(5,-7.5,0));
        n4 = new Node(new Geo_Location(-2.4,1.3,0));

        gr1 = new DWGraph();
        gr2 = new DWGraph();
        test = new DWGraph();
        gr1.addNode(n1);
        gr1.addNode(n2);
        gr1.addNode(n3);
        gr1.addNode(n4);
        gr1.connect(0,1,3);
        gr1.connect(0,2,6);
        gr1.connect(0,3,9);
        gr1.connect(1,2,4);
        gr1.connect(1,0,4);

        test.addNode(n1);
        test.addNode(n2);
        test.addNode(n3);
        test.addNode(n4);
        test.connect(0,1,3);
        test.connect(0,2,6);
        test.connect(0,3,9);
        test.connect(1,2,4);
        test.connect(1,0,4);

        gr2.addNode(n1);
        gr2.addNode(n2);
        gr2.addNode(n3);
        gr2.addNode(n4);
        gr2.connect(0,1,3);
        gr2.connect(0,2,3);
    }
    @Test
    //adds by shallow copy so assert by address is enough
    void addNode() throws InterruptedException {
       // gr1.addNode(n1);
      //  gr1.addNode(n2);
        assertEquals(n1,gr1.getNode(0));
        assertEquals(n2,gr1.getNode(1));

        //gr2.addNode(n1);
       // gr2.addNode(n2);
        assertEquals(n1,gr2.getNode(0));
        assertEquals(n2,gr2.getNode(1));

    }
    @Test
    void connect() {
       // gr1.addNode(n1);
      //  gr1.addNode(n2);
      //  gr1.connect(0,1,3);

        assertEquals(n1.getKey(),gr1.getEdge(0,1).getSrc());
        assertEquals(n2.getKey(),gr1.getEdge(0,1).getDest());
        assertEquals(3,gr1.getEdge(0,1).getWeight());


//        gr2.addNode(n1);
//        gr2.addNode(n2);
//        gr2.connect(0,1,3);
        assertEquals(n1.getKey(),gr2.getEdge(0,1).getSrc());
        assertEquals(n2.getKey(),gr2.getEdge(0,1).getDest());
    }

    @Test
        //adds by shallow copy so assert by address is enough
    void getNode() {

        gr1.addNode(n1);
        gr1.addNode(n2);
        gr1.addNode(n3);
        assertEquals(n3,gr1.getNode(2));

        gr2.addNode(n1);
        gr2.addNode(n2);
        gr2.addNode(n3);
        assertEquals(n3,gr2.getNode(2));
    }


    @Test
        //adds by shallow copy so assert by address is enough
    void getEdge() {

//        gr1.addNode(n1);
//        gr1.addNode(n2);
//        gr1.addNode(n3);
//        gr1.connect(0,1,3);
//        gr1.connect(1,0,4);
        System.out.println(gr1.edgeSize());

        assertEquals(0,gr1.getEdge(0,1).getSrc());
        assertEquals(1,gr1.getEdge(0,1).getDest());
        assertEquals(3,gr1.getEdge(0,1).getWeight());

//        gr2.addNode(n1);
//        gr2.addNode(n2);
//        gr2.connect(0,1,3);
        assertEquals(0,gr2.getEdge(0,1).getSrc());
        assertEquals(1,gr2.getEdge(0,1).getDest());
        assertEquals(3,gr2.getEdge(0,1).getWeight());

    }

    @Test
    void nodeIter() {

//        gr1.addNode(n1);
//        gr1.addNode(n2);
//        gr1.addNode(n3);
//        gr1.addNode(n4);
        Iterator<NodeData> nit = gr1.nodeIter();
        int count=0;
        NodeData temp=null;
        for (Iterator<NodeData> it = nit; it.hasNext(); ) {
            temp = it.next();
            count++;
        }
        assertEquals(4,count);
        assertEquals(n4, temp);
        for (Iterator<NodeData> itN = test.nodeIter(); itN.hasNext(); ) {
            NodeData n = itN.next();
            itN.remove();
        }
        assertEquals(test.nodeSize(),0);

    }

    @Test
    void edgeIter() {

//        gr1.addNode(n1);
//        gr1.addNode(n2);
//        gr1.addNode(n3);
//        gr1.addNode(n4);
//        gr1.connect(0,1,3);
//        gr1.connect(1,2,4);
        Iterator<EdgeData> e = test.edgeIter();
        for (Iterator<EdgeData> itE = e; itE.hasNext(); ) {
            itE.next();
            itE.remove();
        }
       assertEquals(test.edgeSize(),0);
        Iterator<EdgeData> eit = gr1.edgeIter();
        int count=0;
        EdgeData temp=null;
        for (Iterator<EdgeData> it = eit; it.hasNext(); ) {
            temp = it.next();
            count++;
        }

        assertEquals(5,count);
        assertEquals(1, temp.getSrc());
        assertEquals(2, temp.getDest());
        assertEquals(4, temp.getWeight());
  }

    @Test
    void EdgeIterNode() {

//        gr1.addNode(n1);
//        gr1.addNode(n2);
//        gr1.addNode(n3);
//        gr1.addNode(n4);
//        gr1.connect(0,1,3);
//        gr1.connect(1,2,4);
//        gr1.connect(0,2,6);
//        gr1.connect(0,3,9);
        Iterator<NodeData> nit = test.nodeIter();
            Iterator<EdgeData> e = test.edgeIter(nit.next().getKey());
            for (Iterator<EdgeData> itE = e; itE.hasNext(); ) {
                itE.next();
                itE.remove();
            }

        assertEquals(test.edgeSize(), 2);
        Iterator<EdgeData> eit = gr1.edgeIter(0);
        EdgeData temp = null;
        int count = 0;
        for (Iterator<EdgeData> it = eit; it.hasNext(); ) {
            temp = it.next();
            count++;
        }
        assertEquals(3, count);
        assertEquals(0, temp.getSrc());
        assertEquals(3, temp.getDest());
        assertEquals(9, temp.getWeight());


   }



    @Test
    void removeNode() {
//hello
//        gr1.addNode(n1);
//        gr1.addNode(n2);
//        gr1.addNode(n3);
//        gr1.addNode(n4);
        gr1.removeNode(n1.getKey());
        gr1.removeNode(n2.getKey());
        assertEquals(2,gr1.nodeSize());
    }

    @Test
    void nodeSize() {

//        gr1.addNode(n1);
//        gr1.addNode(n2);
//        gr1.addNode(n3);
//        gr1.addNode(n4);
        assertEquals(4,gr1.nodeSize());
    }

    @Test
    void edgeSize() {

//        gr1.addNode(n1);
//        gr1.addNode(n2);
//        gr1.addNode(n3);
//        gr1.addNode(n4);
//        gr1.connect(1,2,4);
//        gr1.connect(0,2,6);
//        gr1.connect(0,3,9);
        assertEquals(5,gr1.edgeSize());
    }

    @Test
    void getMC() {

//        gr1.addNode(n1);
//        gr1.addNode(n2);
//        gr1.addNode(n3);
//        gr1.addNode(n4);
        assertEquals(9,gr1.getMC());
    }

}