package ru.netology.nerecipe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nerecipe.R
import ru.netology.nerecipe.databinding.RecipeListItemBinding
import ru.netology.nerecipe.dto.RecipeWithInfo
import java.util.*
import kotlin.collections.ArrayList


internal class RecipeAdapter(
    private val interactionListener: RecipeInteractionListener
) : ListAdapter<RecipeWithInfo, RecipeAdapter.ViewHolder>(DiffCallBack) {

 //   val initialRecipeDataList = ArrayList<RecipeWithInfo>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecipeListItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
//-----------add search
//
//    override fun getItemCount(): Int {
//        return initialRecipeDataList.size
//    }
//
//    fun getFilter(): Filter {
//        return recipeFilter
//    }
//
//    private val recipeFilter = object : Filter() {
//        override fun performFiltering(constraint: CharSequence?): FilterResults {
//            val filteredList: ArrayList<RecipeWithInfo> = ArrayList()
//            if (constraint == null || constraint.isEmpty()) {
//                initialRecipeDataList.let { filteredList.addAll(it) }
//            } else {
//                val query = constraint.toString().trim().lowercase(Locale.getDefault())
//                initialRecipeDataList.forEach {
//                    if (it.recipe.recipeName.lowercase(Locale.ROOT).contains(query)) {
//                        filteredList.add(it)
//                    }
//                }
//            }
//            val results = FilterResults()
//            results.values = filteredList
//            return results
//        }
//
//        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
//            if (results?.values is ArrayList<*>) {
//                initialRecipeDataList.clear() //TODO how to clear main list
//                initialRecipeDataList.addAll(results.values as ArrayList<RecipeWithInfo>)
//                notifyDataSetChanged()
//            }
//        }
//    }


    //--------search

    class ViewHolder(
        private val binding: RecipeListItemBinding,
        private val listener: RecipeInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {



        private lateinit var recipe: RecipeWithInfo



        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.menu).apply {
                inflate(R.menu.options_recipe)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.remove -> {
                            listener.onRemoveClicked(recipe.recipe)
                            true
                        }
                        R.id.edit -> {
                            listener.onEditClicked(recipe.recipe)
                            true
                        }
                        else -> false
                    }
                }
            }
        }

        init {
            binding.favorites.setOnClickListener { listener.onFavoriteClicked(recipe.recipe) }
            binding.menu.setOnClickListener { popupMenu.show() }
            binding.recipeName.setOnClickListener { listener.onShowRecipeClicked(recipe.recipe) }
            binding.category.setOnClickListener { listener.onShowRecipeClicked(recipe.recipe) }
            binding.author.setOnClickListener { listener.onShowRecipeClicked(recipe.recipe) }
            binding.authorName.setOnClickListener { listener.onShowRecipeClicked(recipe.recipe) }
        }

        fun bind(recipe: RecipeWithInfo) {
            this.recipe = recipe
            with(binding) {
                recipeName.text = recipe.recipe.recipeName
                category.text = recipe.category.categoryName
                authorName.text = recipe.recipe.authorName
                favorites.isChecked = recipe.recipe.isFavorites
            }
        }
    }
    private object DiffCallBack : DiffUtil.ItemCallback<RecipeWithInfo>() {
        override fun areItemsTheSame(oldItem: RecipeWithInfo, newItem: RecipeWithInfo) =
            oldItem.recipe.recipeId == newItem.recipe.recipeId

        override fun areContentsTheSame(oldItem: RecipeWithInfo, newItem: RecipeWithInfo) =
            oldItem == newItem

    }
}




//class CityListAdapter(private var cityDataList: ArrayList<CityDataObject>) :
//    RecyclerView.Adapter<CityViewHolder>() {
//
//    // Create a copy of localityList that is not a clone
//    // (so that any changes in localityList aren't reflected in this list)
//    val initialCityDataList = ArrayList<CityDataObject>().apply {
//        cityDataList?.let { addAll(it) }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
//        return CityViewHolder(
//            LayoutInflater.from(parent.context)
//                .inflate(R.layout.city_name_row_layout, parent, false)
//        )
//    }
//
//    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
//        holder.bind(cityDataList[position])
//    }
//
//    override fun getItemCount(): Int {
//        return cityDataList.size
//    }
//
//    fun getFilter(): Filter {
//        return cityFilter
//    }
//
//    private val cityFilter = object : Filter() {
//        override fun performFiltering(constraint: CharSequence?): FilterResults {
//            val filteredList: ArrayList<CityDataObject> = ArrayList()
//            if (constraint == null || constraint.isEmpty()) {
//                initialCityDataList.let { filteredList.addAll(it) }
//            } else {
//                val query = constraint.toString().trim().toLowerCase()
//                initialCityDataList.forEach {
//                    if (it.cityName.toLowerCase(Locale.ROOT).contains(query)) {
//                        filteredList.add(it)
//                    }
//                }
//            }
//            val results = FilterResults()
//            results.values = filteredList
//            return results
//        }
//
//        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
//            if (results?.values is ArrayList<*>) {
//                cityDataList.clear()
//                cityDataList.addAll(results.values as ArrayList<CityDataObject>)
//                notifyDataSetChanged()
//            }
//        }
//    }
//}

//class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//    private val cityName: TextView = itemView.findViewById(R.id.city_name)
//    private val cityCode: TextView = itemView.findViewById(R.id.city_code)
//    fun bind(cityDataObject: CityDataObject) {
//        cityName.text = cityDataObject.cityName
//        cityCode.text = cityDataObject.cityCode
//    }
//}


