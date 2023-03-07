package com.github.zipcodewilmington;

//Tim Park
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * @author xt0fer
 * @version 1.0.0
 * @date 5/27/21 11:02 AM
 */
public class Hangman {
    String userInput = "";
    String randomWord = null;
    Scanner sc = new Scanner(System.in);
    char[] secretWord;
    char[] guessedSoFar;
    ArrayList <Character> alreadyUsed = new ArrayList<Character>();

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
        int guesses = 8;
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
        secretWord = randomWord.toCharArray();
        guessedSoFar = new char[secretWord.length];
        System.out.println("Let's Play Wordguess version 1.0\n" + "Current Guesses:");
        for (int i = 0; i <= secretWord.length - 1; i++) {
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
        while (guesses < 8 || guesses > 0) {
            System.out.println();
            System.out.print(guessedSoFar);
            System.out.println("\nYou have " + guesses + " tries left.\n" +
                    "Enter a single character: ");
            letterGuessed = sc.nextLine().toLowerCase().charAt(0);
            while (alreadyUsed.contains(letterGuessed)){
                System.out.println("You already guessed this letter, try a different one! ");
                letterGuessed = sc.nextLine().toLowerCase().charAt(0);
                if (!alreadyUsed.contains(letterGuessed)){
                    break;
                }
            }
            alreadyUsed.add(letterGuessed);
            this.checkInput(letterGuessed);
            this.checkGuess(letterGuessed);
            if (win(letterGuessed) == true) {
                break;
            }
            if (checkGuess(letterGuessed) != true) {
                guesses--;
            }
            if (guesses == 0){
                System.out.println("You lost! The word was " + randomWord);
                break;
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
    public boolean checkGuess(char letterGuessed){ //checks if
        boolean valid = false;
        for (int i = 0; i < secretWord.length ; i++){
            if (secretWord[i] == letterGuessed){
                valid = true;
                guessedSoFar[i] = letterGuessed;
                if (guessedSoFar == secretWord) {
                    System.out.println("You won!");
                }
            }
        }
        return valid;
    }
    public boolean win(char letterGuessed) {
        boolean win = false;
        checkGuess(letterGuessed);
        if (guessedSoFar == secretWord) {
            System.out.println("Congrats you won!");
            win = true;
        }
        return win;
    }
}

