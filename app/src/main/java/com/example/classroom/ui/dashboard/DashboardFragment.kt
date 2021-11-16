package com.example.classroom.ui.dashboard

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.DimenRes
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.classroom.R
import com.example.classroom.databinding.FragmentDashboardBinding
import com.example.classroom.model.Classroom
import com.example.classroom.model.userData
import com.example.classroom.ui.FragmentCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class DashboardFragment(private val fragmentCallback: FragmentCallback) : Fragment(), AdapterView.OnItemSelectedListener {

    private val dashboardViewModel: DashboardViewModel by viewModels()

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding

    private lateinit var examAdapter: ExamAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        bindUI()
        updateAttendanceData(userData[0])

        return binding?.root
    }

    private fun bindUI() {
        examAdapter = ExamAdapter(requireContext(), listOf())

        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            userData.map { it.name })

        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx =
            resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            page.scaleY = 1 - (0.25f * abs(position))
            page.alpha = 0.5f + (1 - abs(position))
        }

        val itemDecoration = HorizontalMarginItemDecoration(
            requireContext(),
            R.dimen.viewpager_current_item_horizontal_margin
        )

        binding?.apply {
            spinner.apply {
                adapter = spinnerAdapter
                onItemSelectedListener = this@DashboardFragment
            }
            viewPager.apply {
                Log.d("Dashboard", dashboardViewModel.exams.toString())
                adapter = examAdapter
                offscreenPageLimit = 1
                setPageTransformer(pageTransformer)
                addItemDecoration(itemDecoration)
            }
            addExam.setOnClickListener {
                fragmentCallback.openFragmentById(R.id.nav_exams)
            }
            dashboardViewModel.exams.observe(viewLifecycleOwner, {
                Log.d("Dashboard", it.toString())
                if(it != null)
                examAdapter.updateData(it)
            })
        }
    }

    private fun updateAttendanceData(classroom: Classroom) {
        val attendanceCount =
            (classroom.attendedClasses * 100) / (classroom.totalClass - classroom.leftClasses)
        binding?.apply {
            totalClasses.text = classroom.totalClass.toString()
            classesAttended.text = classroom.attendedClasses.toString()
            classesMissed.text = classroom.missedClasses.toString()
            classesLeft.text = classroom.leftClasses.toString()

            progress.apply {
                progress = attendanceCount
            }

            lowAttendance.isVisible = attendanceCount <= 75
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        updateAttendanceData(userData[position])
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}

class HorizontalMarginItemDecoration(context: Context, @DimenRes horizontalMarginInDp: Int) :
    RecyclerView.ItemDecoration() {

    private val horizontalMarginInPx: Int =
        context.resources.getDimension(horizontalMarginInDp).toInt()

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        outRect.right = horizontalMarginInPx
        outRect.left = horizontalMarginInPx
    }

}