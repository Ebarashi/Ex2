package Gui;

import api.DirectedWeightedGraphAlgorithms;
import api.EdgeData;
import api.GeoLocation;
import api.NodeData;
import imp.DWGAlgo;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.Iterator;


public class myPanel extends JPanel {

    public DirectedWeightedGraphAlgorithms graphAlgo;
    private double minx = Double.MAX_VALUE, maxx = Double.MIN_VALUE, miny = Double.MAX_VALUE, maxy = Double.MIN_VALUE;
    private double scalex = 1, scaley = 1;

    public myPanel() {
        super();
        graphAlgo = new DWGAlgo();
        this.setBackground(Color.black);
    }

    public myPanel(String s) {
        super();
        graphAlgo = new DWGAlgo();
        graphAlgo.load(s);
        this.setBackground(Color.black);
    }



    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        scale();
        System.out.println(minx+" "+maxx+"  \n"+miny +"  "+maxy);

        for (Iterator<NodeData> nit = graphAlgo.getGraph().nodeIter(); nit.hasNext(); ) {
            NodeData temp2 = nit.next();

            for (Iterator<EdgeData> it = graphAlgo.getGraph().edgeIter(temp2.getKey()); it.hasNext(); ) {
                EdgeData temp = it.next();
               int x=(int)((graphAlgo.getGraph().getNode(temp.getSrc()).getLocation().x()-minx)*scalex*0.96+29)+30;
               int y=(int)((graphAlgo.getGraph().getNode(temp.getSrc()).getLocation().y()-miny)*scaley*0.96+29)+50;

               int xp=(int)((graphAlgo.getGraph().getNode(temp.getDest()).getLocation().x()-minx)*scalex*0.96+29)+30;
               int yp=(int)((graphAlgo.getGraph().getNode(temp.getDest()).getLocation().y()-miny)*scaley*0.96+29)+50;
               g.setColor(Color.RED);
               g.drawLine(x,y,xp,yp);
               Double Teta=Math.atan2(yp-y,xp-x);
               dArrow((Graphics2D) g,Teta,xp,yp);
               // t =temp.getWeight();
               //String ff= String.format("%.03f",t);
               //g.setColor(Color.white);
               //g.drawString(ff,x-50,y-50);//draw in the middle

            }
        }


        for (Iterator<NodeData> it = graphAlgo.getGraph().nodeIter(); it.hasNext(); ) {
            NodeData temp = it.next();
            GeoLocation location = temp.getLocation();
            int x=(int)((location.x()-minx)*scalex*0.96+29)+30;
            int y=(int)((location.y()-miny)*scaley*0.96+29)+50;

            g.setColor(Color.red);
            g.drawOval(x-12, y-12, 24, 24);
            g.setColor(Color.white);
            g.setFont(new Font("default",Font.BOLD,16));
            g.drawString(temp.getKey()+"",(x-8),(y+6));
        }
    }

    private void scale(){
        minx = Double.MAX_VALUE; maxx = Double.MIN_VALUE; miny = Double.MAX_VALUE;maxy = Double.MIN_VALUE;
        scalex = 1; scaley = 1;
        for(Iterator<NodeData> it =graphAlgo.getGraph().nodeIter();it.hasNext();){
            NodeData temp = it.next();
            GeoLocation location = temp.getLocation();
            if(location.x()> maxx)
                maxx=location.x();
            if (location.x()<minx)
                minx=location.x();
            if (location.y()>maxy)
                maxy=location.y();
            if (location.y()<miny)
                miny=location.y();
        }
        scalex=((int)Toolkit.getDefaultToolkit().getScreenSize().width-50)/Math.abs(minx-maxx);
        scalex=scalex*0.7;
        scaley=((int)Toolkit.getDefaultToolkit().getScreenSize().height-150)/Math.abs(miny-maxy);
        scaley=scaley*0.7;
    }
    private void dArrow(Graphics2D g2, double theta, double x0, double y0) {
        g2.setStroke(new BasicStroke(2));
        double b = 16;
        double pi = Math.PI / 6;
        double x1 = x0 - b * Math.cos(theta + pi);
        double y1 = y0 - b * Math.sin(theta + pi);
        double x2 = x0 - b * Math.cos(theta - pi);
        double y2 = y0 - b * Math.sin(theta - pi);
        int[] xPoints = {(int)x0, (int) x1, (int) x2};
        int[] yPoints = {(int)y0, (int) y1, (int) y2};
        g2.fillPolygon(xPoints, yPoints, 3);

    }

}