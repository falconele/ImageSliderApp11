package com.elementarylogics.imagesliderapp.adaptors.searchproductadaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.elementarylogics.imagesliderapp.R
import com.elementarylogics.imagesliderapp.adaptors.offersAdaptor.DiffUtilOffers
import com.elementarylogics.imagesliderapp.dataclases.Product
import com.squareup.picasso.Picasso
import java.util.ArrayList

class SearchProductRecyclerAdaptor : RecyclerView.Adapter<SearchProductRecyclerAdaptor.ViewHolder> {

    public lateinit var mEmployees: ArrayList<Product>
    lateinit var context: Context

    constructor(employeeList: ArrayList<Product>, context: Context) {
        this.mEmployees = employeeList
        this.context = context
    }


    override
    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.products_list_item, parent, false)
        return ViewHolder(view)
    }

    override
    fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val employee = mEmployees[position]


        holder.tvProductName.setText(employee.off)
        Picasso.get().load(employee.imgProductPath).centerCrop()
            .error(R.drawable.ic_home_black_24dp)
        holder.tvPrice.setText(employee.priceOrigonal)
//        holder.imgDeliveryStatus.setText(employee.priceOrigonal)

        holder.ProductWeight.setText(employee.unit)
        holder.tvQuantity.setText(employee.itemQuantity.toString())
        holder.tvDeliveryTime.setText(employee.deliveryTime)


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
        holder.imgDetail.setOnClickListener(View.OnClickListener {

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

    public fun itemchanged(position: Int) {
        mEmployees.get(position).itemQuantity++
        notifyItemChanged(position)
//        notifyItemChanged(position)

    }

    public fun updateData(position: Int) {

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val cardProductListItem:CardView
        val tvProductName: TextView
        val imgDetail: ImageView
        val ProductWeight: TextView
        val imgIcon: ImageView
        val imgAdd: ImageView
        val imgMinus: ImageView
        val btnAdd: Button
        val tvPrice: TextView
        val imgDeliveryStatus: ImageView
        val relAdd: RelativeLayout
        val relAddMinus: RelativeLayout
        val tvQuantity: TextView
        val tvDeliveryTime:TextView


        init {
            cardProductListItem=itemView.findViewById(R.id.cardProductListItem)

            imgDetail = itemView.findViewById(R.id.tvUnit)
            ProductWeight = itemView.findViewById(R.id.tvName)
            imgIcon = itemView.findViewById(R.id.imgIcon) as ImageView

            imgAdd = itemView.findViewById(R.id.imgAdd)
            imgMinus = itemView.findViewById(R.id.imgMinus)

            tvProductName = itemView.findViewById(R.id.tvOffer)
            tvPrice = itemView.findViewById(R.id.tvDiscountedPrice)
            imgDeliveryStatus = itemView.findViewById(R.id.tvOrignalPrice)
            btnAdd = itemView.findViewById(R.id.btnAdd)

            relAdd = itemView.findViewById(R.id.relAdd)
            relAddMinus = itemView.findViewById(R.id.relAddMinusQuantity)

            tvQuantity = itemView.findViewById(R.id.tvQuantity)

            tvDeliveryTime=itemView.findViewById(R.id.tvDeliveryTime)

        }
    }

}