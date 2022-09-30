/** Check for airlines with no IATA CODE (\N) and remove them before adding to map. Check number first though */



import java.util.*;
import java.util.Map.Entry;
import java.io.*;
// import java.lang.*;

/**<h1> Air Route Driver class </h1>
 * <p>Contains methods and functions for powering the Air Route finder program</p>
 * @author Wepea Adamwaba Buntugu
 * @version 2022-09-27
 * @since 1.0
 */
public class AirRouteDriver {
    /**Finds the route from a specified start city to an end city
     * @param startCity - The city we are starting from
     * @param endCity - The destination city
     * @returns String Arraylist of the stops and airlines needed to get to endCity from startCity
     */

    /**Stores hashmap of all usable airlines (those that are active). Stores airlines IATA Code as a key
     * and the airline name and country as a value in an array. 
     */
    private static LinkedHashMap <String, String[]> finalAirlines = new LinkedHashMap<>();


    /**Stores hashmap of all processed routes (with relevant info). Stores airlines IATA Code as a key
     * and the start airport code and end airport as a value in an array. 
     */
    public static LinkedHashMap<String, ArrayList<String[]>> finalRoutes = new LinkedHashMap<>();


    /**Stores hashmap of all processed airports (with relevant info). Stores Airport IATA Code as a key
     * and the AirportID, Name, Country, Latitude and Longitude as a value in an array. 
     */
    private static LinkedHashMap<String, String[]> finalAirports = new LinkedHashMap<>();

    public ArrayList<String> findRoute(String startCity, String endCity){
        return null;

        //Check if there is a route that has startCity(startCityCode) and endCity(endCityCode) as source and destination
        //Add the airline code, the 
        
        //If not, return some recursion magic lool
    }

    /**Returns the a collections-view of the airlines linkedhash map */
    public static Set<Map.Entry<String, String []>> getAllAirlines() throws IOException{
        processAirlines();
        return finalAirlines.entrySet();
    }
    
    /**Returns the a collections-view of the routes linkedhash map */
    public static Set<Entry<String, ArrayList<String[]>>> getAllRoutes() throws IOException{
        processRoutes();
        return finalRoutes.entrySet();
    }

    /**Returns the a collections-view of the routes linkedhash map */
    public static Set<Map.Entry<String, String[]>> getAllAirports()throws IOException {
        processAirports();
        return finalAirports.entrySet();
    }

    /**<h2> Process Airlines method </h2>
     * Cleans airline data (removes inactive airlines and irrelevant data) and stores it in Linkedhashmap
     * @returns True if operation is successful and false otherwise
     * @throws IOException 
    */
    public static boolean processAirlines() throws IOException{
        try{
            String [] holdLines;
            FileReader freader = new FileReader("airlines.csv");
            FileWriter fwriter = new FileWriter("OutputAirlines.txt");

            //Could write fixed dataset to new file
            BufferedWriter bw = new BufferedWriter(fwriter);

            BufferedReader br = new BufferedReader(freader);
            // int noAirlines = 0;

            do{
                
                String singleLine = br.readLine();
                if(singleLine == null){

                    break;
                }
                holdLines = singleLine.split(",");
                
                //Check if airline is active
                if (holdLines[holdLines.length - 1] == "N"){
                    continue;
                }
                // noAirlines++;

                String IATA = holdLines[3];
                String airlineName = holdLines[1];
                String airlineCountry = holdLines[6];
                String [] airlinenameCountry = {airlineName, airlineCountry};
                // String airlineNameCountry = airlineName + ", " + airlineCountry;

                //Add relevant data points to hashmap
                finalAirlines.put(IATA, airlinenameCountry);

            }
            while(true);
            // System.out.println(noAirlines + " Airlines successfully processed");


            for(Map.Entry<String, String[]> entry: finalAirlines.entrySet()){
                bw.write("IATA code = " + entry.getKey() + " Airline Name = " + entry.getValue()[0] + 
                " Airline Country of Origin = " + entry.getValue()[1]);
                bw.newLine();
            }
            bw.close();
            br.close();

        }
       
        catch(FileNotFoundException fne){
            System.out.println("FileNotFoundException thrown");
            return false;
        }
        

        return true;
    }

    /**<h2> Process Routes method </h2>
     * Cleans routes data (removes irrelevant data) and stores it in Linkedhashmap
     * @returns True if operation is successful and false otherwise
     * @throws IOException 
    */
    public static boolean processRoutes() throws IOException{
        try{
            String [] holdLines;
            FileReader freader = new FileReader("routes.csv");
            FileWriter fwriter = new FileWriter("OutputAirlines.txt");

            //Could write fixed dataset to new file
            BufferedWriter bw = new BufferedWriter(fwriter);

            BufferedReader br = new BufferedReader(freader);
            int noAirlines = 0;

            do{
                
                String singleLine = br.readLine();
                if(singleLine == null){

                    break;
                }
                holdLines = singleLine.split(",");
                
                //Check if airline is active
                if (holdLines.length != 9){
                    continue;
                }
                noAirlines++;

                String AirlineCode = holdLines[0];
                String startAirportCode = holdLines[2];
                String destinationAirportCode = holdLines[4];
                String [] startendAirportCodes = {startAirportCode, destinationAirportCode};
                // String airlineNameCountry = airlineName + ", " + airlineCountry;

                //Add relevant data points to hashmap
                if(!finalRoutes.containsKey(AirlineCode)){
                    ArrayList<String[]> holdStartEndCodes = new ArrayList<String[]>();
                    holdStartEndCodes.add(startendAirportCodes);
                    finalRoutes.put(AirlineCode, holdStartEndCodes);

                }
                else{
                    finalRoutes.get(AirlineCode).add(startendAirportCodes);
                }


            }
            while(true);
            // System.out.println(noAirlines + " Airlines successfully processed");


            for(Entry<String, ArrayList<String[]>> entry: finalRoutes.entrySet()){
                for(int index = 0; index < entry.getValue().size(); index++){
                    bw.write("Airlinecode = " + entry.getKey() + " || Start Airport Code = " + entry.getValue().get(index)[0] + 
                " || Destination Airport Code = " + entry.getValue().get(index)[1]);
                bw.newLine();
                }
                
            }
            bw.close();
            br.close();
            // System.out.println(finalRoutes.size() + " Routes successfully processed");


        }
       
        catch(FileNotFoundException fne){
            System.out.println("FileNotFoundException thrown");
            return false;
        }
        

        return true;
    }

    /**<h2> Process Airports method </h2>
     * Cleans airline data (removes irrelevant data) and stores it in Linkedhashmap
     * @returns True if operation is successful and false otherwise
     * @throws IOException 
    */
    public static boolean processAirports() throws IOException{
        try{
            FileReader freader = new FileReader("airports.csv");
            BufferedReader br = new BufferedReader(freader);

            String rawAirport;
            String [] holdLines;

            //Relevant airport data
            String airlineIataCode;
            String airportID;
            String airportName;
            String airportCity;
            String airportCountry;

            //These will be converted to string for the purposes of storing but will be converted back to floats for 
            //calculations
            String latitude;
            String longitude;

            do{
                rawAirport = br.readLine();
                if(rawAirport == null){
                    break;
                }
                holdLines = rawAirport.split(",");
                if(holdLines.length != 14){
                    ArrayList<String> weirdAirports = new ArrayList<>();
                    //Add contents of array which has info of problem airports(with extra commas) to an arrayList
                    //for easy deletion
                    for(int i = 0; i < holdLines.length; i++){
                        weirdAirports.add(holdLines[i]);
                    }
                    weirdAirports = modifyArrayList(weirdAirports);
                    // System.out.println("Fixed list = " + weirdAirports);

                     airlineIataCode = weirdAirports.get(4);

                     airportID = weirdAirports.get(0);
                     airportName = weirdAirports.get(1);
                     airportCity = weirdAirports.get(2);
                     airportCountry = weirdAirports.get(3);
                     latitude = weirdAirports.get(6);
                     longitude = weirdAirports.get(7); 
                     
                     String [] airportInfoValues =  {airportID, airportName, airportCity, airportCountry, 
                        latitude, longitude};

                    //Removing airports with IATA code of \N 
                    if(airlineIataCode.length() != 3){
                        continue;
                    } 

                    finalAirports.put(airlineIataCode, airportInfoValues);

                }
                else{
                    airlineIataCode = holdLines[4];
            
                    airportID = holdLines[0];
                    airportName = holdLines[1];
                    airportCity = holdLines[2];
                    airportCountry = holdLines[3];
                    latitude = holdLines[6];
                    longitude = holdLines[7]; 
    
                    String [] airportInfoValues =  {airportID, airportName, airportCity, airportCountry, 
                        latitude, longitude};
                      
                    //Removing airports with IATA code of \N 
                    if(airlineIataCode.length() != 3){
                        continue;
                    }   

                    finalAirports.put(airlineIataCode, airportInfoValues);
                }

            }
            while(true);

            br.close();
            freader.close();

        }
        catch(IOException ioe){
            System.out.println("IOException thrown");
            return false;
        }

        return true;
    }

    /**Checks for elements which have been incorrectly split by commas and merges them
     * @param weirdAirports ArrayList with elements split by comma
     * @return ArrayList of elements that have been fixed
     */
    public static ArrayList<String> modifyArrayList(ArrayList<String> weirdAirports){
        for(int index = 0; index < weirdAirports.size(); index++){
            String element = weirdAirports.get(index);
            char firstElement = element.charAt(0);
            char quote= '"';
            if(firstElement == quote){
                // System.out.println("String = " + element + " index = " + index);
                String mergedString = element + "" + weirdAirports.get(index + 1);
                weirdAirports.set(index, mergedString);    
                String toRemove = weirdAirports.get(index+1);
                // System.out.println("String to remove = " + toRemove + "index = " + index);
                weirdAirports.remove(toRemove);
                // System.out.println("Modified arrayList = " + weirdAirports);
                ArrayList<String> finalArrayList = new ArrayList<String>(weirdAirports);
                return finalArrayList;
            }
        }
        return null;
    
    }

    public static void main(String[] args) throws IOException {

    }
}
