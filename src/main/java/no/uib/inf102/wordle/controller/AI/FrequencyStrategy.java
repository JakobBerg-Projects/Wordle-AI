package no.uib.inf102.wordle.controller.AI;


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
            guesses.eliminateWords(feedback); // O(m * k)
        }
        String bestString = null; // O(1)
        int highestScore = -1; // O(1)

        // Precompute position frequencies
        guesses.computePositionFrequencies(); // O(m * k)

        // Iterate over possible words to find the best guess
        for (String possibleWord : guesses.possibleAnswers()) { // O(m)
            int currentScore = WordleWordList.countGreenMatches(possibleWord, guesses.positionFrequencies); // O(k)

            // Update bestString if currentScore is higher
            if (currentScore > highestScore) { // O(1)
                highestScore = currentScore; // O(1)
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