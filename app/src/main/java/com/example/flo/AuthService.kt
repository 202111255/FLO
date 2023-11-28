package com.example.flo

import android.util.Log
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class AuthService {
    private lateinit var signUpView: SignUpView

    fun setSignUpView(signUpView: SignUpView){
        this.signUpView = signUpView
    }

    fun signUp(user: User) {

        val signUpService = getRetrofit().create(AuthRetrofitInterface::class.java).apply {

            signUp(user).enqueue(object : retrofit2.Callback<AuthResponse> {
                override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                    if (response.isSuccessful && response.code() == 200) {
                        val signUpResponse: AuthResponse = response.body()!!

                        Log.d("SIGNUP-RESPONSE", signUpResponse.toString())

                        when (val code = signUpResponse.code) {
                            1000 -> signUpView.onSignUpSuccess()
                            2016, 2017 -> {
                                signUpView.onSignUpFailure()
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    //실패처리
                }
            })
        }
    }

}