package com.elementarylogics.imagesliderapp.network

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class ResponseResult<out T> {
    @SerializedName("status")
    @Expose
    private var status: Boolean? = null
    @SerializedName("code")
    @Expose
    private var code: Int? = null
    @SerializedName("message")
    @Expose
    private var message: String? = null
    @SerializedName("data")
    @Expose
    private var data: T? = null

    fun getStatus(): Boolean? {
        return status
    }

    fun setStatus(status: Boolean?) {
        this.status = status
    }

    fun getCode(): Int? {
        return code
    }

    fun setCode(code: Int?) {
        this.code = code
    }

    fun getMessage(): String? {
        return message
    }

    fun setMessage(message: String?) {
        this.message = message
    }

    fun getData(): T? {
        return data
    }

//    fun setData(data: T) {
//        this.data = data
//    }
}