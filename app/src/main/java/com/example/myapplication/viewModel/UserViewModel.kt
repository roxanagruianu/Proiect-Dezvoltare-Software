package com.example.myapplication.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.myapplication.data.model.UserEntity
import com.example.myapplication.data.model.UserRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    private val _loginEvent  = MutableSharedFlow<UserEntity?>(replay = 0)
    val loginEvent: SharedFlow<UserEntity?> = _loginEvent

    fun registerUser(user: UserEntity) {
        viewModelScope.launch {
            repository.registerUser(user)
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val user = repository.login(email, password)
            _loginEvent.emit(user)
        }
    }

    private val _currentUser = MutableLiveData<UserEntity?>()
    val currentUser: LiveData<UserEntity?> = _currentUser

    fun loadUserByEmail(email: String) {
        viewModelScope.launch {
            val user = repository.getUserByEmail(email)
            _currentUser.postValue(user)
        }
    }
}
