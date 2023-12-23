package com.example.ikt205_todoapp.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ToDoList(
    var id: String = "",
    var name: String,
    var nrOfTasks: Int = 0,
    var tasksFinished: Int = 0,
    var tasks:MutableList<ToDoItem> = mutableListOf()
):Parcelable

@Parcelize
data class ToDoItem (
    val id: String = "",
    var name: String = "",
    var isCompleted: Boolean = false
): Parcelable {
    public fun changeStatus() {
        isCompleted = !isCompleted
    }
}
