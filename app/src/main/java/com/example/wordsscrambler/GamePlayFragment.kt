package com.example.wordsscrambler
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.wordsscrambler.databinding.FragmentGamePlayBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class GamePlayFragment : Fragment() {

    private var binding: FragmentGamePlayBinding? = null
    private val viewModel: GamePlayViewModel by lazy {
        ViewModelProvider(this).get(GamePlayViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGamePlayBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.skipButton?.setOnClickListener {
            skipWord()
        }
        binding?.submitButton?.setOnClickListener {
            checkIfCorrect()
        }

        if(viewModel.getCurrentWordsCount() == 10){
            showInformation()
        }

        updateDisplay()
    }

    private fun skipWord() {
        // Implement the logic to skip the current word and get the next word from the ViewModel
        updateDisplay()
        binding?.currentNumberOfWords?.text = "${viewModel.getCurrentWordsCount()} of 10"
        binding?.scoreText?.text = "Score: ${viewModel.getScore()}"

    }

    private fun checkIfCorrect() {
        // Implement the logic to check if the user's input is correct
        val playerWord = binding?.playerText?.text.toString()

        if(viewModel.checkUserWords(playerWord)){
            viewModel.increaseScore()
            binding?.scoreText?.text = "Score: ${viewModel.getScore()}"
        }
        updateDisplay()
    }

    private fun updateDisplay() {
        // Get the new scrambled word before updating the UI
        viewModel.getNextWord()
        binding?.displayText?.text = viewModel.getCurrentWords()
        binding?.currentNumberOfWords?.text= "${viewModel.getCurrentWordsCount()} of 10"
        binding?.playerText?.setText("") // user input should be empty now

        if (viewModel.getCurrentWordsCount() == 10) {
            showInformation()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun showInformation(){
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Congratulations")
            .setMessage("You scored: ${viewModel.getScore()}")
            .setNegativeButton("Exit") { _, _ ->
                activity?.finish()
            }
            .setPositiveButton("Start Over") { _, _ -> resetGame() }
            .show()
    }

    private fun resetGame(){
        viewModel.reset()
        updateDisplay()
    }

}
