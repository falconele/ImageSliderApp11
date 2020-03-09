package com.elementarylogics.imagesliderapp.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Constraints
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elementarylogics.imagesliderapp.R
import com.elementarylogics.imagesliderapp.adaptors.addressesadaptor.AddressAdaptor
import com.elementarylogics.imagesliderapp.dataclases.AddressModel
import com.elementarylogics.imagesliderapp.network.Apis
import com.elementarylogics.imagesliderapp.network.ResponseListResult
import com.elementarylogics.imagesliderapp.network.RetrofitClient
import com.elementarylogics.imagesliderapp.utils.ApplicationUtils
import com.elementarylogics.imagesliderapp.utils.Utility
import com.elementarylogics.imagesliderapp.utils.Utility.Companion.addressExtra
import com.elementarylogics.imagesliderapp.utils.Utility.Companion.isEditExtra
import kotlinx.android.synthetic.main.activity_addresses.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddressesActivity : AppCompatActivity(), AddressAdaptor.ItemClickListner {


    val REQ_ADDRESS = 109

    override fun onItemClicklistner(position: Int) {
        val intent = Intent(this, AddNewAddressActivity::class.java)
        intent.putExtra(isEditExtra, true)
        intent.putExtra(addressExtra, addressesList.get(position))
        startActivityForResult(intent, REQ_ADDRESS)
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addresses)

        recAddress!!.setLayoutManager(LinearLayoutManager(applicationContext))




        recAddress.addItemDecoration(
            DividerItemDecoration(
                applicationContext,
                LinearLayoutManager.VERTICAL
            )
        )
        runAnimation(recAddress!!)


        relAddNewAddress.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, AddNewAddressActivity::class.java))
        })


//        addAddresses()

        getAllAddresses()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

            if (requestCode == REQ_ADDRESS)
                getAllAddresses()

    }


    lateinit var adapter: AddressAdaptor
    private fun runAnimation(recyclerView: RecyclerView) {
        adapter = AddressAdaptor(Utility.address, applicationContext!!, this)
        recyclerView.adapter = adapter
    }

    var addressesList: List<AddressModel> = arrayListOf()
    fun getAllAddresses() {
        Utility.showProgressBar(this, progressBar, true)
        val api: Apis = RetrofitClient.getClient()!!.create(Apis::class.java)
        val token: String =
            "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJsb2dnZWRJbkFzIjoiYWRtaW4iLCJpYXQiOjE0MjI3Nzk2Mzh9.gzSraSYS8EXBxLN_oWnFSRgCzcmJmMjLiuyu5CSpyHI"
        val call: Call<ResponseListResult<AddressModel>> =
            api.getAllAddresses(
                token,
                "1"
            )

        call.enqueue(object : Callback<ResponseListResult<AddressModel>> {
            override fun onResponse(
                call: Call<ResponseListResult<AddressModel>>,
                response: Response<ResponseListResult<AddressModel>>
            ) {
                Utility.showProgressBar(this@AddressesActivity, progressBar, false)
                try {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus()!!) {
                            if (response.body().getData() != null && response.body().getData()!!.size > 0) {

                                addressesList = response.body().getData() as List<AddressModel>
                                adapter.addItems(addressesList)
//                                adapter.notifyDataSetChanged()

//                                setUserDetails()
                                ApplicationUtils.showToast(
                                    this@AddressesActivity,
                                    response.body().getMessage() ,
                                    true
                                )
                            }
                        } else {
                            ApplicationUtils.showToast(
                                this@AddressesActivity,
                                response.body().getMessage().toString() + "",
                                false
                            )
                        }
                    } else {
                        val jsonObject = JSONObject(response.errorBody().string())
                        ApplicationUtils.showToast(
                            this@AddressesActivity,
                            jsonObject.getString("message") + "",
                            false
                        )
                    }
                } catch (e: Exception) {
//                    showProgressBar(false)
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                call: Call<ResponseListResult<AddressModel>>,
                t: Throwable
            ) {
//                showProgressBar(false)
                Utility.showProgressBar(this@AddressesActivity, progressBar, false)
                Log.d(
                    Constraints.TAG,
                    "onFailure: " + t.message
                )
            }
        })
    }
}
