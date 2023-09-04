package com.storm.feedpage.data.paging

interface Paginator<Key, Item> {
    suspend fun loadNextItem()
    fun reset()

}