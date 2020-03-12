package com.elementarylogics.imagesliderapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elementarylogics.imagesliderapp.R
import com.elementarylogics.imagesliderapp.adaptors.myordersadaptor.MyOrderAdaptor
import com.elementarylogics.imagesliderapp.dataclases.Order

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [MyPendingOrdersFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MyPendingOrdersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyPendingOrdersFragment : Fragment(), MyOrderAdaptor.OnItemClick {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null



    override fun onItemClickListner(position: Int) {

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }



    lateinit var recMyOrders: RecyclerView
    lateinit var views: View


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        views = inflater.inflate(R.layout.fragment_myorders_list, container, false)
        recMyOrders = views.findViewById(R.id.recMyOrders)
        recMyOrders!!.setLayoutManager(LinearLayoutManager(context))
        recMyOrders.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )
        runAnimation(recMyOrders!!)
        return views
    }

    lateinit var adapter: MyOrderAdaptor
    private fun runAnimation(recyclerView: RecyclerView) {
        val orders = listOf(
            Order(
                1,
                "02/02/2020",
                "",
                "melon,laptop,cd,soap..",
                "Lahore road shaikuphura jhumra",
                "Ali",
                "150.6$",
                "Completed",
                "https://www.google.com/imgres?imgurl=https%3A%2F%2Fi.pinimg.com%2Foriginals%2F75%2F2b%2F67%2F752b67b53f06b3707f133f8e81862ab8.jpg&imgrefurl=https%3A%2F%2Fwww.pinterest.com%2Fpin%2F819514463412770988%2F&tbnid=JrdmmW9dOJsW6M&vet=12ahUKEwj_35-z8u7nAhXO04UKHfhmBLwQMygDegUIARCMAg..i&docid=vpwMbEU2pKdSYM&w=1920&h=1080&q=nature%20wallpapers&ved=2ahUKEwj_35-z8u7nAhXO04UKHfhmBLwQMygDegUIARCMAg"
            ),
            Order(
                9,
                "02/02/2020",
                "",
                "melon,laptop,cd,soap...",
                "Lahore road shaikuphura jhumra",
                "Ali",
                "150.6$",
                "Completed",
                "https://www.google.com/imgres?imgurl=https%3A%2F%2Fi.pinimg.com%2Foriginals%2F75%2F2b%2F67%2F752b67b53f06b3707f133f8e81862ab8.jpg&imgrefurl=https%3A%2F%2Fwww.pinterest.com%2Fpin%2F819514463412770988%2F&tbnid=JrdmmW9dOJsW6M&vet=12ahUKEwj_35-z8u7nAhXO04UKHfhmBLwQMygDegUIARCMAg..i&docid=vpwMbEU2pKdSYM&w=1920&h=1080&q=nature%20wallpapers&ved=2ahUKEwj_35-z8u7nAhXO04UKHfhmBLwQMygDegUIARCMAg"
            ),
            Order(
                2,
                "02/02/2020",
                "",
                "melon,laptop,cd,soap...",
                "Lahore road shaikuphura jhumra",
                "Ali",
                "150.6$",
                "Completed",
                "https://www.google.com/imgres?imgurl=https%3A%2F%2Fwebneel.com%2Fwallpaper%2Fsites%2Fdefault%2Ffiles%2Fimages%2F08-2018%2F3-nature-wallpaper-mountain.jpg&imgrefurl=https%3A%2F%2Fwebneel.com%2Fnature-wallpapers-your-desktop&tbnid=PTQGU6Dz2RVDgM&vet=12ahUKEwj_35-z8u7nAhXO04UKHfhmBLwQMygEegUIARCOAg..i&docid=tOD8uXI5Fe0ujM&w=1600&h=1008&q=nature%20wallpapers&ved=2ahUKEwj_35-z8u7nAhXO04UKHfhmBLwQMygEegUIARCOAg"
            ),
            Order(
                3,
                "02/02/2020",
                "",
                "melon,laptop,cd,soap...",
                "Lahore road shaikuphura jhumra",
                "Ali",
                "150.6$",
                "Completed",
                "https://webneel.com/wallpaper/2-nature-wallpaper-grass?s=o"
            ),
            Order(
                4,
                "02/02/2020",
                "",
                "melon,laptop,cd,soap...",
                "Lahore road shaikuphura jhumra",
                "Ali",
                "150.6$",
                "Completed",
                "https://webneel.com/wallpaper/i/island-beach-scenery-wallpaper/524/o/9022"
            ),
            Order(
                5,
                "02/02/2020",
                "",
                "melon,laptop,cd,soap...",
                "Lahore road shaikuphura jhumra",
                "Ali",
                "150.6$",
                "Completed",
                "https://webneel.com/wallpaper/i/mediterranean-beach-wallpaper/563/o/9022"
            ),
            Order(
                6,
                "02/02/2020",
                "",
                "melon,laptop,cd,soap...",
                "Lahore road shaikuphura jhumra",
                "Ali",
                "150.6$",
                "Completed",
                "https://webneel.com/wallpaper/i/croatian-boat-scenery-wallpaper/535/o/9022"
            ),
            Order(
                7,
                "02/02/2020",
                "",
                "melon,laptop,cd,soap...",
                "Lahore road shaikuphura jhumra",
                "Ali",
                "150.6$",
                "Completed",
                "https://www.google.com/imgres?imgurl=https%3A%2F%2Fi.pinimg.com%2Foriginals%2F75%2F2b%2F67%2F752b67b53f06b3707f133f8e81862ab8.jpg&imgrefurl=https%3A%2F%2Fwww.pinterest.com%2Fpin%2F819514463412770988%2F&tbnid=JrdmmW9dOJsW6M&vet=12ahUKEwj_35-z8u7nAhXO04UKHfhmBLwQMygDegUIARCMAg..i&docid=vpwMbEU2pKdSYM&w=1920&h=1080&q=nature%20wallpapers&ved=2ahUKEwj_35-z8u7nAhXO04UKHfhmBLwQMygDegUIARCMAg"
            ),
            Order(
                8,
                "02/02/2020",
                "",
                "melon,laptop,cd,soap...",
                "Lahore road shaikuphura jhumra",
                "Ali",
                "150.6$",
                "Completed",
                "https://www.google.com/imgres?imgurl=https%3A%2F%2Fi.pinimg.com%2Foriginals%2F75%2F2b%2F67%2F752b67b53f06b3707f133f8e81862ab8.jpg&imgrefurl=https%3A%2F%2Fwww.pinterest.com%2Fpin%2F819514463412770988%2F&tbnid=JrdmmW9dOJsW6M&vet=12ahUKEwj_35-z8u7nAhXO04UKHfhmBLwQMygDegUIARCMAg..i&docid=vpwMbEU2pKdSYM&w=1920&h=1080&q=nature%20wallpapers&ved=2ahUKEwj_35-z8u7nAhXO04UKHfhmBLwQMygDegUIARCMAg"
            ),
            Order(
                10,
                "02/02/2020",
                "",
                "melon,laptop,cd,soap...",
                "Lahore road shaikuphura jhumra",
                "Ali",
                "150.6$",
                "Completed",
                "https://www.google.com/imgres?imgurl=https%3A%2F%2Fi.pinimg.com%2Foriginals%2F75%2F2b%2F67%2F752b67b53f06b3707f133f8e81862ab8.jpg&imgrefurl=https%3A%2F%2Fwww.pinterest.com%2Fpin%2F819514463412770988%2F&tbnid=JrdmmW9dOJsW6M&vet=12ahUKEwj_35-z8u7nAhXO04UKHfhmBLwQMygDegUIARCMAg..i&docid=vpwMbEU2pKdSYM&w=1920&h=1080&q=nature%20wallpapers&ved=2ahUKEwj_35-z8u7nAhXO04UKHfhmBLwQMygDegUIARCMAg"
            )
        )

        adapter = MyOrderAdaptor(orders, context!!, this)
        recMyOrders.adapter = adapter
//        //recyclerView.layoutAnimation = controller
//        recSearchProduct.adapter!!.notifyDataSetChanged()
        // recyclerView.scheduleLayoutAnimation()


    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyPendingOrdersFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
