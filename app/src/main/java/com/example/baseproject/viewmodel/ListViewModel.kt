package com.example.baseproject.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseproject.R
import com.example.baseproject.network.Repository
import com.example.baseproject.view.data.School
import com.example.baseproject.view.data.SchoolSat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

/**
 * List VM to fetch school list
 */
@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _schoolsUiState = MutableStateFlow<SchoolListUiState>(SchoolListUiState.Loading)
    val schoolsUiState: StateFlow<SchoolListUiState> = _schoolsUiState.asStateFlow()

    private val _schoolsSatUiState =
        MutableStateFlow<SchoolSatListUiState>(SchoolSatListUiState.Loading)
    val schoolsSatUiState: StateFlow<SchoolSatListUiState> = _schoolsSatUiState.asStateFlow()

    companion object {
        private const val TAG = "ListViewModel"
    }

    init {
        getAllData()
    }

    private fun getAllData() {

        val schoolListJob = viewModelScope.async {
            repository.getSchoolList().collectLatest { value: Result<List<School>> ->
                value.onSuccess { schools ->
                    if (schools.isNotEmpty()) {
                        _schoolsUiState.emit(SchoolListUiState.Success(schoolResponseList = schools))
                    } else {
                        _schoolsUiState.emit(SchoolListUiState.Error(R.string.error_fetching_schools_empty))
                    }
                }
                value.onFailure { error ->
                    val errorMessageId = when (error) {
                        is UnknownHostException -> R.string.offline_error_fetching
                        else -> R.string.error_fetching_schools
                    }
                    _schoolsUiState.emit(SchoolListUiState.Error(errorMessageId = errorMessageId))
                }
            }
        }

        val schoolSatListJob = viewModelScope.async {
            repository.getSchoolSatList().collectLatest { value: Result<List<SchoolSat>> ->
                value.onSuccess { schoolSats ->
                    if (schoolSats.isNotEmpty()) {
                        _schoolsSatUiState.emit(SchoolSatListUiState.Success(schoolsSatList = schoolSats))
                    } else {
                        _schoolsSatUiState.emit(SchoolSatListUiState.Error(R.string.error_fetching_schools_empty))
                    }
                }
                value.onFailure { error ->
                    val errorMessageId = when (error) {
                        is UnknownHostException -> R.string.offline_error_fetching
                        else -> R.string.error_fetching_school_sat
                    }
                    _schoolsSatUiState.emit(SchoolSatListUiState.Error(errorMessageId = errorMessageId))
                }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            _schoolsUiState.emit(SchoolListUiState.Loading)
            _schoolsSatUiState.emit(SchoolSatListUiState.Loading)

            schoolListJob.await()
            schoolSatListJob.await()
        }
    }
}

sealed class SchoolListUiState {
    data class Success(val schoolResponseList: List<School>) : SchoolListUiState()
    data class Error(@StringRes val errorMessageId: Int) : SchoolListUiState()
    data object Loading : SchoolListUiState()
}

sealed class SchoolSatListUiState {
    data class Success(val schoolsSatList: List<SchoolSat>) : SchoolSatListUiState()
    data class Error(@StringRes val errorMessageId: Int) : SchoolSatListUiState()
    data object Loading : SchoolSatListUiState()
}