# Wordle - Semester Assignment 1

This document describes the work I've done for the Wordle project as part of my INF102 semester assignment. The goal of this project was to implement a functional Wordle game and develop intelligent AI strategies to play the game efficiently. Below is a summary of the work I completed for each task.

---

## **1. Wordle Game Implementation**

### **MatchWord Method**
- **Task:** Implemented the `WordleAnswer::matchWord` method.
- **Description:** This method checks the guessed word and gives feedback on each letter, indicating if it's **CORRECT**, **MISPLACED**, or **WRONG**. The implementation correctly handles multiple occurrences of the same letter and adjusts the feedback accordingly.
- **Testing:** Ran `WordleAnswerTest.java` to verify the correctness of the implementation.

**Runtime Analysis:**
- **Time Complexity:** O(k * n), where `k` is the length of the word (which is constant in this case) and `n` is the number of characters in the word list.
- This method compares each character in the guessed word against the answer, resulting in a linear pass through both the guessed word and the secret word.

---

## **2. AI Strategies**

### **RandomStrategy**
- **Task:** Integrated and tested the `RandomStrategy` AI.
- **Description:** The AI randomly guesses words without considering the feedback provided by previous guesses.
- **Result:** This is a basic AI and serves as the starting point for more intelligent strategies.

### **EliminateStrategy**
- **Task:** Implemented the `EliminateStrategy` in `WordleWordList::eliminateWords`.
- **Description:** This strategy eliminates all non-possible words from the list of possible answers based on the feedback received from previous guesses. The AI narrows down its guesses by considering only words that match the given feedback, making the guessing process more efficient.
- **Testing:** Ran the `WordleWordListTest` and integrated the strategy into `WordleMain`.
- **Result:** The AI was able to solve the game in fewer guesses compared to the RandomStrategy, averaging around 4.1 guesses per game.

**Runtime Analysis:**
- **Time Complexity:** O(k * n * m), where `n` is the number of words in the list, `m` is the number of remaining possible words, and `k` is the length of each word.
- This is because the method needs to check each word in the list against the feedback for the current guess.

---

### **FrequencyStrategy**
- **Task:** Implemented the `FrequencyStrategy::makeGuess` method.
- **Description:** The strategy selects the word that has the highest expected number of correct matches (green tiles) from the list of possible words. It prioritizes common letters, improving the likelihood of eliminating incorrect words with each guess.
- **Testing:** Ran `FrequencyStrategyTest` and integrated it into `WordleMain`.
- **Result:** This AI was able to solve the game in approximately 3.93 guesses on average, outperforming the `EliminateStrategy`.

**Runtime Analysis:**
- **Time Complexity:** O(k * n * m), where `k` is the length of each word, `n` is the number of possible words, and `m` is the number of remaining guesses.
- The complexity comes from comparing each word to all other possible words in the list and calculating the number of correct matches.

---

### **MyStrategy (Improved AI)**
- **Task:** Developed an improved strategy, `MyStrategy`, that performs better than the `FrequencyStrategy`.
- **Description:** This strategy optimizes the guessing process by considering not only the frequency of common letters but also the structure of the feedback. It avoids wasting guesses on letters that are already confirmed as incorrect and prioritizes words that maximize the elimination of multiple incorrect options in one guess.
- **Testing:** Ran `AIPerformance` to evaluate performance. 
- **Result:** The AI performs better than `FrequencyStrategy`, solving the game in fewer guesses, with an average guess count lower than the expected 3.93.

**Runtime Analysis:**
- **Time Complexity:** O(k * n * m), similar to previous strategies but with additional optimizations for faster elimination of non-possible words.
- This AI performs optimizations based on previous guesses and feedback, making it more efficient in reducing the list of possible answers.

---

## **3. Code Quality**

- The code is modular, readable, and follows object-oriented principles.
- I used the **Model-View-Controller** (MVC) design pattern, ensuring that the game's logic is well-separated from the user interface.
- Comments and documentation have been added throughout the code to explain the function and purpose of key methods.

---

## **4. Runtime Analysis (svar.md)**

- **MatchWord:** O(k * n) - One pass through each word in the list.
- **EliminateWords:** O(k * n * m) - Iterates through all possible words and compares them against the feedback.
- **FrequencyStrategy::makeGuess:** O(k * n * m) - Evaluates each word in the list based on the expected number of green matches.
- **MyStrategy:** O(k * n * m) - Optimized to minimize unnecessary guesses by considering feedback more efficiently.

---

## **Conclusion**

This project has provided a comprehensive solution to implementing and improving an AI for the Wordle game. By developing multiple AI strategies—starting from a basic random approach and gradually improving the logic to make guesses more intelligently—I have demonstrated my understanding of algorithms, data structures, and runtime optimization. The final AI, `MyStrategy`, performs efficiently and effectively outperforms the initial `FrequencyStrategy` with fewer guesses on average.

---

## **Acknowledgments**

- The task was completed individually, leveraging my experience from INF101 and previous programming assignments.
- I used a **Model-View-Controller** (MVC) approach similar to the Tetris project, ensuring separation of concerns between the game's logic and user interface.
