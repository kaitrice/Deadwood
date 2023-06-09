public class Role {
  private String name;
  private String quote; 
  private int rank;
  private int xCord = 0;
  private int yCord = 0;


  public Role(String name, String quote, int rank, int x, int y) {
    this.name = name;
    this.quote = quote;
    this.rank = rank;
    setRoleXandY(x,y);
    // System.out.println("New Role Created! Name: " + name + " x = " + x + " y = " + y);
  }

  /* GETTERS */
  protected String getName(){
    return this.name;
  }

  protected String getQuote(){
    return this.quote;
  }

  protected int getRoleRank(){
    return this.rank;
  }

  public int getRoleX(){
      return this.xCord;
  }

  public int getRoleY(){
    return this.yCord;
  }

  // Setters
  public void setRoleXandY(int x, int y){
    this.xCord = x;
    this.yCord = y;
  }

  /* DISPLAYS */
  protected void displayRole(){
    System.out.println("Name: " + this.name);
    System.out.println("Quote: " + this.quote);
    System.out.println("Rank: " + this.rank);
    System.out.println();
  }
}
