public class Role {
  private String name;
  private String quote; 
  private int rank;

  public Role(String name, String quote, int rank) {
    this.name = name;
    this.quote = quote;
    this.rank = rank;
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

  /* DISPLAYS */
  protected void displayRole(){
    System.out.println("Name: " + this.name);
    System.out.println("Quote: " + this.quote);
    System.out.println("Rank: " + this.rank);
    System.out.println();
  }
}
