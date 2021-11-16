package com.example.classroom.ui.dashboard

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.classroom.databinding.ExamItemBinding
import com.example.classroom.model.userData
import com.example.classroom.room.Exam
import java.util.*

class ExamAdapter(private val context: Context, private var exams: List<Exam>) :
    RecyclerView.Adapter<ExamAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ExamItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exam = exams[position]
        holder.bind(exam)
    }

    override fun getItemCount(): Int = exams.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(exams: List<Exam>) {
        this.exams = exams
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ExamItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(exam: Exam) {
            binding.apply {
                classroomName.text = userData[exam.classroom].name
                totalMarks.text = "${exam.totalMarks} marks"
                category.text = exam.category

                val startTime = exam.timeframe.split("/")[0].toInt()
                val endTime = exam.timeframe.split("/")[1].toInt()
                val dateTime = exam.dateTime

                var newTimeFrame = ""

                newTimeFrame += if(startTime < 12) {
                    "$startTime am "
                } else {
                    "$startTime pm "
                }

                newTimeFrame += if(endTime < 12) {
                    "$endTime am"
                } else {
                    "$endTime pm"
                }

                timeframe.text = newTimeFrame

                val calendar = Calendar.getInstance()
                take.isVisible = (calendar.get(Calendar.YEAR) == dateTime.get(Calendar.YEAR) && calendar.get(
                    Calendar.MONTH
                ) == dateTime.get(Calendar.MONTH)
                        && calendar.get(Calendar.DAY_OF_MONTH) == dateTime.get(Calendar.DAY_OF_MONTH) && calendar.get(
                    Calendar.HOUR_OF_DAY
                ) >= startTime
                        && calendar.get(Calendar.HOUR_OF_DAY) <= endTime)
            }
        }
    }
}