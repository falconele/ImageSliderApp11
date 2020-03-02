package com.elementarylogics.imagesliderapp.utils

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import com.elementarylogics.imagesliderapp.R
import com.elementarylogics.imagesliderapp.utils.ApplicationUtils.showToast

class ErrorCheckingUtils {
    //    lateinit var context: Context
    companion object {
        lateinit var context: AppCompatActivity
        lateinit var resources: Resources
        lateinit var error: String
        lateinit var success: String


        fun setContextVal(context: AppCompatActivity) {
            this.context = context
            resources = context.resources
            error = resources.getString(R.string.error)
            success = resources.getString(R.string.success)

        }

        fun phoneNumberVerification(str: String): Boolean {
            if (checkEmpty(str, context.resources.getString(R.string.empty_cellno))) {
                if (str.length == 10) {
                    return true
                } else {
                    showToast(
                        context!!,
                        resources.getString(R.string.cell_number_error), false
                    )
                }

            }

            return false
        }


        fun checkEmpty(str: String, errorMsg: String): Boolean {
            if (str != null && str.length > 0) {
                return true
            }
            showToast(
                context!!,
                errorMsg, false
            )
            return false
        }
    }
}