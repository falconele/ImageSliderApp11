package com.elementarylogics.imagesliderapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.elementarylogics.imagesliderapp.R
import com.elementarylogics.imagesliderapp.activities.maps.MapsActivity
import com.elementarylogics.imagesliderapp.dataclases.AddressModel
import com.elementarylogics.imagesliderapp.utils.ApplicationUtils
import com.elementarylogics.imagesliderapp.utils.ErrorCheckingUtils
import com.elementarylogics.imagesliderapp.utils.Utility.Companion.addressExtra
import com.elementarylogics.imagesliderapp.utils.Utility.Companion.isEditExtra
import kotlinx.android.synthetic.main.activity_add_new_address.*
import kotlinx.android.synthetic.main.activity_add_new_address.btnSaveOrUpdate
import kotlinx.android.synthetic.main.activity_add_new_address.etAddress
import kotlinx.android.synthetic.main.activity_add_new_address.etAreaColonySector
import kotlinx.android.synthetic.main.activity_add_new_address.etCity
import kotlinx.android.synthetic.main.activity_add_new_address.etEmail
import kotlinx.android.synthetic.main.activity_add_new_address.etFlatHouse
import kotlinx.android.synthetic.main.activity_add_new_address.etName
import kotlinx.android.synthetic.main.activity_add_new_address.tvTitle
//import kotlinx.android.synthetic.main.fragment_profile_slider.*


class AddNewAddressActivity : AppCompatActivity() {


    val REQ_CODE_MAP: Int = 123
    var isEdit: Boolean = false
    var addressNickName:String=""




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_address)

        isEdit = intent.getBooleanExtra(isEditExtra, false)
        if (isEdit) {
            val address = intent.getParcelableExtra<AddressModel>(addressExtra)
            if (address != null) {
                setGender(address.gender)
                setAddressType(address.addressType)
                etName.setText(address.name)
                etEmail.setText(address.mail)
                etAddress.setText(address.address)
                etFlatHouse.setText(address.flatHouse)
                etAreaColonySector.setText(address.areaColony)
                etCity.setText(address.city)
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
                intent.putExtra("lat", 1234.5)
                intent.putExtra("lon", 1234.5)
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
        addressNickName=addressType
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


    fun validateData() {

        ErrorCheckingUtils.setContextVal(this)

//        if (!ErrorCheckingUtils.profileVerification(profileFile)) return
        if (!ErrorCheckingUtils.checkEmpty(
                etName.text.toString(),
                resources.getString(R.string.empty_name)
            )
        ) return
        if (!ErrorCheckingUtils.checkEmpty(
                etLastName.text.toString(),
                resources.getString(R.string.empty_last_name)
            )
        ) return

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
    }

}
