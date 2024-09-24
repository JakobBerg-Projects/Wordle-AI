package no.uib.inf102.wordle.controller.AI;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import no.uib.inf102.wordle.model.Dictionary;
import no.uib.inf102.wordle.model.word.AnswerType;
import no.uib.inf102.wordle.model.word.WordleAnswer;
import no.uib.inf102.wordle.model.word.WordleCharacter;
import no.uib.inf102.wordle.model.word.WordleWord;
import no.uib.inf102.wordle.model.word.WordleWordList;

public class MyStrategy implements IStrategy {

    private Dictionary dictionary;
    private WordleWordList guesses;
    int guessCount = 0;
    private HashSet<Character> seenChars = new HashSet<>();

    public MyStrategy(Dictionary dictionary) {
        this.dictionary = dictionary;
        reset();
    }

    @Override
    public String makeGuess(WordleWord feedback) {
        if (feedback != null) {
            //System.out.println(guesses.possibleAnswers().size());
            guesses.eliminateWords(feedback);
        }


        String bestString = null;
        int mostGreenMatches = - 1;
        int possibleAnswersSize = guesses.possibleAnswers().size();
        boolean enforceDistinctAndUnseen = possibleAnswersSize >= dictionary.answerWordsSize()* 0.5;

        for (String possibleWord : guesses.possibleAnswers()) {

            // Enforcing different unseen characters if the possible answers hasnt been halfed from the dictionairy
            if (enforceDistinctAndUnseen) {
                if (!hasUniqueCharacters(possibleWord) || charIsSeen(possibleWord)) {
                    continue;
                }
            }
            

            int currentGreenMatches;
            currentGreenMatches = WordleWordList.countGreenMatches(possibleWord, guesses.possibleAnswers());
            

        if (currentGreenMatches > mostGreenMatches) {
            mostGreenMatches = currentGreenMatches;
            bestString = possibleWord;
        }
    }
    addCharsToSeen(bestString);

    return bestString;
    }


    
    
    private boolean hasUniqueCharacters(String word) {
        HashSet<Character> uniqueChars = new HashSet<>();
        for (char c : word.toCharArray()) {
            if (!uniqueChars.add(c)) {
                return false;  // Duplicate character found
            }
        }
        return true;  // All characters are unique
    }

    private void addCharsToSeen(String bestWord){
        for (Character character : bestWord.toCharArray()) {
            seenChars.add(character);
        }
    }
    private boolean charIsSeen(String word){
        for (Character character : word.toCharArray()) {
            if(seenChars.contains(character)){
                return true;
            }
        }
        return false;
    }

    

    @Override
    public void reset() {
        guesses = new WordleWordList(dictionary);
        seenChars.clear();
    }

    
}

