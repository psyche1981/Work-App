package psyche.androidapp.workcalcs;

import static psyche.androidapp.workcalcs.ProfaniLibrary.*;
import static psyche.androidapp.workcalcs.Utilities.*;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



public class ProfaniMan extends Activity implements OnClickListener
{
	//App Stuff
	private static TextView answerBox, titleBox, livesBox, wrongBox, infoBox;
	private static Button bGuess, bNewWord, bDefine;
	private static EditText etGuess;

	
	protected void onCreate(Bundle b)
	{
		super.onCreate(b);
		setContentView(R.layout.profaniman);
		initActivity();
		initText();
		startGame();
	}
	
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		super.onCreateOptionsMenu(menu);
		MenuInflater menInf = getMenuInflater();
		menInf.inflate(R.menu.popup_profaniman, menu);
		return true;
	}
		
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch(item.getItemId())
		{
		case R.id.popup_profani_info:
			Intent i = new Intent("psyche.androidapp.workcalcs.PROFANIINFO");
			startActivity(i);
			break;
		}
		return false;
	}

	private void initActivity()
	{
		titleBox = (TextView)findViewById(R.id.tvProfaniTitle);
		answerBox = (TextView)findViewById(R.id.tvProfaniWord);
		livesBox = (TextView)findViewById(R.id.tvProfaniLives);
		wrongBox = (TextView)findViewById(R.id.tvProfaniDerp);
		infoBox = (TextView)findViewById(R.id.tvProfaniInfo);
		bGuess = (Button)findViewById(R.id.bProfaniGuess);
		bNewWord = (Button)findViewById(R.id.bProfaniNewWord);
		etGuess = (EditText)findViewById(R.id.etProfaniGuess);
		bDefine = (Button)findViewById(R.id.bProfaniDefine);
		
		bGuess.setOnClickListener(this);
		bNewWord.setOnClickListener(this);
		bDefine.setOnClickListener(this);
	}
	
	private void initText()
	{
		titleBox.setTextColor(Color.WHITE);
		livesBox.setTextColor(Color.RED);
	}
	
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.bProfaniGuess:
			clickGuess();
			break;
		case R.id.bProfaniNewWord:
			startGame();
			break;
		case R.id.bProfaniDefine:
			if(gameIsOver())
			{
				if(win)
				{
					String word = activeWord;
					String definition = definitions[indexOf(activeWord, words)];
					Bundle b = new Bundle();
					b.putString("word", word);
					b.putString("definition", definition);
					Intent i = new Intent("psyche.androidapp.workcalcs.PROFANIDEFINE");
					i.putExtras(b);
					startActivity(i);
				}
				else
					infoBox.setText("Bugger Off!");
				
			}
			else
			{
				infoBox.setText("Nice try!");
			}
			
			break;
		}
		
	}
	
	private static void clickGuess()
	{
		if(!gameIsOver())
		{
			guess();
		}
		else
		{
			infoBox.setText("For some more childish fun, press the New Profanity button");
			
		}
		
		etGuess.setText("");
	}
	
	

	//Game Stuff
	public static final int MAX_WRONG_TRIES = 5;
	public static final int MAX_GUESSES  = 50;
	
	
	private static char[] tokens, guess;
	private static int guessCount;
	private static String activeWord;
	private static boolean[] correctGuesses;
	private static int correctGuessCounter;
	private static int guessesToWin;
	private static int incorrectGuesses;
	private static int lives , numSpaces;
	private static String[] answer, sucGuesses, unsucGuesses;
	private static boolean gameOver, win, lose;
	private static Exception number_entered;
	
	public static void startGame()
	{
		initGame();
		initBoolArr();		
		initAnswer();
		initScreen();
	}
	
	public static void initScreen()
	{
		answerBox.setText(stringArrayToString(answer));
		livesBox.setText(Integer.toString(lives));
		wrongBox.setText("");
		infoBox.setText("");
	}
	
	
	public static void initGame()
	{
		guessCount = correctGuessCounter = incorrectGuesses = numSpaces = 0;
		lives = MAX_WRONG_TRIES;
		activeWord = randomWordSelect(words);
		System.out.printf("%s\n", activeWord);
		number_entered = new Exception();
		
		gameOver = win = lose = false;
		tokens = stringSplitToChars(activeWord);
		answer = new String[tokens.length];
		unsucGuesses = new String[MAX_WRONG_TRIES];
		
		correctGuesses = new boolean[tokens.length];
		
		guess = new char[MAX_GUESSES];
		
	}
	
	public static void initBoolArr()
	{
		for(int i = 0; i < correctGuesses.length; i++)
		{
			if(tokens[i] == 32)
			{
				numSpaces++;
				correctGuesses[i] = true;
			}
			else
				correctGuesses[i] = false;
		}
		guessesToWin = countUniqueChars(activeWord) - nSpaceChars();
		sucGuesses = new String[guessesToWin];
	}
	
	public static void guess()
	{
		try
		{
			etGuess.getText().getChars(0, 1, guess, guessCount);
			if(guess[guessCount] >= '0' && guess[guessCount] <= '9') throw number_entered;
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			infoBox.setText("Enter a letter!");
			return;
		}
		checkGuess(guessCount);
		guessCount++;
		
		
		
		if(gameIsOver())
		{
			if(win)
			{
				answerBox.setText("Yes! The profanity is: \n" + activeWord);
				infoBox.setText(randomWordSelect(winString));
			}
			else if(lose)
			{
				answerBox.setText("Better Luck Next Time");
				infoBox.setText(randomWordSelect(loseString));
			}
		}
	}

	public static void initAnswer()
	{
		for(int i = 0; i < tokens.length; i++)
		{
			if(correctGuesses[i])
			{
				answer[i] = "/ ";
			}
				
			else
				answer[i] = "_ ";
		}
		
	}
	
	public static void addToAnswer(String s)
	{
		
		for(int i = 0; i < tokens.length; i++)
		{
			if(correctGuesses[i] && answer[i] == "_ ")
			{
				answer[i] = (s + " ");
			}
		}
		answerBox.setText(stringArrayToString(answer));
	}
	
	public static void checkGuess(int counter)
	{
		char c = guess[counter];
		if(c >= 'A' && c <= 'Z')
		{
			c += 32;
		}
		String s = String.valueOf(c);
		boolean dupCorrGuess = false;
		boolean dupUncorrGuess = false;
		
		if(sucGuesses[0] != null)
		{
			dupCorrGuess = checkPrevGuesses(sucGuesses, s);
		}
		if(unsucGuesses[0] != null)
		{
			dupUncorrGuess = checkPrevGuesses(unsucGuesses, s);
		}
		if(activeWord.contains(s))
		{
			if(dupCorrGuess)
			{
				guessCount--;
				infoBox.setText("Duplicate Guess You Knob!");
			}
			else
			{
				sucGuesses[correctGuessCounter] = (s + " ");
				correctGuessCounter++;
				infoBox.setText(randomWordSelect(praise));
				amendBoolArray(s);
				addToAnswer(s);
			}
		}
		else
		{
			if(dupUncorrGuess)
			{
				guessCount--;
				infoBox.setText("Its Still Wrong, Retard");
			}
			else
			{
				unsucGuesses[incorrectGuesses] = (s + " ");
				wrongBox.setText(stringArrayToString(unsucGuesses));
				infoBox.setText(insult[incorrectGuesses]);
				incorrectGuesses++;
				lives--;
				livesBox.setText(Integer.toString(lives));
			}
		}
	}
	
	public static boolean checkPrevGuesses(String[] s ,String curGuess)
	{
		boolean prevGuess = false;
		int counter = nElementsNotNull(s) - 1;
		for(int i = counter; i >= 0; i--)
		{
			if(s[i].contains(curGuess))
				prevGuess = true;
		}
		return prevGuess;
	}
		
	public static void amendBoolArray(String letter)
	{
		for(int i = 0; i < correctGuesses.length; i++)
		{
			String s = String.valueOf(tokens[i]);
			if(s.equals(letter))
			{
				correctGuesses[i] = true;
			}
		}
	}
	
	public static boolean gameIsOver()
	{
		if(correctGuessCounter == guessesToWin)
		{
			win = true;
			gameOver = true;
		}
		if(lives == 0)
		{
			lose = true;
			gameOver = true;
		}
		return gameOver;
	}
	
	private static int nSpaceChars()
	{
		if(numSpaces == 0)
			return 0;
		
		else
			return 1;
	}

	
}
