package com.aloisiomartinez.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.aloisiomartinez.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAdapter()

        setupLayout()

    }

    private fun setupLayout() {
        binding.fabNewTask.setOnClickListener {
            val rand = (1..100).random()

            adapter.addTask(
                Task(
                    "titulo $rand",
                    "Teste"
                )
            )
        }
    }

    private fun setupAdapter() {
        adapter = TaskAdapter() {task ->
            adapter.deleteTask(task)
        }
        binding.rvTasks.adapter = adapter

    }
}