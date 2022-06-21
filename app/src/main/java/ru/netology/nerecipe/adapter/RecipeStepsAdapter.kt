package ru.netology.nerecipe.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.netology.nerecipe.R
import ru.netology.nerecipe.databinding.RecipeStepsBinding
import ru.netology.nerecipe.dto.Steps


internal class RecipeStepsAdapter(

) : ListAdapter<Steps, RecipeStepsAdapter.ViewHolderSteps>(DiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderSteps {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecipeStepsBinding.inflate(inflater, parent, false)
        return ViewHolderSteps(binding)
    }


    override fun onBindViewHolder(holder: ViewHolderSteps, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolderSteps(
        private val binding: RecipeStepsBinding

    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var steps: Steps



        fun bind(steps: Steps) {
            this.steps = steps
            with(binding) {
                numberOfStep.text = steps.numberOfStep.toString()
                descriptionOfStep.text = steps.contentOfStep
                if (steps.imageUrl != null) {

                    val picasso =
                        Picasso.Builder(imageOfStep.context)
                            .listener { picasso, uri, exception ->
                                println(exception.message)
                            }
                            .build()
                    picasso
                        .load(steps.imageUrl)
                       // .resize(700, 500)
                        .error(R.drawable.ic_baseline_error_outline_24)

                        .into(imageOfStep)
;
                } else {
                    imageOfStep.visibility = View.GONE

                }
            }

        }
    }

    private object DiffCallBack : DiffUtil.ItemCallback<Steps>() {
        override fun areItemsTheSame(oldItem: Steps, newItem: Steps) =
            oldItem.stepId == newItem.stepId

        override fun areContentsTheSame(oldItem: Steps, newItem: Steps) =
            oldItem == newItem

    }


}