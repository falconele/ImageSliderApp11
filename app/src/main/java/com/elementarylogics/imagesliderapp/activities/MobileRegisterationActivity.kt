package com.elementarylogics.imagesliderapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Constraints
import androidx.core.content.ContextCompat
import com.elementarylogics.imagesliderapp.R
import com.elementarylogics.imagesliderapp.dataclases.Register
import com.elementarylogics.imagesliderapp.dataclases.User
import com.elementarylogics.imagesliderapp.network.Apis
import com.elementarylogics.imagesliderapp.network.ResponseResult
import com.elementarylogics.imagesliderapp.network.RetrofitClient
import com.elementarylogics.imagesliderapp.utils.ApplicationUtils
import com.elementarylogics.imagesliderapp.utils.ErrorCheckingUtils
import com.elementarylogics.imagesliderapp.utils.SharedPreference
import com.elementarylogics.imagesliderapp.utils.Utility
import kotlinx.android.synthetic.main.activity_mobile_registeration.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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
        repeated = 30
        updatTimeSec()
//        etDigitsVerification.requestFocus()
//        showInputMethod()
    }

    fun updatTimeSec() {
        if (repeated >= 0) {
            handler.postDelayed(updateTime, 1000L)
            tvResend.setText(resources.getString(R.string.resend_in))
            tvSec.setText(resources.getString(R.string.sec))
            tvTime.setText(repeated.toString())
        } else {
            handler.removeCallbacks(updateTime)
            repeated = 30
            tvTime.setText("")
            tvSec.setText("")
            tvResend.setText(resources.getString(R.string.resend_code))
            tvResend.isEnabled = true

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
        btnVerify.setOnClickListener(View.OnClickListener {
            validateVerificationCode()
        })

        tvResend.setOnClickListener(View.OnClickListener {
            tvResend.isEnabled = false
            validateData()
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
//                Toast.makeText(applicationContext, "Done", Toast.LENGTH_SHORT).show()
                handler.removeCallbacks(updateTime)
                ApplicationUtils.hideKeyboard(this@MobileRegisterationActivity)
                verifyCode()


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


            ApplicationUtils.hideKeyboard(this)
            tvResend.isEnabled = false
            registerUser()
        }

    }

    fun validateVerificationCode() {
        ErrorCheckingUtils.setContextVal(this)
        if (ErrorCheckingUtils.codeVerification(etDigitsVerification.text.toString())) {

        }
    }

    override fun onBackPressed() {

        if (handler != null) {
            handler.removeCallbacks(updateTime)

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


    var register: Register? = null

    fun registerUser() {
        Utility.showProgressBar(this, progressBar, true)
        val api: Apis = RetrofitClient.getClient()!!.create(Apis::class.java)
        val token: String =
            "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJsb2dnZWRJbkFzIjoiYWRtaW4iLCJpYXQiOjE0MjI3Nzk2Mzh9.gzSraSYS8EXBxLN_oWnFSRgCzcmJmMjLiuyu5CSpyHI"
        val call: Call<ResponseResult<Register>> =
            api.registerUser(
                token,
                etMobileNumber.text.toString()
            )

        call.enqueue(object : Callback<ResponseResult<Register>> {
            override fun onResponse(
                call: Call<ResponseResult<Register>>,
                response: Response<ResponseResult<Register>>
            ) {
                Utility.showProgressBar(this@MobileRegisterationActivity, progressBar, false)
                try {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus()!!) {
                            if (response.body().getData() != null) {
                                register = response.body().getData()
                                ApplicationUtils.showToast(
                                    this@MobileRegisterationActivity,
                                    response.body().getMessage(),
                                    true
                                )

                                removeTimerCallbacks()

                            }
                        } else {
                            ApplicationUtils.showToast(
                                this@MobileRegisterationActivity,
                                response.body().getMessage().toString() + "",
                                false
                            )
                        }
                    } else {
                        val jsonObject = JSONObject(response.errorBody().string())
                        ApplicationUtils.showToast(
                            this@MobileRegisterationActivity,
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
                call: Call<ResponseResult<Register>>,
                t: Throwable
            ) {
//                showProgressBar(false)
                Utility.showProgressBar(this@MobileRegisterationActivity, progressBar, false)
                Log.d(
                    Constraints.TAG,
                    "onFailure: " + t.message
                )
            }
        })
    }


    var user: User? = null
    fun verifyCode() {
        Utility.showProgressBar(this, progressBar, true)
        val api: Apis = RetrofitClient.getClient()!!.create(Apis::class.java)
        val token: String =
            "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJsb2dnZWRJbkFzIjoiYWRtaW4iLCJpYXQiOjE0MjI3Nzk2Mzh9.gzSraSYS8EXBxLN_oWnFSRgCzcmJmMjLiuyu5CSpyHI"
        val call: Call<ResponseResult<User>> =
            api.verifyCode(
                token,
                register!!.phone_number,
                etDigitsVerification.text.toString()
            )

        call.enqueue(object : Callback<ResponseResult<User>> {
            override fun onResponse(
                call: Call<ResponseResult<User>>,
                response: Response<ResponseResult<User>>
            ) {
                Utility.showProgressBar(this@MobileRegisterationActivity, progressBar, false)
                try {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus()!!) {
                            if (response.body().getData() != null) {
                                user = response.body().getData()
                                ApplicationUtils.showToast(
                                    this@MobileRegisterationActivity,
                                    response.body().getMessage(),
                                    true
                                )

                                SharedPreference.saveUserProfile(
                                    this@MobileRegisterationActivity,
                                    user
                                )

                                startActivity(
                                    Intent(
                                        this@MobileRegisterationActivity,
                                        ProfileActivity::class.java
                                    )
                                )
                                finish()

                            }
                        } else {
                            tvResend.setText(resources.getString(R.string.resend_code))
                            tvTime.setText("")
                            tvSec.setText("")
                            etDigitsVerification.setText("")
                            tvResend.isEnabled = true
                            ApplicationUtils.showToast(
                                this@MobileRegisterationActivity,
                                response.body().getMessage().toString() + "",
                                false
                            )
                        }
                    } else {
                        val jsonObject = JSONObject(response.errorBody().string())
                        ApplicationUtils.showToast(
                            this@MobileRegisterationActivity,
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
                call: Call<ResponseResult<User>>,
                t: Throwable
            ) {
//                showProgressBar(false)
                Utility.showProgressBar(this@MobileRegisterationActivity, progressBar, false)
                Log.d(
                    Constraints.TAG,
                    "onFailure: " + t.message
                )
            }
        })
    }

}
