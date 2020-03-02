package com.elementarylogics.imagesliderapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.elementarylogics.imagesliderapp.R
import com.elementarylogics.imagesliderapp.utils.ApplicationUtils
import com.elementarylogics.imagesliderapp.utils.ErrorCheckingUtils
import kotlinx.android.synthetic.main.activity_mobile_registeration.*


class MobileRegisterationActivity : AppCompatActivity() {
    val handler = Handler()
    val runnable = object : Runnable {
        override fun run() {
            removeTimerCallbacks()
        }
    }


    fun removeTimerCallbacks() {
        tvTitle.setText(getString(R.string.verify))
        progressBar.visibility = View.GONE
        linRegisteration.visibility = View.GONE
        linMainVerification.visibility = View.VISIBLE
        tvTime.setText(
            repeated.toString()
        )
        handler.removeCallbacks(runnable)
        updatTimeSec()
//        etDigitsVerification.requestFocus()
//        showInputMethod()
    }

    fun updatTimeSec() {
        if (repeated >= 0) {
            handler.postDelayed(updateTime, 1000L)
            tvTime.setText(repeated.toString())
        } else {
            handler.removeCallbacks(updateTime)
            repeated = 30
        }
    }

    val updateTime = Runnable {
        repeated--
        updatTimeSec()

    }
    var repeated: Int = 30
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mobile_registeration)

        etMobileNumber.addTextChangedListener(textWatcher)
        etDigitsVerification.addTextChangedListener(pinCodeTextWatcher)
        etMobileNumber.requestFocus()
        showInputMethod()

        linMain.setOnClickListener(View.OnClickListener {
            ApplicationUtils.hideKeyboard(this)
        })
        btnNext.setOnClickListener(View.OnClickListener {
            //            Toast.makeText(applicationContext, "Clicked", Toast.LENGTH_SHORT).show()


            validateData()

        })

        imgBack.setOnClickListener(View.OnClickListener {
            finish()
        })


    }

    val textWatcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s.toString().length >= 10) {
                btnNext.backgroundTintList =
                    ContextCompat.getColorStateList(applicationContext, R.color.light_orange)
            } else {
                btnNext.backgroundTintList = ContextCompat.getColorStateList(
                    applicationContext,
                    R.color.button_placeholder_color
                )
            }
        }
    }

    val pinCodeTextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (s.toString().length == 4) {
                Toast.makeText(applicationContext, "Done", Toast.LENGTH_SHORT).show()
                startActivity(
                    Intent(
                        this@MobileRegisterationActivity,
                        AddressDateTimeActivity::class.java
                    )
                )
                handler.removeCallbacks(updateTime)
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

    }

    //    @SuppressLint("ResourceType")
    fun validateData() {
        ErrorCheckingUtils.setContextVal(this)
        if (ErrorCheckingUtils.phoneNumberVerification(etMobileNumber.text.toString())) {
            progressBar.visibility = View.VISIBLE
            handler.postDelayed(runnable, 1000L)
            ApplicationUtils.hideKeyboard(this)
        }

    }

    override fun onBackPressed() {

        if (handler != null) {
            handler.removeCallbacks(updateTime)
            handler.removeCallbacks(runnable)
        }

        super.onBackPressed()
    }

    fun showInputMethod() {
        val imm =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    }
}
