package com.elementarylogics.imagesliderapp.adaptors.addressesadaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.elementarylogics.imagesliderapp.R
import com.elementarylogics.imagesliderapp.dataclases.AddressModel

class AddressAdaptor : RecyclerView.Adapter<AddressAdaptor.ViewHolder> {

    public lateinit var mAddressList: ArrayList<AddressModel>
    lateinit var context: Context
    lateinit var itemClickListner: ItemClickListner

    constructor(
        addressList: ArrayList<AddressModel>,
        context: Context,
        itemClickListner: ItemClickListner
    ) {
        this.mAddressList = addressList

        this.context = context
        this.itemClickListner = itemClickListner

    }

    interface ItemClickListner {
        fun onItemClicklistner(position: Int)
    }


    override
    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.address_item, parent, false)
        return ViewHolder(view)
    }

    override
    fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val employee = mAddressList[position]


        holder.tvName.setText(employee.gender + " " + employee.name)
        holder.tvFlatHouse.setText(employee.flatHouse)
        holder.tvAreaColonySector.setText(employee.areaColony)
        holder.tvAddress.setText(employee.address)
        holder.tvAddressType.setText(employee.addressType)


        holder.imgEdit.setOnClickListener(View.OnClickListener {
            itemClickListner.onItemClicklistner(position)
        })
        holder.cardAddress.setOnClickListener(View.OnClickListener {
            itemClickListner.onItemClicklistner(position)
        })
    }

    fun addItems(addresses: List<AddressModel>) {
//        this.mAddressList.clear()
//        this.mAddressList.addAll(addresses)
        notifyDataSetChanged()
    }

    override
    fun getItemCount(): Int {
        return mAddressList.size
    }

//    override fun getItemId(position: Int): Long {
//        return position as Long
//    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val cardAddress: CardView
        val tvAddressType: TextView
        val imgEdit: ImageView
        val tvName: TextView
        val tvFlatHouse: TextView
        val tvAreaColonySector: TextView
        val tvAddress: TextView


        init {
            cardAddress = itemView.findViewById(R.id.cardAddress)
            imgEdit = itemView.findViewById(R.id.imgEdit)
            tvName = itemView.findViewById(R.id.tvName)
            tvAddressType = itemView.findViewById(R.id.tvAddressType)
            tvFlatHouse = itemView.findViewById(R.id.tvFlatHouse)
            tvAreaColonySector = itemView.findViewById(R.id.tvAreaColonySector)
            tvAddress = itemView.findViewById(R.id.tvAddress)

        }
    }

}