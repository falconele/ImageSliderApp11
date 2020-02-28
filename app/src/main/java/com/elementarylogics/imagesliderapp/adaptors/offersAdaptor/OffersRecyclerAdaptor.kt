package com.elementarylogics.imagesliderapp.adaptors.offersAdaptor


//import com.squareup.picasso.Picasso
import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elementarylogics.imagesliderapp.R
import com.elementarylogics.imagesliderapp.dataclases.Product
import java.util.*


class OffersRecyclerAdaptor : RecyclerView.Adapter<OffersRecyclerAdaptor.ViewHolder> {

    public lateinit var mEmployees: ArrayList<Product>
    lateinit var context: Context

    val colors = listOf(
        R.color.color1,
        R.color.color2,
        R.color.color3,
        R.color.color0,
        R.color.color4,
        R.color.color5,
        R.color.color6,
        R.color.color7,
        R.color.color8,
        R.color.color9
    )


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

//        holder.linMainItem.setBackgroundResource(colors[position])
        holder.tvOffer.setText(employee.off)
//        Picasso.get().load(employee.imgProductPath).centerCrop()
//            .error(R.drawable.ic_home_black_24dp)
        Glide.with(context).load(employee.imgProductPath).into(holder.imgIcon)
        holder.tvDiscountedPrice.setText(employee.discountedPrice)
        holder.tvOrignalPrice.setText(employee.priceOrigonal)
        holder.tvOrignalPrice.setPaintFlags(holder.tvOrignalPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
        holder.tvName.setText(employee.name)
        holder.tvUnit.setText(employee.unit)
        holder.tvQuantity.setText(employee.itemQuantity.toString())
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

    public fun itemchanged(position: Int) {
        mEmployees.get(position).itemQuantity++
        notifyItemChanged(position)
//        notifyItemChanged(position)

    }

    public fun updateData(position: Int) {

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
        val linMainItem: LinearLayout
        val cardItemOffer:CardView


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

            linMainItem = itemView.findViewById(R.id.linMainItem)

            cardItemOffer=itemView.findViewById(R.id.cardItemOffer)

        }
    }

}