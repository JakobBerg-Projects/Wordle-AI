package no.uib.inf102.wordle.controller.AI;

import java.util.HashSet;

import no.uib.inf102.wordle.model.Dictionary;
import no.uib.inf102.wordle.model.word.WordleWord;
import no.uib.inf102.wordle.model.word.WordleWordList;

public class MyStrategy implements IStrategy {

    private Dictionary dictionary;
    private WordleWordList guesses;
    int guessCount = 0;

    public MyStrategy(Dictionary dictionary) {
        this.dictionary = dictionary;
        reset();
    }

    @Override
    public String makeGuess(WordleWord feedback) {
        if (feedback != null) {
            guesses.eliminateWords(feedback);
        }
        String bestString = null;
        int bestScore = - 1;
        for(String s : guesses.possibleAnswers()){
            if (guessCount<2 && !isDifferentChars(s)) {
                continue;  // Skip this word if it doesn't have unique characters
            }
            int currentScore = scoreWord(s);
            if(currentScore>bestScore){
                bestScore = currentScore;
                bestString = s;
            }
        }
        guesses.remove(bestString);
        guessCount += 1;
        return bestString;
    }


    private int scoreWord(String s){
        int score = 0;
        for (char c : s.toCharArray()) {
            score+= scoreChar(c);
    }
    return score;
    }


    // Method to score an individual character based on frequency in the remaining possible answers
    private int scoreChar(char ch) {
    int charFrequency = 0;

    // Calculate the frequency of the character 'ch' in all possible answers
    for (String word : guesses.possibleAnswers()) {
        for (char c : word.toCharArray()) {
            if (c == ch) {
                charFrequency++;  // Increment the frequency count if the character matches
            }
        }
    }

    return charFrequency;  // Return the frequency as the score for the character
}
    @Override
    public void reset() {
        guesses = new WordleWordList(dictionary);
    }
    
    public boolean isDifferentChars(String word){
        HashSet<Character> seen = new HashSet<>();
        for (char c : word.toCharArray()) {
            if(seen.contains(c)){
                return false;
            }
            seen.add(c);
        }
        return true;
    }
    
}
