package com.example.convertscurrency.Activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.convertscurrency.api.Country_Api
import com.example.convertscurrency.pojoClass.Example
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.appcompat.widget.AppCompatSpinner
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_main.*
import java.math.RoundingMode
import android.widget.TextView
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.graphics.Color
import com.example.convertscurrency.R
import es.dmoral.toasty.Toasty


class MainActivity : AppCompatActivity() {

    var BASE_URL = "https://jsonvat.com/"
    lateinit var currency_Responses: Example

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar_ID.visibility=View.VISIBLE

 //Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val currencyApi = retrofit.create(Country_Api::class.java)

        val call = currencyApi.allCountry

        call.enqueue(object : Callback<Example> {
            override fun onResponse(call: Call<Example>, response: Response<Example>) {

                currency_Responses = response.body()!!


// call spinner method
                setCountry_List_in_Spinner(
                    currency_Responses,
                    applicationContext,
                    spinner_country_name_ID
                )
//set radio button
                setRadio_button(
                    currency_Responses,
                    applicationContext,
                    spinner_country_name_ID,
                    radioGroup_vata_Rate,
                    radio_button_1_ID,
                    radio_button_2_ID,
                    radio_button_3_ID,
                    radio_button_4_ID,
                    radio_button_5_ID,
                    radio_button_6_ID,
                    amount_ET_ID,
                    total_amount_TV_ID,
                    tax_amount_TV_ID,
                    total_amount_with_Vat_TV
                )
                progressBar_ID.visibility=View.GONE


            }

            override fun onFailure(call: Call<Example>, t: Throwable) {

            }
        })

    }
}

//set spinner value
fun setCountry_List_in_Spinner(
    currency_Responses: Example,
    applicationContext: Context,
    spinner_country_name_ID: AppCompatSpinner
) {
    var country_list: ArrayList<String> = ArrayList()

    var num = 1..currency_Responses.rates!!.size

//add country name to country List
    for (a in num) {
        Log.e("Tag", "country name: " + currency_Responses.rates!!.get(a - 1).name)
        currency_Responses.rates!!.get(a - 1).name?.let { country_list.add(it) }
        Log.e("Tag", "country list size: " + country_list.size)
    }

// create an adapter from the list

    val adapter = object : ArrayAdapter<String>(applicationContext, R.layout.simple_spinner_item_custom,country_list) {
        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = super.getDropDownView(position, convertView, parent)

            val text = view.findViewById<View>(R.id.spinnerText) as TextView
            text.setTextColor(Color.BLACK)

            return view
        }
    }
// set the adapter on the spinner
    spinner_country_name_ID.setAdapter(adapter)
}

//set radio button text
fun setRadio_button(
    currency_Responses: Example,
    applicationContext: Context?,
    spinner_country_name_ID: AppCompatSpinner?,
    radioGroup_vata_Rate: RadioGroup?,
    radio_button_1_ID: RadioButton,
    radio_button_2_ID: RadioButton,
    radio_button_3_ID: RadioButton,
    radio_button_4_ID: RadioButton,
    radio_button_5_ID: RadioButton,
    radio_button_6_ID: RadioButton,
    amount_ET_ID: TextInputEditText,
    total_amount_TV_ID: TextView,
    tax_amount_TV_ID: TextView,
    total_amount_with_Vat_TV: TextView
    ) {

    var standard_radio: Double? = null
    var reduce_radio: Double? = null
    var reduced1_radio: Double? = null
    var reduced2_radio: Double? = null
    var parking_radio: Double? = null
    var superReduced_radio: Double? = null


    // Spinner click listener
    spinner_country_name_ID?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

            radio_button_1_ID.visibility = View.GONE
            radio_button_2_ID.visibility = View.GONE
            radio_button_3_ID.visibility = View.GONE
            radio_button_4_ID.visibility = View.GONE
            radio_button_5_ID.visibility = View.GONE
            radio_button_6_ID.visibility = View.GONE
            //un checked all radio button
            radio_button_1_ID.isChecked=false
            radio_button_2_ID.isChecked=false
            radio_button_3_ID.isChecked=false
            radio_button_4_ID.isChecked=false
            radio_button_5_ID.isChecked=false
            radio_button_6_ID.isChecked=false


  //set value 00 in the  TV
            total_amount_TV_ID.setText("Original Amount  =     00")

            tax_amount_TV_ID.text = "Tax                     =         00"

            total_amount_with_Vat_TV.text = "00"

            var num = 1..currency_Responses.rates!!.size

//add country name to country List
            for (a in num) {
                var country_name = currency_Responses.rates!!.get(a - 1).name
                val country_name_spinner = spinner_country_name_ID!!.getSelectedItem().toString()


//                if (country_name.equals(country_name_spinner)) {
                if (country_name_spinner.equals(country_name, false)) {
                    Log.e("Tag", "country name: " + country_name + " " + country_name_spinner)

                    standard_radio = currency_Responses.rates!!.get(position).periods!!.get(0).rates!!.standard
                    reduce_radio = currency_Responses.rates!!.get(position).periods!!.get(0).rates!!.reduced
                    reduced1_radio = currency_Responses.rates!!.get(position).periods!!.get(0).rates!!.reduced1
                    reduced2_radio = currency_Responses.rates!!.get(position).periods!!.get(0).rates!!.reduced2
                    parking_radio = currency_Responses.rates!!.get(position).periods!!.get(0).rates!!.parking
                    superReduced_radio = currency_Responses.rates!!.get(position).periods!!.get(0).rates!!.superReduced

                    if (standard_radio != null) {
                        radio_button_1_ID.visibility = View.VISIBLE
                        radio_button_1_ID.text = "Standard ("+standard_radio.toString()+" %)"
                    } else {
                        radio_button_1_ID.visibility = View.GONE
                    }

                    if (reduce_radio != null) {
                        radio_button_2_ID.visibility = View.VISIBLE
                        radio_button_2_ID.text = "Reduce ("+reduce_radio.toString()+" %)"
                    } else {
                        radio_button_2_ID.visibility = View.GONE
                    }

                    if (reduced1_radio != null) {
                        radio_button_3_ID.visibility = View.VISIBLE
                        radio_button_3_ID.text = "reduced_1 ("+reduced1_radio.toString()+" %)"
                    } else {
                        radio_button_3_ID.visibility = View.GONE
                    }

                    if (reduced2_radio != null) {
                        radio_button_4_ID.visibility = View.VISIBLE

                        radio_button_4_ID.text = "reduced_2 ("+reduced2_radio.toString()+" %)"
                    } else {
                        radio_button_4_ID.visibility = View.GONE
                    }

                    if (parking_radio != null) {
                        radio_button_5_ID.visibility = View.VISIBLE
                        radio_button_5_ID.text = "parking ("+parking_radio.toString()+" %)"
                    } else {
                        radio_button_5_ID.visibility = View.GONE
                    }
                    if (superReduced_radio != null) {
                        radio_button_6_ID.visibility = View.VISIBLE
                        radio_button_6_ID.text = "superReduced ("+superReduced_radio.toString()+" %)"
                    } else {
                        radio_button_6_ID.visibility = View.GONE
                    }
                }
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>) {
            // Code to perform some action when nothing is selected
        }
    }


    // Get radio group selected item using on checked change listener
    radioGroup_vata_Rate!!.setOnCheckedChangeListener { group, checkedId ->

        val radio: RadioButton = radioGroup_vata_Rate.findViewById(checkedId)


        if (radio_button_1_ID.isChecked) {
            var vate=  standard_radio
//calculate and set amount in Text View
            calculate_amount_And_set(
                amount_ET_ID,
                vate,
                total_amount_TV_ID,
                tax_amount_TV_ID,
                total_amount_with_Vat_TV,
                applicationContext
            )

        }
        if (radio_button_2_ID.isChecked) {
            var vate=  reduce_radio

//calculate and set amount in Text View
            calculate_amount_And_set(
                amount_ET_ID,
                vate,
                total_amount_TV_ID,
                tax_amount_TV_ID,
                total_amount_with_Vat_TV,
                applicationContext
            )


        }
        if (radio_button_3_ID.isChecked) {
            var vate=  reduced1_radio

//calculate and set amount in Text View
            calculate_amount_And_set(
                amount_ET_ID,
                vate,
                total_amount_TV_ID,
                tax_amount_TV_ID,
                total_amount_with_Vat_TV,
                applicationContext
            )


        }
        if (radio_button_4_ID.isChecked) {
            var vate=  reduced2_radio


//calculate and set amount in Text View
            calculate_amount_And_set(
                amount_ET_ID,
                vate,
                total_amount_TV_ID,
                tax_amount_TV_ID,
                total_amount_with_Vat_TV,
                applicationContext
            )


        }
        if (radio_button_5_ID.isChecked) {
            var vate=  parking_radio

//calculate and set amount in Text View
            calculate_amount_And_set(
                amount_ET_ID,
                vate,
                total_amount_TV_ID,
                tax_amount_TV_ID,
                total_amount_with_Vat_TV,
                applicationContext
            )

        }
        if (radio_button_6_ID.isChecked) {
            var vate=  superReduced_radio

//calculate and set amount in Text View
            calculate_amount_And_set(
                amount_ET_ID,
                vate,
                total_amount_TV_ID,
                tax_amount_TV_ID,
                total_amount_with_Vat_TV,
                applicationContext
            )

        }


    }

}

//calculate and set amount in Text View
fun calculate_amount_And_set(
    amount_ET_ID: TextInputEditText,
    vate: Double?,
    total_amount_TV_ID: TextView,
    tax_amount_TV_ID: TextView,
    total_amount_with_Vat_TV: TextView,
    applicationContext: Context?
) {

    if (amount_ET_ID.text?.isEmpty()!!){
//        Toast.makeText(applicationContext,"Enter Value",Toast.LENGTH_SHORT).show()
        Toasty.error(applicationContext!!,"Enter the value",Toasty.LENGTH_SHORT).show()
        return
    }

    total_amount_TV_ID.setText("Original Amount  =     ${amount_ET_ID.text.toString()}")
    var total_vat= (vate?.times(java.lang.Double.parseDouble(amount_ET_ID.text.toString())))!!.div(100)
    tax_amount_TV_ID.text = "Tax                     =         ${total_vat}"

    total_amount_with_Vat_TV.text = ((java.lang.Double.parseDouble(amount_ET_ID.text.toString())).plus(total_vat!!)).toBigDecimal().setScale(2, RoundingMode.UP).toDouble().toString()
}

