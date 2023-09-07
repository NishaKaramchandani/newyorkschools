package com.example.baseproject.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseproject.R
import com.example.baseproject.network.Repository
import com.example.baseproject.view.data.School
import com.example.baseproject.view.data.SchoolSat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

/**
 * Details VM to fetch SAT scores based on school id
 */
@HiltViewModel
class DetailsViewModel @Inject constructor (private val repository: Repository): ViewModel() {

    private val _schoolSatUiState = MutableSharedFlow<SchoolSatUiState>()
    val schoolSatUiState: Flow<SchoolSatUiState> = _schoolSatUiState.asSharedFlow()

    companion object {
        private const val TAG = "DetailsViewModel"
    }

    fun getSchoolSatData(id: String) {
        viewModelScope.launch {
            repository.getSchoolSat(id).collectLatest { value: Result<SchoolSat> ->
                value.onSuccess { schoolSat ->
                    _schoolSatUiState.emit(SchoolSatUiState.Success(schoolSat))
                }
                value.onFailure { error ->
                    val errorMessageId = when (error) {
                        is UnknownHostException -> R.string.offline_error_fetching
                        else -> R.string.error_fetching_school_sat
                    }
                    _schoolSatUiState.emit(SchoolSatUiState.Error(errorMessageId = errorMessageId))
                }
            }
        }
    }
}

sealed class SchoolSatUiState {
    data class Success(val schoolSat: SchoolSat) : SchoolSatUiState()
    data class Error(@StringRes val errorMessageId: Int) : SchoolSatUiState()
    data object Loading : SchoolSatUiState()
}

