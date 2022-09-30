public class Node{
    String startIATA;
    String endIATA;
    Double startLat;
    Double startLong;
    Double endLat;
    Double endLong;
    Node parent;
    public Node(String startIATA, String endIATA){
        this.startIATA = startIATA;
        this.endIATA = endIATA;
        this.endLat = null;
        this.endLong = null;
        this.startLat = null;
        this.startLong = null;
        this.parent = null;
    }
    public Node(String startIATA, String endIATA, Node parent){
        this.startIATA = startIATA;
        this.endIATA = endIATA;
        this.parent = parent;
        this.endLat = null;
        this.endLong = null;
        this.startLat = null;
        this.startLong = null;
    }
    public Node(String startIATA, String endIATA, String startLat, String startLong, String endLat, String endLong){
        this.startIATA = startIATA;
        this.endIATA = endIATA;
        this.endLat = Double.parseDouble(endLat);
        this.endLong =  Double.parseDouble(endLong);
        this.startLat =  Double.parseDouble(startLat);
        this.startLong =  Double.parseDouble(startLong);
        this.parent = null;

    }

    public void addParent(Node parent){
        this.parent = parent;
    }

    @Override
    public boolean equals(Object other){
        Node other1 = (Node)other;
        return (this.startIATA.equals(other1.startIATA) && this.endIATA.equals(other1.endIATA));

    }

    @Override
    public int hashCode(){
        return this.startIATA.hashCode();
    }

    public String toString(){
        // String data = "Start IATA = " + this.startIATA + " --> " + this.endIATA + " = End IATA"; //+ " || Distance = " + this
        String data;
        if (this.parent != null){
            data = "Start IATA = " + this.startIATA + " --> " + "End IATA = "+ this.endIATA ; //+ " || Parent start = " + this.parent.startIATA + " parent end = " + this.parent.endIATA ; //+ " || Distance = " + this
        }
        else{
            data = "Start IATA = " + this.startIATA + " --> " + "End IATA = "+ this.endIATA ; //+ " || Distance = " + this

        }
        return data;
    }

    public static void main(String[] args) {
        Node node1 = new Node("FKJ", "MSG");
        Node node2 = new Node("DVE", "KML");
        Node node3 = new Node("LBY", "PLG");
        Node node4 = new Node("MSG", "DVE");


        node2.addParent(node4);
        node4.addParent(node1);

        System.out.println("Node 1 - " + node1);
        System.out.println("Node 2 - " + node2);
        System.out.println("Node 3 - " + node3);
        System.out.println("Node 4 - " + node4);

    }
    

}