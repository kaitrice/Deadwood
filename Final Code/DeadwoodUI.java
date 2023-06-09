/*

 Deadwood GUI helper file
 Author: Moushumi Sharmin
 This file shows how to create a simple GUI using Java Swing and Awt Library
 Classes Used: JFrame, JLabel, JButton, JLayeredPane

*/

//Layers:
// 0 - board 
// 1 - card backs
// 2 - menu (buttons - right)
// 3 - player info (bottom)
// 4 - card fronts
// 5 - shoot counters
// 6 - completed x counters
// 7+ - all player dice

import java.awt.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import javax.sound.midi.SysexMessage;

import java.awt.event.*;
import javax.swing.JOptionPane;
import java.util.ArrayList;

public class DeadwoodUI extends JFrame {

  // JPanel
  JPanel allPlayersLabel;

  // JLabels
  JLabel boardlabel;
  JLabel cardlabel;
  JLabel cardlabel1;
  JLabel cardlabel2;
  static JLabel playerlabel;
  JLabel mLabel;

  // JTextAreas
  JTextArea pickRoom;
  
  //JButtons
  JButton bAct;
  JButton bRehearse;
  static JButton bMove;
  JButton bChoose;
  JButton bUpgrade;
  JButton bEnd;
  JButton bSubmit;
  JButton moveSubmit = new JButton("Submit");

  // JComboBoxes
  JComboBox<String> chooseNeighbor;
  
  // JLayered Pane
  static JLayeredPane bPane;

  // Instances
  Gameboard gameboard = Gameboard.getGameboard();
  Action a = new Action();

  //varaibles 
  static Player[] players;
  static int numPlayers;
  static Player curplayer;
  int neighborChosen;
  
  // Constructor
  public DeadwoodUI() {
      
       // Set the title of the JFrame
      super("Deadwood");
       // Set the exit option for the JFrame
      setDefaultCloseOperation(EXIT_ON_CLOSE);

      gameboard.setupGame();
      
       // Create the JLayeredPane to hold the display, cards, dice and buttons
      bPane = getLayeredPane();
    
       // Create the deadwood board
      boardlabel = new JLabel();
      ImageIcon icon =  new ImageIcon("required_images/board.jpg");
      boardlabel.setIcon(icon); 
      boardlabel.setBounds(0,0,icon.getIconWidth(),icon.getIconHeight());
      
       // Add the board to the lowest layer
      bPane.add(boardlabel, new Integer(0));
      
       // Set the size of the GUI
      setSize(icon.getIconWidth()+200,icon.getIconHeight());

       // Add the scene back card to each set
      ArrayList<Set> setList = gameboard.getBoard();
      for(int b = 0; b < setList.size()-2; b++){
        JLabel cardBack = new JLabel();
        ImageIcon back =  new ImageIcon("required_images/CardBack-small.jpg");
        cardBack.setIcon(back);
        cardBack.setBounds(setList.get(b).getX(),setList.get(b).getY(),back.getIconWidth()+2,back.getIconHeight());
        cardBack.setOpaque(true);
         // Add the card to the lower layer
        bPane.add(cardBack, new Integer(1));

        // JLabel cardFront = new JLabel();
        // ImageIcon front =  new ImageIcon("required_images/cards/01.png");
        // // ImageIcon front = setList.get(b).getScene().getCardImage();
        // cardFront.setIcon(front);
        // cardFront.setBounds(setList.get(b).getX(),setList.get(b).getY(),front.getIconWidth()+2,front.getIconHeight());
        // cardFront.setOpaque(true);
        //  // Add the card to the lower layer
        // bPane.add(cardFront, new Integer(3));
      }
    
      // // Add a dice to represent a player. 
      // // Role for Crusty the prospector. The x and y co-ordiantes are taken from Board.xml file
      // playerlabel = new JLabel();
      // ImageIcon pIcon = new ImageIcon("required_images/dice/b1.png");
      // playerlabel.setIcon(pIcon);
      // playerlabel.setBounds(0,450,pIcon.getIconWidth(),pIcon.getIconHeight());  
      // //playerlabel.setBounds(114,227,46,46);
      // playerlabel.setVisible(true);
      // bPane.add(playerlabel,new Integer(3));
      
       // Create the Menu for action buttons
       mLabel = new JLabel("MENU");
       mLabel.setBounds(icon.getIconWidth()+40,0,100,20);
       bPane.add(mLabel,new Integer(2));

      //  System.out.println("ICON WIDTH: " + icon.getIconWidth());

       // Create Action buttons
       bAct = new JButton("ACT");
       bAct.setBackground(Color.white);
       bAct.setBounds(icon.getIconWidth()+10, 30,100, 20);
       bAct.addMouseListener(new boardMouseListener());
       
       bRehearse = new JButton("REHEARSE");
       bRehearse.setBackground(Color.white);
       bRehearse.setBounds(icon.getIconWidth()+10,60,100, 20);
       bRehearse.addMouseListener(new boardMouseListener());
       
       bMove = new JButton("MOVE");
       bMove.setBackground(Color.white);
       bMove.setBounds(icon.getIconWidth()+10,90,100, 20);
       bMove.addMouseListener(new boardMouseListener());

       bChoose = new JButton("CHOOSE");
       bChoose.setBackground(Color.white);
       bChoose.setBounds(icon.getIconWidth()+10,120,100, 20);
       bChoose.addMouseListener(new boardMouseListener());

       bUpgrade = new JButton("UPGRADE");
       bUpgrade.setBackground(Color.white);
       bUpgrade.setBounds(icon.getIconWidth()+10,150,100, 20);
       bUpgrade.addMouseListener(new boardMouseListener());

       bEnd = new JButton("END");
       bEnd.setBackground(Color.white);
       bEnd.setBounds(icon.getIconWidth()+10,180,100, 20);
       bEnd.addMouseListener(new boardMouseListener());

       // Place the action buttons in the top layer
       bPane.add(bAct, new Integer(2));
       bPane.add(bRehearse, new Integer(2));
       bPane.add(bMove, new Integer(2));
       bPane.add(bChoose, new Integer(2));
       bPane.add(bUpgrade, new Integer(2));
       bPane.add(bEnd, new Integer(2));

      //  allPlayersLabel = new JPanel();
      //  allPlayersLabel.setBounds(0,icon.getIconHeight()+20,100,20);
      //  bPane.add(allPlayersLabel,new Integer(3));
  }
  
  // This class implements Mouse Events
  class boardMouseListener implements MouseListener{
  
      // Code for the different button clicks
      public void mouseClicked(MouseEvent e) {  


        /**
         * If we choose a role or move
         *  then we need to call moveDie
         *  to update player location but in player class & visually
         */

        if (e.getSource()== bAct){
            //playerlabel.setVisible(true);
            System.out.println("Acting is Selected\n");
        }
        else if (e.getSource()== bRehearse){
            System.out.println("Rehearse is Selected\n");
        }
        
        else if (e.getSource()== bMove){

          System.out.println("The move button was pressed! \n");

          String[] neighbors = curplayer.getSet().getNeighborString();
          chooseNeighbor = new JComboBox<String> (neighbors);
          pickRoom = new JTextArea("Select which \n set you \n would like \n to move to \n then click \n 'Submit'");
          moveSubmit = new JButton("Submit");

          neighborChosen = 1;
          chooseNeighbor.setSelectedIndex(0); // default 2 players

          bPane.remove(mLabel);
          bPane.remove(bMove);
          bPane.remove(bAct);
          bPane.remove(bRehearse);
          bPane.remove(bUpgrade);
          bPane.remove(bChoose);
          bPane.remove(bEnd);

          pickRoom.setBackground(Color.white);
          pickRoom.setBounds(1240,0,100, 125);  
          pickRoom.setEnabled(false);
          bPane.add(pickRoom, new Integer(2));
          
          chooseNeighbor.setBackground(Color.white);
          chooseNeighbor.setBounds(1210,200,100, 200);
          bPane.add(chooseNeighbor, new Integer(2));

          Component[] comps = chooseNeighbor.getComponents();
          for(int i = 0; i < comps.length; i++){
          int n = i;
          comps[i].addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
            System.out.println("clicked");
            neighborChosen = n + 1;

            moveSubmit.setBackground(Color.white);
            moveSubmit.setBounds(1210, 600,100, 20);
            bPane.add(moveSubmit, new Integer(2));
            moveSubmit.addMouseListener(new boardMouseListener());

            bPane.revalidate();
            bPane.repaint();
            bPane.setVisible(true);
            }
          });
          }

        }  
        else if (e.getSource()== bChoose){
          System.out.println("Choose is Selected\n");
        } 
        else if (e.getSource()== bUpgrade){
          System.out.println("Upgrade is Selected\n");
        }       
        else if (e.getSource()== bEnd){
          System.out.println("End is Selected\n");

        } 
        else if (e.getSource() == moveSubmit){

          System.out.println("Submit Button Has been Pressed \n");

          bPane.remove(moveSubmit);
          bPane.remove(chooseNeighbor);
          bPane.remove(pickRoom);


          if (!(a.move(curplayer, neighborChosen))) {
            System.out.println("Move Error!");
          }

          displayPlayerInfo();

          if(!curplayer.getSet().getShown()){ //the scene card has not been flipped over yet
            curplayer.getSet().setShown(true);

            System.out.println("SCENE: " + curplayer.getSet().getScene().getName());

            JLabel cardFront = new JLabel(); // flipping the scene card
            // ImageIcon front =  curplayer.getSet().getScene().getCardImage();
            // ImageIcon front =  new ImageIcon("required_images/cards/25.png");
            String path = curplayer.getSet().getScene().getCardImage();
            ImageIcon front =  new ImageIcon(path);

            cardFront.setIcon(front);
            cardFront.setBounds(curplayer.getSet().getX(),curplayer.getSet().getY(),front.getIconWidth()+2,front.getIconHeight());
            cardFront.setOpaque(true);
            bPane.add(cardFront, new Integer(4)); // Add the card to the scene card layer
          }

          moveDie(curplayer, curplayer.getSet().getX(), curplayer.getSet().getY());
        
          bChoose.setBackground(Color.white);
          bChoose.setBounds(1210,180,100, 20);
          bPane.add(bChoose, new Integer(2));

          bEnd.setBackground(Color.white);
          bEnd.setBounds(1210,220,100, 20);
          bPane.add(bEnd, new Integer(2));

          bPane.revalidate();
          bPane.repaint();

          // displayPlayerInfo();

        }
      }
      public void mousePressed(MouseEvent e) {
        
      }
      public void mouseReleased(MouseEvent e) {
      }
      public void mouseEntered(MouseEvent e) {
      }
      public void mouseExited(MouseEvent e) {
      }
  }

  public static void displayPlayerInfo() { 
    int x = 0;
    System.out.println("displaying player info");

    // GridLayout playerLayout = new GridLayout(1,numPlayers); // (1, numPlayers)
    // allPlayersLabel.setLayout(playerLayout);

    // for the number of players, create a new Jlabel and display player stats
    for (int i = 1; i < Gameboard.getGameboard().getPlayers().length + 1; i++){
      JTextArea pL = new JTextArea("Player "+ i + "\n Rank: " + players[i-1].getRank() + "\n Dollars: " + players[i-1].getDollars() + "\n Credits: " + players[i-1].getCredits() + "\n Location: " + players[i-1].getSet().getName()); //would list all player somewhere in here information.
      // Gameboard.getGameboard().getPlayers()[i-1].displayPlayer();
      if (i == 1){
        pL.setBackground(Color.decode("#047cf8"));
        // players[i-1].setColor("b");
      } else if (i == 2){
        pL.setBackground(Color.decode("#00fbfb"));
        // players[i-1].setColor("c"); 
      } else if (i == 3){ 
        pL.setBackground(Color.decode("#33ff32"));
        // players[i-1].setColor("g");
      } else if (i == 4){
        pL.setBackground(Color.decode("#ff8002"));
        // players[i-1].setColor("o");
      } else if (i == 5) {
        pL.setBackground(Color.decode("#f0c0c0"));
        // players[i-1].setColor("p");
      }
      else if (i == 6) {
        pL.setBackground(Color.decode("#f90400"));
        // players[i-1].setColor("r");
      }
      else if (i == 7) {
        pL.setBackground(Color.decode("#cc00cc"));
        // players[i-1].setColor("v");
      }
      else if (i == 8) {
        pL.setBackground(Color.decode("#feff00"));
        // players[i-1].setColor("y");
      }

      pL.setEditable(false);
      pL.setBounds(x,900,150,100);
      x += 150;
      
      bPane.add(pL,new Integer(3));
      bPane.setVisible(true);
    }
  }

  public static void initDiceLoc() {
    int gap = 0;
    int gap2 = 0;
    for(int b = 0; b < Gameboard.getGameboard().getPlayers().length; b++){
      JLabel pD = new JLabel();
      // System.out.println("players " + b);
      String diceLoc = "required_images/dice/" + players[b].getColor() + Integer.toString(players[b].getRank()) + ".png";
      // System.out.println(diceLoc);
      ImageIcon diceImg =  new ImageIcon(diceLoc);
      pD.setIcon(diceImg);
      if (b > 3) {
        pD.setBounds(players[b].getSet().getX() + gap2,players[b].getSet().getY()+(diceImg.getIconHeight()+2),diceImg.getIconWidth()+2,diceImg.getIconHeight());
        gap2 += diceImg.getIconWidth()+2;
      } else {
        pD.setBounds(players[b].getSet().getX() + gap,players[b].getSet().getY(),diceImg.getIconWidth()+2,diceImg.getIconHeight());
      }
      gap += diceImg.getIconWidth()+2;
      pD.setOpaque(true);
      bPane.add(pD, new Integer(7 + b));
    }
  }

  public static void moveDie(Player p, int newX, int newY) {
    int nPlayer = Integer.parseInt(p.getName().substring(p.getName().length() - 1));
    System.out.println(nPlayer);
    
    bPane.remove(7+nPlayer); //p.getPlayerDie()

    JLabel pD = new JLabel();
    String diceLoc = "required_images/dice/" + p.getColor() + Integer.toString(p.getRank()) + ".png";
    ImageIcon diceImg =  new ImageIcon(diceLoc);
    pD.setIcon(diceImg);

    int gap = (diceImg.getIconWidth() * nPlayer) + (2 * nPlayer);
    
    pD.setBounds(newX+gap,newY+125,diceImg.getIconWidth()+2,diceImg.getIconHeight());
    p.setPXandY(newX, newY);
    bPane.add(pD, new Integer(7+nPlayer));

  }

  // places all shoot counters on the baord. 
  public static void initShootCounters() {
    Gameboard gb = Gameboard.getGameboard();
    ArrayList<Set> sets = gb.getBoard();
    for (int i = 0; i < sets.size()-2; i++) {
      //int c = sets.get(i).getCounters();
      int[][] counterLoc = sets.get(i).getCounterLocs();
      for (int j = 0; j < counterLoc.length; j++) {
        int x = counterLoc[j][0];
        int y = counterLoc[j][1];
        // counters  1   2   3
        //    index (0) (1) (2)
        //  x  (0)   *   *   *
        //  y  (1)   *   *   *
        JLabel sD = new JLabel();
        ImageIcon shootImg =  new ImageIcon("required_images/shot.png");
        sD.setIcon(shootImg);
        sD.setBounds(x,y,shootImg.getIconWidth()+2,shootImg.getIconHeight());
        sD.setOpaque(true);
        bPane.add(sD, new Integer(5));
      }

    } 
  }

  // places all shoot counters on the baord. 
  public static void testRoleSpaces() {
    Gameboard gb = Gameboard.getGameboard();
    ArrayList<Set> sets = gb.getBoard();
    for (int i = 0; i < sets.size()-2; i++) {
      ArrayList<Role> roles = sets.get(i).getExtraAvailable();
      for (int j = 0; j < roles.size(); j++) {
        JLabel sD = new JLabel();
        ImageIcon shootImg =  new ImageIcon("required_images/dice/b1.png");
        sD.setIcon(shootImg);
        sD.setBounds(roles.get(j).getRoleX(),roles.get(j).getRoleY(),shootImg.getIconWidth()+2,shootImg.getIconHeight());
        sD.setOpaque(true);
        bPane.add(sD, new Integer(5));
      }

      ArrayList<Role> leads = sets.get(i).getScene().getLeadAvailable();
      for (int j = 0; j < leads.size(); j++) {
        JLabel sD = new JLabel();
        ImageIcon shootImg =  new ImageIcon("required_images/dice/w1.png");
        sD.setIcon(shootImg);
        sD.setBounds(leads.get(j).getRoleX(),leads.get(j).getRoleY(),shootImg.getIconWidth()+2,shootImg.getIconHeight());
        sD.setOpaque(true);
        bPane.add(sD, new Integer(5));
      }
    }
  }


  public static void main(String[] args) {
    
    Gameboard gb = Gameboard.getGameboard();
    DeadwoodUI board = new DeadwoodUI();
    board.setVisible(true);

    String nPlayers = JOptionPane.showInputDialog(board, "How many players?"); 
    numPlayers = Integer.parseInt(nPlayers);

    if(numPlayers < 2){
      JOptionPane.showMessageDialog(board, "You entered an invalid number of players! You must have between 2 and 8 players. We have bypassed this issue by setting your player number to 2. Thanks for playing!", "Oh No!", JOptionPane.PLAIN_MESSAGE);
      numPlayers = 2;
    }
    if(numPlayers > 8){
      JOptionPane.showMessageDialog(board, "You entered an invalid number of players! You must have between 2 and 8 players. We have bypassed this issue by setting your player number to 8. Thanks for playing!", "Oh No!", JOptionPane.PLAIN_MESSAGE);
      numPlayers = 8;
    }

    players = gb.setPlayers(numPlayers);

    displayPlayerInfo();
    initDiceLoc();
    // gb.setupGame();
    initShootCounters();

    // boolean playing = true;
    // while (playing) {
    //   for (int i = 0; i < numPlayers && gb.getDuration() > 0; i++) {
    //     JOptionPane.showMessageDialog(null, "Player " + (i+1) + " your turn!");
    //   }
    // } 
    

    System.out.println(players.length);

    System.out.println("Game has been set up, lets now test for the move button");
    curplayer = players[0];

    System.out.println(curplayer.getSet().getName());
    // testRoleSpaces();

    //  bPane.add(cardlabel, new Integer(3));

    //     shootCounter.setBounds(140,10,shoot.getIconWidth()+2,shoot.getIconHeight());


    
    // Take input from the user about number of players
    // String nPlayers;
    // int numPlayers = 0;

    // System.out.println("# players " + players.length);
    // displayPlayerInfo(players.length , bPane);
    
  }
}
