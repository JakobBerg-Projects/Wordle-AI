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
        int mostGreenMatches = - 1;

        for (String possibleWord : guesses.possibleAnswers()) {
            int currentGreenMatches;

            currentGreenMatches = WordleWordList.countGreenMatches(possibleWord, guesses.possibleAnswers());
            

        // If the current word has more green matches than the best word so far, choose it
        if (currentGreenMatches > mostGreenMatches) {
            mostGreenMatches = currentGreenMatches;
            bestString = possibleWord;
        }
    }


    return bestString;
}




    @Override
    public void reset() {
        guesses = new WordleWordList(dictionary);
    }
}