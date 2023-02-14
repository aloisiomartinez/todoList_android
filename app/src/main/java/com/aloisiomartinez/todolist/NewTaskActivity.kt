package com.aloisiomartinez.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aloisiomartinez.todolist.Constants.EXTRA_NEW_TASK
import com.aloisiomartinez.todolist.databinding.ActivityNewTaskBinding

class NewTaskActivity : AppCompatActivity() {

    private val binding by lazy { ActivityNewTaskBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnSubmit.setOnClickListener {
            onSubmit()
        }


    }

    private fun onSubmit() {
        if(binding.edtTaskTitle.text.isEmpty()) {
            binding.edtTaskTitle.error = "Por favor, preencha o título da tarefa"
            binding.edtTaskTitle.requestFocus()
            return
        }

        if(binding.edtDescriptionTask.text.isEmpty()) {
            binding.edtDescriptionTask.error = "Por favor, preencha a descrição da tarefa"
            binding.edtDescriptionTask.requestFocus()
            return
        }

        val newTask = Task(
            binding.edtTaskTitle.text.toString(),
            binding.edtDescriptionTask.text.toString()
        )

        val intentResult = Intent()
        intentResult.putExtra(EXTRA_NEW_TASK, newTask)
        setResult(RESULT_OK, intentResult)
        finish()
    }
}