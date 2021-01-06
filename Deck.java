//COMP 1006A/1406A Assignment 3 Problem 3
//By Alexander Kuhn, ID# 101023154, July 11, 2016
//Purpose: This class models a deck of playing cards, so its sole attribute is an array of MyCard objects
//It contains methods to look at the deck's top card, to draw the deck's top card, to randomly shuffle the deck, and to sort the deck (decks come pre-sorted after construction)
//Inputs: One constructor needs no input and just builds a 54-card deck with 1 of each standard card and 2 jokers; the other takes 2 integers as input, the first integer specifying
//how many standard 52-card decks make up this deck, and the second specifying how many jokers are to be added
//Outputs: A single Deck object
import java.util.Arrays;
//The Arrays class contains a sort method, instrumental to sorting the deck
public class Deck{
  public MyCard[] Cards; 
  
  public Deck(){
   /* creates a deck of 54 cards as described in the Cards problem 
   * (52 cards that are in the standard deck of cards plus two jokers)
   */
    int cardIncrement = 0;
    this.Cards = new MyCard[54];
    //This creates a deck with 54 blank cards, then I use two for loops in conjunction with cardIncrement to print one copy of each rank in each suit onto 52 of the blanks
    //After that's done, I just turn two of the blanks into jokers
    
    for (int x = 0; x < 4; x += 1) {
      for (int y = 2; y < 15; y += 1) {
          this.Cards[cardIncrement] = new MyCard(Card.RANKS[y], Card.SUITS[x]);
          cardIncrement += 1;
        }
      }
    
    this.Cards[52] = new MyCard ("Joker", "None");
    this.Cards[53] = new MyCard ("Joker", "None");
    
  }

  
  public Deck(int numStandard, int numJokers){
   /* creates a deck with specified number of copies of the 
   * standard deck (52 cards) and with the specified 
   * number of jokers 
   * 
   * Both inputs must be non-negative
   */
    int cardIncrement = 0;
    this.Cards = new MyCard[(52 * numStandard) + numJokers];
    
    for (int i = 0; i < numStandard; i += 1) {
      for (int x = 0; x < 4; x += 1) {
        for (int y = 2; y < 15; y += 1) {
          this.Cards[cardIncrement] = new MyCard(Card.RANKS[y], Card.SUITS[x]);
          cardIncrement += 1;
        }
      }
    }
    
    for (int z = 0; z < numJokers; z += 1){
      this.Cards[52 * numStandard + z] = new MyCard ("Joker", "None");
    }
    
  }
  
  
  
  
  public int numCards(){
    /* returns the number of cards in the deck */
    return this.Cards.length;
  }
  
  
  
  public Card[] getCards(){
   /* returns all the cards in the deck without modifying the deck 
   * the ordering in the array is the order of the cards at this
   * given time */
    return this.Cards;
  }
  
  
  
  public Card peek(){
    //Purpose: View the top card of the deck
    //Returns: The top card of the deck (DOES NOT MODIFY THE DECK)
    return this.Cards[(Cards.length) - 1];
  }
  
  
  
  public Card pop(){
    //Purpose: removes the top card from the deck and returns it
    //Inputs/Outputs: None
    //Side Effects: Reduces the Cards array in size by 1
    //Returns: The card at the top of the deck (in other words, the card in the last cell of the Cards array)
    Card poppedCard = this.Cards[(Cards.length) - 1];
    MyCard[] adjustedDeck = new MyCard[(Cards.length) - 1];
    
    System.arraycopy(this.Cards, 0, adjustedDeck, 0, (Cards.length - 1));
    this.Cards = adjustedDeck;
    //Arrays aren't malleable, so to reduce Cards in size, I have to create a copy that is one size smaller and that doesn't contain the card that was just drawn
    //Then I overwrite the original Cards with the copy
    
    return poppedCard;
  }
  

  
  public void shuffleDeck(){
    //Purpose: To randomly shuffle the order of the cards in the deck
    //Inputs/Outputs: None
    //Returns: Nothing
    MyCard switchedCard1;
    MyCard switchedCard2;
    int cardToSwitch;
    //There are a lot of algorithms online for efficiently randomizing the contents of arrays
    //Of course, using any of them would be plagiarism
    //So instead, I did this
    //Essentially, it goes over every slot in the deck, pulls out whatever card's there, and switches it with a random other card
    //It's likely some cards will be swapped multiple times, and there's no guarantee they won't end up where they started (though it is unlikely)
    //It does ensure that every card will be swapped at least once
    
    for (int i = 0; i < this.Cards.length; i += 1) {
      cardToSwitch = (int)(Math.random() * (this.Cards.length - 1));
      switchedCard1 = this.Cards[i];
      switchedCard2 = this.Cards[cardToSwitch];
      
      this.Cards[i] = switchedCard2;
      this.Cards[cardToSwitch] = switchedCard1;
    }
  }
  
  public void sortDeck(){
   //Purpose: sorts the deck so that cards are in the order specified in the Cards problem (low cards first)
   //Because the original Card class implemented compareTo, and I overrode it in MyCard, Arrays.sort will have no problem using
   //the ordering values in each card to sort them
   //Inputs/Outputs: None
   //Returns: Nothing
    Arrays.sort(this.Cards);
  }
  
  public static void main (String[] args) {
    Deck testDeck = new Deck();
    testDeck.shuffleDeck();
    testDeck.sortDeck();
    for (int i = 0; i < testDeck.Cards.length; i += 1) {
      System.out.println(testDeck.Cards[i]);
    }
  }
  
}
  
