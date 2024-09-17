package no.uib.inf102.wordle.model.word;

import java.util.List;
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
        int wordLength = answer.length();
    if (guess.length() != wordLength) {
        throw new IllegalArgumentException("Guess and answer must have the same number of letters but guess = " 
                + guess + " and answer = " + answer);
    }

    AnswerType[] feedback = new AnswerType[wordLength];
    boolean[] isGreen = new boolean[wordLength];  // Marks whether a position is a green match (exact)
    int[] answerCharCount = new int[26];  // Tracks occurrences of letters in the answer

    // Step 1: Mark all exact (green) matches
    for (int i = 0; i < wordLength; i++) {
        char g = guess.charAt(i);
        char a = answer.charAt(i);
        
        if (g == a) {
            feedback[i] = AnswerType.CORRECT;  // Green
            isGreen[i] = true;
        } else {
            feedback[i] = AnswerType.WRONG;    // Mark as wrong for now
            answerCharCount[a - 'a']++;        // Count occurrence of this letter in the answer
        }
    }

    // Step 2: Handle partial (yellow) matches
    for (int i = 0; i < wordLength; i++) {
        if (!isGreen[i]) {  // Only process if it's not already marked as green
            char g = guess.charAt(i);
            
            if (answerCharCount[g - 'a'] > 0) {
                feedback[i] = AnswerType.MISPLACED;  // Yellow
                answerCharCount[g - 'a']--;        // Decrease the count since we used one occurrence
            }
        }
    }

    return new WordleWord(guess, feedback);
}
    }

