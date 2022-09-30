import java.util.*;
import java.util.Map.Entry;
import java.io.*;


/**<h1> Air Route Interface class </h1>
 * <p> Provides user interface for using Air Route finder program</p>
 * @author Wepea Adamwaba Buntugu
 * @version 2022-09-27
 * @since 1.0
 */
public class AirRouteInterface {

    //writing is not as necessary just need to return it maybe in a list

    /** <h3> Get Codes method </h3> 
     * Takes two string representations of city names and returns the IATA codes of the airports in 
     * those cities in an Array
     * @param userFile A user file that contains string representation of the city
     * @returns String array with two IATA codes
    */
    public static String[] getCodes(String userFile) throws IOException{
        try{
            String [] holdStartEnd = new String [2];
            FileReader freader = new FileReader(userFile);
            BufferedReader br = new BufferedReader(freader);
            
            

            String [] holdLines;
            String startCity;
            String endCity;

            String startCityList = br.readLine();
            holdLines = startCityList.split(",");
            startCity = holdLines[0];
            String startIATACode = getIATACode(startCity);
            holdStartEnd[0] = startIATACode;
            
            //writing is not as necessary just need to return it
            String endCityList = br.readLine();
            holdLines = endCityList.split(",");
            endCity = holdLines[0];
            String endIATACode = getIATACode(endCity);
            holdStartEnd[1] = endIATACode;

            br.close();
            return holdStartEnd;

            
        }
        catch(IOException ioe){
            System.out.println("IOException thrown + " + ioe.getMessage());
            return null;
        }
    }

    /**<h3> Get IATA code helper method </h3>
     * Helper method for getCodes() which looks through the airports hashmap and returns 
     * the IATA code of the specified city
     * @param startCity String representation of city whose airport is being searched for
     * @returns String representation of IATA Code of the airport present in startCity
     */
    public static String getIATACode(String startCity) throws IOException{
        // Iterator newIt = new Iterator(AirRouteDriver.getAllAirports());

        for(Map.Entry<String, String[]> entry : AirRouteDriver.getAllAirports()){
            // System.out.println(entry.getValue()[2]);
            if(entry.getValue()[2].equals(startCity)){
                return entry.getKey();
            }
        }
        System.out.println("Couldn't be found + " + startCity);
        return null;
    }


    /**<h3> Compute Path Method  </h3> 
     * Takes a string representation of the two airport IATA codes and performs breadth-first search on them.
     * It also creates a graph for the routes space
     * 
     * @param startIATA - The start airport IATA 
     * @param endIATA - The destination airport IATA
    */
    public static ArrayList<Node> computePath(String startIATA, String endIATA) throws IOException{
        AirRouteDriver.processRoutes();
        CountryGraph testGraph = new CountryGraph();

       boolean startCodePresent = false;
       boolean endCodePresent = false;


        for(Entry<String, ArrayList<String[]>> entry: AirRouteDriver.finalRoutes.entrySet()){
            for(int index = 0; index < entry.getValue().size(); index++){
                String startCode = entry.getValue().get(index)[0];
                String endCode = entry.getValue().get(index)[1];
                testGraph.addConnections(startCode, endCode);
                if (startCode.equals(startIATA)){
                    startCodePresent = true;
                }
                if (endCode.equals(endIATA)){
                    startCodePresent = true;
                }
            }             
        }

        if(!startCodePresent || endCodePresent){
            System.out.println("Route cannot be computed, one of your inputs does not have any routes");
            return null;
        }

        // System.out.println("Finally!!!! a graph that will work " + testGraph);
        // System.out.println("About to perform BFS...");
        LinkedHashSet<Node> result = testGraph.bfs(startIATA, endIATA);
        ArrayList<Node> holdPath = new ArrayList<Node> ();
        if(!result.contains(null)){
            holdPath = testGraph.reconstructPath(result);
        }
        return holdPath;
    }

    /**<h3> Find Airline </h3> 
     * Finds an airline that flies the route specified in the passed Node object
     * @param node Node object representing a start and end city
     * @returns A string representation of the airline IATA
    */
    public static String findAirline(Node node){
        for(Entry<String, ArrayList<String[]>> entry: AirRouteDriver.finalRoutes.entrySet()){
            for(int index = 0; index < entry.getValue().size(); index++){
                String startCode = entry.getValue().get(index)[0];
                String endCode = entry.getValue().get(index)[1];
                if(startCode.equals(node.startIATA) && endCode.equals(node.endIATA)){
                    return entry.getKey();
                }
            }
            
        }
        return null;
    }

    /**<h3> Save Results To File method </h3> 
     * Writes the result of the computation to a file
     * @param outputFile The file to which the file will be written
     * @param path The sequence of flights that will be written to the file
     * 
    */
    public static void saveResultsToFile(String outputFile, ArrayList<Node> path) throws IOException{
        FileWriter fwriter = new FileWriter(outputFile);
        BufferedWriter bw = new BufferedWriter(fwriter);
        int count = 0;
        bw.write("This is the shortest possible journey using number of flights as the cost.\n");
        for(Node node: path){
            String airline = findAirline(node);
            bw.write(count++ + ". " + airline + " from " + node.startIATA + " to " + node.endIATA + " 0 Stops");
            bw.newLine();
        }
        bw.write("Total number of flights = " + count);
        bw.newLine();
        bw.write("Total additional stops = 0");
        bw.close();
        System.out.println("Your output can be found at " + outputFile);

    }



    public static void main(String[] args) throws IOException {
        String [] holdStartEnd = new String[2];
        ArrayList<Node> holdPath = new ArrayList<Node>();
        System.out.print("Enter the file you would like to get input from >> ");
        Scanner firstInput = new Scanner(System.in);
        String userFile = firstInput.nextLine();
        holdStartEnd = getCodes(userFile);
        String startIATA = holdStartEnd[0];
        String endIATA = holdStartEnd[1];
        holdPath = computePath(startIATA, endIATA);

        String outputFile = userFile.substring(0, userFile.length()-4) + "_output.txt";
        saveResultsToFile(outputFile, holdPath);

        firstInput.close();
    }

}
