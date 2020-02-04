package com.elementarylogics.imagesliderapp.adaptors.offersAdaptor

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.elementarylogics.imagesliderapp.dataclases.Product

class DiffUtilOffers : DiffUtil.Callback {


    private var mOldEmployeeList: List<Product>? = null
    private var mNewEmployeeList: List<Product>? = null

    constructor(oldEmployeeList: List<Product>, newEmployeeList: List<Product>) {
        this.mOldEmployeeList = oldEmployeeList;
        this.mNewEmployeeList = newEmployeeList;
    }

    override fun getOldListSize(): Int {
        return mOldEmployeeList!!.size
    }

    override fun getNewListSize(): Int {
        return mNewEmployeeList!!.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldEmployeeList!!.get(oldItemPosition).id == mNewEmployeeList!!.get(
            newItemPosition
        ).id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldEmployeeList!!.get(oldItemPosition)
        val newEmployee = mNewEmployeeList!!.get(newItemPosition)

        return oldEmployee.name.equals(newEmployee.name)
    }

    @Nullable
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }


}