//COMP 1006A/1406A Assignment 3 Problem 4
//By Alexander Kuhn, ID# 101023154, July 11, 2016
//Purpose: This class models a hand of playing cards
//It contains methods to play a card from the hand, get a new card from the deck, show the current hand,
//sort the hand via the ordering values from MyCard, and a decision-maker which picks the optimal suit for an 8 to be played as
//Inputs: The constructor takes an array of Card objects as input
//Outputs: A single Hand object
import java.util.Arrays;
//Again, I need this for its sort method
public class Hand implements HandActions {
 
  public static final boolean simpleLogic = true;
  public Card[] cards;
  
  public Hand(Card[] cards){
    this.cards = cards;
  }
  
  public Card playCard(Card[] pile){
    //Purpose: If there's a valid card in hand, plays it; otherwise, does nothing
    //Inputs: An array of Card objects, representing the discard pile (although the only relevant cell in the array is the top of the pile)
    //Side Effects: Removes the played card from the hand
    //Returns: The card played from the hand, or a null value if no card can be played
    Card[] shrunkenHand = new Card[(this.cards.length - 1)];
    Card playedCard;
    int ignorePlayedCard = 0;
    
    //This checks to see if the discard pile is empty, in which case it just plays the first card in hand
    for (int i = 0; i < this.cards.length; i +=1) {
      if (MyCard.createMyCard(pile[pile.length - 1]).suit.intern() == MyCard.createMyCard(this.cards[i]).suit.intern()){
        playedCard = this.cards[i];
        for (int x = 0; x < this.cards.length; x += 1) {
          if (this.cards[x] != this.cards[i]) {
            shrunkenHand[ignorePlayedCard] = this.cards[x];
            ignorePlayedCard += 1;
          }
        }
        this.cards = shrunkenHand;
        return playedCard;
      }
      else if (MyCard.createMyCard(pile[pile.length - 1]).rank == MyCard.createMyCard(this.cards[i]).rank){
        playedCard = this.cards[i];
        for (int x = 0; x < this.cards.length; x += 1) {
          if (this.cards[x] != this.cards[i]) {
            shrunkenHand[ignorePlayedCard] = this.cards[x];
            ignorePlayedCard += 1;
          }
        }
        this.cards = shrunkenHand;
        return playedCard;
      }
      else if (MyCard.createMyCard(this.cards[i]).rank == 8){
        playedCard = this.cards[i];
        for (int x = 0; x < this.cards.length; x += 1) {
          if (this.cards[x] != this.cards[i]) {
            shrunkenHand[ignorePlayedCard] = this.cards[x];
            ignorePlayedCard += 1;
          }
        }
        this.cards = shrunkenHand;
        return playedCard;
      }
      else if (MyCard.createMyCard(this.cards[i]).rank == 1){
        playedCard = this.cards[i];
        for (int x = 0; x < this.cards.length; x += 1) {
          if (this.cards[x] != this.cards[i]) {
            shrunkenHand[ignorePlayedCard] = this.cards[x];
            ignorePlayedCard += 1;
          }
        }
        this.cards = shrunkenHand;
        return playedCard;
      }
    }
    //This exhaustively checks if any card in the hand is legal to play
    
    return null;
  }
  
  public Card[] getCard(Card[] cards) {
    //Purpose: Draws a card
    //Inputs: The cards that remain in the deck (an array of Card objects)
    //Returns: Theoretically, this method could allow the player to discard a card, which would be the return value
    //However, the criteria specify that for this game this method always returns null
    Card[] newHand = new Card[this.cards.length + cards.length];
    int cardsLooper = 0;
      
    for (int i = 0; i < this.cards.length; i += 1){
      newHand[i] = this.cards[i];
    }
    for (int x = this.cards.length; x < (cards.length + this.cards.length); x += 1) {
      newHand[x] = cards[cardsLooper];
      cardsLooper += 1;
    }
    
    this.cards = newHand;
    
    return null;
  }
  
  public Card[] displayHand() {
    return this.cards;
  }
  
  public Card[] sortHand(){
    //Purpose: Sorts hand.cards in the order established in the MyCard class
    //Input: None
    //Returns: The sorted hand; note that while this returns a sorted hand.cards, it won't actually sort hand.cards
    //hand.cards remains as it was when it calls this method unless it's specifically told to be equal to sortHand()
    Card[] sortedHand = this.cards;
    for (int i = 0; i < this.cards.length; i += 1) {
      sortedHand[i] = MyCard.createMyCard(this.cards[i]);
    }
    Arrays.sort(sortedHand);
    return sortedHand;
  }
  
  public String message(String question) {
    //Purpose: If an 8 is played from this hand, determine what the suit of that 8 should be
    //It checks the hand to see if any suit would be advantageous (in other words, whether or not this hand has more of one suit than any other)
    //If it does, it declares the 8 to be of that suit
    //If there's a tie between one or more suits, it weights towards Spades > Hearts > Clubs > Diamonds
    //Input: Any String passed to this hand will trigger this calculation
    //Returns: The suit it wants the 8 to belong to
    //Examples: If this hand holds two cards, one 2 of Spades and one Queen of Diamonds, this would declare the 8 to be of Spades
    //If it had three cards, and the third was an 8 of Diamonds, it would instead declare the 8 to be of Diamonds
    int sumOfDiamonds = 0;
    int sumOfClubs = 0;
    int sumOfHearts = 0;
    int sumOfSpades = 0;
    
    for (int i = 0; i < this.cards.length; i +=1) {
      if (MyCard.createMyCard(this.cards[i]).suit.intern() == Card.SUITS[0].intern()){
        sumOfDiamonds += 1;
      }
      else if (MyCard.createMyCard(this.cards[i]).suit.intern() == Card.SUITS[1].intern()){
        sumOfClubs += 1;
      }
      else if (MyCard.createMyCard(this.cards[i]).suit.intern() == Card.SUITS[2].intern()){
        sumOfHearts += 1;
      }
      else if (MyCard.createMyCard(this.cards[i]).suit.intern() == Card.SUITS[3].intern()){
        sumOfSpades += 1;
      }
    }
    
    if ((sumOfSpades >= sumOfHearts) && (sumOfSpades >= sumOfClubs) && (sumOfSpades >= sumOfDiamonds)){
      return "Spades";
    }
    else if ((sumOfHearts >= sumOfClubs) && (sumOfHearts >= sumOfDiamonds)){
      return "Hearts";
    }
    else if ((sumOfClubs >= sumOfDiamonds)){
      return "Clubs";
    }
    else {
      return "Diamonds";
    }
  }
  
  //public static void main(String[] args) {
    //Hand testHand = new Hand(new Card[]{new MyCard(3, "Clubs"), new MyCard(8, "Diamonds")});
    //Card[] pile = new Card[]{new MyCard(2, "Clubs"), new MyCard(9, "Diamonds")};
    //for (int i = 0; i < testHand.cards.length; i += 1) {
      //System.out.println(testHand.cards[i]);
    //}
    //testHand.sortHand();
    //pile[(pile.length - 1)] = testHand.playCard(pile);
    //for (int i = 0; i < testHand.cards.length; i += 1) {
     //System.out.println(testHand.cards[i]);
    //}
    //System.out.println(testHand.message("?"));
  //}
  
}