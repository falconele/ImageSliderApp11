package com.elementarylogics.imagesliderapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.elementarylogics.expandablerecyclerviewkotlin.ParentRecyclerAdapter
import com.elementarylogics.imagesliderapp.activities.SearchProductActivity
import com.elementarylogics.imagesliderapp.adaptors.offersAdaptor.DummyOffersDataUtil
import com.elementarylogics.imagesliderapp.adaptors.offersAdaptor.OffersRecyclerAdaptor
import com.elementarylogics.imagesliderapp.dataclases.Product
import com.elementarylogics.imagesliderapp.utils.Utility
import com.example.parsaniahardik.kotlin_image_slider.ImageModel
import com.example.parsaniahardik.kotlin_image_slider.SlidingImage_Adapter
import com.example.parsaniahardik.kotlin_image_slider.SlidingImage_Adapter.SaleItemClickListener
import com.google.android.material.textfield.TextInputEditText
import com.viewpagerindicator.CirclePageIndicator
import kotlinx.android.synthetic.main.fragment_dashborad_slider.*
import kotlinx.android.synthetic.main.fragment_dashborad_slider.view.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class DashboradSliderFragment : Fragment(), ParentRecyclerAdapter.Item {


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

    val onsaleItemClcikListener: SaleItemClickListener = object : SaleItemClickListener {
        override fun onSalesItemClickListner(position: Int) {
            Toast.makeText(context, position.toString() + " Sale Clicked", Toast.LENGTH_SHORT)
                .show()

            val action = DashboradSliderFragmentDirections.MoveToSaleFragment()
            action.setId(position.toString())
            action.setTitle(position.toString() + " Sale Clicked")
            NavHostFragment.findNavController(this@DashboradSliderFragment).navigate(action)
        }

    }


    private fun init(view: View) {

        mPager = view.findViewById(R.id.pager) as ViewPager
        mPager!!.adapter =
            SlidingImage_Adapter(activity!!, this.imageModelArrayList!!, onsaleItemClcikListener)

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

//    var products: ArrayList<Product> = ArrayList()

    lateinit var etSearchProduct: TextInputEditText
   val SEARCH_PROD_REQ_CODE = 110

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.fragment_dashborad_slider, container, false)

        view.etSearchProduct.setOnClickListener(View.OnClickListener {
            Toast.makeText(activity, "Clicked", Toast.LENGTH_SHORT).show()
        })
        init(view)

        Utility.productList = DummyOffersDataUtil.getEmployeeListSortedByRole()
        recOffers = view.findViewById(R.id.recOffers)
        offersRecyclerAdaptor = OffersRecyclerAdaptor(Utility.productList, activity!!.applicationContext)
        recOffers.setLayoutManager(
            LinearLayoutManager(
                activity!!.applicationContext,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )
//        recOffers.setItemAnimator(SlideInUpAnimator())
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recOffers)
        recOffers.setAdapter(offersRecyclerAdaptor)


        recyclerView = view.findViewById(R.id.recyclerview)
        recyclerView.setLayoutManager(LinearLayoutManager(context))
        recyclerView.isNestedScrollingEnabled = false
//        ViewCompat.setNestedScrollingEnabled(recyclerView, true)
        runAnimation(recyclerView!!, 0)

//        btna.setOnClickListener(View.OnClickListener {
//
//            offersRecyclerAdaptor.itemchanged(3)
//
//        })
//

        etSearchProduct = view.findViewById(R.id.etSearchProduct)
        etSearchProduct.setOnClickListener(View.OnClickListener {

            val intent = Intent(activity, SearchProductActivity::class.java)
            startActivityForResult(intent, SEARCH_PROD_REQ_CODE)

        })

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


    //parent child recyclerviews
    lateinit var adapter: ParentRecyclerAdapter
    lateinit var recyclerView: RecyclerView
    override fun onClick(position: Int) {

//        var layoutManager = LinearLayoutManager(context)
////
//        layoutManager.scrollToPositionWithOffset(position, 12)
//        recyclerView!!.layoutManager = layoutManager


//        Toast.makeText(context,recyclerView.getChildAt(position).x.toString()+"   "+ recyclerView.getChildAt(position).y.toString(), Toast.LENGTH_SHORT).show()
        adapter.notifyDataSetChanged()
        moveToPosition(position)


//        recyclerView.smoothScrollToPosition(position)
//        scrollView.scrollTo(0, y.toInt())
//        recyclerView!!.scrollToPosition(position)


    }

    fun moveToPosition(position: Int) {
        Timer("SettingUp", false).schedule(500) {

            val displayMetrics = DisplayMetrics()
            activity!!.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics)
            val height = (displayMetrics.heightPixels / 2) - linHeader.height
            val y = height + recyclerView.getChildAt(position).bottom
            scrollView.smoothScrollTo(0, y.toInt())
//            Toast.makeText(context, "moved", Toast.LENGTH_SHORT).show()
        }
    }

    private fun runAnimation(recyclerView: RecyclerView, type: Int) {
        val context = recyclerView.context
        var controller: LayoutAnimationController? = null

        if (type == 0)
            controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_fall_down)


        adapter = ParentRecyclerAdapter(context, this)
        recyclerView.adapter = adapter
        //recyclerView.layoutAnimation = controller
        recyclerView.adapter!!.notifyDataSetChanged()
        // recyclerView.scheduleLayoutAnimation()

        //some new line added
        //some new line added
    }

}
