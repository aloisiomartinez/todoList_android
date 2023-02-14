package com.aloisiomartinez.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
           startActivity(Intent(this, NewTaskActivity::class.java))
        }
    }

    private fun setupAdapter() {
        adapter = TaskAdapter(
            onDeleteClick = { taskToConfirmDeletion ->

                showDeleteConfirmation(taskToConfirmDeletion) { taskToBeDeleted ->
                    adapter.deleteTask(taskToBeDeleted)
                }
            },
            onClick = { taskToBeShowed ->
                showTaskDetails(taskToBeShowed) { taskToBeUpdated ->

                    adapter.updateTask(taskToBeUpdated)
                }
            }
        )
        binding.rvTasks.adapter = adapter

    }

    private fun showTaskDetails(task: Task, onTaskStatusChanged: (Task) -> Unit) {
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setTitle("Detalhes da tarefa")
            setMessage(
                """
                    Título: ${task.title}
                    Descrição: ${task.description}
                    Concluída: ${
                        if(task.done) 
                            "Sim"
                        else 
                            "Não"
                        
                    }
                """.trimIndent()
            )
            setPositiveButton(
                if(task.done)
                    "Não concluída"
                else
                    "Concluída"

            ) { _, _ ->
                task.done = !task.done
                onTaskStatusChanged(task)
            }
            setNegativeButton("Fechar") { dialog, _ ->
                dialog.dismiss()
            }
        }
        builder.show()
    }

    private fun showDeleteConfirmation(task: Task, onConfirm: (Task) -> Unit) {
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setTitle("Confirmação")
            setMessage("Deseja excluir a tarefa \"${task.title}\"?")
            setPositiveButton("Sim") { _, _ ->
                onConfirm(task)
            }
            setNegativeButton("Não") { dialog, _ ->
                dialog.dismiss()
            }
        }
        builder.show()
    }
}