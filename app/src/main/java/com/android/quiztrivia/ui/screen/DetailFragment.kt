package com.android.quiztrivia.ui.screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.quiztrivia.databinding.FragmentDetailBinding
import com.android.quiztrivia.util.model.Item
import com.android.quiztrivia.util.model.ResultArg
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.json.Json

/**
 * detail fragment, invoked when pressed on the item of recycler view
 */
@AndroidEntryPoint
class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var data: String
    lateinit var resultArg: ResultArg

    companion object{
        private const val TAG = "detailFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            Log.i(TAG, "Arguments for detail fragment received")
            data = DetailFragmentArgs.fromBundle(it).detailArgument
            resultArg= Json.decodeFromString(data)
            Log.i(TAG, "Arguments for detail fragment settled")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        Log.i(TAG, "Detail Frag created")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = this@DetailFragment
            detailFragment = this@DetailFragment
            detailImageView.setImageResource(resultArg.item.image)
        }
    }

    fun navigateToStartQuiz() {
        Log.i(TAG, "Navigation Started")
        val action = DetailFragmentDirections.actionDetailFragmentToQuizFragment(data)
        findNavController().navigate(action)
        Log.i(TAG, "Navigation Performed")
    }

}