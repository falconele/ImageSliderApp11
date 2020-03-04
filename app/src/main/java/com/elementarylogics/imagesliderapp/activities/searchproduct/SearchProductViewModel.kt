package com.elementarylogics.imagesliderapp.activities.searchproduct

import androidx.lifecycle.ViewModel
import com.elementarylogics.imagesliderapp.dataclases.Product
import com.elementarylogics.imagesliderapp.network.Apis
import com.elementarylogics.imagesliderapp.utils.Utility
import io.reactivex.Completable

class SearchProductViewModel : ViewModel() {

    private val originalPosts = Utility.productList
    val filteredPosts: MutableList<Product> = mutableListOf()
    val oldFilteredPosts: MutableList<Product> = mutableListOf()

    init {
        oldFilteredPosts.addAll(originalPosts)
    }

    fun search(query: String): Completable = Completable.create {
        val wanted = originalPosts.filter {
            it.name.contains(query)
        }.toList()

        filteredPosts.clear()
        filteredPosts.addAll(wanted)
        it.onComplete()
    }



}