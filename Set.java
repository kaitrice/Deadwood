import java.util.ArrayList;

public class Set {
    private ArrayList<Role> extraAvailable = new ArrayList<Role>(); //list of available extra roles;
    private ArrayList<Player> extraPlayers = new ArrayList<Player>(); //list of available extra roles;
    private int counters;
    protected ArrayList<String> neighborsString; // convert to set later
    private ArrayList<Set> neighborsSet = new ArrayList<Set>(); //list of available extra roles;
    private Scene curScene;
    private boolean shown = false;
    private boolean available = true;
    private String name;
    private int[][] upgradeArr = new int[2][5];

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

    public ArrayList<Set> getNeighbors(){
      return this.neighborsSet;
    }

    public int getCounters(){
      return this.counters;
    }

    protected ArrayList<Role> getExtraAvailable(){
      return this.extraAvailable;
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

    private boolean getAvailable() {
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

  public void setCounter() {
    this.counters--;
  }

    
  protected void setAvailable() {
    this.available = false;
  }

  protected void setExtraPlayer(Player p) {
    extraPlayers.add(p);
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

  /* HELPERS */
  public String sceneCompleted() {
    if (!available) {
      return " - Scene is currently completed.";
    }
    return "";
  }
}
