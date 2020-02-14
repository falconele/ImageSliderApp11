package com.elementarylogics.imagesliderapp.adaptors.lastminutebuyadaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.elementarylogics.imagesliderapp.R
import com.elementarylogics.imagesliderapp.dataclases.Product
import com.squareup.picasso.Picasso

class LastMinuteBuyAdaptor : RecyclerView.Adapter<LastMinuteBuyAdaptor.ViewHolder> {

    public lateinit var mEmployees: List<Product>
    lateinit var context: Context
    lateinit var itemClickListner: ItemClickListner

    constructor(
        employeeList: List<Product>,
        context: Context,
        itemClickListner: ItemClickListner
    ) {
        this.mEmployees = employeeList
        this.context = context
        this.itemClickListner = itemClickListner

    }

    interface ItemClickListner {
        fun onItemClicklistner(position: Int)
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


        holder.tvProductName.setText(employee.name)
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
            itemClickListner.onItemClicklistner(position)
        })
        holder.cardProductListItem.setOnClickListener(View.OnClickListener {
            itemClickListner.onItemClicklistner(position)
        })
    }


//    fun updateEmployeeListItems(employees: List<Product>) {
//        val diffCallback = DiffUtilOffers(this.mEmployees, employees)
//        val diffResult = DiffUtil.calculateDiff(diffCallback)
//
//        this.mEmployees.clear()
//        this.mEmployees.addAll(employees)
//
//
//        diffResult.dispatchUpdatesTo(this)
//    }

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

        val cardProductListItem: CardView
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
        val tvDeliveryTime: TextView


        init {
            cardProductListItem = itemView.findViewById(R.id.cardProductListItem)

            imgDetail = itemView.findViewById(R.id.imgDetail)
            ProductWeight = itemView.findViewById(R.id.tvProductWeight)
            imgIcon = itemView.findViewById(R.id.imgIcon) as ImageView

            imgAdd = itemView.findViewById(R.id.imgAdd)
            imgMinus = itemView.findViewById(R.id.imgMinus)

            tvProductName = itemView.findViewById(R.id.tvProductName)
            tvPrice = itemView.findViewById(R.id.tvPrice)
            imgDeliveryStatus = itemView.findViewById(R.id.imgDeliveryTime)
            btnAdd = itemView.findViewById(R.id.btnAdd)

            relAdd = itemView.findViewById(R.id.relAdd)
            relAddMinus = itemView.findViewById(R.id.relAddMinusQuantity)

            tvQuantity = itemView.findViewById(R.id.tvQuantity)

            tvDeliveryTime = itemView.findViewById(R.id.tvDeliveryTime)

        }
    }

}