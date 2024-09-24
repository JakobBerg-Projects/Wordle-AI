package no.uib.inf102.wordle.model.word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import no.uib.inf102.wordle.model.Dictionary;

/**
 * This class describes a structure of two lists for a game of Wordle: The list
 * of words that can be used as guesses and the list of words that can be
 * possible answers.
 */
public class WordleWordList {

	/**
	 * All words in the game. These words can be used as guesses.
	 */
	private Dictionary allWords;

	/**
	 * A subset of <code>allWords</code>. <br>
	 * </br>
	 * These words can be the answer to a wordle game.
	 */
	private List<String> possibleAnswers;

	/**
	 * Create a WordleWordList that uses the full words and limited answers of the
	 * GetWords class.
	 */
	public WordleWordList(Dictionary dictionary) {
		this.allWords = dictionary;
		this.possibleAnswers = new ArrayList<>(dictionary.getAnswerWordsList());
	}

	/**
	 * Get the list of all guessing words.
	 * 
	 * @return all words
	 */
	public Dictionary getAllWords() {
		return allWords;
	}

	/**
	 * Returns the list of possible answers.
	 * 
	 * @return
	 */
	public List<String> possibleAnswers() {
		return Collections.unmodifiableList(possibleAnswers);
	}

	/**
	 * Eliminates words from the possible answers list using the given
	 * <code>feedback</code>
	 * 
	 * @param feedback
	 */
	public void eliminateWords(WordleWord feedback) {

		List<String> newPossibleAnswers = new ArrayList<>(); // O(1)
		
		// Iterate over all the words in possible answers O(m)
		for (String word : possibleAnswers) {

			// Check if the word is possible given the feedback
        	// WordleWord.isPossibleWord(word, feedback) is O(k)
			if (WordleWord.isPossibleWord(word, feedback)) {
				newPossibleAnswers.add(word); // O(1) Average time complexity of add for arraylist. worst-case scenario, when a new array has to be created and all the elements copied to it, it’s O(n)
			}
		}
		
		possibleAnswers = newPossibleAnswers; // O(1)
	}

	/**
	 * Returns the amount of possible answers in this WordleWordList
	 * 
	 * @return size of
	 */
	public int size() {
		return possibleAnswers.size();
	}

	/**
	 * Removes the given <code>answer</code> from the list of possible answers.
	 * 
	 * @param answer
	 */
	public void remove(String answer) {
		possibleAnswers.remove(answer);
	}

	/**
	 * Returns the word length in the list of valid guesses.
	 * 
	 * @return
	 */
	public int wordLength() {
		return allWords.WORD_LENGTH;
	}

	
	public static int countGreenMatches(String guess, List<String> possibleAnswers) {
		int count = 0; // O(1)
		int len = guess.length(); // O(1)
	
		// Iterate through each possible answer O(m)
		for (String word : possibleAnswers) { 
			// Check if each character in the guess matches the corresponding character in the word
			// Iterate through characters in word O(k)
			for (int i = 0; i < len; i++) {
				if (guess.charAt(i) == word.charAt(i)) { // O(1)
					count++; // O(1)
				}
			}
		}
	
		return count; // O(1)
	}
}