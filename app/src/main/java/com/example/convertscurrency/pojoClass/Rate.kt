package com.example.convertscurrency.pojoClass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Rate {

    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("code")
    @Expose
    var code: String? = null
    @SerializedName("country_code")
    @Expose
    var countryCode: String? = null
    @SerializedName("periods")
    @Expose
    var periods: List<Period>? = null

}