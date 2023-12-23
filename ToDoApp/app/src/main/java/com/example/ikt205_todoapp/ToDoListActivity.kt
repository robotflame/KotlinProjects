package com.example.ikt205_todoapp

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ikt205_todoapp.data.*
import com.example.ikt205_todoapp.databinding.ActivityToDoListBinding

class ToDoListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityToDoListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityToDoListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val receivedToDoList = intent.getParcelableExtra<ToDoList>("EXTRA_TODO_INFO")

        if(receivedToDoList != null) {
            binding.todoItemListing.layoutManager = LinearLayoutManager(this)
            binding.todoItemListing.adapter = ToDoItemAdapter(receivedToDoList.tasks, receivedToDoList)

        } else {
            finish()
            return
        }


        ToDoManager.instance.onToDoItemDelete = {
            (binding.todoItemListing.adapter as ToDoItemAdapter).updateCollection2()
        }

        binding.addItemFloatingActionButton.setOnClickListener {
            addNewToDoItemDialog(receivedToDoList)

        }
    }

    private fun addNewToDoItemDialog(toDoList: ToDoList) {
        val dialog = AlertDialog.Builder(this)
        val itemEditText = EditText(this)
        dialog.setMessage("Add New todo item")
        dialog.setTitle("Enter To Do item Text")
        dialog.setView(itemEditText)
        dialog.setPositiveButton("Submit") { _: DialogInterface, _: Int ->
            if (itemEditText.text.isNotEmpty()) {
                ToDoManager.instance.addToDoItem(toDoList, ToDoItem(name = itemEditText.text.toString()))
            }
        }

        dialog.setNegativeButton("Cancel") { _, _ -> }
        dialog.show()
    }
}