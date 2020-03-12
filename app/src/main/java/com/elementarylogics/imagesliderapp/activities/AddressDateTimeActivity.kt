package com.elementarylogics.imagesliderapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Constraints
import com.elementarylogics.imagesliderapp.R
import com.elementarylogics.imagesliderapp.dataclases.AddressModel
import com.elementarylogics.imagesliderapp.network.Apis
import com.elementarylogics.imagesliderapp.network.ResponseResult
import com.elementarylogics.imagesliderapp.network.RetrofitClient
import com.elementarylogics.imagesliderapp.utils.ApplicationUtils
import com.elementarylogics.imagesliderapp.utils.SharedPreference
import com.elementarylogics.imagesliderapp.utils.Utility
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_address_date_time.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddressDateTimeActivity : AppCompatActivity() {

    val REQ_EDIT_DEL = 111

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_address_date_time)

        imgEdit.setOnClickListener(View.OnClickListener {
            startActivityForResult(
                Intent(this, AddressesActivity::class.java).putExtra(
                    "did",
                    deliveryAddress?.id.toString() ?: "-1"
                ), REQ_EDIT_DEL
            )
        })

        relDateandTime.setOnClickListener(View.OnClickListener {
            showBottomSheetDialog()

        })

        linItemsInCart.setOnClickListener(View.OnClickListener {
            //            startActivity(Intent(this, MyCartActivity::class.java))
            finish()
        })
        imgBack.setOnClickListener(View.OnClickListener {
            finish()
        })
        imgTryAgain.setOnClickListener(View.OnClickListener {
            imgTryAgain.visibility = View.GONE
            getAllAddresses()
        })
        btnProceedToPayment.setOnClickListener(View.OnClickListener {
            startActivity(Intent(AddressesActivity@this,PaymentActivity::class.java))
        })
        getAllAddresses()
    }


    lateinit var imgCross: ImageView
    lateinit var btnDone: Button
    lateinit var dateNumberPicker: NumberPicker
    val dates = arrayOf(
        "tomorrow  10:00 AM - 10:00 PM",
        "feb 21, 2020  10:00 AM - 10:00 PM",
        "feb 22, 2020  10:00 AM - 10:00 PM"
    )

    fun showBottomSheetDialog() {
        val view = layoutInflater.inflate(R.layout.bottomsheet_date_and_time, null)
        imgCross = view.findViewById(R.id.imgCross)
        btnDone = view.findViewById(R.id.btnDone)
        dateNumberPicker = view.findViewById(R.id.dateNumberPicker)
        dateNumberPicker.displayedValues = dates
        dateNumberPicker.maxValue = 2
        dateNumberPicker.minValue = 0
        dateNumberPicker.setOnValueChangedListener { numberPicker, i, i1 ->
            val valuePicker1 = dateNumberPicker.value
            Toast.makeText(applicationContext, dates.get(valuePicker1), Toast.LENGTH_SHORT).show()

        }

        val dialog = BottomSheetDialog(this)
        dialog.setCancelable(false)
        btnDone.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })

        imgCross.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })


        dialog.setContentView(view)
        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        getAllAddresses()
    }


    var deliveryAddress: AddressModel? = null
    fun getAllAddresses() {

        linAddress.visibility = View.INVISIBLE

        Utility.showProgressBar(this, progressBar, true)
        val api: Apis = RetrofitClient.getClient()!!.create(Apis::class.java)
        var id = "-1"
        var token: String = ""
        val user = SharedPreference.getUserData(applicationContext)
        if (user != null) {
            id = user.id.toString()
            token = "Bearer " + user.code
        }
        Toast.makeText(applicationContext, user.id.toString(), Toast.LENGTH_SHORT).show()
        val call: Call<ResponseResult<AddressModel>> =
            api.getDeliveryAddress(
                token,
                id
            )

        call.enqueue(object : Callback<ResponseResult<AddressModel>> {
            override fun onResponse(
                call: Call<ResponseResult<AddressModel>>,
                response: Response<ResponseResult<AddressModel>>
            ) {
                Utility.showProgressBar(this@AddressDateTimeActivity, progressBar, false)
                try {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus()!!) {
                            if (response.body().getData() != null) {

//                                ApplicationUtils.showToast(
//                                    this@AddressDateTimeActivity,
//                                    response.body().getMessage(),
//                                    true
//                                )
                                val deliverAddressList = response.body().getData()!!
                                deliveryAddress = deliverAddressList
                                setAddressDetail()

                            } else {

                                disableAddress()
                            }

                        } else {

                            disableAddress()
                            ApplicationUtils.showToast(
                                this@AddressDateTimeActivity,
                                response.body().getMessage().toString() + "",
                                false
                            )
                        }
                    } else {
                        disableAddress()
                        val jsonObject = JSONObject(response.errorBody().string())
                        ApplicationUtils.showToast(
                            this@AddressDateTimeActivity,
                            jsonObject.getString("message") + "",
                            false
                        )
                    }
                } catch (e: Exception) {
//                    showProgressBar(false)

                    disableAddress()
                    disableAddress()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                call: Call<ResponseResult<AddressModel>>,
                t: Throwable
            ) {
//                showProgressBar(false)

                disableAddress()
                Utility.showProgressBar(this@AddressDateTimeActivity, progressBar, false)
                Log.d(
                    Constraints.TAG,
                    "onFailure: " + t.message
                )
            }
        })
    }


    fun setAddressDetail() {
        if (deliveryAddress != null) {

            linAddress.visibility = View.VISIBLE
            btnProceedToPayment.visibility = View.VISIBLE


            tvAddressType.setText(deliveryAddress!!.address_type)
            tvName.setText(deliveryAddress!!.name)
            tvAddress1.setText(deliveryAddress!!.house_flate_number)
            tvAddress2.setText(deliveryAddress!!.area_colony)
            tvCompleteAddress.setText(deliveryAddress!!.address)

        }
    }

    fun disableAddress() {
        linAddress.visibility = View.INVISIBLE
        imgTryAgain.visibility = View.VISIBLE
        btnProceedToPayment.visibility = View.INVISIBLE
    }

}
