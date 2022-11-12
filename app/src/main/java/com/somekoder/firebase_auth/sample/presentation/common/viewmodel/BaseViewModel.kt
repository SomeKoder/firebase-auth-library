package com.somekoder.firebase_auth.sample.presentation.common.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseViewModel<Intent, State, Effect> : ViewModel() {

    private val initialState : State by lazy { createInitialState() }
    abstract fun createInitialState() : State

    val currentState: State
        get() = uiState.value

    private val _uiState : MutableStateFlow<State> = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val _intent : MutableSharedFlow<Intent> = MutableSharedFlow()
    private val intent = _intent.asSharedFlow()

    private val _uiEffect : Channel<Effect> = Channel()
    val uiEffect = _uiEffect.receiveAsFlow()

    init {
        subscribeEvents()
    }

    private fun subscribeEvents() {
        viewModelScope.launch {
            intent.collect {
                handleEvent(it)
            }
        }
    }

    abstract fun handleEvent(intent : Intent)

    fun setIntent(intent : Intent) {
        val newIntent = intent
        viewModelScope.launch { _intent.emit(newIntent) }
    }

    protected fun setState(reduce: State.() -> State) {
        val newState = currentState.reduce()
        _uiState.value = newState
    }

    protected fun setEffect(builder: () -> Effect) {
        val effectValue = builder()
        viewModelScope.launch { _uiEffect.send(effectValue) }
    }
}