package com.example.ikt205_todoapp.data

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.example.ikt205_todoapp.databinding.TodoItemBinding



class ToDoItemAdapter(private var toDoItemCollection:MutableList<ToDoItem>?, private var toDoList: ToDoList): RecyclerView.Adapter<ToDoItemAdapter.ViewHolder>() {
    class ViewHolder(val binding: TodoItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(toDoItem: ToDoItem?, toDoList: ToDoList) {
            if (toDoItem != null) {
                binding.checkBox.text = toDoItem.name
                binding.checkBox.isChecked = toDoItem.isCompleted

                binding.checkBox.setOnCheckedChangeListener { _:CompoundButton, _: Boolean ->
                    ToDoManager.instance.statusUpdate(toDoList, toDoItem)
                }


                binding.deleteItemButton.setOnClickListener {
                    ToDoManager.instance.deleteToDoItem(toDoList, toDoItem)
                }

            }


        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val toDoItem = toDoItemCollection?.get(position)
        if(toDoItem != null) {
            holder.bind(toDoItem,  toDoList)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            TodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = toDoItemCollection?.size ?: 0

    fun updateCollection2(){
        notifyDataSetChanged()
    }


}