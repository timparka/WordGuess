package com.github.zipcodewilmington;

//Tim Park
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.List;

/**
 * @author xt0fer
 * @version 1.0.0
 * @date 5/27/21 11:02 AM
 */
public class Hangman {
    String again = "";
    String userInput = "";
    String randomWord = null;
    Scanner sc = new Scanner(System.in);
    char[] secretWord;
    char[] guessedSoFar;

    //  1. starts the game
//  2. a random word is chosen from a list
//  3. the number of tries allowed is set to the length of the word
//  4. setup the two (the solution and the player's guesses) character arrays
//  5. WHILE the numbers of tries is less than tries allowed AND
//  6. the player has not guessed the word
//  7. print the current state of the player's guesses
//  8. ask the player for a letter (a character)
//  9. if the letter is a minus "-", quit the game
//  10. else process the letter
//  11. if the letter makes the work complete,
//  12. the player wins
//  13. after the while loop
//  14. if the word is not guessed, player loses
    public static void main(String[] args) {
        new Hangman().run();
    }

    public void run() {
        while (playAgain(again)){
        int guesses = 8;
        ArrayList <Character> alreadyUsed = new ArrayList<Character>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Words.txt"));
            String line = reader.readLine();
            List<String> words = new ArrayList<String>(); //instatiating array list called words
            while (line != null) { //while line has a text input
                String[] wordsLine = line.split(" "); //indexes each word in a spot in array
                for (String word : wordsLine) {
                    words.add(word);
                }
                line = reader.readLine();
            }
            Random rand = new Random(System.currentTimeMillis());
            randomWord = words.get(rand.nextInt(words.size()));//random word chosen
        } catch (Exception e) {
            // Handle this
        }
        secretWord = randomWord.toCharArray();//[a (0), p [1], p[2], l[3], e [4]]
        guessedSoFar = new char[secretWord.length];//[void, void, void, void, void]
        System.out.println("Let's Play Wordguess version 1.0\n" + "Current Guesses:");
        for (int i = 0; i <= guessedSoFar.length - 1; i++) {
            System.out.print(guessedSoFar[i] = '-');//prints out '-' as the length of secretWord
        }
        System.out.println("\nYou have " + guesses + " tries left.\n" +
                "Enter a single character: ");
        char letterGuessed = sc.nextLine().toLowerCase().charAt(0);//have to get userinput using charAt(index 0)
        alreadyUsed.add(letterGuessed);
        this.checkInput(letterGuessed);
        this.checkGuess(letterGuessed);
        if (checkGuess(letterGuessed) != true) {
            guesses--;
        }
        while (guesses != 0) {
            System.out.println();
            System.out.print(guessedSoFar);
            System.out.println("\nYou have " + guesses + " tries left.\n" +
                    "Enter a single character: ");
            letterGuessed = sc.nextLine().toLowerCase().charAt(0);
            while (alreadyUsed.contains(letterGuessed)) {
                System.out.println("You already guessed this letter, try a different one! ");
                letterGuessed = sc.nextLine().toLowerCase().charAt(0);
                if (!alreadyUsed.contains(letterGuessed)) {
                    break;
                }
            }
            alreadyUsed.add(letterGuessed); //adds char to alreadyused array
            this.checkInput(letterGuessed); //checks if user enters a char
            this.checkGuess(letterGuessed);
            if (checkGuess(letterGuessed) != true) {
                guesses--;
            } else if (win(secretWord, guessedSoFar)) {
                break;
            }
            if (guesses == 0) {
                System.out.println("You lost! The word was " + randomWord);
                System.out.println("Would you like to play again? (yes or no) ");
                again = sc.nextLine();
                playAgain(again);
            }
        }

    }
    }

    public char checkInput(char letterGuessed) { //just checks if user entered a char
        boolean correctLetter = false;
        while (correctLetter == false) {

            if (Character.isLetter(letterGuessed)) {
                correctLetter = true;
            }
        }
        return letterGuessed;
    }
    public boolean checkGuess(char letterGuessed){ //checks if userInput is equal to any value in secretword array
        boolean valid = false;
        for (int i = 0; i < secretWord.length ; i++){
            if (secretWord[i] == letterGuessed){
                valid = true;
                guessedSoFar[i] = letterGuessed;
            }
        }
        return valid;
    }
    public boolean playAgain(String again){ //method just sets this value to true or false depending on string
        boolean restart = true;
        if (again.equals("yes")){
            restart = true;
        } else if (again.equals("no")) {
            restart = false;
        }
        return restart;
    }
    public boolean win(char [] secretWord, char [] guessedSoFar) {
        boolean win = false;
        if (Arrays.equals(secretWord, guessedSoFar)) {
            System.out.println("You won!");
            System.out.println("Would you like to play again? (yes or no) ");
            again = sc.nextLine();
            playAgain(again);
            win = true;
        }
        return win;
    }
}

