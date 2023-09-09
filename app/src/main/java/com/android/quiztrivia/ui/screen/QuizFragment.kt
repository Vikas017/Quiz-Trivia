package com.android.quiztrivia.ui.screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.android.quiztrivia.R
import com.android.quiztrivia.databinding.FragmentQuizBinding
import com.android.quiztrivia.network.QuizApi
import com.android.quiztrivia.ui.QuizViewModel
import com.android.quiztrivia.ui.viewmodel.ViewModelFactory
import com.android.quiztrivia.util.model.Item
import com.android.quiztrivia.util.model.ResultArg
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

@AndroidEntryPoint
class QuizFragment: Fragment() {
    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var quizApi: QuizApi
    private lateinit var item: Item
    companion object {
        private const val TAG = "quizFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            Log.i(TAG, "Arguments for quiz fragment received")
            val data = QuizFragmentArgs.fromBundle(it).quizArguments
            item = Json.decodeFromString<ResultArg>(data).item
            Log.i(TAG, "Arguments for quiz fragment settled")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        Log.i(TAG, "Quiz Frag Initialized bindings")
        return binding.root
    }


    private val viewModel: QuizViewModel by viewModels { ViewModelFactory( quizApi, item) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = this@QuizFragment
            quizViewModel = viewModel
            quizFragment = this@QuizFragment
        }
        observeCounter()
        if (viewModel.counter.value!! == viewModel.showQuizListSize())
            binding.nextAndSkipBtn.text = context?.getString(R.string.submit)

    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    //helper function
    private fun isRadioButtonSelected(): Boolean {
        binding.apply {
            if (
                optionFourRadioButton.isChecked ||
                optionThreeRadioButton.isChecked ||
                optionTwoRadioButton.isChecked ||
                optionOneRadioButton.isChecked
            ) {
                Log.i(TAG, "Radio Button selected")
                return true
            }
            Log.i(TAG, "Radio Button not selected")
            return false
        }
    }

    private fun observeCounter() {
        val observer = Observer<Int> {
            binding.questionCounterTextView.text =
                context?.getString(R.string.string_counter, it, viewModel.showQuizListSize())
        }
        viewModel.counter.observe(viewLifecycleOwner, observer)
    }

    fun onButtonClick() {
        Log.i(TAG, "Button clicked")
        if (isRadioButtonSelected()) {
            val selectedRadioText = binding.radioGroup
                .findViewById<RadioButton>(binding.radioGroup.checkedRadioButtonId).text
            Log.i(TAG, "Radio button id received")
            viewModel.increaseCounter(selectedRadioText.toString()) {
                //do on submit the quiz
                Log.i(TAG, "ViewModel increaseCounter() called")
                val score = viewModel.showScore()
                val resultArg: ResultArg
                when (score) {
                    10 -> resultArg = ResultArg(
                        item = item,
                        correct = score,
                        incorrect = viewModel.showQuizListSize()-score,
                            dayGif = R.drawable.amazing_day,
                            nightGif = R.drawable.amazing_night
                        )
                    in 8..9 -> resultArg = ResultArg(
                        item = item,
                        correct = score,
                        incorrect = viewModel.showQuizListSize()-score,
                            dayGif = R.drawable.excellent_day,
                            nightGif = R.drawable.excellent_night
                        )
                    in 6..7 -> resultArg = ResultArg(
                        item = item,
                        correct = score,
                        incorrect = viewModel.showQuizListSize()-score,
                            dayGif = R.drawable.cool_day,
                            nightGif = R.drawable.cool_night
                        )
                    else -> resultArg = ResultArg(
                        item = item,
                        correct = score,
                        incorrect = viewModel.showQuizListSize()-score,
                            dayGif = R.drawable.fail_day,
                            nightGif = R.drawable.fail_night
                        )
                }

                val data = Json.encodeToString(resultArg)
                val action = QuizFragmentDirections.actionQuizFragmentToResultFragment(data)
                findNavController().navigate(action)
            }
            binding.radioGroup.clearCheck()
        } else
             Toast.makeText(context, "select an option", Toast.LENGTH_SHORT).show()
    }
}