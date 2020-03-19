package com.elementarylogics.imagesliderapp.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Constraints
import com.elementarylogics.imagesliderapp.R
import com.elementarylogics.imagesliderapp.dataclases.ContentPage
import com.elementarylogics.imagesliderapp.network.Apis
import com.elementarylogics.imagesliderapp.network.ResponseResult
import com.elementarylogics.imagesliderapp.network.RetrofitClient
import com.elementarylogics.imagesliderapp.utils.ApplicationUtils
import com.elementarylogics.imagesliderapp.utils.SharedPreference
import com.elementarylogics.imagesliderapp.utils.Utility
import kotlinx.android.synthetic.main.activity_addresses.progressBar
import kotlinx.android.synthetic.main.activity_addresses.tvTitle
import kotlinx.android.synthetic.main.activity_content_pages.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContentPagesActivity : AppCompatActivity() {

    var key: String = ""
    var title:String=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_pages)

        key = intent.getStringExtra("key")
        title=intent.getStringExtra("title")
        tvTitle.setText(title)
        webView.settings.setSupportZoom(true)
        webView.settings.loadWithOverviewMode = false
        webView.settings.useWideViewPort = false
//        webView.settings.builtInZoomControls = true

        imgBack.setOnClickListener(View.OnClickListener {
            finish()
        })

        getPageContent()
    }


    var contentPage: ContentPage? = null

    fun getPageContent() {
        Utility.showProgressBar(this, progressBar, true)
        val api: Apis = RetrofitClient.getClient()!!.create(Apis::class.java)
//        val token: String =
//            "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJsb2dnZWRJbkFzIjoiYWRtaW4iLCJpYXQiOjE0MjI3Nzk2Mzh9.gzSraSYS8EXBxLN_oWnFSRgCzcmJmMjLiuyu5CSpyHI"


        var token: String = ""
        val user = SharedPreference.getUserData(applicationContext)
        if (user != null) {

            token = "Bearer " + user.code
        }

        val call: Call<ResponseResult<ContentPage>> =
            api.getContent(
                token,
                key
            )

        call.enqueue(object : Callback<ResponseResult<ContentPage>> {
            override fun onResponse(
                call: Call<ResponseResult<ContentPage>>,
                response: Response<ResponseResult<ContentPage>>
            ) {
                Utility.showProgressBar(this@ContentPagesActivity, progressBar, false)
                try {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus()!!) {
                            if (response.body().getData() != null) {

                                contentPage = response.body().getData()

                                webView.setWebViewClient(WebViewClient())
                                webView.getSettings().setJavaScriptEnabled(true)
                                if (contentPage!!.content != null)
                                    webView.loadData(contentPage!!.content, "text/html", "utf-8")


                            }
                        } else {
                            ApplicationUtils.showToast(
                                this@ContentPagesActivity,
                                response.body().getMessage().toString() + "",
                                false
                            )
                        }
                    } else {
                        val jsonObject = JSONObject(response.errorBody().string())
                        ApplicationUtils.showToast(
                            this@ContentPagesActivity,
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
                call: Call<ResponseResult<ContentPage>>,
                t: Throwable
            ) {
//                showProgressBar(false)
                Utility.showProgressBar(this@ContentPagesActivity, progressBar, false)
                Log.d(
                    Constraints.TAG,
                    "onFailure: " + t.message
                )
            }
        })
    }

}
