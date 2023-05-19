import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
//import java.awt.FlowLayout;
//import java.applet.Applet;

public class DeadwoodInterface {
  // singleton
  // observer to display

  // move, for loop for neighbors create menu item buttons
  // makeNeighborButtons(ArrayList Neighbors)
  // board is an icon

  public static void main(String args[]){
    
    //Creating the Frame
    JFrame frame = getFrame();

    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); //setting window dimensions
    frame.setBounds(100, 100, (int) (dim.getWidth()/1.2), (int) (dim.getHeight()/1.2));

    frame.setTitle("Welcome to Deadwood!");

    // String IMG_PATH = "required images/board.jpg";

    // try {
    //   BufferedImage img = ImageIO.read(new File(IMG_PATH));
    //   ImageIcon icon = new ImageIcon(img);
    //   JPanel jp = new JPanel();
    //   jp.add(icon);
    // } catch (IOException e) {
    //   e.printStackTrace();
    // }

    // IDEA: Beginning Image with a Start Game button that intializes setUpGame

    /* IDEA: How many players with drop down menu - setPlayers opens a scanner to 
             determine the number of players, should we turn it into a button or something? */ 

    // Board image
    JPanel imgPanel = new JPanel(new BorderLayout());
    JLabel imgLabel = new JLabel(new ImageIcon((new ImageIcon("required images/board.jpg").getImage().getScaledInstance(950, 625, Image.SCALE_AREA_AVERAGING))));
    //JLabel view = new JLabel(new ImageIcon("required images/board.jpg")); - old version
    //imgLabel.setSize(1,1); - old re-sizing
    imgPanel.add(imgLabel);

    Border border = BorderFactory.createLineBorder(Color.decode("#B17246"),4); // creating a border with deadwood theme orange color

    //Creating the panel on left side with action buttons
    JPanel buttonPanel = new JPanel(new BorderLayout()); // panel for buttons
    GridLayout buttonLayout = new GridLayout(3,2); //would also reccommend (6,1) 
    buttonPanel.setLayout(buttonLayout);
    buttonPanel.setBackground(Color.decode("#FFF5EC"));
    buttonPanel.setBorder(border);
    JButton upgrade = new JButton("Upgrade");
    // upgrade.setBorder(border); - border for buttons, if wanted
    JButton move = new JButton("Move");
    JButton act = new JButton("Act");
    JButton rehearse = new JButton("Rehearse");
    JButton choose = new JButton("Choose Role");
    JButton endTurn = new JButton("End Turn");
  
    // add action for button panel
    buttonPanel.add(move); //BorderLayout.NORTH - took out the direction for rn
    buttonPanel.add(choose); // BorderLayout.LINE_START
    buttonPanel.add(rehearse); // BorderLayout.EAST
    buttonPanel.add(act); //BorderLayout.SOUTH
    buttonPanel.add(upgrade); //BorderLayout.CENTER
    buttonPanel.add(endTurn); //BorderLayout.WEST 

    // JPanel secondaryColumn = new JPanel(new BorderLayout()); - additional column if needed
    // JButton testButton = new JButton("testButton");
    // secondaryColumn.add(testButton);

    //Creating a panel on bottom with player info 
    JPanel playerPanel = new JPanel(new BorderLayout());
    getPlayers(playerPanel, 7); //theoretically, we could call gb.getPlayers().getlength() - returns a player array
    //playerPanel.setBorder(border);
    /* 
    setPlayers(); - started to cause some issues when calling setUpGame. Wouldn't take in 4 as a parameter for number of players
    and kept iterating through asking for each player's name after already being entered. 

    */


    //Adding Components to the frame.
    frame.getContentPane().add(BorderLayout.SOUTH, playerPanel);
    frame.getContentPane().add(BorderLayout.WEST, buttonPanel);
    frame.getContentPane().add(BorderLayout.EAST, imgPanel);
    //frame.getContentPane().add(BorderLayout.CENTER, secondaryColumn); - if we want another column. 
    frame.setVisible(true);
  }

  public static JFrame getFrame() {
    //Creating the Frame
    JFrame frame = new JFrame("Deadwood");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(400, 400);

    return frame;
  }

  public static void getPlayers(JPanel playerPanel, int numPlayers) { 

    GridLayout playerLayout = new GridLayout(1,numPlayers); // (1, numPlayers)
    playerPanel.setLayout(playerLayout);

    // for the number of players, create a new Jlabel and display player stats
    for (int i = 1; i < numPlayers + 1; i++){
      JTextArea playerLabel = new JTextArea("Player "+ i + "\n Name: \n Rank: \n Dollars: \n Credits: \n"); //would list all player somewhere in here information.
      // IDEA: have displayPlayer() return a string with all player information
      playerPanel.add(playerLabel);
      //playerLabel.setBackground(Color.decode("#FFF5EC"));
    }

  }

  
} //DeadwoodInterface Bracket
