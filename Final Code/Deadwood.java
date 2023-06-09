import java.util.Scanner;

public class Deadwood {
    private static void displayRules() {
      System.out.println("RULES");
      System.out.println("Enter 'm' for move");
      System.out.println("Enter 'c' for choose a role");
      System.out.println("Enter 'a' for act");
      System.out.println("Enter 'r' for rehearse");
      System.out.println("Enter 'u' for upgrade (NOTE: Player must be located in the Office)");
      System.out.println("Enter 'e' for end turn");
      System.out.println("Enter 'end game' to end the game");
      System.out.println();
    }

    public static void main(String arg[]){
      boolean playing = true;
      String playerAction;
      Scanner deadwoodIn = new Scanner(System.in);

      while (playing) {
        System.out.println("Welcome to Deadwood!");
        Gameboard gb = Gameboard.getGameboard();
        
        System.out.println("Setting up game...");
        gb.setupGame();

        System.out.println("Ready to play...");
        System.out.println();

        Action a = new Action();
        while (gb.getDuration() > 0) {

          for (int i = 0; i < gb.getPlayers().length && gb.getAvailableScenes() > 1; i ++) {
            /* print all player locations */
            System.out.println("All player locations:");

            for (int j = 0; j < gb.getPlayers().length; j++) {
              System.out.println(gb.getPlayers()[j].getName() + " located in " + gb.getPlayers()[j].getSet().getName());
            }

            System.out.println();

            /* notify active player */
            System.out.println("It's " + gb.getPlayers()[i].getName() + "'s turn!");

            gb.getPlayers()[i].displayPlayer();
            System.out.println();
            displayRules();

            System.out.print("Choose an action: ");

            /* get user action */
            playerAction = deadwoodIn.nextLine();
            System.out.println();

            /* MOVE */
            if (playerAction.equals("m")) {
              /* player is not already working a role */
              if (gb.getPlayers()[i].getRole() == null) {
                Boolean choose = a.move(gb.getPlayers()[i].getSet(), gb.getPlayers()[i]);

                /* player moved into office */
                if (gb.getPlayers()[i].getSet().getName() == "office") {
                  System.out.print("[y or n] Do you want to upgrade your rank? ");
                  String userChoice = deadwoodIn.nextLine();
                  System.out.println();

                  boolean valid = false;

                  /* upgrade? */
                  while (!valid) {
                    if (userChoice.equals("y")) {
                      a.upgradeRank(gb.getPlayers()[i]);
                      valid = true;
                    } else if (userChoice.equals("n")) {
                      System.out.println("Turn ended.");
                      valid = true;
                    } else {
                      System.out.println("Invalid option. Try again");
                    }
                  }
                }

                /* take a role? */
                if (choose) {
                  a.chooseRole(gb.getPlayers()[i].getSet(), gb.getPlayers()[i]);
                }
                
              } else {
                System.out.println("Invalid action! Player cannot move while working a role.");
                System.out.println("Turn forfeited.");
              }
            } 
            /* CHOSE A ROLE */
            else if (playerAction.equals("c")) {
              /* player is not already working and the scene is available */
              if (gb.getPlayers()[i].getRole() == null && gb.getPlayers()[i].getSet().getScene() != null) {
                a.chooseRole(gb.getPlayers()[i].getSet(), gb.getPlayers()[i]);
              } else {
                System.out.println("Invalid action! Player cannot chose a new role.");
                System.out.println("Turn forfeited.");
              }
            } 
            /* ACT */
            else if (playerAction.equals("a")) {
              /* player is currently working a role */
              if (gb.getPlayers()[i].getRole() != null) {
                a.act(gb.getPlayers()[i], gb.getPlayers()[i].getSet());
              } else {
                System.out.println("Invalid action! Player cannot act without a role.");
                System.out.println("Turn forfeited.");
              }

            } 
            /* REHEARSE */
            else if (playerAction.equals("r")) {
              /* player is currently working a role */
              if (gb.getPlayers()[i].getRole() != null) {
                a.rehearse(gb.getPlayers()[i].getSet(), gb.getPlayers()[i]);
              } else {
                System.out.println("Invalid action! Player cannot rehearse without a role.");
                System.out.println("Turn forfeited.");
              }
            } 
            /* UPGRADE */
            else if (playerAction.equals("u")) {
              a.upgradeRank(gb.getPlayers()[i]);
            } 
            /* END TURN */
            else if (playerAction.equals("e")) {
              System.out.println("Turn ended.");
              System.out.println();
            } 
            /* END GAME */
            else if (playerAction.equals("end game")) {
              /* complete game */
              gb.totalScores();
              System.out.println("Exiting...");
              /* exit game */
              return;
            } 
            /* INVALID INPUT */
            else {
              System.out.println("Invalid action.");
              System.out.println("Turn forfeited.");            
            }
          }

          /* trigger End of Day */
          if (gb.getAvailableScenes() == 1) {
            gb.endOfDay();
          }
        }
        System.out.println("Thanks for playing!");

        Boolean valid = false;

        // play again?
        while (!valid) {
          System.out.print("[y or n] Want to play again?");
          String userChoice = deadwoodIn.nextLine();

          if (userChoice.equals("y")) {
            System.out.println("New game starting...");
            valid = true;
            playing = true;
          } else if (userChoice.equals("n")) {
            System.out.println("Exiting...");
            valid = true;
            playing = false;
          } else {
            System.out.println("Invalid option. Try again");
          }

        }
      }
    }
}
