package com.example.ratingsoft.ui.login


import android.app.backup.SharedPreferencesBackupHelper
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.TaskStackBuilder
import com.example.ratingsoft.R
import com.example.ratingsoft.databinding.ActivityLoginBinding
import com.example.ratingsoft.io.Response.LoginResponse
import com.example.ratingsoft.network.ApiService.LoginApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val preferences = PreferencesBackupHelper.defaultPrefs(this)
        if (preferences["token", ""].contains("."))


}
