package com.elementarylogics.imagesliderapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.elementarylogics.imagesliderapp.R
import com.elementarylogics.imagesliderapp.activities.maps.MapsActivity
import com.elementarylogics.imagesliderapp.dataclases.AddressModel
import com.elementarylogics.imagesliderapp.utils.Utility.Companion.addressExtra
import com.elementarylogics.imagesliderapp.utils.Utility.Companion.isEditExtra
import kotlinx.android.synthetic.main.activity_add_new_address.*


class AddNewAddressActivity : AppCompatActivity() {


    val REQ_CODE_MAP: Int = 123
    var isEdit: Boolean = false
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
            val intent = Intent(this, MapsActivity::class.java)
            intent.putExtra("address", etAddress.text!!.toString())
            intent.putExtra("lat", 1234.5)
            intent.putExtra("lon", 1234.5)
            startActivityForResult(intent, REQ_CODE_MAP)
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

}
