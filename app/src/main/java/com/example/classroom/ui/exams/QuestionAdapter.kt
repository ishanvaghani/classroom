package com.example.classroom.ui.exams

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.view.allViews
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.classroom.databinding.AddOptionDialogBinding
import com.example.classroom.databinding.QuestionItemBinding
import com.example.classroom.databinding.RadioButtonBinding
import com.example.classroom.model.Question

class QuestionAdapter(
    private val context: Context,
    private var questions: ArrayList<Question>
) : RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = QuestionItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val question = questions[position]
        holder.bind(question, position)
    }

    override fun getItemCount(): Int = questions.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateQuestions(questions: ArrayList<Question>) {
        this.questions = questions
        notifyDataSetChanged()
    }

    fun getQuestions(): List<Question> {
        return questions
    }

    inner class ViewHolder(private val binding: QuestionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(question: Question, position: Int) {
            binding.apply {
                this.question.setText(question.question)
                this.question.isEnabled = question.isEditing
                question.options.forEachIndexed { index, string ->
                    val radioButtonBinding = RadioButtonBinding.inflate(
                        LayoutInflater.from(context),
                        binding.root,
                        false
                    )
                    radioButtonBinding.root.id = index
                    radioButtonBinding.root.text = string
                    options.addView(radioButtonBinding.root)
                }
                addOption.setOnClickListener { showAddOptionDialog() }
                if (question.isEditing) {
                    save.isVisible = true
                    delete.isVisible = true
                    edit.isVisible = false
                    remove.isVisible = false
                } else {
                    edit.isVisible = true
                    remove.isVisible = true
                    save.isVisible = false
                    delete.isVisible = false
                }
                save.setOnClickListener {
                    val optionList = ArrayList<String>()
                    binding.options.children.forEach {
                        optionList.add((it as RadioButton).text.toString())
                    }
                    questions.removeAt(position)
                    questions.add(
                        position,
                        Question(
                            questions.size,
                            binding.question.text.toString(),
                            false,
                            optionList
                        )
                    )
                    notifyItemChanged(position)
                }
                delete.setOnClickListener {
                    questions.removeAt(position)
                    notifyItemChanged(position)
                }
            }
        }

        private fun showAddOptionDialog() {
            val addOptionDialogBinding =
                AddOptionDialogBinding.inflate(LayoutInflater.from(context), binding.root, false)
            val dialogBuilder = AlertDialog.Builder(context)
            dialogBuilder.setView(addOptionDialogBinding.root)
            val dialog = dialogBuilder.create()
            dialog.setCanceledOnTouchOutside(true)
            dialog.show()

            addOptionDialogBinding.apply {
                add.setOnClickListener {
                    val radioButtonBinding = RadioButtonBinding.inflate(
                        LayoutInflater.from(context),
                        binding.root,
                        false
                    )
                    radioButtonBinding.root.id = binding.options.childCount
                    radioButtonBinding.root.text = option.text.toString()
                    binding.options.addView(radioButtonBinding.root)
                    dialog.dismiss()
                    binding.addOption.isVisible = binding.options.childCount < 4
                }
            }
        }
    }

}