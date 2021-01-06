//COMP 1006A/1406A Assignment 3 Problem 5
//By Alexander Kuhn, ID# 101023154, July 11, 2016
//Purpose: Models a game of Crazy Eights with the rules specified in the rubric
//It contains methods to create players, to have those players draw, to have a game play out turn by turn, and to use all those methods to create a game
//Inputs: One integer argument between 2 and 7 (inclusive) specifying how many players are playing
//Outputs: Prints all the moves made in the game, separated out into turns
//Shows the card players need to play onto, shows each player's hand before drawing a card, shows the card the player plays, and shows their hands after playing or drawing a card
//Also displays what happens in special cases (if a 2/4/8/Joker gets played
public class Game {
  public static Hand[] playerHands;
  public static Deck currentDeck;
  public static MyCard[] discardPile;
  
  public static Hand[] playerBuilder(int numPlayers){
    //Purpose: Creates players with empty hands
    //Inputs: An integer specifying the size of the array it returns
    //Returns: An array of players with empty hands
    playerHands = new Hand[numPlayers];
    for (int i = 0; i < playerHands.length; i += 1) {
        playerHands[i] = new Hand(new Card[]{null, null, null, null, null, null, null, null});
      }
    return playerHands;
  }
  
  public static MyCard[] startDrawing(Hand[] playerHands) {
    int whichHand = 0;
    //Purpose: Models "Turn 0", i.e. when everyone is just drawing cards from the deck
    //The criteria mandate that each player draws one card at a time and that everyone draws to 8
    //So these loops give each player a card in cell 0, then cell 1, etc., until everyone has 8
    //It also places the first card in the discard pile
    //Inputs: An array of players with empty hands
    //Returns: The first card in the discard pile
    //Side Effects: Once it's done giving players cards, it prints each player's current hand as a single line to the console
    //It also fills the hand of each player in the array
    while (whichHand < 8) {
      for (int i = 0; i < playerHands.length; i += 1) {
        playerHands[i].cards[whichHand] = currentDeck.pop();
      }
    whichHand += 1;
    }
    for (int x = 0; x < playerHands.length; x += 1) {
      System.out.print("Player " + (x + 1) + "'s Opening Hand: ");
      for (int y = 0; y < playerHands[x].cards.length; y += 1) { 
         System.out.print(playerHands[x].cards[y] + " ");
      }
      System.out.println("");
    }
    
    MyCard[] initialPile = new MyCard[]{MyCard.createMyCard(currentDeck.pop())};
    while (initialPile[0].rank == 1) {
      System.out.println("Initial draw was a Joker. Drawing again.");
      initialPile = new MyCard[]{MyCard.createMyCard(currentDeck.pop())};
    }
    
    System.out.println("");
    return initialPile;
  }
  
  public static void executeTurn() {
    //Purpose: Handles everything involved in a game of Crazy Eights
    //Models each player's turn, makes them draw if they don't have a valid move, checks to see if the game is over, determines winner
    //Inputs: Uses a shuffled deck, a group of players with filled hands, and a discard pile that retains whatever the last card played was
    //Outputs: Prints everything that happens in the game
    //Side Effects: Runs through the deck and players' hands - as the game goes on, fewer and fewer Card objects exist in both
    Card attemptedPlay;
    //attemptedPlay is the card the player wants to play
    //if it's null, then the player can't play anything, and needs to draw instead
    int gameIsOver = 0;
    int turnCounter = 0;
    //Only necessary for neatly displaying turns
    double lowestScore = 0.0;
    int winningPlayer = 0;
    //The player with the lowest score wins - lowestScore retains whatever the lowest score is, and winningPlayer retains who has that score
    MyCard lastCardOnPile = discardPile[0];
    //This is necessary so a Joker that's been played knows what it needs to copy
    
    int ruleOfTwo = 0;
    int ruleOfFour = 0;
    //These are declared to be equal to 1 if a 2 or 4 has been played
    //They let the game know if it needs to execute a 'special' turn, and if it does, which one it needs to execute
    
    while ((currentDeck.Cards.length > 0) && (gameIsOver != 1)) {

      for (int i = 0; i < playerHands.length; i += 1) {
        System.out.println("TURN " + (turnCounter + 1));
        
        if (ruleOfFour == 1){
          System.out.println("Last card was a Four! Player " + (i + 1) + " skips a turn!");
          ruleOfFour = 0;
          turnCounter += 1;
          System.out.println("");
          continue;
        }
        //This prevents any of the loop from executing if the last card played was a 4, because that makes the next player skip a turn
        
        if (ruleOfTwo == 1){
          System.out.println("Last card was a Two! Player " + (i + 1) + " draws 2 cards and skips a turn!");
          if (currentDeck.Cards.length > 0) {
            playerHands[i].getCard(new Card[]{currentDeck.pop()});
            if (currentDeck.Cards.length > 0) {
              playerHands[i].getCard(new Card[]{currentDeck.pop()});
            }
          }
          
          if (currentDeck.Cards.length == 0){
            gameIsOver = 1;
            for (int y = 0; y < playerHands.length; y += 1){
              double playerScore = 0;
              for (int x = 0; x < playerHands[y].cards.length; x += 1){
                playerScore += MyCard.createMyCard(playerHands[y].cards[x]).rank;
              }
              if (lowestScore == 0.0) {
                lowestScore = playerScore;
                winningPlayer = 1 + y;
              }
              else if (playerScore <= lowestScore) {
                lowestScore = playerScore;
                winningPlayer = 1 + y;
              }
            }
            System.out.println("Game over, deck depleted! Player " + winningPlayer + " wins! Scored " + lowestScore);
            break;
          }
          System.out.print("Player " + (i + 1) + "'s Hand: ");
          for (int y = 0; y < playerHands[i].cards.length; y += 1) { 
            System.out.print(playerHands[i].cards[y] + " ");
          }
          System.out.println("- " + currentDeck.Cards.length + " cards remain in the deck.");
          ruleOfTwo = 0;
          turnCounter += 1;
          System.out.println("");
          continue;
        }
        //This draws two cards for the current player if the last card played was a 2, which means it also has to check if that depletes the deck and ends the game
        //If it does, it tabulates each player's score and declares a winner; ordinarily, a player who ended the game this way but drew a valid card would get to play it before scores were calculated
        //However, because of 2's effect, in this particular bit of logic they don't get to play anything
        //Otherwise, it just draws two cards and skips the rest of the turn
        
        System.out.println("Top of the discard pile: " + discardPile[0]);
        System.out.print("Player " + (i + 1) + "'s Hand: ");
        for (int y = 0; y < playerHands[i].cards.length; y += 1) { 
          System.out.print(playerHands[i].cards[y] + " ");
        }
        System.out.println("");
        attemptedPlay = (playerHands[i].playCard(discardPile));
        //This prints the state of the game before any moves are made - what the current player's hand looks like, and the card in the discard pile they need to play around
        
        while (attemptedPlay == null) {
          if (currentDeck.Cards.length > 0) {
            playerHands[i].getCard(new Card[]{currentDeck.pop()});
            System.out.println("Player " + (i + 1) + " has no valid moves and draws: " + currentDeck.Cards.length + " cards remain in the deck.");
            System.out.print("Player " + (i + 1) + "'s Hand: ");
            for (int y = 0; y < playerHands[i].cards.length; y += 1) { 
              System.out.print(playerHands[i].cards[y] + " ");
            }
            System.out.println("");
          }
          else if (currentDeck.Cards.length == 0){
            gameIsOver = 1;
            for (int y = 0; y < playerHands.length; y += 1){
              double playerScore = 0;
              for (int x = 0; x < playerHands[y].cards.length; x += 1){
                playerScore += MyCard.createMyCard(playerHands[y].cards[x]).rank;
              }
              if (lowestScore == 0.0) {
                lowestScore = playerScore;
                winningPlayer = 1 + y;
              }
              else if (playerScore <= lowestScore) {
                lowestScore = playerScore;
                winningPlayer = 1 + y;
              }
            }
            System.out.println("Game over, deck depleted! Player " + winningPlayer + " wins! Scored " + lowestScore);
            break;
          }
          attemptedPlay = (playerHands[i].playCard(discardPile));
        }
        //This entire loop is only used if the current player can't play a card; if so, it makes them keep drawing cards until the deck runs dry or they have a valid play
        //If they depleted the deck but have a valid play, they get to play that one card before the game ends
        //As before, a depleted deck here will result in the game adding up the scores and declaring a winner
        //Note that in the event of a tie, the player with the highest number will win
        //So if Player 1 and Player 3 both have a score of 19 when this loop finishes, it will say Player 3 won
        
        if (gameIsOver == 1) {
          break;
        }
        //If the game is over, this keeps it from going on
        
        if (attemptedPlay != null){
          System.out.println("Player " + (i + 1) + " plays their " + attemptedPlay + ".");
          discardPile[0] = MyCard.createMyCard(attemptedPlay);
          if (discardPile[0].rank == 8) {
            discardPile[0].suit = playerHands[i].message("What suit should this be? ");
            System.out.println("Player " + (i + 1) + " makes their 8's suit " + discardPile[0].suit + ".");
          }
          else if (discardPile[0].rank == 2) {
            System.out.println("Player " + (i + 1) + " has played a 2! Next player draws 2 cards and loses their turn!");
            ruleOfTwo = 1;
          }
          else if (discardPile[0].rank == 4) {
            System.out.println("Player " + (i + 1) + " has played a 4! Next player loses their turn!");
            ruleOfFour = 1;
          }
          else if (discardPile[0].rank == 1) {
            discardPile[0] = lastCardOnPile;
            System.out.println("Player " + (i + 1) + "'s Joker is treated as " + discardPile[0] + ".");
          }
          lastCardOnPile = discardPile[0];
          System.out.print("Player " + (i + 1) + "'s Hand: ");
          for (int y = 0; y < playerHands[i].cards.length; y += 1) { 
            System.out.print(playerHands[i].cards[y] + " ");
          }
          System.out.println("");
        }
        //This if statement handles a player playing a card
        //It will only trigger if it has a valid card to work with
        //Whatever it is, it gets added to the top of the discard pile; 2s/4s trigger a queue to execute a special condition at the beginning of the next turn
        //8s and Jokers are converted to whatever card they need to be
        
        turnCounter += 1;
        if (playerHands[i].cards.length == 0){
          System.out.println("Player " + (i + 1) + " is victorious!");
          gameIsOver = 1;
          break;
        }
        //If playing a card caused this player's hand to be empty, they are declared to have won, and the game ends
        
        if (currentDeck.Cards.length == 0){
            gameIsOver = 1;
            for (int y = 0; y < playerHands.length; y += 1){
              double playerScore = 0;
              for (int x = 0; x < playerHands[y].cards.length; x += 1){
                playerScore += MyCard.createMyCard(playerHands[y].cards[x]).rank;
              }
              if (lowestScore == 0.0) {
                lowestScore = playerScore;
                winningPlayer = 1 + y;
              }
              else if (playerScore <= lowestScore) {
                lowestScore = playerScore;
                winningPlayer = 1 + y;
              }
            }
            System.out.println("Game over, deck depleted! Player " + winningPlayer + " wins! Scored " + lowestScore);
            break;
          }
        //One final check to see if the deck is empty
        System.out.println("");
      }
    }
  }
  
  public static void main (String[] args) {
    if ((args.length == 0) || (args.length > 1)) {
      System.out.println("This program requires a single integer argument between 2 and 7 in order to function.");
      return;
    }
    //This statement and the else at the end of the main method deal with invalid arguments
    //You must enter a single integer to run this program; this if prevents it from crashing if you don't
    //The else at the end simply prints an error message if the integer argument is invalid
    
    if ((Integer.parseInt(args[0]) >= 2) && (Integer.parseInt(args[0]) <= 7)) {
      if (Integer.parseInt(args[0]) >= 6) {
        currentDeck = new Deck(2, 4);
      }
      else {
        currentDeck = new Deck();
      }
      
      currentDeck.shuffleDeck();
      //Shuffles
      
      playerHands = playerBuilder(Integer.parseInt(args[0]));
      //Creates the appropriate number of players and initializes players' hands
      
      discardPile = startDrawing(playerHands);
      //Everyone draws to start the game
      
      executeTurn();
      //Turns play out until the game ends
    
    }
    else {
      System.out.println("Invalid number of players. Please try again with a number greater than or equal to 2 but no more than 7.");
    }
  }
}