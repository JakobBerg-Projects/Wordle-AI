package no.uib.inf102.wordle.model.word;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import no.uib.inf102.wordle.model.Dictionary;

/**
 * This class represents an answer to a Wordle puzzle.
 * 
 * The answer must be one of the words in the LEGAL_WORDLE_LIST.
 */
public class WordleAnswer {

    private final String WORD;

    private Dictionary dictionary;

    private static Random random = new Random();

    /**
     * Creates a WordleAnswer object with a random word from the answer word list
     */
    public WordleAnswer(Dictionary dictionary) {
        this(random, dictionary);
    }

    /**
     * Creates a WordleAnswer object with a random word from the answer word list
     * using a specified random object.
     * This gives us the opportunity to set a seed so that tests are repeatable.
     */
    public WordleAnswer(Random random, Dictionary dictionary) {
        this(getRandomWordleAnswer(random, dictionary), dictionary);
    }

    /**
     * Creates a WordleAnswer object with a given word.
     * 
     * @param answer
     */
    public WordleAnswer(String answer, Dictionary dictionary) {
        this.WORD = answer.toLowerCase();
        this.dictionary = dictionary;
    }

    /**
     * Gets a random wordle answer
     * 
     * @param random
     * @return
     */
    private static String getRandomWordleAnswer(Random random, Dictionary dictionary) {
        List<String> possibleAnswerWords = dictionary.getAnswerWordsList();
        int randomIndex = random.nextInt(possibleAnswerWords.size());
        String newWord = possibleAnswerWords.get(randomIndex);
        return newWord;
    }

    /**
     * Guess the Wordle answer. Checks each character of the word guess and gives
     * feedback on which that is in correct position, wrong position and which is
     * not in the answer word.
     * This is done by updating the AnswerType of each WordleCharacter of the
     * WordleWord.
     * 
     * @param wordGuess
     * @return wordleWord with updated answertype for each character.
     */
    public WordleWord makeGuess(String wordGuess) {
        if (!dictionary.isLegalGuess(wordGuess))
            throw new IllegalArgumentException("The word '" + wordGuess + "' is not a legal guess");

        WordleWord guessFeedback = matchWord(wordGuess, WORD);
        return guessFeedback;
    }

    /**
     * Generates a WordleWord showing the match between <code>guess</code> and
     * <code>answer</code>
     * 
     * @param guess
     * @param answer
     * @return
     */
    public static WordleWord matchWord(String guess, String answer) {
        int wordLength = answer.length(); // O(1)
        if (guess.length() != wordLength) { // O(1)
        throw new IllegalArgumentException("Guess and answer must have the same number of letters but guess = " 
                + guess + " and answer = " + answer);
    }

    AnswerType[] feedback = new AnswerType[wordLength]; // O(k)
    boolean[] isGreen = new boolean[wordLength]; // O(k)
    Map<Character, Integer> answerCharCount = new HashMap<>(); // O(1)

    // Step 1: Mark all exact (green) matches
    // Iterates over the word, therefor runtime is O(k)
    for (int i = 0; i < wordLength; i++) {
        char g = guess.charAt(i); // O(1)
        char a = answer.charAt(i); // O(1)
        
        if (g == a) { // O(1)
            feedback[i] = AnswerType.CORRECT; // O(1)
            isGreen[i] = true; // O(1)
        } else {
            feedback[i] = AnswerType.WRONG; // O(1)
            answerCharCount.put(a, answerCharCount.getOrDefault(a, 0) + 1); // O(1)
        }
    }

    // Step 2: Handle partial (yellow) matches O(k) runtime
    // Iterates over the word, therefor runtime is O(k) worst case, however it skips the characters marked as correct
    for (int i = 0; i < wordLength; i++) { 
        if (!isGreen[i]) {  // O(1)
            char g = guess.charAt(i); // O(1)
            
            if (answerCharCount.getOrDefault(g, 0) > 0) { // O(1)
                feedback[i] = AnswerType.MISPLACED;  // O(1)
                answerCharCount.put(g, answerCharCount.get(g) - 1); // O(1)     
            }
        }
    }

    return new WordleWord(guess, feedback); // O(k)
}
    }

