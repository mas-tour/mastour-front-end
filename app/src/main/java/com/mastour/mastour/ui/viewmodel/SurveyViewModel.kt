package com.mastour.mastour.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mastour.mastour.data.remote.SurveyResponse
import com.mastour.mastour.data.repository.Repository
import com.mastour.mastour.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SurveyViewModel @Inject constructor(private val repository: Repository): ViewModel() {
    private val _surveyResponse: MutableStateFlow<UiState<SurveyResponse>> = MutableStateFlow(UiState.Loading)
    val surveyResponse: StateFlow<UiState<SurveyResponse>>
        get() = _surveyResponse

    private val _answers = mutableStateListOf<Int>()
    val answers: SnapshotStateList<Int> get() = _answers

    private val _token = mutableStateOf("")
    private val token: State<String> get() = _token

    fun addAnswers(answers: List<MutableState<Int>>) {
        answers.forEach { answer ->
            _answers.add(answer.value)
        }
    }

    fun postAnswers() {
        viewModelScope.launch {
            repository.getUserToken().collect() {
                _token.value = it
                repository.survey(answers.toList(), token.value).collect { uiState ->
                    _surveyResponse.value = uiState
                }
            }
        }
    }
}