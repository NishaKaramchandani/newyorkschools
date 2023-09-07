package com.example.baseproject.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.baseproject.databinding.FragmentSchoolDetailsBinding
import com.example.baseproject.view.data.SchoolSat
import com.example.baseproject.viewmodel.DetailsViewModel
import com.example.baseproject.viewmodel.SchoolSatListUiState
import com.example.baseproject.viewmodel.SchoolSatUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Fragment Details to display SAT scores
 */
@AndroidEntryPoint
class FragmentDetails : Fragment() {

    private lateinit var binding: FragmentSchoolDetailsBinding

    private val viewModel: DetailsViewModel by viewModels()

    companion object {
        private const val TAG = "FragmentDetails"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSchoolDetailsBinding.inflate(layoutInflater, container, false)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val selectedSchoolId = arguments?.let { FragmentDetailsArgs.fromBundle(it).schoolId }
        Log.d(TAG, "onViewCreated: $selectedSchoolId")

        if (selectedSchoolId != null) {
            viewModel.getSchoolSatData(selectedSchoolId)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.schoolSatUiState.collect { schoolSatUiState ->
                    Log.d(TAG, "onViewCreated: $schoolSatUiState")
                    when (schoolSatUiState) {
                        is SchoolSatUiState.Loading -> {
                            showLoadingView()
                        }

                        is SchoolSatUiState.Success -> {
                            val selectedSchool: SchoolSat =
                                schoolSatUiState.schoolSat
                            setSchoolSatData(selectedSchool)
                            showDetailsView()
                        }

                        is SchoolSatUiState.Error -> {
                            showErrorView(resources.getString(schoolSatUiState.errorMessageId))
                        }
                    }
                }
            }
        }
    }

    private fun showLoadingView() {
        binding.detailsLoadingProgressbar.visibility = View.VISIBLE
        binding.schoolSatContainer.visibility = View.GONE
        binding.messageTextView.visibility = View.GONE
    }

    private fun showDetailsView() {
        binding.messageTextView.visibility = View.GONE
        binding.schoolSatContainer.visibility = View.VISIBLE
        binding.detailsLoadingProgressbar.visibility = View.GONE
    }

    private fun showErrorView(message: String) {
        binding.messageTextView.visibility = View.VISIBLE
        binding.schoolSatContainer.visibility = View.GONE
        binding.detailsLoadingProgressbar.visibility = View.GONE
        binding.messageTextView.text = message
    }

    private fun setSchoolSatData(selectedSchool: SchoolSat) {
        (activity as AppCompatActivity).supportActionBar?.title = selectedSchool.name
        binding.totalTextview.text = selectedSchool.totalSat
        binding.readingTextview.text = selectedSchool.readingAvgScore
        binding.mathTextview.text = selectedSchool.mathAvgScore
        binding.writingTextview.text = selectedSchool.writingAvgScore
    }
}