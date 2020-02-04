package com.elementarylogics.expandablerecyclerviewkotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.elementarylogics.imagesliderapp.R

class ChildRecyclerAdaptor : RecyclerView.Adapter<ChildRecyclerAdaptor.SimpleViewHolder>() {

    internal var dataSource: MutableList<Int>


    init {
        dataSource = ArrayList()

        for (i in 0..50) {
            dataSource.add(i)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.child_item, parent, false)
        return SimpleViewHolder(view)
    }

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        holder.textView.text = dataSource[position].toString()
        holder.imgArrow.setOnClickListener(View.OnClickListener {

            Toast.makeText(it.context, position.toString() , Toast.LENGTH_SHORT).show()
        })

    }

    override fun getItemCount(): Int {
        return dataSource.size
    }


    class SimpleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView
        var imgArrow: ImageView

        init {
            textView = itemView.findViewById(R.id.textView)
            imgArrow = itemView.findViewById(R.id.imgChild)
        }
    }


}