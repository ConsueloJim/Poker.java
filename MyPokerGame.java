package PJ4;
import java.util.*;

/*
 * Ref: http://en.wikipedia.org/wiki/Video_poker
 *      http://www.google.com/ig/directory?type=gadgets&url=www.labpixies.com/campaigns/videopoker/videopoker.xml
 *
 *
 * Short Description and Poker rules:
 *
 * Video poker is also known as draw poker. 
 * The dealer uses a 52-card deck, which is played fresh after each currentHand. 
 * The player is dealt one five-card poker currentHand. 
 * After the first draw, which is automatic, you may hold any of the cards and draw 
 * again to replace the cards that you haven't chosen to hold. 
 * Your cards are compared to a table of winning combinations. 
 * The object is to get the best possible combination so that you earn the highest 
 * payout on the bet you placed. 
 *
 * Winning Combinations
 *  
 * 1. Jacks or Better: a pair pays out only if the cards in the pair are Jacks, 
 * 	Queens, Kings, or Aces. Lower pairs do not pay out. 
 * 2. Two Pair: two sets of pairs of the same card denomination. 
 * 3. Three of a Kind: three cards of the same denomination. 
 * 4. Straight: five consecutive denomination cards of different suit. 
 * 5. Flush: five non-consecutive denomination cards of the same suit. 
 * 6. Full House: a set of three cards of the same denomination plus 
 * 	a set of two cards of the same denomination. 
 * 7. Four of a kind: four cards of the same denomination. 
 * 8. Straight Flush: five consecutive denomination cards of the same suit. 
 * 9. Royal Flush: five consecutive denomination cards of the same suit, 
 * 	starting from 10 and ending with an ace
 *
 */


/* This is the main poker game class.
 * It uses Decks and Card objects to implement poker game.
 * Please do not modify any data fields or defined methods
 * You may add new data fields and methods
 * Note: You must implement defined methods
 */



public class MyPokerGame {

    // default constant values
    private static final int startingBalance=100;
    private static final int numberOfCards=5;

    // default constant payout value and currentHand types
    private static final int[] multipliers={1,2,3,5,6,9,25,50,250};
    private static final String[] goodHandTypes={ 
	  "Royal Pair" , "Two Pairs" , "Three of a Kind", "Straight", "Flush	", 
	  "Full House", "Four of a Kind", "Straight Flush", "Royal Flush" };

    // must use only one deck
    private static final Decks oneDeck = new Decks(1);

    // holding current poker 5-card hand, balance, bet    
    private List<Card> currentHand;
    private List<Card> tempHand = new ArrayList<Card>();
    private List<Card> tempDeck = new ArrayList<Card>();
    private int balance;
    private int bet;
    //current hand Rank and Suit
    int[] rank = new int[14];
    int[] suit = new int[5];
    //Show payout table counter
    boolean showTable = true;
    int finalShowTable = 0;
    
    /** default constructor, set balance = startingBalance */
    public MyPokerGame()
    {
	this(startingBalance);
    }

    /** constructor, set given balance */
    public MyPokerGame(int balance)
    {
	this.balance= balance;
    }

    /** This display the payout table based on multipliers and goodHandTypes arrays */
    private void showPayoutTable()
    { 
	System.out.println("\n\n");
	System.out.println("Payout Table   	      Multiplier   ");
	System.out.println("=======================================");
	int size = multipliers.length;
	for (int i=size-1; i >= 0; i--) {
		System.out.println(goodHandTypes[i]+"\t|\t"+multipliers[i]);
	}
	System.out.println("\n\n");
    }

    /** Check current currentHand using multipliers and goodHandTypes arrays
     *  Must print yourHandType (default is "Sorry, you lost") at the end of function.
     *  This can be checked by testCheckHands() and main() method.
     */
    private void checkHands()
    {
        // implement this method!
         boolean win = false;
        //populate rank array
        for (int i = 1; i < 14; i++) {
            rank[i] = 0;
        }
        for (int i = 0; i <= 4; i++) {
            rank[currentHand.get(i).getRank()]++;
        }
        //populate suit array
        for (int i = 0; i < suit.length; i++) {
            suit[i] = 0;
        }
        for (int i = 0; i < suit.length; i++) {
            suit[currentHand.get(i).getSuit()]++;
        }

        //check if Hand is Awesome
        while (!win) {
            if (checkRoyalFlush()) {
                System.out.println("Royal Flush!");
                balance += bet * 250;
                win = true;

            } else if (checkStraightFlush()) {
                System.out.println("Straight Flush!");
                balance += bet * 50;
                win = true;

            } else if (checkFourOfKind()) {
                System.out.println("Four of a kind!");
                balance += bet * 25;
                win = true;

            } else if (checkFullHouse()) {
                System.out.println("Full House!");
                balance += bet * 9;
                win = true;

            } else if (checkFlush()) {
                System.out.println("Flush!");
                balance += bet * 6;
                win = true;

            } else if (checkStraight()) {
                System.out.println("Straight!");
                balance += bet * 5;
                win = true;

            } else if (checkThreeOfKind()) {
                System.out.println("Three of a Kind!");
                balance += bet * 3;
                win = true;

            } else if (checkTwoPair()) {
                System.out.println("Two Pair!");
                balance += bet * 2;
                win = true;

            } else if (checkRoyalPair()) {
                System.out.println("Royal Pair!");
                balance += bet;
                win = true;

            } else {
                System.out.println("Sorry, you lost :(");
                break;
            }
        }
        //add statements
    }

    private boolean checkRoyalPair() {
        int sameCard = 1;
        int checkRoyal = 0;
        for (int i = 13; i > 0; i--) {
            if (rank[i] > sameCard) {
                sameCard = rank[i];
                checkRoyal = i;

            }
        }
        if ((checkRoyal == 1 || checkRoyal > 10) && sameCard == 2) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkTwoPair() {
        int pair = 0;
        for (int i = 13; i >= 1; i--) {
            if (rank[i] == 2) {
                pair++;
            }
        }
        if (pair == 2) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkThreeOfKind() {
        boolean isThreeOfKind = false;
        for (int i = 13; i >= 1; i--) {
            if (rank[i] == 3) {
                isThreeOfKind = true;
            }
        }
        return isThreeOfKind;

    }

    private boolean checkStraight() {
        boolean isStraight = false;
        for (int i = 1; i <= 9; i++) {
            if (rank[i] == 1 && rank[i + 1] == 1
                    && rank[i + 2] == 1 && rank[i + 3] == 1 && rank[i + 4] == 1) {
                isStraight = true;
            }
        }
        if (rank[1] == 1 && rank[10] == 1 && rank[11]
                == 1 && rank[12] == 1 && rank[13] == 1) {
            isStraight = true;
        }
        return isStraight;
    }

    private boolean checkFlush() {
        boolean isFlush = false;
        for (int i = 0; i < 5; i++) {
            if (suit[i] == 5) {
                isFlush = true;
            }
        }
        return isFlush;
    }

    private boolean checkFullHouse() {
        boolean isFullHouse = false;
        if (checkThreeOfKind()) {
            for (int j = 13; j >= 1; j--) {
                if (rank[j] == 2) {
                    isFullHouse = true;
                }
            }
        }
        return isFullHouse;
    }

    private boolean checkFourOfKind() {
        boolean isFourOfKind = false;
        for (int i = 13; i >= 1; i--) {
            if (rank[i] == 4) {
                isFourOfKind = true;
            }
        }
        return isFourOfKind;
    }

    private boolean checkStraightFlush() {
        boolean isStraightFlush = false;
        if (checkFlush() && checkStraight()) {
            isStraightFlush = true;
        }
        return isStraightFlush;

    }

    private boolean checkRoyalFlush() {
        boolean isRoyalFlush = false;
        if (checkFlush()) {
            if (rank[1] == 1 && rank[10] == 1 && rank[11]
                    == 1 && rank[12] == 1 && rank[13] == 1) {
                isRoyalFlush = true;
            }
        }
        return isRoyalFlush;
    
    }


    /*************************************************
     *   add new private methods here ....
     *
     *************************************************/
public void checkIfInt() {
        Scanner myScanner = new Scanner(System.in);
        System.out.print("Please enter your bet in the form of an integer: ");
        try {
            bet = myScanner.nextInt();   
        } catch (InputMismatchException e) {
            System.err.println("Please enter a valid integer.");
            checkIfInt();
        }
        if (bet < 0) {
            System.out.println("Your bet has to be positive!");
            checkIfInt();
        } else if (bet > balance) {
            System.out.print("Your bet is higher than your available balance. "
                    + "Please enter a smaller bet: ");
            bet = myScanner.nextInt();
        }
    }
    public void checkCardChoice() throws PlayingCardException {
        try {
            Scanner cardNums = new Scanner(System.in);
            System.out.print("Which cards would you like to keep? (Enter the "
                    + "position of the cards (1, 2, 3, 4, or 5): ");
            String choice = cardNums.nextLine();
            String[] stringArray = choice.split("\\s");
            int[] intArray = new int[stringArray.length];
            int counter = 0;
            for (int i = 0; i < intArray.length; i++) {
                intArray[i] = Integer.parseInt(stringArray[i]);
                tempHand.add(currentHand.get(intArray[i] - 1));
                counter++;
            }
            int addCard = (currentHand.size() - counter);
            tempDeck = oneDeck.deal(addCard);
            for (int i = 0; i < addCard; i++) {
                tempHand.add(tempDeck.remove(0));
            }
        } catch (NumberFormatException e) {
            System.err.println("That is not an integer!");
            checkCardChoice();
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Enter a number 1-5");
            checkCardChoice();
        }
    }
    public void playAgain() throws PlayingCardException {
        System.out.print("Would you like to play again? (y/n): ");
            Scanner playAgain = new Scanner(System.in);
            if (playAgain.hasNext("y") || playAgain.hasNext("Y")) {
                //asks if want to see payout table. If "no" twice, stops asking
                showTable = true;
                while (showTable && finalShowTable < 2) {
                    payoutTable();
                    break;
                }
                currentHand.clear();
                play();
            } else if (playAgain.hasNext("n") || playAgain.hasNext("N")) {
                System.out.println("Thanks for playing!");
                System.exit(0);
            } else {
                System.out.println("You must enter a y or n!");
                playAgain();
            }
    }
    public void payoutTable() {
        System.out.print("Would you like to see the payout table? "
                            + "(y/n): ");
                    Scanner payoutTable = new Scanner(System.in);
                    if (payoutTable.hasNext("n") || payoutTable.hasNext("N")) {
                        showTable = false;
                        finalShowTable++;
                    } else if (payoutTable.hasNext("y") || 
                            payoutTable.hasNext("Y")) {
                        showTable = true;
                        finalShowTable = 0;
                    } else {
                        System.out.println("You must enter a y or n!");
                        payoutTable();
                    }
    }

    public void play() throws PlayingCardException {
    {
    /** The main algorithm for single player poker game 
     *
     * Steps:
     * 		showPayoutTable()
     *
     * 		++	
     * 		show balance, get bet 
     *		verify bet value, update balance
     *		reset deck, shuffle deck, 
     *		deal cards and display cards
     *		ask for position of cards to keep  
     *          get positions in one input line
     *		update cards
     *		check hands, display proper messages
     *		update balance if there is a payout
     *		if balance = O:
     *			end of program 
     *		else
     *			ask if the player wants to play a new game
     *			if the answer is "no" : end of program
     *			else : showPayoutTable() if user wants to see it
     *			goto ++
     */

        // implement this method!
        if (showTable && finalShowTable < 2) {
            showPayoutTable();
        }
        System.out.println("***************************************");
        System.out.println("Your current balance is: " + balance);
        //check if input is valid
        checkIfInt();
        System.out.println();
        balance -= bet;
        System.out.println("Your balance is: " + balance);
        //Reset, shuffle deck, and deal currentHand
        oneDeck.reset();
        oneDeck.shuffle();
        System.out.println();
        currentHand = oneDeck.deal(numberOfCards);
        System.out.println(currentHand);
        System.out.println();
        //check if input is valid
        checkCardChoice();
        currentHand = tempHand;
        System.out.println();
        System.out.println("Your new hand is:\n " + currentHand);
        System.out.println();
        checkHands();
        //Game is finsihed, Start Again?
        if (balance <= 0) {
            System.out.println("You're all out of cash! "
                    + "Better luck next time.");
            System.exit(0);
        } else {
            playAgain();
        }
    }
        
   }


    /** Do not modify this. It is used to test checkHands() method 
     *  checkHands() should print your current hand type
     */ 
    public void testCheckHands()
    {
      	try {
    		currentHand = new ArrayList<Card>();

		// set Royal Flush
		currentHand.add(new Card(1,3));
		currentHand.add(new Card(10,3));
		currentHand.add(new Card(12,3));
		currentHand.add(new Card(11,3));
		currentHand.add(new Card(13,3));
		System.out.println(currentHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Straight Flush
		currentHand.set(0,new Card(9,3));
		System.out.println(currentHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Straight
		currentHand.set(4, new Card(8,1));
		System.out.println(currentHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Flush 
		currentHand.set(4, new Card(5,3));
		System.out.println(currentHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// "Royal Pair" , "Two Pairs" , "Three of a Kind", "Straight", "Flush	", 
	 	// "Full House", "Four of a Kind", "Straight Flush", "Royal Flush" };

		// set Four of a Kind
		currentHand.clear();
		currentHand.add(new Card(8,3));
		currentHand.add(new Card(8,0));
		currentHand.add(new Card(12,3));
		currentHand.add(new Card(8,1));
		currentHand.add(new Card(8,2));
		System.out.println(currentHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Three of a Kind
		currentHand.set(4, new Card(11,3));
		System.out.println(currentHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Full House
		currentHand.set(2, new Card(11,1));
		System.out.println(currentHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Two Pairs
		currentHand.set(1, new Card(9,1));
		System.out.println(currentHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Royal Pair
		currentHand.set(0, new Card(3,1));
		System.out.println(currentHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// non Royal Pair
		currentHand.set(2, new Card(3,3));
		System.out.println(currentHand);
    		checkHands();
		System.out.println("-----------------------------------");
      	}
      	catch (Exception e)
      	{
		System.out.println(e.getMessage());
      	}
    }

    /* Quick testCheckHands() */
    public static void main(String args[]) 
    {
	MyPokerGame mypokergame = new MyPokerGame();
	mypokergame.testCheckHands();
    }
}
