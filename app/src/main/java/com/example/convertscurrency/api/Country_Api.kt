package com.example.convertscurrency.api

import com.example.convertscurrency.pojoClass.Example
import retrofit2.Call
import retrofit2.http.GET


interface Country_Api {

    @get:GET(".")
    val allCountry: Call<Example>
}
