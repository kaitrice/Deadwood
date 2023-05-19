import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;


import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class Parser {
  // will parse all the xml files into data structures

  protected Document getDocFromFile(String filename)
    throws ParserConfigurationException{
      {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = null;

        try{
          doc = db.parse(filename);
        } catch (Exception ex){
          System.out.println("XML parse failure");
          ex.printStackTrace();
        }
        return doc;
      } // exception handling
    }

  protected ArrayList<Set> readBoardData(Document b) {
    ArrayList<Set> board = new ArrayList<Set>();
    String setTitle;
    String counter;
    
    
    //board
    Element root = b.getDocumentElement();
    // sets
    NodeList sets = root.getElementsByTagName("set");
    NodeList trailer = root.getElementsByTagName("trailer");
    NodeList office = root.getElementsByTagName("office");
    
    for (int i = 0; i < sets.getLength(); i++) {
      ArrayList<String> neighbors = new ArrayList<String>();
      ArrayList<Role> extraAvailable = new ArrayList<Role>();
      int count = 0;

        Node set = sets.item(i);
        setTitle = set.getAttributes().getNamedItem("name").getNodeValue();
    
        NodeList children = set.getChildNodes();

        for(int j = 0; j < children.getLength(); j++) {
          Node sub = children.item(j);

          if ("neighbors".equals(sub.getNodeName())) {
            NodeList room = sub.getChildNodes();

            for (int r = 0; r < room.getLength(); r++){
              Node n = room.item(r);
              
              if ("neighbor".equals(n.getNodeName())) {
                String neighborName = n.getAttributes().getNamedItem("name").getNodeValue();
                neighbors.add(neighborName);
              }
            } // room loop
          }

          if ("takes".equals(sub.getNodeName())) {
            NodeList takes = sub.getChildNodes();


            for (int t = 0; t < takes.getLength(); t++){
              Node n = takes.item(t);
  
              if ("take".equals(n.getNodeName())) {
                count++;
                String takesNum = n.getAttributes().getNamedItem("number").getNodeValue();
              }
            } // takes loop
          }
          if ("parts".equals(sub.getNodeName())) {
            NodeList parts = sub.getChildNodes();

            for (int p = 0; p < parts.getLength(); p++){
              Node n = parts.item(p);
  
              if ("part".equals(n.getNodeName())) {
                String partLine = "";
                String partsName = n.getAttributes().getNamedItem("name").getNodeValue();
                String partsLevel = n.getAttributes().getNamedItem("level").getNodeValue();

                NodeList partInfo = n.getChildNodes(); // grab part attribue

                for (int k = 0; k < partInfo.getLength(); k++){

                  Node partSub = partInfo.item(k);

                  if("line".equals(partSub.getNodeName())){
                    partLine = partSub.getTextContent();
                  }
                }
                Role newRole = new Role(partsName, partLine, Integer.parseInt(partsLevel));
                extraAvailable.add(newRole);
              }
            } // takes loop
          }
        } // children loop
        Set newSet = new Set(setTitle, neighbors, count, extraAvailable);
        board.add(newSet);
      } // set loop

    // trailer info
    ArrayList<String> trailerSetNeighbors = new ArrayList<String>();
    Node trailerRoot = trailer.item(0);
    NodeList trailerSub = trailerRoot.getChildNodes();
      
    for (int i = 0; i < trailerSub.getLength(); i++) {
      Node n = trailerSub.item(i);
        
      if ("neighbors".equals(n.getNodeName())) {
        NodeList trailerNeighbors = n.getChildNodes();

        for (int r = 0; r < trailerNeighbors.getLength(); r++) {
          Node tNeighbors = trailerNeighbors.item(r);
            if ("neighbor".equals(tNeighbors.getNodeName())) {
            String trailerNeighborName = tNeighbors.getAttributes().getNamedItem("name").getNodeValue();
            trailerSetNeighbors.add(trailerNeighborName);
          }
        } // Trailer Neighbor loop
      }
    } // Trailer sub loop
    Set trailerSet = new Set("trailer", trailerSetNeighbors);
    board.add(trailerSet);
    
    // Office
    ArrayList<String> officeSetNeighbors = new ArrayList<String>();
    int[][] upgradeArr = new int[2][5];

    Node officeRoot = office.item(0);
    NodeList officeSub = officeRoot.getChildNodes();
      
    for (int i = 0; i < officeSub.getLength(); i++) {
      Node n = officeSub.item(i);
        
      if ("neighbors".equals(n.getNodeName())) {
        NodeList officeNeighbors = n.getChildNodes();

        for (int r = 0; r < officeNeighbors.getLength(); r++) {
          Node oNeighbors = officeNeighbors.item(r);
          if ("neighbor".equals(oNeighbors.getNodeName())) {
            String officeNeighborName = oNeighbors.getAttributes().getNamedItem("name").getNodeValue();
            officeSetNeighbors.add(officeNeighborName);
          }
        } // Office Neighbor loop
      }

      if ("upgrades".equals(n.getNodeName())) {
        NodeList upgrades = n.getChildNodes();

        for (int r = 0; r < upgrades.getLength(); r++) {
          Node upgrade = upgrades.item(r);

          if ("upgrade".equals(upgrade.getNodeName())) {
            String level =  upgrade.getAttributes().getNamedItem("level").getNodeValue();
            int levelInt = Integer.parseInt(level);  
            String cur =  upgrade.getAttributes().getNamedItem("currency").getNodeValue();
            String amt =  upgrade.getAttributes().getNamedItem("amt").getNodeValue();
            int amtInt = Integer.parseInt(amt);
            
            if ("dollar".equals(cur)) {
              upgradeArr[0][levelInt-2] = amtInt;
            }
            if ("credit".equals(cur)) {
              upgradeArr[1][levelInt-2] = amtInt;
            }
          }
        } // upgrades loop
      }
    } // Office sub loop
    Set officeSet = new Set("office", officeSetNeighbors,upgradeArr);
    board.add(officeSet);    
    return board;
  } // method

  protected ArrayList<Scene> readCardData(Document c) {
    ArrayList<Scene> deck = new ArrayList<>();
    String sceneName;
    String sceneDescription = "";
    String sceneBudget;
    

    //cards
    Element root = c.getDocumentElement();
    // card
    NodeList cards = root.getElementsByTagName("card");
    
    for (int i = 0; i < cards.getLength(); i++) {
      ArrayList<Role> leadAvailable = new ArrayList<>();
      Node card = cards.item(i);
      sceneName = card.getAttributes().getNamedItem("name").getNodeValue();
      //img
      sceneBudget = card.getAttributes().getNamedItem("budget").getNodeValue(); // int

      NodeList children = card.getChildNodes();

      //for loop for each on card role
      for(int j = 0; j < children.getLength(); j++){
        Node sub = children.item(j);

        if ("scene".equals(sub.getNodeName())) {
          String sceneNum =  sub.getAttributes().getNamedItem("number").getNodeValue();
          sceneDescription = sub.getTextContent();
        } // scene info
        if ("part".equals(sub.getNodeName())) {
          String roleName;
          String roleQuote = "";
          String roleRank;

          roleName = sub.getAttributes().getNamedItem("name").getNodeValue();
          roleRank = sub.getAttributes().getNamedItem("level").getNodeValue();

          NodeList part = sub.getChildNodes(); // grab part attribue

          for (int k = 0; k < part.getLength(); k++){

            Node partInfo = part.item(k);

            if("line".equals(partInfo.getNodeName())){
              roleQuote = partInfo.getTextContent();
            }
          }
          Role newRole = new Role(roleName, roleQuote, Integer.parseInt(roleRank));
          leadAvailable.add(newRole);
        } // on card roles  
        
      } // for children
      Scene newScene = new Scene(sceneName, Integer.parseInt(sceneBudget), sceneDescription, leadAvailable);
      deck.add(newScene);
    } // for card

    return deck;
  } // method

  protected ArrayList<Scene> parseDeck() {
    ArrayList<Scene> deck = new ArrayList<>();
    Document doc = null;
    Parser parsing = new Parser();
    try{

      doc = parsing.getDocFromFile("Cards.xml");
      deck = parsing.readCardData(doc);      
    }catch (Exception e){
      System.out.println("Error = "+e);
    }
    return deck;
  }

  protected ArrayList<Set> parseBoard() {
    ArrayList<Set> board = new ArrayList<>();
    Document doc = null;
    Parser parsing = new Parser();
    try{

      doc = parsing.getDocFromFile("Board.xml");
      board = parsing.readBoardData(doc);
      
    }catch (Exception e){
      
      System.out.println("Error = "+e);
      
    }

    return board;
  }

}