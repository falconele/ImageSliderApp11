package com.elementarylogics.expandablerecyclerviewkotlin


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elementarylogics.imagesliderapp.DashboradSliderFragment
import com.elementarylogics.imagesliderapp.MainActivity
import com.elementarylogics.imagesliderapp.R


class ParentRecyclerAdapter(context: Context, fragment: DashboradSliderFragment) :
    RecyclerView.Adapter<ParentRecyclerAdapter.SimpleViewHolder>() {

    internal var dataSource: MutableList<Int>
    internal var dataSource2: MutableList<Int>
//    internal var activity: MainActivity
    var itemOpened: Int
    var context: Context
    var item: Item

    init {
        itemOpened = -1
        dataSource = ArrayList()
        dataSource2 = ArrayList()
        for (i in 0..50) {
            for (j in 0..50)
                dataSource2.add(j)
            dataSource.add(i)
        }
        this.context = context
//        this.activity = activity
        this.item = fragment

    }

    val childItemClickListner:ChildRecyclerAdaptor.ItemClickListner=object :ChildRecyclerAdaptor.ItemClickListner{
        override fun onItemClickListner(position: Int) {
            item.onChildItemClick(position)
        }

    }
    public interface Item {
        fun onItemClick(position: Int)
        fun onChildItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_item, parent, false)
        return SimpleViewHolder(view)
    }

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        holder.textView.text = dataSource[position].toString()

        holder.imgArrow.setOnClickListener(View.OnClickListener {

            if (itemOpened == position) {
                itemOpened = -1

            } else {

                holder.recyclerView.visibility = View.VISIBLE
                holder.recyclerView.layoutManager =
                    GridLayoutManager(it.context, 3, RecyclerView.HORIZONTAL, false)
                val childRecyclerAdaptor = ChildRecyclerAdaptor(childItemClickListner)
                holder.recyclerView.adapter = childRecyclerAdaptor
                Toast.makeText(it.context, position.toString(), Toast.LENGTH_SHORT).show()

                itemOpened = position
            }
            this.item.onItemClick(position)
        })
        if (position == itemOpened) {
            holder.recyclerView.layoutManager =
                GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)
            val childRecyclerAdaptor = ChildRecyclerAdaptor(childItemClickListner)
            holder.recyclerView.adapter = childRecyclerAdaptor
            holder.recyclerView.visibility = View.VISIBLE
//            activity.recyclerView!!.scrollToPosition(position)

        } else {
            holder.recyclerView.visibility = View.GONE
        }

    }

    override fun getItemCount(): Int {
        return dataSource.size
    }


    class SimpleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView
        var imgArrow: ImageView
        var recyclerView: RecyclerView

        init {
            textView = itemView.findViewById(R.id.textView)
            imgArrow = itemView.findViewById(R.id.imgArrow)
            recyclerView = itemView.findViewById(R.id.recChild)
        }
    }


}
