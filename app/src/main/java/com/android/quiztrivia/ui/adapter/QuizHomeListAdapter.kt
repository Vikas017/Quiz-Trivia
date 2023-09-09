package com.android.quiztrivia.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.android.quiztrivia.databinding.QuizHomeListItemBinding
import com.android.quiztrivia.ui.screen.HomeFragmentDirections
import com.android.quiztrivia.util.model.Item
import com.android.quiztrivia.util.model.ResultArg
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

class QuizHomeListAdapter(
    private val quizAvailableList: List<ResultArg>,
    private val navController: NavController
    ) : RecyclerView.Adapter<QuizHomeListAdapter.QuizHomeListViewHolder>() {

    inner class QuizHomeListViewHolder(
        val binding: QuizHomeListItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizHomeListViewHolder {
        val layout =
            QuizHomeListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuizHomeListViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return quizAvailableList.size
    }

    override fun onBindViewHolder(holder: QuizHomeListViewHolder, position: Int) {
        val data = Json.encodeToString(quizAvailableList[position])
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(data)
        holder.binding.apply {
            imageView.setImageResource(quizAvailableList[position].item.image)
            imageView.contentDescription = quizAvailableList[position].item.title
            titleTextView.text = quizAvailableList[position].item.title
            itemCardView.setOnClickListener {
                navController.navigate(action) //navigate to detail screen
            }
        }
    }
}