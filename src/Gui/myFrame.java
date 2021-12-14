package Gui;

import api.EdgeData;
import api.NodeData;
import imp.Node;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

public class myFrame  extends JFrame implements ActionListener {
    private myPanel panel;
    private JLabel toScreen;
    private JMenuItem Save, Load;
    private JButton ShortestPath, IsConnected, Center, Tsp, removeN, removeE;
    private JTextField src, dst, node, tsp;

    public myFrame() {
        super();
        toScreen = new JLabel("(be sure to delete the entire contents of the textbox including spaces before adding a new value)answers:");
        toScreen.setBackground(Color.red);
        toScreen.setOpaque(true);
        initFrame();
        panel=new myPanel();
        initPanel();
        addMenu();
    }
    public myFrame(String gPath) {
        super();
        toScreen = new JLabel("(be sure to delete the entire contents of the textbox including spaces before adding a new value)answers:");
        toScreen.setBackground(Color.black);
        toScreen.setForeground(Color.red);
        toScreen.setOpaque(true);
        initFrame();
        panel = new myPanel(gPath);
        initPanel();
        addMenu();
    }

    private void initPanel() {

        Tsp = new JButton("Tsp");
        panel.add(Tsp);
        Tsp.addActionListener(this::actionPerformed);

        Center = new JButton("Center");
        panel.add(Center);
        Center.addActionListener(this::actionPerformed);

        IsConnected = new JButton("IsConnected");
        panel.add(IsConnected);
        IsConnected.addActionListener(this::actionPerformed);

        ShortestPath = new JButton("ShortestPath");
        panel.add(ShortestPath);
        ShortestPath.addActionListener(this::actionPerformed);

        removeE = new JButton("removeE");
        panel.add(removeE);
        removeE.addActionListener(this::actionPerformed);

        removeN = new JButton("removeN");
        panel.add(removeN);
        removeN.addActionListener(this::actionPerformed);

        src = new JTextField("src(for rE,SP)");
        panel.add(src);
        src.addActionListener(this::actionPerformed);

        dst = new JTextField("dst(for rE,SP)");
        dst.setBounds(50, 50, 50, 50);
        dst.setLocation(100, 100);

        panel.add(dst);
        dst.addActionListener(this::actionPerformed);

        node = new JTextField("node(for removeN)");
        panel.add(node);
        node.addActionListener(this::actionPerformed);

        tsp = new JTextField("enter list of nodes like 2,3,4,5,6 for Tsp");
        panel.add(tsp);
        tsp.addActionListener(this::actionPerformed);

        panel.add(toScreen);

        this.add(panel);
    }

    private void initFrame() {
        this.setSize((int)(Toolkit.getDefaultToolkit().getScreenSize().width)-50,
                (int)(Toolkit.getDefaultToolkit().getScreenSize().height)-150);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setDefaultLookAndFeelDecorated(true);
        this.setBackground(Color.red);
    }


    private void addMenu() {
        JMenuBar bar = new JMenuBar();
        JMenu File = new JMenu("File");

        this.Save = new JMenuItem("Save");
        File.add(Save);
        this.Save.addActionListener(new saveAction());

        this.Load = new JMenuItem("Load");
        File.add(Load);
        this.Load.addActionListener(new loadAction());

        bar.add(File);
        this.setJMenuBar(bar);
    }

    private class saveAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            panel.graphAlgo.save("NewGraph.json");

        }
    }

    private class loadAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser temp = new JFileChooser("data/");
            temp.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Json files", "json");
            temp.addChoosableFileFilter(filter);
            int r = temp.showOpenDialog(null);
            if (r == JFileChooser.APPROVE_OPTION) {
                String file_path = temp.getSelectedFile().getAbsolutePath();
                panel.graphAlgo.load(file_path);
                panel.repaint();
            }

        }
    }

    public void actionPerformed(ActionEvent e) {

        int sr = -1, ds = -1, n = -1;
        List<NodeData> cities = new LinkedList<>();
        try {
            for (String s : tsp.getText().split(",")) {
                cities.add(panel.graphAlgo.getGraph().getNode(Integer.parseInt(s)));
            }
        }
        catch (Exception Er1){}
        try {
            sr = Integer.parseInt(src.getText());
        } catch (Exception Er2) {}

        try {
            ds = Integer.parseInt(dst.getText());
        } catch (Exception Er3) {}

        try {
            n = Integer.parseInt(node.getText());
        } catch (Exception Er4) {}

        //toScreen.setText("" + sr + ds + n);


        switch (e.getActionCommand()) {
            case "Tsp":
                System.out.println("in Tsp");
                if(cities.size()>0)
                    TspToDO(cities);
                else
                    toScreen.setText("value input error");
                break;
            case "IsConnected":
                System.out.println("in is conn");
                Connect();
                break;
            case "ShortestPath":
                System.out.println("in sh");
                if(sr>=0 &&ds>=0 ){
                    shortPath(sr,ds);
                }
                else
                    toScreen.setText("value input error");
                break;
            case "Center":
                center();
                System.out.println("in center");
                break;
            case "removeE":
                if (sr>=0 && ds>=0 && ds!=sr)
                    RemoveE(sr,ds);
                System.out.println("in edge");
                break;
            case "removeN":
                if (n>=0)
                    RemoveN(n);
                System.out.println("in node");
                break;
        }


    }


    private void TspToDO(List<NodeData> l) {
        toScreen.setText("the path is: "+panel.graphAlgo.tsp(l).toString());
        panel.repaint();
    }
    public void Connect(){
        toScreen.setText("is connected: "+panel.graphAlgo.isConnected());
        panel.repaint();
    }
    public void shortPath(int sr,int ds){
        List<NodeData> temp = panel.graphAlgo.shortestPath(sr,ds);
        toScreen.setText("shortestPath dist is: "+panel.graphAlgo.shortestPathDist(sr,ds)+" path is: "+temp);
        panel.repaint();
    }
    public void center(){
        toScreen.setText("center node is: "+panel.graphAlgo.center().toString());
        panel.repaint();
    }
    public void RemoveN(int n){
        NodeData x= panel.graphAlgo.getGraph().removeNode(n);
        toScreen.setText(x.toString()+" was removed");
        panel.repaint();
    }
    public void RemoveE(int sr,int ds){
        EdgeData x= panel.graphAlgo.getGraph().removeEdge(sr,ds);
        toScreen.setText(x.toString()+" was removed");
        panel.repaint();
    }
}

