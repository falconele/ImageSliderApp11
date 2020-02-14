package com.elementarylogics.imagesliderapp.activities.searchproduct

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elementarylogics.imagesliderapp.R
import com.elementarylogics.imagesliderapp.activities.ProductDetailActivity
import com.elementarylogics.imagesliderapp.adaptors.searchproductadaptor.SearchProductRecyclerAdaptor
import com.elementarylogics.imagesliderapp.utils.Utility
import com.google.android.material.textfield.TextInputEditText
import com.jakewharton.rxbinding2.widget.textChanges
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_search_product.*
import java.util.concurrent.TimeUnit

class SearchProductActivity : AppCompatActivity(), SearchProductRecyclerAdaptor.ItemClickListner {
    override fun onItemClicklistner(position: Int) {
        val intent = Intent(this, ProductDetailActivity::class.java)
        startActivity(intent)
    }

    lateinit var recSearchProduct: RecyclerView
    lateinit var etSearchProduct: TextInputEditText

    // for view model
    private lateinit var viewModel: SearchProductViewModel
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_product)

        viewModel = ViewModelProviders.of(this).get(SearchProductViewModel::class.java)



        tvIteamCount.setText(Utility.productList.size.toString() + " " + getString(R.string.results_found))
        recSearchProduct = findViewById(R.id.recSearchProduct)
        etSearchProduct=findViewById(R.id.etSearchProduct)
        recSearchProduct!!.setLayoutManager(LinearLayoutManager(applicationContext))
        recSearchProduct.addItemDecoration(
            DividerItemDecoration(
                applicationContext,
                LinearLayoutManager.VERTICAL
            )
        )
        runAnimation(recSearchProduct!!)

        imgBack.setOnClickListener(View.OnClickListener {
            setResultsFun()
        })
        imgCross.setOnClickListener(View.OnClickListener {
            etSearchProduct.setText("")
        })


        etSearchProduct
            .textChanges()
            .debounce(200, TimeUnit.MILLISECONDS)
            .subscribe {
                viewModel
                    .search(it.toString())
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        val diffResult = DiffUtil.calculateDiff(PostsDiffUtilCallback(viewModel.oldFilteredPosts, viewModel.filteredPosts))
                        viewModel.oldFilteredPosts.clear()
                        viewModel.oldFilteredPosts.addAll(viewModel.filteredPosts)
                        diffResult.dispatchUpdatesTo(recSearchProduct.adapter!!)
                    }.addTo(disposable)
            }.addTo(disposable)

    }

    lateinit var adapter: SearchProductRecyclerAdaptor
    private fun runAnimation(recyclerView: RecyclerView) {


        adapter = SearchProductRecyclerAdaptor(viewModel.oldFilteredPosts, this, this)
        recSearchProduct.adapter = adapter
//        //recyclerView.layoutAnimation = controller
//        recSearchProduct.adapter!!.notifyDataSetChanged()
        // recyclerView.scheduleLayoutAnimation()

        //some new line added
        //some new line added
    }


    val SEARCH_PROD_REQ_CODE = 110
    fun setResultsFun() {
        val intent = Intent()
        intent.putExtra("name", "amir")
        setResult(RESULT_OK, intent)
        finish()
    }
}
