package com.elementarylogics.imagesliderapp.utils

import android.content.res.Resources
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.elementarylogics.imagesliderapp.R
import com.elementarylogics.imagesliderapp.utils.ApplicationUtils.showToast
import java.io.File

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
                    if (str[0].equals('3', true)) {
                        return true
                    } else {
                        showToast(
                            context!!,
                            resources.getString(R.string.cell_number_start_error), false
                        )
                        return false
                    }
                } else {
                    showToast(
                        context!!,
                        resources.getString(R.string.cell_number_error), false
                    )
                }

            }

            return false
        }


        fun codeVerification(str:String):Boolean {
            if (checkEmpty(str, context.resources.getString(R.string.empty_verificationCode))) {
                if (str.length==4) {
                    return true
                } else {
                    showToast(
                        context!!,
                        resources.getString(R.string.verification_code_error), false
                    )
                }

            }

            return false
        }

        fun checkEmpty(str: String, errorMsg: String): Boolean {
            if (str != null && str.replace(" ", "").replace("\n", "").length > 0) {
                return true
            }
            showToast(
                context!!,
                errorMsg, false
            )
            return false
        }

        fun emailVerification(str: String): Boolean {
            if (checkEmpty(str, context.resources.getString(R.string.empty_email))) {
                if (Patterns.EMAIL_ADDRESS.matcher(str).matches()) {
                    return true
                } else {
                    showToast(
                        context!!,
                        resources.getString(R.string.emai_error), false
                    )
                }

            }

            return false
        }

        fun profileVerification(file: File?): Boolean {
            if (file != null) {
                return true
            }
            showToast(
                context!!,
                resources.getString(R.string.empty_profile), false
            )
            return false
        }

    }
}