package com.john1jan.sslpinning.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.john1jan.sslpinning.network.GithubApi
import com.john1jan.sslpinning.network.GithubUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private var _userData = MutableLiveData<GithubUser>()
    val userData: LiveData<GithubUser>
        get() = _userData

    init {
        _userData.value = null
    }

    fun getUserData(profile: String) {
        val api = GithubApi.retrofitService.getUserData(profile)

        api.enqueue(object : Callback<GithubUser> {
            override fun onFailure(call: Call<GithubUser>, t: Throwable) {
                Log.d("TAG_TAG", "Failed :" + t.message)
            }

            override fun onResponse(call: Call<GithubUser>, response: Response<GithubUser>) {
                _userData.value = response.body()
            }
        })
    }
}