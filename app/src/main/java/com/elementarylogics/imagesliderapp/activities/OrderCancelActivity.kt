package com.elementarylogics.imagesliderapp.activities

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.elementarylogics.imagesliderapp.R
import com.elementarylogics.imagesliderapp.utils.SharedPreference
import kotlinx.android.synthetic.main.activity_order_cancel.*

class OrderCancelActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_cancel)

        btnContinuShopping.setOnClickListener(View.OnClickListener {
            ratingDialog()
        })
    }

    private fun ratingDialog() {

        lateinit var dialog: AlertDialog
        val builder = AlertDialog.Builder(this)
//        builder.setTitle(resources.getString(R.string.are_you_sure))
        builder.setView(R.layout.dialog_ratings)


        val dialogClickListener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    Toast.makeText(this, "Log out", Toast.LENGTH_SHORT).show()
                    SharedPreference.saveUserProfile(this@OrderCancelActivity, null)

                }
                DialogInterface.BUTTON_NEGATIVE -> {
                }

            }
        }

        builder.setPositiveButton(resources.getString(R.string.comment), dialogClickListener)
        builder.setNegativeButton(resources.getString(R.string.cancel), dialogClickListener)
        dialog = builder.create()
        dialog.show()
    }
}
