public class Banker {
  private Banker() {}
  
  private static Banker bankerInstance = new Banker();
  
  public static Banker getBanker(){
    return bankerInstance;
  }

  /* SETTERS */
  protected void setDollars(int dollars, Player p) {
    p.dollars = p.getDollars() + dollars;
  }

  protected void setCredits(int credits, Player p) {
      p.credits = p.getCredits() + credits;
  }

  protected void addPractice(Player p) {
    p.practiceChips = p.getPracticeChips() + 1;
  }

  protected void zeroPractice(Player p) {
      p.practiceChips = 0;
  }

  protected void payout(Set s) {
    if (s.getCounters() == 0) { 
      s.sceneCompleted();

      if (s.getScene().getLeadPlayers().size() > 0){
        System.out.println("Paying leads.");
        Action a = new Action();
        int[] dice = a.rollDice(s.getScene().getBudget());
        System.out.println("Rolling " + dice.length + " dice...");
        System.out.print("The dice values are: ");
        for (int i = 0; i < dice.length; i++) {
          System.out.print(dice[i] + " ");
        }
        System.out.println();

        int[] pay = new int[s.getScene().getLeadPlayers().size()];

        int d = 0;
        while (d < dice.length) {
          for(int k = 0; k < s.getScene().getLeadPlayers().size(); k++) {
            if (d < dice.length)
              pay[k] += dice[d++];
          }
        }

        // (pay leads) for the length of leadPlayers 
        for(int k = 0; k < s.getScene().getLeadPlayers().size(); k++){
          Player p = s.getScene().getLeadPlayers().get(k);
          setDollars(pay[k], p);

          System.out.println(p.getName() + " was paid " + pay[k] + " dollars");
          System.out.println();
        }
        // (pay extras) for the length of extraPlayers
        System.out.println("Paying extras.");
        for (int i = 0; i < s.getExtraPlayers().size(); i++){
          Player p = s.getExtraPlayers().get(i);
          Role r = p.getRole();
          setDollars(r.getRoleRank(), p);
          System.out.println(p.getName() + " was paid " + r.getRoleRank() + " dollars.");
          System.out.println();
        }
      } else {
        System.out.println("There are no active lead players, no bonus payout.");
        System.out.println();
      }
      int leadSize = s.getScene().getLeadPlayers().size();
      // zero practice chips - must happen if counters = 0
      for(int k = 0; k < leadSize; k++) {
        Player p = s.getScene().getLeadPlayers().get(k);
        zeroPractice(p);
        p.setRole(null);
      }
      s.getScene().setLeadPlayers();

      int extraSize =  s.getExtraPlayers().size();
      for(int k = 0; k < extraSize; k++) {
        Player p = s.getExtraPlayers().get(k);
        zeroPractice(p);
        p.setRole(null);
      }
      s.setExtraPlayers();
    }
  }
}
