package com.example.ikt205_todoapp

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ikt205_todoapp.data.ToDoList
import com.example.ikt205_todoapp.data.ToDoListAdapter
import com.example.ikt205_todoapp.data.ToDoManager
import com.example.ikt205_todoapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.todoListListing.layoutManager = LinearLayoutManager(this)
        binding.todoListListing.adapter =
            ToDoListAdapter(emptyList<ToDoList>(), this::onToDoListClicked)

        ToDoManager.instance.onToDoList = {
            (binding.todoListListing.adapter as ToDoListAdapter).updateCollection(it)
        }

        ToDoManager.instance.onToDoListDelete = {
            (binding.todoListListing.adapter as ToDoListAdapter).updateCollection2()
        }

        ToDoManager.instance.load()

        binding.addListFloatingActionButton.setOnClickListener {
            addNewToDoListDialog()
        }
    }

    private fun addNewToDoListDialog() {
        val dialog = AlertDialog.Builder(this)
        val itemEditText = EditText(this)
        dialog.setMessage("Add New todo list")
        dialog.setTitle("Enter To Do List Text")
        dialog.setView(itemEditText)
        dialog.setPositiveButton("Submit") { _: DialogInterface, _: Int ->
            if (itemEditText.text.isNotEmpty()) {
                ToDoManager.instance.addToDoList(ToDoList(name = itemEditText.text.toString()))
            }
        }

        dialog.setNegativeButton("Cancel") { _, _ -> }
        dialog.show()
    }

    private fun onToDoListClicked(todoList: ToDoList): Unit {
        val intent = Intent(this, ToDoListActivity::class.java)
        intent.putExtra("EXTRA_TODO_INFO", todoList)


        startActivity(intent)
    }
}