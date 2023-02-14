package com.aloisiomartinez.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.aloisiomartinez.todolist.Constants.EXTRA_NEW_TASK
import com.aloisiomartinez.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TaskAdapter
    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->

        if (result.resultCode != RESULT_OK)
            return@registerForActivityResult


        val task = result.data?.extras?.getSerializable(EXTRA_NEW_TASK) as Task
        adapter.addTask(task)
        onDataUpdate()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAdapter()
        setupLayout()

    }

    private fun onDataUpdate() = if (adapter.isEmpty()) {
        binding.rvTasks.visibility = View.GONE
        binding.tvNoData.visibility = View.VISIBLE
    } else {
        binding.rvTasks.visibility = View.VISIBLE
        binding.tvNoData.visibility = View.GONE
    }

    private fun setupLayout() {
        binding.fabNewTask.setOnClickListener {

            resultLauncher.launch(Intent(this, NewTaskActivity::class.java))

        }
    }

    private fun setupAdapter() {

        adapter = TaskAdapter(
            onDeleteClick = { taskToConfirmDeletion ->
                showDeleteConfirmation(taskToConfirmDeletion) { taskToBeDeleted ->
                    adapter.deleteTask(taskToBeDeleted)
                    onDataUpdate()
                }
            },
            onClick = { taskToBeShowed ->
                showTaskDetails(taskToBeShowed) { taskToBeUpdated ->
                    adapter.updateTask(taskToBeUpdated)
                }
            }
        )
        binding.rvTasks.adapter = adapter
        onDataUpdate()

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
                    if (task.done)
                        "Sim"
                    else
                        "Não"
                }
                """.trimIndent()
            )
            setPositiveButton(if (task.done) "Não concluída" else "Concluída") { _, _ ->
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