package ru.netology.nerecipe.adapter

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.RecyclerView


private val itemTouchHelper by lazy {
    val simpleItemTouchCallback =
        object : ItemTouchHelper.SimpleCallback(UP or DOWN or START or END, 0) {

        //    private val mAdapter: ItemTouchHelperAdapter? = null

         //   fun SimpleItemTouchHelperCallback(adapter: RecipeAdapter) {
          //      mAdapter = adapter
          //  }
            override fun isLongPressDragEnabled(): Boolean {
                return true
            }
            override fun isItemViewSwipeEnabled(): Boolean {
                return false
            }


            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val adapter = recyclerView.adapter as RecipeAdapter
                val from = viewHolder.bindingAdapterPosition
                val to = target.bindingAdapterPosition
                adapter.moveItemInRecyclerViewList(from, to)
                adapter.notifyItemMoved(from, to)
                return true
            }


            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                //val adapter =
                //    adapter.onItemDismiss(viewHolder.bindingAdapterPosition);
                //on swipe tells you when an item is swiped left or right from its position ( swipe to delete)
            }

            override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
                super.onSelectedChanged(viewHolder, actionState)

                super.onSelectedChanged(viewHolder, actionState)

                if (actionState == ACTION_STATE_DRAG) {
                    viewHolder?.itemView?.alpha = 0.5f
                }
            }

            override fun clearView(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ) {
                super.clearView(recyclerView, viewHolder)

                super.clearView(recyclerView, viewHolder)

                viewHolder.itemView.alpha = 1.0f
            }
        }

    ItemTouchHelper(simpleItemTouchCallback)
}