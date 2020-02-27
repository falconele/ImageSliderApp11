package com.elementarylogics.imagesliderapp.adaptors.myordersadaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elementarylogics.imagesliderapp.R
import com.elementarylogics.imagesliderapp.dataclases.Order
import java.util.*

class MyOrderAdaptor : RecyclerView.Adapter<MyOrderAdaptor.ViewHolder> {

    public lateinit var ordersList: List<Order>
    lateinit var context: Context
    lateinit var onItemClick: OnItemClick

    constructor(ordersList: List<Order>, context: Context,onItemClick: OnItemClick) {
        this.ordersList = ordersList
        this.context = context
        this.onItemClick=onItemClick
    }

    public interface OnItemClick {
        fun onItemClickListner(position: Int)
    }

    override
    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.my_orders_item, parent, false)
        return ViewHolder(view)
    }

    override
    fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = ordersList[position]


        holder.tvRefNo.setText(order.id.toString())
        Glide.with(context).load(order.benificieryImg).into(holder.imgBenificiery)
        holder.tvProductList.setText(order.productList)
        holder.tvAddress.setText(order.address)
        holder.tvBenificieryName.setText(order.benificieryName)
        holder.tvDateTime.setText(order.dtProcessed)
        holder.tvAmount.setText(order.amount.toString())
        holder.tvStatus.setText(order.status)

        holder.cardOrderItem.setOnClickListener(View.OnClickListener {
            Toast.makeText(context, position.toString(), Toast.LENGTH_SHORT).show()
            onItemClick.onItemClickListner(position)

        })

    }


//    fun updateEmployeeListItems(employees: List<Order>) {
//        val diffCallback = DiffUtilOffers(this.ordersList, employees)
//        val diffResult = DiffUtil.calculateDiff(diffCallback)
//
//        this.ordersList.clear()
//        this.ordersList.addAll(employees)
//
//
//        diffResult.dispatchUpdatesTo(this)
//    }

    override
    fun getItemCount(): Int {
        return ordersList.size
    }

//    override fun getItemId(position: Int): Long {
//        return position as Long
//    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvRefNo: TextView
        val tvDateTime: TextView
        val tvBenificieryName: TextView
        val imgBenificiery: ImageView
        val tvProductList: TextView
        val tvAddress: TextView
        val cardOrderItem: CardView
        val tvStatus: TextView
        val tvAmount: TextView


        init {
            tvDateTime = itemView.findViewById(R.id.tvDateTime)
            tvBenificieryName = itemView.findViewById(R.id.tvBenificieryName)
            imgBenificiery = itemView.findViewById(R.id.imgBenificiery) as ImageView
            tvRefNo = itemView.findViewById(R.id.tvRefNo)
            tvProductList = itemView.findViewById(R.id.tvProductList)
            tvAddress = itemView.findViewById(R.id.tvAddress)
            cardOrderItem = itemView.findViewById(R.id.cardOrderItem)
            tvStatus = itemView.findViewById(R.id.tvStatus)
            tvAmount = itemView.findViewById(R.id.tvAmount)

        }
    }

}