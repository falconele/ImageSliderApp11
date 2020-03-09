package com.elementarylogics.imagesliderapp.activities

//import kotlinx.android.synthetic.main.fragment_profile_slider.*
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Constraints
import androidx.core.content.ContextCompat
import com.elementarylogics.imagesliderapp.R
import com.elementarylogics.imagesliderapp.activities.maps.MapsActivity
import com.elementarylogics.imagesliderapp.dataclases.AddressModel
import com.elementarylogics.imagesliderapp.network.Apis
import com.elementarylogics.imagesliderapp.network.ResponseResult
import com.elementarylogics.imagesliderapp.network.RetrofitClient
import com.elementarylogics.imagesliderapp.utils.ApplicationUtils
import com.elementarylogics.imagesliderapp.utils.ErrorCheckingUtils
import com.elementarylogics.imagesliderapp.utils.Utility
import com.elementarylogics.imagesliderapp.utils.Utility.Companion.addressExtra
import com.elementarylogics.imagesliderapp.utils.Utility.Companion.isEditExtra
import kotlinx.android.synthetic.main.activity_add_new_address.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

//import kotlinx.android.synthetic.main.fragment_profile_slider.*


class AddNewAddressActivity : AppCompatActivity() {


    val REQ_CODE_MAP: Int = 123
    var isEdit: Boolean = false
    var addressNickName: String = ""
//    var address:AddressModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_address)

        isEdit = intent.getBooleanExtra(isEditExtra, false)
        if (isEdit) {

            addressModel = intent.getSerializableExtra(addressExtra) as? AddressModel
            if (addressModel != null) {
//                setGender(addressModel!!.gender)
                setAddressType(addressModel!!.address_type)
                etName.setText(addressModel!!.name)
                etEmail.setText(addressModel!!.email)
                etAddress.setText(addressModel!!.address)
                etFlatHouse.setText(addressModel!!.house_flate_number)
                etAreaColonySector.setText(addressModel!!.area_colony)
                etCity.setText(addressModel!!.city)
                lattitude = addressModel!!.latitude
                longitude = addressModel!!.longitude
                btnSaveOrUpdate.setText(resources.getString(R.string.update))
                tvTitle.setText(resources.getString(R.string.update_address))

            }
        }


        etAddress.setOnClickListener(View.OnClickListener {


            if (!ApplicationUtils.isEnableGPS(this)) ApplicationUtils.enableGPS(
                this
            ) else {

                val intent = Intent(this, MapsActivity::class.java)
                intent.putExtra("address", etAddress.text!!.toString())
                intent.putExtra("lat", lattitude)
                intent.putExtra("lon", longitude)
                startActivityForResult(intent, REQ_CODE_MAP)
            }
        })

        btnHome.setOnClickListener(View.OnClickListener {
            setAddressType(resources.getString(R.string.home))
        })
        btnOffice.setOnClickListener(View.OnClickListener {
            setAddressType(resources.getString(R.string.office))
        })
        btnOthers.setOnClickListener(View.OnClickListener {
            setAddressType(resources.getString(R.string.others))
        })

        btnSaveOrUpdate.setOnClickListener(View.OnClickListener {
            validateData()
        })

        imgBack.setOnClickListener(View.OnClickListener {
            finish()
        })

    }


    fun setGender(genderType: String) {
        if (genderType.equals(resources.getString(R.string.mr).replace(" ", ""), true)) {
            radMr.isChecked = true
        } else if (genderType.equals(resources.getString(R.string.mrs), true)) {
            radMrs.isChecked = true
        } else if (genderType.equals(resources.getString(R.string.miss), true)) {
            radMis.isChecked = true
        }
    }

    fun setAddressType(addressType: String) {
        addressNickName = addressType
        btnHome.backgroundTintList =
            ContextCompat.getColorStateList(applicationContext, R.color.light_grey)
        btnHome.setTextColor(
            ContextCompat.getColorStateList(
                applicationContext,
                R.color.colorPrimary
            )
        )
        btnOffice.backgroundTintList =
            ContextCompat.getColorStateList(applicationContext, R.color.light_grey)
        btnOffice.setTextColor(
            ContextCompat.getColorStateList(
                applicationContext,
                R.color.colorPrimary
            )
        )
        btnOthers.backgroundTintList =
            ContextCompat.getColorStateList(applicationContext, R.color.light_grey)
        btnOthers.setTextColor(
            ContextCompat.getColorStateList(
                applicationContext,
                R.color.colorPrimary
            )
        )


        if (addressType.equals(resources.getString(R.string.home), true)) {
            btnHome.backgroundTintList =
                ContextCompat.getColorStateList(applicationContext, R.color.colorPrimary)
            btnHome.setTextColor(ContextCompat.getColorStateList(applicationContext, R.color.white))
        } else if (addressType.equals(resources.getString(R.string.office), true)) {
            btnOffice.backgroundTintList =
                ContextCompat.getColorStateList(applicationContext, R.color.colorPrimary)
            btnOffice.setTextColor(
                ContextCompat.getColorStateList(
                    applicationContext,
                    R.color.white
                )
            )
        } else if (addressType.equals(resources.getString(R.string.others), true)) {
            btnOthers.backgroundTintList =
                ContextCompat.getColorStateList(applicationContext, R.color.colorPrimary)
            btnOthers.setTextColor(
                ContextCompat.getColorStateList(
                    applicationContext,
                    R.color.white
                )
            )
        }
    }


    var lattitude: Double = 0.0
    var longitude: Double = 0.0
    var profileFile: File? = null


    fun validateData() {


        ErrorCheckingUtils.setContextVal(this)

//        if (!ErrorCheckingUtils.profileVerification(profileFile)) return
        if (!ErrorCheckingUtils.checkEmpty(
                etName.text.toString(),
                resources.getString(R.string.empty_name)
            )
        ) return
//        if (!ErrorCheckingUtils.checkEmpty(
//                etLastName.text.toString(),
//                resources.getString(R.string.empty_last_name)
//            )
//        ) return

        if (!ErrorCheckingUtils.emailVerification(etEmail.text.toString()))
            return

        if (!ErrorCheckingUtils.checkEmpty(
                etAddress.text.toString(),
                resources.getString(R.string.empty_address)
            )
        ) return

        if (!ErrorCheckingUtils.checkEmpty(
                etFlatHouse.text.toString(),
                resources.getString(R.string.empty_flat_house)
            )
        ) return

        if (!ErrorCheckingUtils.checkEmpty(
                etAreaColonySector.text.toString(),
                resources.getString(R.string.empty_area_colony_sector)
            )
        ) return
        if (!ErrorCheckingUtils.checkEmpty(
                etCity.text.toString(),
                resources.getString(R.string.empty_city)
            )
        ) return
        if (!ErrorCheckingUtils.checkEmpty(
                addressNickName,
                resources.getString(R.string.address_type_error)
            )
        ) return
        saveAddress()
    }

    var addressModel: AddressModel? = null

    fun saveAddress() {
//        var token =
//            SharedPreference.getSharedPrefValue(activity as AppCompatActivity, Constants.USER_TOKEN)
//        token = "Bearer $token"


        val first_name: RequestBody = RequestBody.create(
            MediaType.parse("text/plain"),
            etName.getText().toString()
        )
        val last_name = RequestBody.create(
            MediaType.parse("text/plain"),
            etLastName.text.toString()
        )

        val email = RequestBody.create(
            MediaType.parse("text/plain"),
            etEmail.text.toString()
        )

        val address_type: RequestBody = RequestBody.create(
            MediaType.parse("text/plain"),
            addressNickName
        )
        val address = RequestBody.create(
            MediaType.parse("text/plain"),
            etAddress.text.toString()
        )
        val latitude: RequestBody =
            RequestBody.create(MediaType.parse("text/plain"), lattitude.toString() + "")
        val longitude: RequestBody =
            RequestBody.create(MediaType.parse("text/plain"), longitude.toString() + "")
        val flatHouse = RequestBody.create(
            MediaType.parse("text/plain"),
            etFlatHouse.text.toString()
        )
        val areaColony = RequestBody.create(
            MediaType.parse("text/plain"),
            etAreaColonySector.text.toString()
        )

        val delivery_address_id = RequestBody.create(
            MediaType.parse("text/plain"),
            addressModel!!.id.toString()
        )

        val city = RequestBody.create(
            MediaType.parse("text/plain"),
            etCity.text.toString()
        )

        var imageBodyPart: MultipartBody.Part? = null
        if (profileFile != null) {
            val image: RequestBody = RequestBody.create(
                MediaType.parse("image/jpeg"),
                profileFile
            )
            imageBodyPart =
                MultipartBody.Part.createFormData("image", profileFile!!.getName(), image)
        }





        Utility.showProgressBar(this, progressBar, true)
        val api: Apis = RetrofitClient.getClient()!!.create(Apis::class.java)
        val token: String =
            "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJsb2dnZWRJbkFzIjoiYWRtaW4iLCJpYXQiOjE0MjI3Nzk2Mzh9.gzSraSYS8EXBxLN_oWnFSRgCzcmJmMjLiuyu5CSpyHI"

        val call: Call<ResponseResult<AddressModel>> =
            api.saveOrUpdateAddress(
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
                Utility.showProgressBar(this@AddNewAddressActivity, progressBar, false)
                try {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus()!!) {
                            if (response.body().getData() != null) {
//                                user = response.body().getData() as User
//                                setUserDetails()
                                ApplicationUtils.showToast(
                                    this@AddNewAddressActivity,
                                    response.body().getMessage(),
                                    true
                                )
                            }
                        } else {
                            ApplicationUtils.showToast(
                                this@AddNewAddressActivity,
                                response.body().getMessage().toString() + "",
                                false
                            )
                        }
                    } else {
                        val jsonObject = JSONObject(response.errorBody().string())
                        ApplicationUtils.showToast(
                            this@AddNewAddressActivity,
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
                Utility.showProgressBar(this@AddNewAddressActivity, progressBar, false)
                Log.d(
                    Constraints.TAG,
                    "onFailure: " + t.message
                )
            }
        })


    }

    var address = ""

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQ_CODE_MAP) {
                address = data!!.getStringExtra("address")
                etAddress.setText(address)
                lattitude = data.getDoubleExtra("lat", 0.0)
                longitude = data.getDoubleExtra("lon", 0.0)
                if (address != null && address !== "") {
                    etAddress.setText(address)
                }
            }
        }

    }


}
