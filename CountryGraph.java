import java.util.*;
import java.io.*;

/**<h2> Country Graph class </h2>
 * Class used for constructing a graph that can be used to represent all the connections between 
 * cities shown by the routes csv.
 * 
 * @author Wepea Adamwaba Buntugu
 * @version 1.0
 * @since 2022-09-30
 */
public class CountryGraph {
    public static LinkedHashMap<String, LinkedList<Node>>  allConnections = new LinkedHashMap<>();

    public boolean addConnections(String startIATA, String endIATA){
        try{
            if(allConnections.get(startIATA) == null){
                allConnections.put(startIATA, new LinkedList<Node>());
            }
            
            allConnections.get(startIATA).add(new Node(startIATA, endIATA));
            
            return true;
        }
        catch(Exception e){
            System.out.println("Addition failed. " + e.getMessage());
            return false;
        }

    }

    /** <h3> Get values method </h3>
     * Returns all the values associated with a particular key.
     * @param key The key whose values are going to be returned
     * @returns A linked list of all the nodes associated with a particular key
     */
    public LinkedList<Node> get(String key){
        return allConnections.get(key);
    }

    /**<h3> Add Parent Function </h3> 
     * This function assigns a parent node to all the Node objects in a list to allow for backtracking in 
     * breadth-first search after solution has been reached.
     * @param list The list of Node objects the function will be using
     * @param newParent The parent Node to be assigned 
    */
    public static boolean addParentToList(LinkedList<Node> list, Node newParent){
        try{
            for(Node indiNode: list){
                indiNode.addParent(newParent);
                // System.out.println("Nodes = " + indiNode);

            }
            return true;
        }
        catch(Exception e){
            System.out.println("Parent addition error = " + e.getMessage());
            return false;
        }
        
    }

    public String toString(){
        String data = "";
        for(Map.Entry<String, LinkedList<Node>> entry: allConnections.entrySet()){
            data += "Original start - " + entry.getKey() + " || Nodes = " + entry.getValue() + "\n";
        }
        return data;
    }
    
    /**<h3> Breadth first search algorithm </h3>
     * Breadth first search algorithm that looks for the shortest possible journey based on the number of flights
     * @param startIATA The IATA code of the airport from which the journey will be taken
     * @param endIATA The IATA code of the destination airport
     * @returns path A Linked Hash Set of node objects that represent that path that has been found
     */
    public LinkedHashSet<Node> bfs(String startIATA, String endIATA){
        LinkedHashSet<Node> path = new LinkedHashSet<Node>();
        LinkedHashSet<Node> dummyPath = new LinkedHashSet<Node>();

        HashSet<Node> explored = new HashSet<Node>();
        ArrayList<LinkedList<Node>> frontier = new ArrayList<LinkedList<Node>>();

        String destination = endIATA;
        LinkedList<Node> currState = this.get(startIATA);
        
        frontier.add(currState);

        while(frontier.size() > 0){
            currState = frontier.remove(0);
            for(Node indiNode: currState){
                if(!explored.contains(indiNode) && !frontier.contains(currState)){
                    if(indiNode.endIATA.equals(destination)){
                        System.out.println("Solution found");
                        path.add(indiNode);
                        ArrayList<Node> holdAnswers = new ArrayList<Node>();
                        int count = 0;

                        while(indiNode != null){
                            if(holdAnswers.contains(indiNode) || indiNode.startIATA.equals(startIATA)){
                                path.add(indiNode);
                                return path;
                            }
                            path.add(indiNode);
                            holdAnswers.add(indiNode);

                            indiNode = indiNode.parent;

                            
                        }
                        return path;
                    }
                    explored.add(indiNode);
                 
                    if(this.get(indiNode.endIATA) != null){
                        frontier.add(this.get(indiNode.endIATA));
                        addParentToList(this.get(indiNode.endIATA), indiNode);
                        
                    }
            
                }

            }   
        }
        return dummyPath;
    }


    /**<h3> Reconstruct path method </h3>
     * Reconstructs the path of the breadth first search algorithm 
     * @param path List of nodes and their parents from the breadth first search along the solved path to the solution
     * @returns finalSolution A list of all start and end pairs from the start point to the destination
     */
    public ArrayList<Node> reconstructPath(LinkedHashSet<Node> path){
        ArrayList<Node> finalSolution = new ArrayList<Node>(path);
        if(finalSolution.size() == 0){
            System.out.println("No Solution found");
        }
        else{
            Collections.reverse(finalSolution);
        }
        

        return finalSolution;
    }
    public static void main(String[] args) {
        CountryGraph newGraph = new CountryGraph();
        newGraph.addConnections("FKJ", "MSG");
        newGraph.addConnections("FKJ", "TWK");
        newGraph.addConnections("MSG", "DTV");
        newGraph.addConnections("PML", "DVE");
        newGraph.addConnections("FKJ", "FTR");        
        newGraph.addConnections("FKJ", "PML");
        newGraph.addConnections("FKJ", "ROW");
        newGraph.addConnections("MSG", "SIR");
        newGraph.addConnections("DVE", "FKJ");
        newGraph.addConnections("DVE", "KML");
        newGraph.addConnections("SIR", "JCL");
        newGraph.addConnections("SIR", "JOB");
        newGraph.addConnections("TWK", "RON");
        newGraph.addConnections("TWK", "FLK");
        newGraph.addConnections("TWK", "ASC");
        newGraph.addConnections("RON", "ASE");
        newGraph.addConnections("RON", "DRK");
        newGraph.addConnections("FTR", "DRK");
        newGraph.addConnections("DRK", "MCI");
        newGraph.addConnections("DRK", "MUN");




        System.out.println(newGraph);
        System.out.println("About to perform BFS...");
        LinkedHashSet<Node> result = newGraph.bfs("FKJ", "MUN");

        if(!result.contains(null)){
            newGraph.reconstructPath(result);
        }
        
        

        
    }
    
}
