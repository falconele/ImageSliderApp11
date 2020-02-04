package com.elementarylogics.imagesliderapp

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.elementarylogics.imagesliderapp.adaptors.offersAdaptor.DummyOffersDataUtil
import com.elementarylogics.imagesliderapp.adaptors.offersAdaptor.OffersRecyclerAdaptor
import com.elementarylogics.imagesliderapp.dataclases.Product
import com.example.parsaniahardik.kotlin_image_slider.ImageModel
import com.example.parsaniahardik.kotlin_image_slider.SlidingImage_Adapter
import com.viewpagerindicator.CirclePageIndicator
import kotlinx.android.synthetic.main.fragment_dashborad_slider.view.*
import java.util.*
import kotlin.collections.ArrayList


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class DashboradSliderFragment : Fragment() {


    private var imageModelArrayList: ArrayList<ImageModel>? = null

    private val myImageList = intArrayOf(
        R.drawable.harley2,
        R.drawable.benz2,
        R.drawable.vecto,
        R.drawable.webshots,
        R.drawable.bikess,
        R.drawable.img1
    )

    private fun populateList(): ArrayList<ImageModel> {

        val list = ArrayList<ImageModel>()

        for (i in 0..5) {
            val imageModel = ImageModel()
            imageModel.setImage_drawables(myImageList[i])
            list.add(imageModel)
        }

        return list
    }

    private fun init(view: View) {

        mPager = view.findViewById(R.id.pager) as ViewPager
        mPager!!.adapter = SlidingImage_Adapter(activity!!, this.imageModelArrayList!!)

        val indicator = view.findViewById(R.id.indicator) as CirclePageIndicator

        indicator.setViewPager(mPager)

        val density = resources.displayMetrics.density

        //Set circle indicator radius
        indicator.setRadius(5 * density)

        NUM_PAGES = imageModelArrayList!!.size

        // Auto start of viewpager
        val handler = Handler()
        val Update = Runnable {
            if (currentPage == NUM_PAGES) {
                currentPage = 0
            }
            mPager!!.setCurrentItem(currentPage++, true)
        }
        val swipeTimer = Timer()
        swipeTimer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(Update)
            }
        }, 3000, 3000)

        // Pager listener over indicator
        indicator.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageSelected(position: Int) {
                currentPage = position

            }

            override fun onPageScrolled(pos: Int, arg1: Float, arg2: Int) {

            }

            override fun onPageScrollStateChanged(pos: Int) {

            }
        })

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        imageModelArrayList = ArrayList()
        imageModelArrayList = populateList()


    }

    lateinit var recOffers: RecyclerView
    lateinit var offersRecyclerAdaptor: OffersRecyclerAdaptor

    var products: ArrayList<Product> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.fragment_dashborad_slider, container, false)

        view.etSearchProduct.setOnClickListener(View.OnClickListener {
            Toast.makeText(activity, "Clicked", Toast.LENGTH_SHORT).show()
        })
        init(view)

        products = DummyOffersDataUtil.getEmployeeListSortedByRole()
        recOffers = view.findViewById(R.id.recOffers)
        offersRecyclerAdaptor = OffersRecyclerAdaptor(products!!, activity!!.applicationContext)
        recOffers.setLayoutManager(LinearLayoutManager(activity!!.applicationContext,LinearLayoutManager.HORIZONTAL,false))
//        recOffers.setItemAnimator(SlideInUpAnimator())
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recOffers)
        recOffers.setAdapter(offersRecyclerAdaptor)


        return view
    }


    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DashboradSliderFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

        private var mPager: ViewPager? = null
        private var currentPage = 0
        private var NUM_PAGES = 0
    }
}
