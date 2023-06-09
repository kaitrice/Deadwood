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
      // String x = "0";
      // String y = "0";
      int x = 0;
      int y = 0;

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
                  if("area".equals(partSub.getNodeName())){

                    /* train station */
                    if (partsName.equals("Crusty Prospector")) {
                      x = 115;
                      y = 230;
                    } else if (partsName.equals("Dragged by Train")) {
                      x = 50;
                      y = 270;
                    } else if (partsName.equals("Preacher with Bag")) {
                      x = 115;
                      y = 320;
                    } else if (partsName.equals("Cyrus the Gunfighter")) {
                      x = 50;
                      y = 350;
                    }
                    /* jail */ 
                    else if (partsName.equals("Prisoner In Cell")) {
                      x = 520;
                      y = 25;
                    } else if (partsName.equals("Feller in Irons")) {
                      x = 520;
                      y = 110;
                    } 
                    /* main street  */ 
                    else if (partsName.equals("Railroad Worker")) {
                      x = 640;
                      y = 25;
                    } else if (partsName.equals("Falls off Roof")) {
                      x = 725;
                      y = 25;
                    } else if (partsName.equals("Woman in Black Dress")) {
                      x = 640;
                      y = 110;
                    } else if (partsName.equals("Mayor McGinty")) {
                      x = 725;
                      y = 110;
                    } 
                    /* General Store */
                    else if (partsName.equals("Man in Overalls")) {
                      x = 240;
                      y = 280;
                    } else if (partsName.equals("Mister Keach")) {
                      x = 240;
                      y = 360;
                    } 
                    /* Saloon */
                    else if (partsName.equals("Woman in Red Dress")) {
                      x = 880;
                      y = 275;
                    } else if (partsName.equals("Reluctant Farmer")) {
                      x = 880;
                      y = 360;
                    } 
                    /* Ranch */
                    else if (partsName.equals("Man Under Horse")) {
                      x = 490;
                      y = 525;
                    } else if (partsName.equals("Shot in Leg")) {
                      x = 490;
                      y = 610;
                    } else if (partsName.equals("Saucy Fred")) {
                      x = 415;
                      y = 610;
                    } 
                    /* Bank */
                    else if (partsName.equals("Flustered Teller")) {
                      x = 910;
                      y = 470;
                    } else if (partsName.equals("Suspicious Gentleman")) {
                      x = 910;
                      y = 555;
                    } 
                    /* secret hideout */
                    if (partsName.equals("Clumsy Pit Fighter")) {
                      x = 435;
                      y = 720;
                    } else if (partsName.equals("Dangerous Tom")) {
                      x = 435;
                      y = 810;
                    } else if (partsName.equals("Thug with Knife")) {
                      x = 525;
                      y = 720;
                    } else if (partsName.equals("Penny, who is lost")) {
                      x = 525;
                      y = 810;
                    }
                    /* church */
                    else if (partsName.equals("Dead Man")) {
                      x = 860;
                      y = 730;
                    } else if (partsName.equals("Crying Woman")) {
                      x = 860;
                      y = 810;
                    } 
                    /* hotel */
                    if (partsName.equals("Sleeping Drunkard")) {
                      x = 1115;
                      y = 470;
                    } else if (partsName.equals("Faro Player")) {
                      x = 1050;
                      y = 510;
                    } else if (partsName.equals("Australian Bartender")) {
                      x = 1050;
                      y = 600;
                    } else if (partsName.equals("Falls from Balcony")) {
                      x = 1115;
                      y = 560;
                    }

                    // x = partSub.getAttributes().getNamedItem("x").getNodeValue();
                    // y = partSub.getAttributes().getNamedItem("y").getNodeValue();
                    // System.out.println("The area of a role has been parsed for " + partsName);
                    // System.out.println("x = "+ x + " y = "+ y);
                  }
                }
                // Role newRole = new Role(partsName, partLine, Integer.parseInt(partsLevel),Integer.parseInt(x),Integer.parseInt(y));
                Role newRole = new Role(partsName, partLine, Integer.parseInt(partsLevel),x,y);
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
          int x = 0;
          int y = 0;
          

          roleName = sub.getAttributes().getNamedItem("name").getNodeValue();
          roleRank = sub.getAttributes().getNamedItem("level").getNodeValue();

          NodeList part = sub.getChildNodes(); // grab part attribue

          for (int k = 0; k < part.getLength(); k++){

            Node partInfo = part.item(k);

            if("line".equals(partInfo.getNodeName())){
              roleQuote = partInfo.getTextContent();
            }
            if("area".equals(partInfo.getNodeName())){
              // x = partInfo.getAttributes().getNamedItem("x").getNodeValue();
              // y = partInfo.getAttributes().getNamedItem("y").getNodeValue();
            }
          }
          // Role newRole = new Role(roleName, roleQuote, Integer.parseInt(roleRank), Integer.parseInt(x), Integer.parseInt(y));
          Role newRole = new Role(roleName, roleQuote, Integer.parseInt(roleRank), x,y);

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

      for(int i = 1; i < deck.size()+1; i++){ 
        String s;
        if(i < 10){
          s = "required_images/cards/" + "0" + Integer.toString(i) + ".png";
          // System.out.println(s);
        } else{
          s = "required_images/cards/" + Integer.toString(i) + ".png";
          // System.out.println(s);
        }
        deck.get(i-1).setCardImage(s);
      }
      
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

      for (int i = 0; i < board.size(); i++){
        if(board.get(i).getName().equals("Ranch")){
          /* set card loction */
          board.get(i).setXandY(250, 475);
          /* set shppt */
          int[][] cL = {{530,470},{480,470}};
          board.get(i).setCounterLocs(cL);

        }else if (board.get(i).getName().equals("Train Station")){
          /* set card loction */
          board.get(i).setXandY(20, 65);
          /* set shppt */
          int[][] cL = {{140,10},{90,10},{40,10}};
          board.get(i).setCounterLocs(cL);

        }else if (board.get(i).getName().equals("Main Street")){
          /* set card loction */
          board.get(i).setXandY(970, 25);
          /* set shppt */
          int[][] cL = {{810,15},{860,15},{910,15}};
          board.get(i).setCounterLocs(cL);

        } else if (board.get(i).getName().equals("Jail")){
          /* set card loction */
          board.get(i).setXandY(280, 25);
          /* set shppt */
          int[][] cL = {{440,160}}; //jail
          board.get(i).setCounterLocs(cL);

        } else if (board.get(i).getName().equals("Saloon")){
          /* set card loction */
          board.get(i).setXandY(630, 280);
          /* set shppt */
          int[][] cL = {{680,210},{630,210}};
          board.get(i).setCounterLocs(cL);

        }else if(board.get(i).getName().equals("Bank")){
          /* set card loction */
          board.get(i).setXandY(625, 475);
          /* set shppt */
          int[][] cL = {{843,552}};
          board.get(i).setCounterLocs(cL);

        }else if(board.get(i).getName().equals("Secret Hideout")){
          /* set card loction */
          board.get(i).setXandY(25, 735);
          /* set shppt */
          int[][] cL = {{358,768},{302,765}, {248,768}};
          board.get(i).setCounterLocs(cL);

        }else if(board.get(i).getName().equals("Church")){
          /* set card loction */
          board.get(i).setXandY(625, 735);
          /* set shppt */
          int[][] cL = {{685,680},{625,678}};
          board.get(i).setCounterLocs(cL);

        }else if(board.get(i).getName().equals("Hotel")){
          /* set card loction */
          board.get(i).setXandY(965, 735);
          /* set shppt */
          int[][] cL = {{1113,685},{1060,685},{1007,685}};
          board.get(i).setCounterLocs(cL);

        }else if(board.get(i).getName().equals("General Store")){
          /* set card loction */
          board.get(i).setXandY(370, 280);
          /* set shppt */
          int[][] cL = {{320,330},{320,280}};
          board.get(i).setCounterLocs(cL);

        }else if(board.get(i).getName().equals("trailer")){
          /* set card loction */
          board.get(i).setXandY(1005,250);
        }else if(board.get(i).getName().equals("office")){
          /* set card loction */
          board.get(i).setXandY(0,450);
        }
      }
      
    }catch (Exception e){
      
      System.out.println("Error = "+e);
      
    }

    return board;
  }

}