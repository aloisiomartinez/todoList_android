package com.aloisiomartinez.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aloisiomartinez.todolist.databinding.ActivityNewTaskBinding

class NewTaskActivity : AppCompatActivity() {

    private val binding by lazy { ActivityNewTaskBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


    }
}