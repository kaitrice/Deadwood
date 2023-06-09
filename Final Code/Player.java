import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Player {
  // all player values

  private String name;
  protected int dollars;
  protected int credits;
  protected int practiceChips;
  private int rank;
  private Set set;
  private Role role;
  private String color; // b, c, g, o, p, r , v, y, w
  private ImageIcon[] diceImgs;
  ImageIcon die;
  JLabel playerDie = new JLabel();
  private int xPCord = 0;
  private int yPCord = 0;

  public Player(String name, int dollars, int credits, int practiceChips, int rank, Set set) {
    this.name = name;
    this.dollars = dollars;
    this.credits = credits;
    this.practiceChips = practiceChips;
    this.rank = rank; 
    this.set = set;
  }
    
  /* GETTERS */
  public String getName() {
    return this.name;
  }

  public int getPX(){
    return this.xPCord;
  }

  public int getPY(){
    return this.yPCord;
  }

  public int getDollars(){
    return this.dollars;
  }

  public int getCredits(){ 
    return this.credits;
  }

  public int getPracticeChips(){
    return this.practiceChips;
  }

  public int getRank(){ 
    return this.rank;
  }

  public Set getSet(){
    return this.set;
  }

  public Role getRole(){
    return this.role;
  }

  public String getColor(){
    return this.color;
  }

  public ImageIcon[] getDiceImgs(){
    return this.diceImgs;
  }

  public JLabel getPlayerDieIcon(){
    return this.playerDie;
  }

  /* SETTERS */
  public void setPXandY(int x, int y){
    // System.out.println(" x=" + x + " y=" + y);
    this.xPCord = x;
    this.yPCord = y;
  }

  protected void setSet(Set s){
    this.set = s;
  }

  protected void setRank(int rank){
    this.rank = rank;
  }

  protected void setRole(Role r){
      this.role = r;
  }

  protected void setColor(String c){
    this.color = c;
}

  public void setDiceImgs(ImageIcon[] d){
    this.diceImgs = d;
  }

  public void setPlayerDieIcon(){
    int r = this.rank;
    playerDie.setIcon(diceImgs[r-1]);
  }

  /* DISPLAYS */
  public void displayPlayer(){
    System.out.println("You have " + this.dollars + " Dollars.");
    System.out.println("You have " + this.credits + " Credits.");
    System.out.println("You are Rank " + this.rank + ".");
    System.out.println("You're currently located on " + (this.set).getName() + ".");

    if (this.role != null){
      System.out.println("Your role is " + this.role.getName() + ".");
      System.out.println("You have " + this.practiceChips + " Practice Chips.");
    }
    System.out.println();
  }
}
