package no.uib.inf102.wordle.controller.AI;


import java.util.HashSet;


import no.uib.inf102.wordle.model.Dictionary;
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
            guesses.eliminateWords(feedback); // O(m*k)
        }

        String bestString = null; // O(1)
        int highestScore = - 1; // O(1)
        int possibleAnswersSize = guesses.possibleAnswers().size(); // O(1)
        boolean enforceDistinctAndUnseen = possibleAnswersSize >= dictionary.answerWordsSize()* 0.5; // O(1)
        guesses.computePositionFrequencies(); // O(m * k)

        for (String possibleWord : guesses.possibleAnswers()) { // O(m)
            

            // Enforcing different unseen characters if the possible answers hasnt been halfed from the dictionairy
            if (enforceDistinctAndUnseen) { // O(1)
                if (!hasUniqueCharacters(possibleWord) || charIsSeen(possibleWord)) { 
                    continue;
                }
            }
            
            int currentScore = WordleWordList.countGreenMatches(possibleWord, guesses.positionFrequencies); // O(k){
            
            
            

        if (currentScore > highestScore) { // O(1)
            highestScore = currentScore; // O(1)
            bestString = possibleWord; // O(1)
        }
    }
    addCharsToSeen(bestString); //

    return bestString; // O(1)
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

