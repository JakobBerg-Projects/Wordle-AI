package no.uib.inf102.wordle.controller.AI;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import no.uib.inf102.wordle.model.Dictionary;
import no.uib.inf102.wordle.model.word.WordleAnswer;
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
            guesses.eliminateWords(feedback);  // Eliminate based on actual feedback
        }

        // Step 1: Calculate letter frequencies for remaining possible answers
        Map<Character, Integer> frequencyMap = calculateLetterFrequencies();

        // Step 2: Iterate over possible guesses and find the best one based on letter frequencies
        String bestString = null;
        int bestScore = -1;

        for (String guess : guesses.possibleAnswers()) {
            int currentScore = scoreWord(guess, frequencyMap);
            
            if (currentScore > bestScore) {
                bestScore = currentScore;
                bestString = guess;
            }
        }

        return bestString;  // Return the word with the best score based on frequencies
    }

    // Calculate the frequency of each character in the remaining possible answers
    private Map<Character, Integer> calculateLetterFrequencies() {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        
        for (String word : guesses.possibleAnswers()) {
            for (char c : word.toCharArray()) {
                frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
            }
        }

        return frequencyMap;
    }

    // Score a word based on how common its characters are in the remaining answers
    private int scoreWord(String word, Map<Character, Integer> frequencyMap) {
        int score = 0;
        
        // Only count the first occurrence of each letter in the word
        HashSet<Character> seen = new HashSet<>();
        for (char c : word.toCharArray()) {
            if (!seen.contains(c)) {
                score += frequencyMap.getOrDefault(c, 0);
                seen.add(c);
            }
        }

        return score;
    }

    @Override
    public void reset() {
        guesses = new WordleWordList(dictionary);
        guessCount = 0;
    }
}

