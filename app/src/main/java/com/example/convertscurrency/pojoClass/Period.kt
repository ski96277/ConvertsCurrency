package com.example.convertscurrency.pojoClass

import android.util.Log
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Period {

    @SerializedName("effective_from")
    @Expose
    var effectiveFrom: String? = null
    @SerializedName("rates")
    @Expose
    var rates: Rates? = null

}