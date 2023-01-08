package com.droidcon.login.data

import android.content.Context
import com.droidcon.login.model.User
import com.droidcon.login.network.ApiInterface
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class AuthUserRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private val apiInterface = ApiInterface.invoke()
    //private val api = ApiInterface

    //suspend fun saveUser(user: User) = apiInterface.saveUser(user)
    suspend fun saveUser(user: User) = safeApiCall { apiInterface.saveUser(user) }
}