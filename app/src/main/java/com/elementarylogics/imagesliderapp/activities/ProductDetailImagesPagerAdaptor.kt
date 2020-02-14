package com.elementarylogics.imagesliderapp.activities

import android.content.Context
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.elementarylogics.imagesliderapp.R
import com.example.parsaniahardik.kotlin_image_slider.ImageModel
import java.util.ArrayList

class ProductDetailImagesPagerAdaptor (
    private val context: Context,
    private val imageModelArrayList: ArrayList<ImageModel>, saleItemClickListener: ItemClickListener
) : PagerAdapter() {
    private val inflater: LayoutInflater
    lateinit var saleItemClickListener: ItemClickListener

    init {
        inflater = LayoutInflater.from(context)
        this.saleItemClickListener=saleItemClickListener
    }


    public interface ItemClickListener {
        fun onImageClickListner(position: Int)
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return imageModelArrayList.size
    }

    override fun instantiateItem(view: ViewGroup, position: Int): Any {
        val imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false)!!

        val imageView = imageLayout
            .findViewById(R.id.image) as ImageView


        imageView.setImageResource(imageModelArrayList[position].getImage_drawables())
        imageView.setOnClickListener(View.OnClickListener {
            saleItemClickListener.onImageClickListner(position)
        })

        view.addView(imageLayout, 0)

        return imageLayout
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun restoreState(state: Parcelable?, loader: ClassLoader?) {}

    override fun saveState(): Parcelable? {
        return null
    }


}