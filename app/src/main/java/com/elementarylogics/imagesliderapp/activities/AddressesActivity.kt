package com.elementarylogics.imagesliderapp.activities

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
import com.elementarylogics.imagesliderapp.dataclases.User
import com.elementarylogics.imagesliderapp.network.Apis
import com.elementarylogics.imagesliderapp.network.ResponseListResult
import com.elementarylogics.imagesliderapp.network.ResponseResult
import com.elementarylogics.imagesliderapp.network.RetrofitClient
import com.elementarylogics.imagesliderapp.utils.ApplicationUtils
import com.elementarylogics.imagesliderapp.utils.SharedPreference
import com.elementarylogics.imagesliderapp.utils.Utility
import com.elementarylogics.imagesliderapp.utils.Utility.Companion.addressExtra
import com.elementarylogics.imagesliderapp.utils.Utility.Companion.isEditExtra
import kotlinx.android.synthetic.main.activity_add_new_address.*
import kotlinx.android.synthetic.main.activity_addresses.*
import kotlinx.android.synthetic.main.activity_addresses.progressBar
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddressesActivity : AppCompatActivity(), AddressAdaptor.ItemClickListner {


    val REQ_ADDRESS = 109
    var REQ_ADD_ADDRESS = 110

    override fun onItemClicklistner(position: Int) {
        val intent = Intent(this, AddNewAddressActivity::class.java)
        intent.putExtra(isEditExtra, true)
        intent.putExtra(addressExtra, addressesList.get(position))
        startActivityForResult(intent, REQ_ADD_ADDRESS)
    }

    lateinit var selectedDeliverAddress: AddressModel
    override fun onItemSelected(position: Int) {
        selectedDeliverAddress = addressesList.get(position)
        updateDeliveryAddress()
    }


    var did:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addresses)

        did=intent.getStringExtra("did")
        recAddress!!.setLayoutManager(LinearLayoutManager(applicationContext))
        recAddress.addItemDecoration(
            DividerItemDecoration(
                applicationContext,
                LinearLayoutManager.VERTICAL
            )
        )
        runAnimation(recAddress!!)


        relAddNewAddress.setOnClickListener(View.OnClickListener {
            startActivityForResult(Intent(this, AddNewAddressActivity::class.java), REQ_ADD_ADDRESS)
        })


//        addAddresses()

        getAllAddresses()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQ_ADD_ADDRESS)
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
//        val token: String =
//            "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJsb2dnZWRJbkFzIjoiYWRtaW4iLCJpYXQiOjE0MjI3Nzk2Mzh9.gzSraSYS8EXBxLN_oWnFSRgCzcmJmMjLiuyu5CSpyHI"


        var id = "-1"
        var token: String = ""
        val user = SharedPreference.getUserData(applicationContext)
        if (user != null) {
            id = user.id.toString()
            token = "Bearer " + user.code
        }

        val call: Call<ResponseListResult<AddressModel>> =
            api.getAllAddresses(
                token,
                id
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
//                                ApplicationUtils.showToast(
//                                    this@AddressesActivity,
//                                    response.body().getMessage(),
//                                    true
//                                )
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

    lateinit var user: User
    var id = ""
    var token = ""

    fun updateDeliveryAddress() {

        user = SharedPreference.getUserData(this)
        if (user != null) {
            id = user.id.toString()
            token = "Bearer " + user.code
        }

        val first_name: RequestBody = RequestBody.create(
            MediaType.parse("text/plain"),
            selectedDeliverAddress.name
        )
//        val last_name = RequestBody.create(
//            MediaType.parse("text/plain"),
//            etLastName.text.toString()
//        )

        val email = RequestBody.create(
            MediaType.parse("text/plain"),
            selectedDeliverAddress.email
        )

        val address_type: RequestBody = RequestBody.create(
            MediaType.parse("text/plain"),
            selectedDeliverAddress.address_type
        )
        val address = RequestBody.create(
            MediaType.parse("text/plain"),
            selectedDeliverAddress.address
        )
        val latitude: RequestBody =
            RequestBody.create(
                MediaType.parse("text/plain"),
                selectedDeliverAddress.latitude.toString() + ""
            )
        val longitude: RequestBody =
            RequestBody.create(
                MediaType.parse("text/plain"),
                selectedDeliverAddress.longitude.toString() + ""
            )
        val flatHouse = RequestBody.create(
            MediaType.parse("text/plain"),
            selectedDeliverAddress.house_flate_number
        )
        val areaColony = RequestBody.create(
            MediaType.parse("text/plain"),
            selectedDeliverAddress.area_colony
        )
        var delivery_address_id: RequestBody? = null
        var customer_id: RequestBody? = null

        delivery_address_id = RequestBody.create(
            MediaType.parse("text/plain"),
            did
        )


        val city = RequestBody.create(
            MediaType.parse("text/plain"),
            selectedDeliverAddress.city
        )

        var imageBodyPart: MultipartBody.Part? = null
//        if (profileFile != null) {
//            val image: RequestBody = RequestBody.create(
//                MediaType.parse("image/jpeg"),
//                profileFile
//            )
//            imageBodyPart =
//                MultipartBody.Part.createFormData("image", profileFile!!.getName(), image)
//        }


        Utility.showProgressBar(this, progressBar, true)
        val api: Apis = RetrofitClient.getClient()!!.create(Apis::class.java)
        var call: Call<ResponseResult<AddressModel>>

        call =
            api.updateDeliveryAddress(
                token,
                first_name,
                email,
                latitude,
                longitude,
                address,
                imageBodyPart,
                city,
                address_type,
                flatHouse,
                delivery_address_id,
                areaColony
            )



        call.enqueue(object : Callback<ResponseResult<AddressModel>> {
            override fun onResponse(
                call: Call<ResponseResult<AddressModel>>,
                response: Response<ResponseResult<AddressModel>>
            ) {
                Utility.showProgressBar(this@AddressesActivity, progressBar, false)
                try {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus()!!) {
                            if (response.body().getData() != null) {
//                                user = response.body().getData() as User
//                                setUserDetails()
//                                ApplicationUtils.showToast(
//                                    this@AddressesActivity,
//                                    response.body().getMessage(),
//                                    true
//                                )
                                finish()
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
                call: Call<ResponseResult<AddressModel>>,
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
