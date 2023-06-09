import java.util.ArrayList;

public class Set {
    private ArrayList<Role> extraAvailable = new ArrayList<Role>(); //list of available extra roles;
    private ArrayList<Player> extraPlayers = new ArrayList<Player>(); //list of available extra roles;
    private int counters;
    // 0 - 1
    // 1 -2
    private int counterLoc[][];
    protected ArrayList<String> neighborsString; // convert to set later
    private ArrayList<Set> neighborsSet = new ArrayList<Set>(); //list of available extra roles;
    private Scene curScene;
    private boolean shown = false;
    private boolean available = true;
    private String name;
    private int[][] upgradeArr = new int[2][5];
    private int xCord = 0;
    private int yCord = 0;

    // normal set
    public Set(String name, ArrayList<String> neighbors, int counters, ArrayList<Role> extraAvailable) {
      this.name = name;
      this.neighborsString = neighbors;
      this.counters = counters;
      this.extraAvailable = extraAvailable;
    }

    // Trailer
    public Set(String name, ArrayList<String> neighbors) {
      this.name = name;
      this.neighborsString = neighbors;
    }

    // Office
    public Set(String name, ArrayList<String> neighbors, int[][] upgradeArr) {
      this.name = name;
      this.neighborsString = neighbors;
      this.upgradeArr = upgradeArr;
    }

    /* GETTERS */
    public String getName(){
      return this.name;
    }

    public int getX(){
      return this.xCord;
    }

    public int getY(){
      return this.yCord;
    }

    public ArrayList<Set> getNeighbors(){
      return this.neighborsSet;
    }

    public String[] getNeighborString(){
      String[] retNeighbors = new String[neighborsString.size()];
        for (int i = 0; i < neighborsString.size(); i++){
          retNeighbors[i] = neighborsString.get(i);
        }
        return retNeighbors;
    }

    public int getCounters(){
      return this.counters;
    }

    public int[][] getCounterLocs(){
      return this.counterLoc;
    }

    public boolean getShown(){
      return this.shown;
    }

    protected ArrayList<Role> getExtraAvailable(){
      return this.extraAvailable;
    }

    protected Role[] getExtraAvailableArray(){
      Role[] retNeighbors = new Role[extraAvailable.size()];
      for (int i = 0; i < extraAvailable.size(); i++){
        retNeighbors[i] = extraAvailable.get(i);
      }
      return retNeighbors;
    }

    protected Role[] getRolesAvailable(){
      int numRoles = extraAvailable.size() + curScene.getLeadAvailable().size();
      Role[] retNeighbors = new Role[numRoles];
      int i = 0; // retNeighbors indexing - 0 to lead size index as well
      int j = 0; // extra indexing - continue indexing i for retNeighbors

      while (i++ < curScene.getLeadAvailable().size()) {
        retNeighbors[i] = curScene.getLeadAvailable().get(i);
      }

      while (j++ < numRoles) {
        retNeighbors[i] = extraAvailable.get(j);
        i++;
      }

      return retNeighbors;
    }

    protected ArrayList<Player> getExtraPlayers(){
      return this.extraPlayers;
    }

    public Scene getScene(){
        return this.curScene;
    }

    protected int[][] getUpgrades() {
      return upgradeArr;
    }

    //changed to public from private
    public boolean getAvailable() {
      return this.available;
  }

  public ArrayList<Set>  parseNeighbors(ArrayList<Set> board) {
    ArrayList<Set> n = new ArrayList<Set>();

    for (int i = 0; i < neighborsString.size(); i++){
      for (int k = 0; k < board.size(); k++){
        if (neighborsString.get(i).equals(board.get(k).getName())) {
          n.add(board.get(k));
        }
      }
    }
    this.neighborsSet = n;
    return this.neighborsSet;
  }

  /* SETTERS */
  public void setCurScene(Scene newScene){
    this.curScene = newScene;
  }

  public void setXandY(int x, int y){
    //System.out.println(" x=" + x + " y=" + y);
    this.xCord = x;
    this.yCord = y;
  }

  public void setCounter() {
    this.counters--;
  }

  public void setCounterLocs(int[][] loc){
    this.counterLoc = loc;
  }

  public void setLeadCords() {
    int numRoles = curScene.getLeadAvailable().size();
    if (numRoles == 1) {
      curScene.getLeadAvailable().get(0).setRoleXandY(xCord+80, yCord+50);
    } else if (numRoles == 2) {
      curScene.getLeadAvailable().get(0).setRoleXandY(xCord+50, yCord+50);
      curScene.getLeadAvailable().get(1).setRoleXandY(xCord+110, yCord+50);
    } else if (numRoles == 3) {
      curScene.getLeadAvailable().get(0).setRoleXandY(xCord+20, yCord+50);
      curScene.getLeadAvailable().get(1).setRoleXandY(xCord+80, yCord+50);
      curScene.getLeadAvailable().get(2).setRoleXandY(xCord+140, yCord+50);
    }
  }

    
  protected void setAvailable() {
    this.available = false;
  }

  protected void setExtraPlayer(Player p) {
    extraPlayers.add(p);
  }

  public void setShown(boolean s){
    this.shown = s;
  }

  /* REMOVE */
  protected void removeExtraAvailable(Role r) {
    extraAvailable.remove(r);
  }

  protected void setExtraPlayers() {
    extraPlayers = null;
  }

  public void removeScene(){
    this.curScene = null;
  }

    /* DISPLAYS */
    protected void displaySet(){
      // prints scene information
      System.out.println("Welcome to " + this.getName());
      
      if (this.getScene() != null) {

        System.out.println("Counters: " + this.getCounters());

        if (available) {
          curScene.displayScene();
          displayAllRoles();
        } else {
          System.out.println("This scene is completed. No roles are available");
        }
      }
    }

    public void displayNeighbors(){
    // prints set information
    System.out.println("You are currently in: " + this.getName());
    for (int i = 0; i < this.getNeighbors().size(); i++){
      System.out.println("neighbor " + (i+1) + ": " + neighborsSet.get(i).getName() + neighborsSet.get(i).sceneCompleted());
    }
  }

  public void displayUpgrades(){
    System.out.println("Upgrade chart: ");
    System.out.println("Rank   Dollars   Credits");
    for (int i = 0; i < upgradeArr[0].length; i++){
        System.out.println((i+2) + "        " +  upgradeArr[0][i] + "        " + upgradeArr[1][i]);
    }
  }

  public void displayAllRoles(){
    // prints scene information
    System. out.println("The lead roles available:");
    int size = curScene.getLeadAvailable().size();

    for (int i = 0; i < size; i++){
      System.out.println("Role " + (i+1));
      curScene.getLeadAvailable().get(i).displayRole();
    }

    System. out.println("The extra roles available:");
    for (int j = 0; j < extraAvailable.size(); j++){
      System.out.println("Role " + (size + j + 1));
      extraAvailable.get(j).displayRole();
    }
  }

      protected Role[] getRolesAvailable2Player(Player p){
      int pRank = p.getRank();
      ArrayList<Role> availableRoles = new ArrayList<>();
      
      ArrayList<Role> leads = curScene.getLeadAvailable();
      for (int i = 0; i < leads.size(); i++) {
        if (leads.get(i).getRoleRank() <= pRank) {
          availableRoles.add(leads.get(i));
        }
      }
      
      for (int j = 0; j < extraAvailable.size(); j++) {
        if (extraAvailable.get(j).getRoleRank() <= pRank) {
          availableRoles.add(extraAvailable.get(j));
        }
      }
      
      Role[] retNeighbors = new Role[availableRoles.size()];

      for (int k = 0; k < availableRoles.size(); k++) {
        retNeighbors[k] = availableRoles.get(k);
      }
      
      return retNeighbors;
    }
    

    public String[] stringRolesAvailable(Role[] roles) {
      String[] stringRoles = new String[roles.length];
      String name;

      for (int i = 0; i < roles.length; i++) {
        name = roles[i].getName() + ", Rank: " + Integer.toString(roles[i].getRoleRank());
        stringRoles[i] = name;
      }

      return stringRoles;
    }

  /* HELPERS */
  public String sceneCompleted() {
    if (!available) {
      return " - Scene is currently completed.";
    }
    return "";
  }
}
