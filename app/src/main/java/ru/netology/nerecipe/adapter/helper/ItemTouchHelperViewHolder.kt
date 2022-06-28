package ru.netology.nerecipe.adapter.helper

import androidx.recyclerview.widget.ItemTouchHelper




interface ItemTouchHelperViewHolder {
    fun onItemSelected()

    fun onItemClear()
}