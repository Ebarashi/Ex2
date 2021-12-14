package imp;

import api.GeoLocation;

public class Geo_Location implements api.GeoLocation{

    private double x,y,z;

    //constructor
    public Geo_Location(double x, double y, double z){this.x=x; this.y=y; this.z=z;}
   //copy constructor
    public Geo_Location(GeoLocation other){
        this.x= other.x(); this.y= other.y();this.z=other.z();}
    @Override
    public double x() {return this.x;}

    @Override
    public double y() {return this.y;}

    @Override
    public double z() {return this.z;}

    @Override
    public double distance(api.GeoLocation g) {
        double X = Math.pow(this.x-g.x(),2);
        double Y = Math.pow(this.y-g.y(),2);
        double Z = Math.pow(this.z-g.z(),2);
        return (Math.sqrt(X+Y+Z));
    }
}
