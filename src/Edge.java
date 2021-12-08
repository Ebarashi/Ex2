import api.*;
import org.junit.jupiter.api.Test;

public class Edge implements EdgeData{

    private int src;
    private int dest;
    private double weight;
    private String info;
    private int tag;

    //constructor
    public Edge(int src, int dst, double weight){
        if(weight<0){
            throw new RuntimeException("Edge weight must be positive");
        }
        this.src = src;
        this.dest = dst;
        this.weight = weight;
        this.info = "White";
        this.tag = -1;
    }

    //copy constructor
    public Edge(EdgeData other){
        Edge e = (Edge)other;
        this.src = e.src;
        this.dest = e.dest;
        this.weight = other.getWeight();
        this.info = other.getInfo();
        this.tag = other.getTag();
    }

    @Override
    public int getSrc() {
        return this.src;
    }

    @Override
    public int getDest() {
        return this.dest;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    public void setInfo(String str) {this.info=str;}

    @Override
    public int getTag() {return this.tag;}

    @Override
    public void setTag(int tag) {this.tag=tag;}
}
