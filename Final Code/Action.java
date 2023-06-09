import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;

public class Action {

  // budget of scene
  protected int[] rollDice(int numDie) {
    int[] dice =  new int[numDie];
    //randomize values between 1 and 6
    for (int i = 0; i < numDie; i++) {
      dice[i] = rollDice();
    }
    Arrays.sort(dice);
    return dice;
  }

  // CHANGED
  public int rollDice(){
    return ((int)Math.floor(Math.random() * (6 - 1 + 1) + 1));
  }

  public void chooseRole(Set scur, Player p) {
    if (scur.getName() == "office" || scur.getName() == "trailer") {
      return;
    }
    Scanner in = new Scanner(System.in);
    scur.displayAllRoles();
    ArrayList<Role> extraAvaliable = scur.getExtraAvailable();
    ArrayList<Role> leadAvaliable = scur.getScene().getLeadAvailable();

    int numExtras = extraAvaliable.size();
    int numLeads = leadAvaliable.size();

    int maxNumRole = numExtras + numLeads;
    System.out.print("[Please enter a number] Which role would you like to take? ");
    String userIn = in.nextLine();
    System.out.println();

    if (userIn.equals("e")) {
      return;
    } else {
      try { 
        Integer.parseInt(userIn); 
      } catch(NumberFormatException e) {
        System.out.println("Turn forfeited");
        return; 
      } catch(NullPointerException e) {
        System.out.println("Turn forfeited");
        return;
      }
      int roleNum = Integer.parseInt(userIn);
      // loop through each role
      for (int i = 0; i < maxNumRole; i++) {
        if (roleNum == (i+1)) {
          // leads first
          if (i < numLeads) {
            Role newRole = scur.getScene().getLeadAvailable().get(i);
            if (p.getRank() < newRole.getRoleRank()) {
              System.out.println("You don't have a high enough rank for this role.");
              System.out.println("Turn forfeited.");
              return;
            }
            scur.getScene().setLeadPlayer(p);
            p.setRole(newRole);
            System.out.println(p.getName() + " new role is: " + newRole.getName());
            System.out.println();
            scur.getScene().removeLeadAvailable(newRole);
            return;
          } else {
            Role newRole = scur.getExtraAvailable().get(i-numLeads);
            if (p.getRank() < newRole.getRoleRank()) {
              System.out.println("You don't have a high enough rank for this role.");
              System.out.println("Turn forfeited.");
              return;
            }
            scur.setExtraPlayer(p);
            p.setRole(newRole);
            System.out.println(p.getName() + " new role is: " + newRole.getName());
            System.out.println();
            scur.removeExtraAvailable(newRole);
            return;
          }
        }
      }
      // outside range of maxNumRole
      System.out.println("Invalid role");
      System.out.println("Turn forfeited");
    }
  }

  // Interface chooseRole logic (change player data)
  public void chooseRole(Player p , int chosenRole) {
    Set s = p.getSet();
    Scene sc = p.getSet().getScene();
    Role[] availableRoles = s.getRolesAvailable();
    Role newRole;

    // 1 to set size
    if (chosenRole > sc.getLeadAvailable().size()) {
      newRole = sc.getLeadAvailable().get(chosenRole-1);
    } 
    // set size to all role size
    else {
      chosenRole -= sc.getLeadAvailable().size();
      newRole = s.getExtraAvailable().get(chosenRole-1);
    }

    p.setRole(newRole);

    //delete later
    System.out.println("Player new role: ");
    newRole.displayRole();
  }

  // Interface chooseRole logic (change player data)
  public void chooseRole(Player p , int chosenRole, Role[] availableRoles) {
    Set s = p.getSet();
    Scene sc = p.getSet().getScene();
    Role newRole;

    // [leads] [extras]
    // 0  1  2  3  4  5  

    // choice is 2

    newRole = availableRoles[chosenRole];
    // 0 to set size
    if (sc.getLeadAvailable().contains(newRole)) { // may need to change to < later
      sc.removeLeadAvailable(newRole);
      sc.setLeadPlayer(p);
    } 
    // set size to all role size
    else {
      // chosenRole -= sc.getLeadAvailable().size();
      s.removeExtraAvailable(newRole);
      s.setExtraPlayer(p);
    }

    p.setRole(newRole);

    //delete later
    System.out.println("Player new role: ");
    newRole.displayRole();
  }

  public boolean move(Set scur, Player p) { 
    Scanner in = new Scanner(System.in);
    Gameboard gb = Gameboard.getGameboard();

    ArrayList<Set> sceneNeighbors = scur.getNeighbors();
    scur.displayNeighbors();
    System.out.print("[Please enter a number] Which neighbor would you like to travel to? ");

    String userIn = in.nextLine();
    System.out.println();

      if (userIn.equals("e")) {
        return false;
      } else {
        int maxNeighbors = sceneNeighbors.size();
        try { 
          Integer.parseInt(userIn); 
        } catch(NumberFormatException e) {
          System.out.println("Turn forfeited");
          return false; 
        } catch(NullPointerException e) {
          System.out.println("Turn forfeited");
          return false;
        }
        int chosenNeighbor = Integer.parseInt(userIn);
        // loop through each role
        for (int i = 0; i < maxNeighbors; i++) {
          if (chosenNeighbor == (i+1)) {
            Set newSet = sceneNeighbors.get(i);
            p.setSet(newSet);
            System.out.println(p.getName() + " moved.");
            System.out.println("Welcome to " + newSet.getName() + "!");

            if (newSet.getName() == "office" || newSet.getName() == "trailer") {
              return false;
            }
            // chose a role
            boolean valid = false;

            // Check if user wants to choose a role
            while (!valid) {
              System.out.print("[y or n] Do you want to choose a role? ");
              String userChoice = in.nextLine();

              if (userChoice.equals("y")) {
                valid = true;
                return true;
              } else if (userChoice.equals("n")) {
                System.out.println("Turn ended.");
                valid = true;
                return false;
              } else {
                System.out.println("Invalid option. Try again");
              }
            }

          }
        }
      }
      
      // outside range of maxNeighbors
      System.out.println("Invalid move");
      System.out.println("Turn forfeited.");
      return false;
  }

  // Interface move logic (change player data)
  public boolean move(Player p, int chosenNeighbor) {
    ArrayList<Set> sceneNeighbors = p.getSet().getNeighbors();
    int maxNeighbors = sceneNeighbors.size();
    // loop through each role
    for (int i = 0; i < maxNeighbors; i++) {
      if (chosenNeighbor == (i+1)) {
        Set newSet = sceneNeighbors.get(i);
        p.setSet(newSet);
        
        System.out.println(p.getName() + " moved.");
        System.out.println("Welcome to " + newSet.getName() + "!");

        // if (newSet.getName() == "office" || newSet.getName() == "trailer") {
        //   return false;
        // }
      }
    }

    return true;
  }
  
  public void upgradeRank(Player p) {
    Set s = p.getSet();

    // validate player set
    if (s.getName() != "office") {
      System.out.println("Invalid action! Please move to the Office.");
      System.out.println("Turn forfeited.");
      return;
    }
    
    // get user input
    Scanner in = new Scanner(System.in);
    System.out.println("Welcome to the Office!");
    s.displayUpgrades();
    System.out.print("Please type 'd' for dollars or 'c' for credits: ");
    String currency = in.nextLine();
    System.out.print("Please type rank: ");
    String newRankString = in.nextLine();

    if ("e".equals(currency) || "e".equals(newRankString)) {
      System.out.println("Turn ended.");
      return;
    }

    try { 
      Integer.parseInt(newRankString); 
    } catch(NumberFormatException e) {
      System.out.println("Turn forfeited");
      return; 
    } catch(NullPointerException e) {
      System.out.println("Turn forfeited");
      return;
    }

    int newRank = Integer.parseInt(newRankString);
    int curRank = p.getRank();

    // verify desired rank
    if (curRank >= newRank || newRank > 6) {
      System.out.println("Upgrade unavailable. Current rank: " + curRank);
      return;
    }

    int[][] upgrades = s.getUpgrades();
    int dollars = p.getDollars();
    int credits = p.getCredits();

    // verify payment
    if ("d".equals(currency)) {
      if (upgrades[0][newRank-2] <= dollars) {
        // pay
        Banker b = Banker.getBanker();
        b.setDollars(-upgrades[0][newRank-2], p);
        p.setRank(newRank);
        System.out.println("Congrats! Your new rank is " + newRank + ".");
        System.out.println();
        return;
      }else {
        System.out.println("Invalid Currency.");
        System.out.println("Turn forfeited.");
      }
    } else if ("c".equals(currency)) {
      if (upgrades[1][newRank-2] <= credits) {
        // pay
        Banker b = Banker.getBanker();
        b.setCredits(-upgrades[1][newRank-2], p);
        p.setRank(newRank);
        System.out.println("Congrats! Your new rank is " + newRank + ".");
        return;
      }else {
        System.out.println("Invalid Currency.");
        System.out.println("Turn forfeited.");
      }
    }

    // player can't pay
    System.out.println("Upgrade unavailable. Insufficient funds or payment method.");
    System.out.println(p.getName() + " has " + dollars + " dollars and " + credits + " credits.");
  }

  // Interface upgradeRank logic (change player data)
  public void upgradeRank(Player p, String currency, int newRank) {
    Set s = p.getSet();
    int[][] upgrades = s.getUpgrades();
    int dollars = p.getDollars();
    int credits = p.getCredits();
    Banker b = Banker.getBanker();

    if ("d".equals(currency)) {
      b.setDollars(-upgrades[0][newRank-2], p);
      p.setRank(newRank);
      System.out.println("Congrats! Your new rank is " + newRank + ".");

      return;
    }

    b.setCredits(-upgrades[1][newRank-2], p);
    p.setRank(newRank);
    System.out.println("Congrats! Your new rank is " + newRank + ".");
    return;
    
  }

  protected void act(Player p, Set s) {
    if (s.getScene() == null) {
      System.out.println("This set does not have a scene available");
      System.out.println();
      return;
    }
    // role on dice
    System.out.println("The budget of " + s.getScene().getName() + " is " + s.getScene().getBudget() + ".");
    System.out.println("You currently have " + p.getPracticeChips() + " practice chips. So you must roll a value higher than " + (s.getScene().getBudget() - p.getPracticeChips()) + ".");
    System.out.println();
    System.out.println("Rolling the dice..");

    int diceVal = rollDice();
    int pc = p.getPracticeChips();
    System.out.println("You rolled a " + diceVal);

    // compare die outcome to budget
    Banker b = Banker.getBanker();
    if (diceVal + pc >= s.getScene().getBudget()){
      s.setCounter();
      if(s.getCounters() == 0){
        System.out.println("Congrats! You rolled a number high enough to complete a shoot. Now, all the shoot counters have been removed and we will begin the payout process.");
        b.payout(s);
        s.setAvailable();
        s.removeScene();
        Gameboard gb = Gameboard.getGameboard();
        gb.removeAvailableScenes();
        System.out.println("There are " + gb.getAvailableScenes() + " remaining scenes on the board");
        System.out.println();

        s.setAvailable();

        return;
      } else {
        System.out.println("Congrats! You rolled a number high enough to complete a shoot. The Scene currently has " + s.getCounters() + " counters.");
        System.out.println("Paying leads...");
        for(int j = 0; j < s.getScene().getLeadPlayers().size(); j++){
          b.setCredits(2,p);
          System.out.println(s.getScene().getLeadPlayers().get(j).getName() + " was paid 2 credits.");
        }
        System.out.println("Paying extras...");
        for (int i = 0; i < s.getExtraPlayers().size(); i++){
          b.setCredits(1, p);
          b.setDollars(1, p);
          System.out.println(s.getExtraPlayers().get(i).getName() + " was paid 1 dollar and 1 credit.");
        }
        b.zeroPractice(p);
      }
    }else{
      System.out.println("You did not roll a number high enough. The Scene currently has " + s.getCounters() + " counters.");
      
      if (s.getExtraPlayers().size() > 0) {
        System.out.println("Paying extras...");
        for (int i = 0; i < s.getExtraPlayers().size(); i++){
          b.setDollars(1, s.getExtraPlayers().get(i));
          System.out.println(s.getExtraPlayers().get(i).getName() + " was paid 1 dollar.");
        }
      }
    }
}

  // Interface act logic (change player data)
  protected boolean act(Player p, int diceVal) {
    Banker b = Banker.getBanker();
    Set s = p.getSet();
    Scene sc = s.getScene();
    int budget = sc.getBudget();
    int pc = p.getPracticeChips();

    // remove later
    System.out.println("You rolled a " + diceVal);
    System.out.println("Budget is a " + budget);

    if (diceVal + pc >= budget) {
      s.setCounter();
      if (s.getCounters() == 0) {
        b.payout(s);
        s.setAvailable();
        s.removeScene();
        Gameboard gb = Gameboard.getGameboard();
        gb.removeAvailableScenes();
        
      } 
      
      else {
        for(int j = 0; j < s.getScene().getLeadPlayers().size(); j++){
          b.setCredits(2,p);
        }
        for (int i = 0; i < s.getExtraPlayers().size(); i++){
          b.setCredits(1, p);
          b.setDollars(1, p);
        }
        b.zeroPractice(p);
      }
      return true;
    } 
    if (s.getExtraPlayers().size() > 0) {
      for (int i = 0; i < s.getExtraPlayers().size(); i++){
        b.setDollars(1, s.getExtraPlayers().get(i));
      }
    }

    return false;
  }

  protected void rehearse(Set s, Player p) {
    Banker b = Banker.getBanker();
    Scanner in = new Scanner(System.in);

    if((p.getPracticeChips()) < (s.getScene().getBudget())){
      b.addPractice(p);
    } else{
      System.out.println("Your practice chips are equal to the budget of the Scene. You can no longer rehearse. Would you like to act or end your turn?");
      System.out.println("Please enter 'a' for act and 'e' for end turn");
      String userIn = in.nextLine();
      
      if ("a".equals(userIn)){
        act(p, s);
      }else if ("e".equals(userIn)){
        return;
      }else{
        System.out.println("Invalid Response.");
        System.out.println("Turn forfeited");
      }
    }
  }

  // Interface rehearse logic (change player data)
  protected void rehearse(Player p) {
    Banker b = Banker.getBanker();
    b.addPractice(p);

    System.out.println("Practice chips update");

  }

}