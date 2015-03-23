/*************************************************************************************
 *
 *  This program is used to test PJ4.MyPokerGame class
 * 
 *  PJ4 class allows user to run program as follows:
 *
 *    	java PJ4		// default credit is $100
 *  or 	java PJ4 NNN		// set initial credit to NNN
 *
 *
 **************************************************************************************/

import PJ4.MyPokerGame;

class TestMyPokerGame {

    public static void main(String args[]) 
    {
	MyPokerGame mypokergame;
	if (args.length > 0)
		mypokergame = new MyPokerGame(Integer.parseInt(args[0]));
	else
		mypokergame = new MyPokerGame();
	mypokergame.play();
    }
}
