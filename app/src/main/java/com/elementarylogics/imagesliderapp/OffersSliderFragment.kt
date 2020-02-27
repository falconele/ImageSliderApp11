package com.elementarylogics.imagesliderapp

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_product_listing.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [OffersSliderFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [OffersSliderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OffersSliderFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
//    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var views: View
    lateinit var tabItemsCat:TabLayout
    lateinit var viewpager:ViewPager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        views=inflater.inflate(R.layout.fragment_offers_slider, container, false)
        tabItemsCat=views.findViewById(R.id.tabItemsCat)
        viewpager=views.findViewById(R.id.viewpager)

        setupViewPager(viewpager)
        tabItemsCat.setupWithViewPager(viewpager)



        return views
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(activity!!.supportFragmentManager)
        adapter.addFragment(MyordersListFragment(), resources.getString(R.string.processed))
        adapter.addFragment(MyordersListFragment(), resources.getString(R.string.pending))

//
//        adapter.addFragment(MyOrdersSlidersFragments(), "Tutorial")
//        adapter.addFragment(OffersSliderFragment(), "AndroidQuiz")
//        adapter.addFragment(SalesFragment(), "GithubCode")
        viewPager.setAdapter(adapter)
    }

    internal inner class ViewPagerAdapter(manager: FragmentManager) :
        FragmentPagerAdapter(manager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()


        override fun getCount(): Int {
            return mFragmentList.size
        }

        override fun getItem(position: Int): Fragment {
            return mFragmentList.get(position)
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mFragmentTitleList.get(position)
        }
    }



    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OffersSliderFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OffersSliderFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}
