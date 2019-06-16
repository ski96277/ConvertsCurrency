package com.example.convertscurrency.pojoClass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Example {

    @SerializedName("details")
    @Expose
    var details: String? = null
    @SerializedName("version")
    @Expose
    var version: Any? = null
    @SerializedName("rates")
    @Expose
    var rates: List<Rate>? = null

}