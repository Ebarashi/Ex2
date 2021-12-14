package Gui;


public class mainGui
{
    private myFrame frame;
    public mainGui(){
        frame =new myFrame();
        frame.setVisible(false);
    }
    public mainGui(String str){
        frame=new myFrame(str);
        frame.setVisible(false);
    }
    public void showWindow(){frame.setVisible(true);}
    public static void main(String[] args){
        myFrame frame = new myFrame("data/G1.json");
        frame.setVisible(true);

    }
}
