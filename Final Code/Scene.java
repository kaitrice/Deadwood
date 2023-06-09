import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Scene {
  // information parsed from cards.xml for title, description, & leadRoles
  private String name;
  private int budget;
  private String description;
  private ArrayList<Role> leadAvailable = new ArrayList<Role>();
  private ArrayList<Player> leadPlayers = new ArrayList<Player>();
  private String sceneCard;

  public Scene(String name, int budget, String description, ArrayList<Role> leadAvailable) {
    this.name = name;
    this.budget = budget;
    this.description = description;
    this.leadAvailable = leadAvailable;
  }

  /* GETTERS */
  protected String getName() {
    return this.name;
  }

  protected String getDescription() {
    return this.description;
  }

  protected int getBudget() {
    return this.budget;
  }

  protected String getCardImage() {
    return this.sceneCard;
  }

  protected ArrayList<Role> getLeadAvailable() {
    return this.leadAvailable;
  }

  protected Role[] getLeadAvailableArray(){
    Role[] retNeighbors = new Role[leadAvailable.size()];
    for (int i = 0; i < leadAvailable.size(); i++){
      retNeighbors[i] = leadAvailable.get(i);
    }
    return retNeighbors;
  }
  
  protected ArrayList<Player> getLeadPlayers() {
    return this.leadPlayers;
  }

  /* SETTERS */
  protected void setLeadPlayer(Player p) {
    leadPlayers.add(p);
  }

  /* REMOVE */
  protected void removeLeadAvailable(Role r) {
    leadAvailable.remove(r);
  }

  protected void setLeadPlayers() {
    leadPlayers = null;
  }

  protected void setCardImage(String path) {
    this.sceneCard = path;
  }

  /* DISPLAYS */
  protected void displayScene(){
    // prints scene information
    System.out.println("Scene Name" + this.getName());
    System.out.println("Budget: " + this.getBudget());
    System.out.println("Description: " + this.getDescription());

    displayLeads();
  }

  protected void displayLeads(){
    // prints scene information
    for (int i = 0; i < leadAvailable.size(); i++){
      leadAvailable.get(i).displayRole();
    }
  }
}
