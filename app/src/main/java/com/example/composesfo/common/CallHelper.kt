package com.example.composesfo.common

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CallHelper<T>(private val callBack: CallBack<T>) {
    constructor(callBack: CallBack<T>, requestCode: Int) : this(callBack) {
    }

    fun call(call: Call<T>) {
        call.enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(
                call: Call<T>,
                response: Response<T>
            ) {
                if (response.isSuccessful) {
                    callBack.onResult(response)
                }
            }
        })
    }

    interface CallBack<T> {
        fun onResult(response: Response<T>)
    }
}