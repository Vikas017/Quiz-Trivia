package com.android.quiztrivia.ui.screen

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.quiztrivia.R
import com.android.quiztrivia.databinding.FragmentResultBinding
import com.android.quiztrivia.util.model.ResultArg
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.json.Json

/**
 * This fragment will receive the data from
 * the quiz fragment using navigation safeargs
. */
@AndroidEntryPoint
class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!
    private lateinit var result: String
    //private val viewModel: QuizViewModel by viewModels() //this will create a new instance
    // that's why we are getting the wrong result 0 each and every time

    //helpers
    lateinit var title: String
    lateinit var score: String
    lateinit var correct: String
    lateinit var incorrect: String
    var dayGif: Int = 0
    var nightGif: Int = 0

    companion object {
        private const val TAG = "ResultFragment"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            result = ResultFragmentArgs.fromBundle(it).resultArguments
            val data = Json.decodeFromString<ResultArg>(result)
            correct = data.correct.toString()
            incorrect = data.incorrect.toString()
            val size: Int = data.incorrect + data.correct
            title = data.item.title
            score = getString(R.string.string_counter,data.correct, size)
            dayGif = data.dayGif
            nightGif = data.nightGif
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        result()
        binding.apply {
            lifecycleOwner = this@ResultFragment
            resultFragment = this@ResultFragment
        }
        gifImageNightModeSetter(nightImage = nightGif,dayImage = dayGif)
    }

    //functioning
    private fun result() {
        Log.i(TAG, "Result Function is called")

    }

    private fun gifImageNightModeSetter(nightImage: Int, dayImage: Int) {
        when(context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {
                binding.gifImageView.setImageResource(nightImage)
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                binding.gifImageView.setImageResource(dayImage)
            }
            Configuration.UI_MODE_NIGHT_UNDEFINED -> {
                binding.gifImageView.setImageResource(dayImage)
            }
        }
    }

    //start this quiz again
    fun onRestart() {
        //navigate back to the quiz
        Log.i(TAG, "Restart button pressed")
        val action = ResultFragmentDirections.actionResultFragmentToQuizFragment(result)
        findNavController().navigate(action)
    }

    //show home screen with popping up in the navigation stack
    fun onHome() {
        Log.i(TAG, "Home button pressed")
        findNavController().navigate(R.id.action_resultFragment_to_homeFragment)
    }

}