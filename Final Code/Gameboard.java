import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import javax.swing.*;

import javax.swing.ImageIcon;

public class Gameboard {
  private int duration = 4; 
  private ArrayList<Scene> deck = new ArrayList<>();
  private ArrayList<Set> board = new ArrayList<>();
  private Player[] playerOrder;
  private int numPlayers;
  private static int availableScenes;
  

  private static Gameboard gameBoardInstance = new Gameboard();
  
  public Gameboard(){}

  public static Gameboard getGameboard(){
    return gameBoardInstance;
  }

  /* GETTERS */
  public int getDuration() {
    return duration;
  }

  protected ArrayList<Set> getBoard() {
    return this.board;
  }

  protected ArrayList<Scene> getDeck() {
    return this.deck;

  }

  public int getAvailableScenes() {
    return availableScenes;
  }

  public Player[] getPlayers() {
    return playerOrder;
  }
  
  /* SETTERS */
  // works and is used
  private void setDeck() {
    Parser parsing = new Parser();
    this.deck = parsing.parseDeck();
    Collections.shuffle(deck);
  }

  // work and is used
  private void setBoard() {
    Parser parsing = new Parser();
    this.board = parsing.parseBoard();
    
  }

  // protected Player[] setPlayers() {
  //   Scanner in = new Scanner(System.in);
    
  //   // Ask user how many players
  //   System.out.print("How many players? ");
  //   String userInput = in.nextLine();
  //   try { 
  //     Integer.parseInt(userInput); 
  //   } catch(NumberFormatException e) {
  //     System.out.println("Invalid input for number of players");
  //     return null; 
  //   } catch(NullPointerException e) {
  //     System.out.println("Invalid input for number of players");
  //     return null;
  //   }
    
  //   numPlayers = Integer.parseInt(userInput);
    
  //   Set location = board.get(board.size()-2);
  //   int rank = 1;
  //   int credits = 0;
    
  //   //verify between 2-8
  //   if (numPlayers == 2 || numPlayers == 3) {
  //     duration = 3;
  //   } else if (numPlayers == 5) {
  //     credits = 2;
  //   } else if (numPlayers == 6) {
  //     credits = 4;
  //   } else if (numPlayers == 7 || numPlayers == 8) {
  //     rank = 2;
  //   } else {
  //     //print error
  //     System.out.println("Error: " + numPlayers + " invalid. 2-8 players allowed.");
  //     setPlayers();
  //   }
    
  //   playerOrder = new Player[numPlayers];
    
  //   for (int i = 0; i < playerOrder.length; i++) {
  //     System.out.print("Player " + (i+1) + " please enter your name: ");
  //     String name = in.nextLine();
  //     Player player = new Player(name, 0, credits, 0, rank, location);
  //     playerOrder[i] = player;
  //     player.displayPlayer();
  //   }

  //   return playerOrder;
  // }

  // int num is Jcombobox output
  protected Player[] setPlayers(int num) {
    numPlayers = num;
    
    Set location = board.get(board.size()-2);
    int rank = 1;
    int credits = 0;
    String[] colors = {"b", "c", "g", "o", "p", "r" , "v", "y", "w"};
    
    //verify between 2-8
    if (numPlayers == 2 || numPlayers == 3) {
      duration = 3;
    } else if (numPlayers == 5) {
      credits = 2;
    } else if (numPlayers == 6) {
      credits = 4;
    } else if (numPlayers == 7 || numPlayers == 8) {
      rank = 2;
    } 
    playerOrder = new Player[numPlayers];

    for (int i = 0; i < playerOrder.length; i++) {
      String name = "Player " + Integer.toString(i);
      Player player = new Player(name, 0, credits, 0, rank, location);
      playerOrder[i] = player;

      // String path = "required_images/dice/b" + Integer.toString(i);
      // playerToken[i] =  required_images/dice/b

      
      player.setColor(colors[i]);
      String path = "/required_images/dice/" + player.getColor();

      ImageIcon[] diceImgs = {new ImageIcon(path + "1"), new ImageIcon(path + "2"), new ImageIcon(path + "3"), new ImageIcon(path + "4"), new ImageIcon(path + "5")};
      player.setDiceImgs(diceImgs);
      player.setPlayerDieIcon();
    }
    
    // setting player names to Player 1 ... Player n

    System.out.println("Created" + numPlayers +"player array");
    return playerOrder;
  }
  
  private void setGame() {
    availableScenes = 0;
    for (int i = 0; i < board.size()-2; i++) {
      getBoard().get(i).setCurScene(getDeck().get(i));
      //NEW
      getBoard().get(i).setLeadCords();
      // s.setCurScene(deck.get(i));
      deck.remove(i);
      availableScenes++;
    }
  }

  // working and used
  protected void setNeighborSets() {
    for (int i = 0; i < board.size(); i++) {
      board.get(i).parseNeighbors(getBoard());
    }
  }

  protected void removeAvailableScenes() {
    availableScenes--;
  }
  
  /* METHODS */
  protected void endOfDay() {
    duration--;
    if (duration == 0){
      totalScores();
    } else {
      // print end of day
      System.out.println("The day has ended. The board is reset and players have been returned to the trailers. " + getDuration() + " days remaining.");
      setGame(); 
      
      for (int m = 0; m < numPlayers; m++){
        playerOrder[m].setSet(board.get(board.size()-2));
      }
    }
  }

  protected void endDay() {
    duration--;
    if (duration == 0){
      getWinner();
    } else {
      // print end of day
      System.out.println("The day has ended. The board is reset and players have been returned to the trailers. " + getDuration() + " days remaining.");
      setGame(); 
      
      for (int m = 0; m < numPlayers; m++){
        playerOrder[m].setSet(board.get(board.size()-2));
      }
    }
  }
  
  //triggers when duration is zero
  protected void totalScores() {
    System.out.println("We have reached the end of the game. It is time to total the scores!");
    int[] scores = new int[numPlayers];
    int max = 0;
    Player winner = null;
    for (int i = 0; i < numPlayers; i++) {
      scores[i] = playerOrder[i].getDollars();
      scores[i] += playerOrder[i].getCredits();
      scores[i] += 5 * playerOrder[i].getRank();
        
      if (scores[i] >= max) {
        max = scores[i];
        winner = playerOrder[i];
      }
    }
    //declare winnner
    System.out.println("Congrats " + winner.getName() + " you won with " + max + " points!");
  }

  //triggers when duration is zero
  protected Player getWinner() {
    JOptionPane.showMessageDialog(null, "End of game!");
    int[] scores = new int[numPlayers];
    int max = 0;
    Player winner = null;
    for (int i = 0; i < numPlayers; i++) {
      scores[i] = playerOrder[i].getDollars();
      scores[i] += playerOrder[i].getCredits();
      scores[i] += 5 * playerOrder[i].getRank();
        
      if (scores[i] >= max) {
        max = scores[i];
        winner = playerOrder[i];
      }
    }
    //declare winnner
    System.out.println("Congrats " + winner.getName() + " you won with " + max + " points!");
    JOptionPane.showMessageDialog(null, "Congrats " + winner.getName() + " you won with " + max + " points!");

    return winner;
  }

  public void setupGame() {
    System.out.println("Game setup started");

    setBoard();
    setDeck();
    setNeighborSets();
    setGame();

    System.out.println("Game setup completed");
  }

  public void displayBoard() {
    for (int i = 0; i < board.size(); i++) {
      board.get(i).displaySet();
    }
  }

}
