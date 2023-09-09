package com.android.quiztrivia.ui.screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.quiztrivia.databinding.FragmentHomeBinding
import com.android.quiztrivia.ui.adapter.QuizHomeListAdapter
import com.android.quiztrivia.util.AVAILABLE_QUIZ
import dagger.hilt.android.AndroidEntryPoint

/**
 *
 */
@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: QuizHomeListAdapter

    companion object {
        private const val TAG = "homeFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        Log.i(TAG, "Binding set")
        adapter = QuizHomeListAdapter(AVAILABLE_QUIZ,findNavController())
        Log.i(TAG, "Adapter set with list and navController")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
        Log.i(TAG, "Recyclerview Set")
    }
}