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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baseproject.adapter.SchoolAdapter
import com.example.baseproject.databinding.FragmentListBinding
import com.example.baseproject.utils.safeNavigate
import com.example.baseproject.view.data.School
import com.example.baseproject.viewmodel.ListViewModel
import com.example.baseproject.viewmodel.SchoolListUiState
import com.example.baseproject.viewmodel.SchoolSatListUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Fragment list to display list of schools
 */
@AndroidEntryPoint
class FragmentList : Fragment() {

    private lateinit var binding: FragmentListBinding

    private val viewModel: ListViewModel by viewModels()

    private var schoolAdapter = SchoolAdapter()
    
    companion object {
        private const val TAG = "FragmentList"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(layoutInflater, container, false)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply {
            adapter = schoolAdapter
            layoutManager = LinearLayoutManager(context)
            schoolAdapter.setOnItemClickListener { selectedSchool -> gotoDetails(selectedSchool) }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.schoolsUiState.collect {
                    Log.d(TAG, "onViewCreated: $it")
                    when(it) {
                        is SchoolListUiState.Loading -> {
                            showLoadingView()
                        }
                        is SchoolListUiState.Success -> {
                            showListView()
                            schoolAdapter.differ.submitList(it.schoolResponseList)
                        }
                        is SchoolListUiState.Error -> {
                            showErrorView(resources.getString(it.errorMessageId))
                        }
                    }
                }
            }
        }
    }

    private fun showLoadingView() {
        binding.loadingProgressbar.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
        binding.messageTextView.visibility = View.GONE
    }

    private fun showListView() {
        binding.messageTextView.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
        binding.loadingProgressbar.visibility = View.GONE
    }

    private fun showErrorView(message: String) {
        binding.messageTextView.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
        binding.loadingProgressbar.visibility = View.GONE
        binding.messageTextView.text = message
    }

    private fun gotoDetails(selectedSchool: School) {
        findNavController().safeNavigate(
            FragmentListDirections.actionFragmentListToFragmentDetails(
                selectedSchool.id
            )
        )
    }
}