package com.example.myvalidationapp.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myvalidationapp.data.RetrofitInstance
import com.example.myvalidationapp.data.User
import com.example.myvalidationapp.data.UserRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class UserViewModel : ViewModel() {

    private val repository =
        UserRepository(RetrofitInstance.api)

    val createUserResponse =
        MutableLiveData<Response<User>>()

    val isLoading =
        MutableLiveData<Boolean>()

    val errorMessage =
        MutableLiveData<String>()

    fun createUser(user: User) {

        viewModelScope.launch {

            isLoading.value = true

            try {

                val response =
                    repository.createUser(user)

                if (response.isSuccessful) {

                    createUserResponse.value =
                        response

                } else {

                    errorMessage.value =
                        "API Failed"
                }

            } catch (e: Exception) {

                errorMessage.value =
                    e.message

            } finally {

                isLoading.value = false
            }
        }
    }
}