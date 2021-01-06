//COMP 1006A/1406A Assignment 3 Problem 2
//By Alexander Kuhn, ID# 101023154, July 11, 2016
//Purpose: This class models a single playing card, be it an ordinary card with a suit and rank or a Joker
//It also contains a method to compare two cards and see which has the higher value
//Inputs: The constructors run off a rank and a suit: the rank can be an integer or a string
//Outputs: A single MyCard object

public class MyCard extends Card {
  protected String rankString;
  protected int rank;
  protected String suit;
  protected double ordering;
  protected double rankAsDouble;
  //ordering and rankAsDouble exist for the same reason - to establish a comprehensive hierarchy of cards. Each card's suit is an integer - diamonds are worth 0, clubs 1, hearts 2, spades 3.
  //The rank of a card is a decimal, ranging from .02 (2) to .14 (Ace). This means that each card has a unique value that can be compared to any other card's value to establish which is worth more.
  //So in this system, the value of the 2 of Diamonds is 0.02, and the value of the Ace of Spades is 3.14
  //Establishing this hierarchy is necessary so we can compare cards to each other, and ultimately, sort them
  //Jokers are the highest-value card, and get an ordering value of 10 automatically.
  
  public MyCard(String rank, String suit) {
    for (int i = 0; i < Card.RANKS.length; i += 1) {
      if (Card.RANKS[i].intern() == rank.intern()) {
        this.rank = i;
        if (this.rank == 0) {
          this.rank = 1;
        }
        this.rankString = rank;
        break;
      }
    }
    
    for (int i = 0; i < Card.SUITS.length; i += 1) {
      if (Card.SUITS[i].intern() == suit.intern()) {
        this.suit = suit;
        this.ordering = i;
        break;
      }
    }
    
    rankAsDouble = this.rank;
    this.ordering += rankAsDouble / 100;
    
    if (this.toString().intern() == "J") {
      this.ordering = 10;
    }
    
  }
  
  public MyCard(int rank, String suit) {
    if ((1 <= rank) && (14 >= rank)) {
      this.rank = rank;
      this.rankString = Card.RANKS[rank];
    }
    
    for (int i = 0; i < Card.SUITS.length; i += 1) {
      if (Card.SUITS[i].intern() == suit.intern()) {
        this.suit = suit;
        this.ordering = i;
        break;
      }
    }
    
    rankAsDouble = this.rank;
    this.ordering += rankAsDouble / 100;
    
    if (this.toString().intern() == "J") {
      this.ordering = 10;
    }
    
  }
  
  protected static MyCard createMyCard(Card card){
    //Purpose: This is a semi-constructor I initially created as part of my compareTo method
    //I call it a semi-constructor because while it essentially does the work of a constructor, it's not technically one at all
    //It has a return value, for one
    //It doesn't really have to work this way - I could've just made another constructor - but I did all the work in another method, and only moved it out when I realized how useful it would be
    //for other parts of this assignment
    //Its sole purpose is to turn a Card object into a MyCard object, because there's nothing the Card object does that MyCard doesn't do better
    //Inputs: Takes one Card as input
    //Outputs: None visible
    //Returns: A new MyCard object created using the data retrieved from Card.toString()
    String newCardSuit = "";
    MyCard newCard = new MyCard ("Joker", "None");
    
    if (card.toString().intern() != "J") {
      if (card.toString().intern().substring(0, 1).matches("\\d") && card.toString().intern().substring(1, 2).matches("\\d")) {
        int newCardRank = Integer.parseInt(card.toString().intern().substring(0, 2));
        if (card.toString().intern().charAt(2) == 'D'){
          newCardSuit = "Diamonds";
        }
        else if (card.toString().intern().charAt(2) == 'C'){
          newCardSuit = "Clubs";
        }
        else if (card.toString().intern().charAt(2) == 'H'){
          newCardSuit = "Hearts";
        }
        else if (card.toString().intern().charAt(2) == 'S'){
          newCardSuit = "Spades";
        }
        newCard = new MyCard (newCardRank, newCardSuit);
      }
    
      else if (card.toString().intern().substring(0, 1).matches("\\d")) {
        int newCardRank = Integer.parseInt(card.toString().intern().substring(0, 1));
        if (card.toString().intern().charAt(1) == 'D'){
          newCardSuit = "Diamonds";
        }
        else if (card.toString().intern().charAt(1) == 'C'){
          newCardSuit = "Clubs";
        }
        else if (card.toString().intern().charAt(1) == 'H'){
          newCardSuit = "Hearts";
        }
        else if (card.toString().intern().charAt(1) == 'S'){
          newCardSuit = "Spades";
        }
      
        newCard = new MyCard (newCardRank, newCardSuit);
      }
      //These two ifs are necessary because the Card class doesn't have a rank value or a suit value, just a toString method that establishes what those values would be.
      //And since I can't tamper with the Card class in any way, I have to use that toString method here - these ifs check to see if the first two characters are digits or if only the first one is.
      //It converts either digits plural or digit singular to a rank, then finds the character at the end of the string and converts that into the corresponding suit. Only once that's done can I make a MyCard
      //object with those values and get an ordering value that tells me the value of the object.
    }
    return newCard;
  }
  
  @Override
  public int getRank() {
    return this.rank;
  }
  
  @Override
  public String getRankString() {
    return this.rankString;
  }
  
  @Override
  public String getSuit() {
    return this.suit;
  }
  
  @Override
  public int compareTo(Card card) {
    //Purpose: This gets the value of two cards and compares them
    //Inputs: Takes a single card object
    //Outputs: None visible
    //Returns: If this card has a value greater than the card taken as argument, it returns 1
    //If its value is lower, it returns -1
    //If they have the same value, it returns 0
    //Overall, it's basically identical to the ordinary CompareTo method 
    MyCard newCard = createMyCard(card);
     
    if (this.ordering > newCard.ordering) {
      return 1;
    }
    else if (this.ordering < newCard.ordering) {
      return -1;
    }
    else {
      return 0;
    }
  } 
}