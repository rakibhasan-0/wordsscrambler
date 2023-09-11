package com.example.wordsscrambler
import androidx.lifecycle.ViewModel

class GamePlayViewModel : ViewModel() {

    private var score: Int = 0
    private var currentWordsCount = 0
    private var allWords: List<String> = listOf()
    private var wordsList: MutableList<String> = mutableListOf()
    private lateinit var currentScrambledWord: String


    init{
        allWords = Words().getWords()
    }

    fun getNextWord() {
        if(currentWordsCount < 10){
            var words = allWords.random()
            val tempWord = words.toCharArray()
            tempWord.shuffle()
            currentScrambledWord = String(tempWord)

            while (wordsList.contains(currentScrambledWord)) {
                tempWord.shuffle()
                currentScrambledWord = String(tempWord)
            }

            wordsList.add(currentScrambledWord)
            updateCurrentWords()
        }
    }


    fun checkUserWords(playerWords: String): Boolean {
        if(allWords.contains(playerWords)){
            return true
        }
        return false
    }

    fun increaseScore(){
        score += 10
    }

    fun getScore(): Int {
        return score
    }

    fun getCurrentWordsCount(): Int {
        return currentWordsCount
    }

    private fun updateCurrentWords() {
        currentWordsCount++;
    }

    fun getCurrentWords(): String{
        return currentScrambledWord
    }

    fun reset(){
        score = 0
        currentWordsCount = 0
        wordsList.clear()
    }
}