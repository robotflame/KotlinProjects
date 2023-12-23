package com.example.ikt205_todoapp.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ikt205_todoapp.databinding.TodoListBinding

class ToDoListAdapter(private var toDoListCollection:List<ToDoList>, private val onToDoListClicked:(ToDoList)-> Unit) : RecyclerView.Adapter<ToDoListAdapter.ViewHolder>() {
    class ViewHolder(val binding: TodoListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(toDoList: ToDoList,  onToDoListClicked:(ToDoList)-> Unit) {
            binding.listText.text = toDoList.name
            binding.statusDynamic.text = toDoList.tasksFinished.toString() + "/" + toDoList.nrOfTasks.toString()
            binding.toDoListCard.setOnClickListener {
                onToDoListClicked(toDoList)
            }
            binding.deleteListButton.setOnClickListener {
                ToDoManager.instance.deleteToDoList(toDoList)
            }

        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val toDoList = toDoListCollection[position]
        holder.bind(toDoList,onToDoListClicked)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(TodoListBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }


    override fun getItemCount(): Int = toDoListCollection.size

    fun updateCollection(newToDoLists:List<ToDoList>){
        toDoListCollection = newToDoLists
        notifyDataSetChanged()
    }

    fun updateCollection2(){
        notifyDataSetChanged()
    }
}

