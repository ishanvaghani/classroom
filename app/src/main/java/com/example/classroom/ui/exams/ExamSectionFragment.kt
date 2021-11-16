package com.example.classroom.ui.exams

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.classroom.R
import com.example.classroom.databinding.FragmentExamSectionBinding
import com.example.classroom.model.Question
import com.example.classroom.model.questions
import com.example.classroom.room.Exam
import com.example.classroom.ui.FragmentCallback
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class ExamSectionFragment(private val fragmentCallback: FragmentCallback) : Fragment() {

    private val examViewModel: ExamViewModel by viewModels()

    private var _binding: FragmentExamSectionBinding? = null
    private val binding get() = _binding

    private lateinit var questionAdapter: QuestionAdapter
    private val questionList: ArrayList<Question> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExamSectionBinding.inflate(inflater, container, false)
        bindUI()

        return binding?.root
    }

    private fun bindUI() {
        questionAdapter = QuestionAdapter(requireContext(), questionList)
        binding?.apply {
            back.setOnClickListener {
                parentFragmentManager.popBackStack()
            }
            questionsRecyclerview.apply {
                setHasFixedSize(true)
                adapter = questionAdapter
            }
            addQuestion.setOnClickListener {
                questionList.add(Question(questionList.size, "", true, listOf()))
                questionAdapter.updateQuestions(questionList)
            }
            save.setOnClickListener {
                examViewModel.insert(
                    Exam(
                        arguments?.getInt("classroom")!!,
                        arguments?.getString("syllabus")!!,
                        arguments?.getSerializable("dateTime")!! as Calendar,
                        arguments?.getString("duration")!!,
                        arguments?.getString("timeframe")!!,
                        arguments?.getInt("totalMarks")!!,
                        arguments?.getString("category")!!,
                        instructions.text.toString(),
                        sectionTitle.text.toString(),
                        sectionDescription.text.toString(),
                        questionAdapter.getQuestions()
                    )
                )
                fragmentCallback.openFragmentById(R.id.nav_dashboard)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}