package com.elementarylogics.imagesliderapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elementarylogics.imagesliderapp.R
import com.elementarylogics.imagesliderapp.adaptors.addressesadaptor.AddressAdaptor
import com.elementarylogics.imagesliderapp.dataclases.AddressModel
import com.elementarylogics.imagesliderapp.utils.Utility
import com.elementarylogics.imagesliderapp.utils.Utility.Companion.addressExtra
import com.elementarylogics.imagesliderapp.utils.Utility.Companion.isEditExtra
import kotlinx.android.synthetic.main.activity_addresses.*

class AddressesActivity : AppCompatActivity(), AddressAdaptor.ItemClickListner {

    override fun onItemClicklistner(position: Int) {
        val intent = Intent(this, AddNewAddressActivity::class.java)
        intent.putExtra(addressExtra, Utility.address.get(position))
        intent.putExtra(isEditExtra, true)
        startActivity(intent)
    }

    fun addAddresses() {
        for (i in 0..10) {
            val address = AddressModel(
                i,
                "Mrs.",
                "amir " + i,
                "e" + i + "@gmiil.com",
                "House  " + i,
                "Colony  " + i,
                "Jaranwala",
                "Home",
                "House no 5 street no 5 Alvi Park",
                123.0,
                1234.5
            )
            Utility.address.add(address)

        }
        adapter.addItems(Utility.address)

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




        addAddresses()
    }

    lateinit var adapter: AddressAdaptor
    private fun runAnimation(recyclerView: RecyclerView) {
        adapter = AddressAdaptor(Utility.address, applicationContext!!, this)
        recyclerView.adapter = adapter
    }
}
