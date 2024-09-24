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
            guesses.eliminateWords(feedback); // O(m*k)
        }
        String bestString = null; // O(1)
        int mostGreenMatches = - 1; // O(1)

        // Iterates over all possible word O(m)
        for (String possibleWord : guesses.possibleAnswers()) {
            int currentGreenMatches; // O(1)

            currentGreenMatches = WordleWordList.countGreenMatches(possibleWord, guesses.possibleAnswers()); // O(m*k)
            

        // If the current word has more green matches than the best word so far, choose it
        if (currentGreenMatches > mostGreenMatches) { // O(1)
            mostGreenMatches = currentGreenMatches; // O(1)
            bestString = possibleWord; // O(1)
        }
    }


    return bestString; // O(1)
}




    @Override
    public void reset() {
        guesses = new WordleWordList(dictionary);
    }
}