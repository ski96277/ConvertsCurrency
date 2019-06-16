package com.example.convertscurrency.pojoClass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Rates {

    @SerializedName("super_reduced")
    @Expose
    var superReduced: Double? = null
    @SerializedName("reduced")
    @Expose
    var reduced: Double? = null
    @SerializedName("standard")
    @Expose
    var standard: Double? = null
    @SerializedName("reduced1")
    @Expose
    var reduced1: Double? = null
    @SerializedName("reduced2")
    @Expose
    var reduced2: Double? = null
    @SerializedName("parking")
    @Expose
    var parking: Double? = null
}
