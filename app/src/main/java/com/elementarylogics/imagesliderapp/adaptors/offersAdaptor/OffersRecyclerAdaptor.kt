package com.elementarylogics.imagesliderapp.adaptors.offersAdaptor


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView


import com.elementarylogics.imagesliderapp.R
import com.elementarylogics.imagesliderapp.dataclases.Product
import com.squareup.picasso.Picasso

import android.graphics.Paint


class OffersRecyclerAdaptor : RecyclerView.Adapter<OffersRecyclerAdaptor.ViewHolder> {

    private lateinit var mEmployees: ArrayList<Product>
    lateinit var context: Context

    constructor(employeeList: ArrayList<Product>, context: Context) {
        this.mEmployees = employeeList
        this.context = context
    }

    override
    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_offers_rec, parent, false)
        return ViewHolder(view)
    }

    override
    fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val employee = mEmployees[position]


        holder.tvOffer.setText(employee.off)
        Picasso.get().load(employee.imgProductPath).centerCrop()
            .error(R.drawable.ic_home_black_24dp)
        holder.tvDiscountedPrice.setText(employee.discountedPrice)
        holder.tvOrignalPrice.setText(employee.priceOrigonal)
        holder.tvOrignalPrice.setPaintFlags(holder.tvOrignalPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
        holder.tvName.setText(employee.name)
        holder.tvUnit.setText(employee.unit)
        holder.btnAdd.setOnClickListener(View.OnClickListener {
            Toast.makeText(context, position.toString(), Toast.LENGTH_SHORT).show()
            holder.relAdd.visibility = View.GONE
            holder.tvQuantity.setText(employee.itemQuantity++.toString())
            holder.relAddMinus.visibility = View.VISIBLE

        })
        holder.imgAdd.setOnClickListener(View.OnClickListener {
            Toast.makeText(context, position.toString(), Toast.LENGTH_SHORT).show()
            holder.tvQuantity.setText(employee.itemQuantity++.toString())
        })
        holder.imgMinus.setOnClickListener(View.OnClickListener {
            Toast.makeText(context, position.toString(), Toast.LENGTH_SHORT).show()
            holder.tvQuantity.setText(employee.itemQuantity++.toString())
        })
    }


    fun updateEmployeeListItems(employees: List<Product>) {
        val diffCallback = DiffUtilOffers(this.mEmployees, employees)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.mEmployees.clear()
        this.mEmployees.addAll(employees)


        diffResult.dispatchUpdatesTo(this)
    }

    override
    fun getItemCount(): Int {
        return mEmployees.size
    }

//    override fun getItemId(position: Int): Long {
//        return position as Long
//    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvOffer: TextView
        val tvUnit: TextView
        val tvName: TextView
        val imgIcon: ImageView
        val imgAdd: ImageView
        val imgMinus: ImageView
        val btnAdd: Button
        val tvDiscountedPrice: TextView
        val tvOrignalPrice: TextView
        val relAdd: RelativeLayout
        val relAddMinus: RelativeLayout
        val tvQuantity: TextView


        init {
            tvUnit = itemView.findViewById(R.id.tvUnit)
            tvName = itemView.findViewById(R.id.tvName)
            imgIcon = itemView.findViewById(R.id.imgICon) as ImageView

            imgAdd = itemView.findViewById(R.id.imgAdd)
            imgMinus = itemView.findViewById(R.id.imgMinus)

            tvOffer = itemView.findViewById(R.id.tvOffer)
            tvDiscountedPrice = itemView.findViewById(R.id.tvDiscountedPrice)
            tvOrignalPrice = itemView.findViewById(R.id.tvOrignalPrice)
            btnAdd = itemView.findViewById(R.id.btnAdd)

            relAdd = itemView.findViewById(R.id.relAdd)
            relAddMinus = itemView.findViewById(R.id.relAddMinusQuantity)

            tvQuantity = itemView.findViewById(R.id.tvQuantity)

        }
    }

}