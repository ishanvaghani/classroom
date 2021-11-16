package com.example.classroom.ui.exams

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.classroom.databinding.FragmentExamsBinding
import java.util.*

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener

import android.app.TimePickerDialog
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.classroom.R
import com.example.classroom.model.userData
import com.example.classroom.ui.FragmentCallback
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExamsFragment(private val fragmentCallback: FragmentCallback) : Fragment(), AdapterView.OnItemSelectedListener {

    private var _binding: FragmentExamsBinding? = null
    private val binding get() = _binding

    private val examViewModel: ExamViewModel by viewModels()

    private lateinit var calendar: Calendar

    private var currentClassroom: Int = 0

    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0
    private var hour: Int = 0
    private var minute: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExamsBinding.inflate(inflater, container, false)

        calendar = Calendar.getInstance()

        bindUI()

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        examViewModel.apply {
            classRoom.value?.let { binding?.spinner?.setSelection(it) }
            binding?.syllabus?.setText(syllabus.value)
            if(dateTime.value != null) {
                binding?.date?.text =
                    "${dateTime.value?.time?.date} / ${dateTime.value?.time?.month} / ${dateTime.value?.time?.year}"
                binding?.time?.text =
                    "${dateTime.value?.time?.hours} : ${dateTime.value?.time?.minutes}"
            }
            binding?.duration?.setText(duration.value)
            binding?.timeframe?.setText(timeframe.value)
            totalMark.value?.let { binding?.totalMarks?.setText(it.toString()) }
            binding?.category?.setText(category.value)
        }
    }

    private fun bindUI() {

        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            userData.map { it.name })

        binding?.apply {
            date.setOnClickListener {
                showDatePicker()
            }
            time.setOnClickListener {
                showTimePicker()
            }
            next.setOnClickListener {
                saveData()

                val bundle = Bundle()
                bundle.putInt("classroom", currentClassroom)
                bundle.putString("syllabus", syllabus.text.toString())
                bundle.putSerializable("dateTime", calendar)
                bundle.putString("duration", duration.text.toString())
                bundle.putString("timeframe", timeframe.text.toString())
                bundle.putInt("totalMarks", totalMarks.text.toString().toInt())
                bundle.putString("category", category.text.toString())

                val examSectionFragment = ExamSectionFragment(fragmentCallback)
                examSectionFragment.arguments = bundle

                val transition = parentFragmentManager.beginTransaction()
                transition.replace(R.id.container, examSectionFragment)
                transition.addToBackStack(null)
                transition.commit()
            }
            spinner.apply {
                adapter = spinnerAdapter
                onItemSelectedListener = this@ExamsFragment
            }
        }
    }

    private fun saveData() {
        calendar.set(year, month, day, hour, minute)
        examViewModel.apply {
            classRoom.value = currentClassroom
            syllabus.value = binding?.syllabus?.text.toString()
            dateTime.value = calendar
            duration.value = binding?.duration?.text.toString()
            timeframe.value = binding?.timeframe?.text.toString()
            totalMark.value = binding?.totalMarks?.text.toString().toInt()
            category.value = binding?.category?.text.toString()
        }
    }

    private fun showDatePicker() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val myDateListener = OnDateSetListener { arg0, arg1, arg2, arg3 ->
            this.day = arg3
            this.month = arg2 + 1
            this.year = arg1
            binding?.date?.text = "$arg3 / ${arg2 + 1} / $arg1"
        }
        val datePickerDialog = DatePickerDialog(requireContext(), myDateListener, year, month, day)
        datePickerDialog
        datePickerDialog.datePicker.minDate = calendar.timeInMillis
        datePickerDialog.show()
    }

    private fun showTimePicker() {
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val mTimePicker = TimePickerDialog(
            context,
            { timePicker, selectedHour, selectedMinute ->
                this.hour = selectedHour
                this.minute = selectedMinute
                binding?.time?.text = "$selectedHour:$selectedMinute:00"
            },
            hour,
            minute,
            true
        )

        mTimePicker.setTitle("Select Time")
        mTimePicker.show()

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        currentClassroom = position
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}