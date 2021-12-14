import Gui.mainGui;
import  api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import imp.DWGAlgo;
import imp.DWGraph;

/**
 * This class is the main class for Ex2 - your implementation will be tested using this class.
 */
public class Ex2 {
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraph getGrapg(String json_file) {
        DirectedWeightedGraph ans = new DWGraph();
        DWGAlgo gAlgo = new DWGAlgo();
        gAlgo.init(ans);
        gAlgo.load(json_file);
        return gAlgo.getGraph();
    }
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraphAlgorithms getGrapgAlgo(String json_file) {
        DirectedWeightedGraph ans = new DWGraph();
        DWGAlgo gAlgo = new DWGAlgo();
        gAlgo.init(ans);
        gAlgo.load(json_file);
        return gAlgo;
    }
    /**
     * This static function will run your GUI using the json fime.
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     *
     */
    public static void runGUI(String json_file) {
        mainGui gui;
        DirectedWeightedGraphAlgorithms alg;
        if (json_file.length()>0 ){
            alg = getGrapgAlgo(json_file);
            gui = new mainGui(json_file);}
        gui = new mainGui();
        gui.showWindow();

    }
    public static void main(String[] args) {
        if (args.length < 1){
            runGUI("");
        }
        else{
            runGUI(args[0]);
        }
    }
}