package com.picpay.desafio.android.shared.android.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.shared.android.dispatchers.DispatchersProvider
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

interface StateViewModel<State, Intention> {
    val state: StateFlow<State>
    fun publish(intention: Intention)
}

abstract class StateViewModelImpl<State, Intention>(
    private val dispatchersProvider: DispatchersProvider,
    initialState: State
) : ViewModel(), StateViewModel<State, Intention>, Reducer<State> by ReducerImpl(initialState) {

    private var lastIntention: Intention? = null

    private val publisher = MutableSharedFlow<Intention>()

    init {
        publisher.onEach { intention ->
            viewModelScope.launch(dispatchersProvider.io) { handleIntentions(intention) }
            this.lastIntention = intention
        }.shareIn(viewModelScope, SharingStarted.Eagerly)
    }

    final override val state: StateFlow<State> by lazy { mutableState }

    final override fun publish(intention: Intention) {
        viewModelScope.launch(dispatchersProvider.io) { publisher.emit(intention) }
    }

    abstract suspend fun handleIntentions(intention: Intention)

}
