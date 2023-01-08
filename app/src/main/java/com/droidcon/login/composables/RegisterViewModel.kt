package com.droidcon.login.composables

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.droidcon.login.data.AuthUserRepository
import com.droidcon.login.data.DataResult
import com.droidcon.login.model.User
import com.droidcon.login.response.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: AuthUserRepository): ViewModel() {

        //private val repository = AuthUserRepository(application)

        var save: Job? = null


        private val _savedUserStatus = MutableLiveData<Resource<String>>()
        val savedUserStatus: LiveData<Resource<String>> = _savedUserStatus


    fun saveUserData(user: User){
        save = CoroutineScope(Dispatchers.IO).launch {
            _savedUserStatus.postValue(Resource.loading(null))
            val response = repository.saveUser(user)
            withContext(Dispatchers.Main) {
                val results = (response as DataResult.Success).data
                if (!results.email?.isEmpty()!!) {
                    _savedUserStatus.postValue(Resource.success(results.email, ""))
                } else {
                    _savedUserStatus.postValue(Resource.error(null, "Error Occurred while Saving User"))
                }
            }
        }
    }
}