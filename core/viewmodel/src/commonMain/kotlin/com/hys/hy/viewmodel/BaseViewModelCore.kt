package com.hys.hy.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface UiState

interface UiEvent

interface Container<STATE : UiState, EVENT : UiEvent> {
    val uiStateFlow: StateFlow<STATE>
    val uiEventFlow: SharedFlow<EVENT>
}

interface MutableContainer<STATE : UiState, EVENT : UiEvent> : Container<STATE, EVENT> {
    fun updateState(action: STATE.() -> STATE)

    fun emitEvent(event: EVENT)
}

internal class RealContainer<STATE : UiState, SINGLE_EVENT : UiEvent>(
    initialState: STATE,
    private val parentScope: CoroutineScope,
) : MutableContainer<STATE, SINGLE_EVENT> {
    private val _internalStateFlow = MutableStateFlow(initialState)
    private val _internalSingleEventSharedFlow = MutableSharedFlow<SINGLE_EVENT>()
    override val uiStateFlow: StateFlow<STATE> = _internalStateFlow
    override val uiEventFlow: SharedFlow<SINGLE_EVENT> = _internalSingleEventSharedFlow
    override fun emitEvent(event: SINGLE_EVENT) {
        parentScope.launch {
            _internalSingleEventSharedFlow.emit(event)
        }
    }

    override fun updateState(action: STATE.() -> STATE) {
        _internalStateFlow.update { it.action() }
    }
}

fun <STATE : UiState, SINGLE_EVENT : UiEvent> ViewModel.containers(
    initialState: STATE,
): Lazy<MutableContainer<STATE, SINGLE_EVENT>> {
    return ContainerLazy(initialState, viewModelScope)
}

class ContainerLazy<STATE : UiState, SINGLE_EVENT : UiEvent>(
    initialState: STATE,
    parentScope: CoroutineScope
) : Lazy<MutableContainer<STATE, SINGLE_EVENT>> {

    private var cache: MutableContainer<STATE, SINGLE_EVENT>? = null

    override val value: MutableContainer<STATE, SINGLE_EVENT> = cache
        ?: RealContainer<STATE, SINGLE_EVENT>(initialState, parentScope).also {
            cache = it
        }

    override fun isInitialized() = cache != null
}

abstract class BaseViewModelCore<T : UiState, S : UiEvent> : ViewModel() {
    //！！ 不应该在initialState中获取子类的构造函数的参数值或者属性值 因为这个时候子类的构造函数还没有执行
    private val initialState: T by lazy { initialState() }
    protected abstract fun initialState(): T
    private val _mContainer by containers<T, S>(initialState = initialState)
    val container: Container<T, S>
        get() = _mContainer

    init {
        viewModelScope.launch {
            reduce(_mContainer)
        }
    }

    protected abstract suspend fun reduce(container: MutableContainer<T, S>)

    fun sendEvent(action: S) {
        _mContainer.emitEvent(action)
    }

}

