package com.example.ikt205_todoapp.data


class ToDoManager {
    private lateinit var toDoListCollection: MutableList<ToDoList>
    var onToDoList: ((List<ToDoList>) -> Unit)? = null
    var onToDoListDelete: (() -> Unit)? = null
    var onToDoItemDelete: (() -> Unit)? = null

    fun load() {
        toDoListCollection = mutableListOf(
            ToDoList("ID_1", "Todo list 1", 5, 2, mutableListOf<ToDoItem>(ToDoItem("22", "t1", true), ToDoItem("1", "t2", true))),
            ToDoList("ID_2", "Todo list 2", 5, 2, mutableListOf<ToDoItem>()),
            ToDoList("ID_3", "Todo list 3", 5, 2, mutableListOf<ToDoItem>()),
            ToDoList("ID_4", "Todo list 4", 5, 2, mutableListOf<ToDoItem>()),
            ToDoList("ID_5", "Todo list 5", 5, 2, mutableListOf<ToDoItem>())
        )

        onToDoList?.invoke(toDoListCollection)
    }

    fun addToDoList(todolist: ToDoList) {
        toDoListCollection.add(todolist)
        onToDoList?.invoke(toDoListCollection)
    }

    fun addToDoItem(todoList: ToDoList, todoItem: ToDoItem){
        todoList.tasks.add(todoItem)
        todoList.nrOfTasks++
    }

    fun deleteToDoList(todolist: ToDoList) {
        toDoListCollection.remove(todolist)
        onToDoListDelete?.invoke()
    }

    fun deleteToDoItem(todolist: ToDoList, todoItem: ToDoItem) {
        todolist.tasks.remove(todoItem)
        //ToDoListSelected.SelectedToDoList?.deleteToDoItem(todoItem)
        onToDoItemDelete?.invoke()
    }

    fun statusUpdate(todoList: ToDoList, todoItem: ToDoItem) {
        todoItem.changeStatus()
        if(todoItem.isCompleted) {
            todoList.tasksFinished++
        } else {todoList.tasksFinished--}

    }


    companion object {
        val instance = ToDoManager()
    }
}
