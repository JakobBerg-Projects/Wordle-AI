package no.uib.inf102.wordle.controller.AI;

import java.util.HashMap;
import java.util.Map;

import no.uib.inf102.wordle.model.Dictionary;
import no.uib.inf102.wordle.model.word.WordleWord;
import no.uib.inf102.wordle.model.word.WordleWordList;

/**
 * This strategy finds the word within the possible words which has the highest
 * expected
 * number of green matches.
 */
public class FrequencyStrategy implements IStrategy {

    private Dictionary dictionary;
    private WordleWordList guesses;

    public FrequencyStrategy(Dictionary dictionary) {
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
            int currentScore = scoreWord(s);
            if(currentScore>bestScore){
                bestScore = currentScore;
                bestString = s;
            }
        }
        guesses.remove(bestString);
        
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
}