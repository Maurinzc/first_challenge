package br.com.alura.firstchallenge

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.firstchallenge.databinding.RequestListBinding

class RequestList : AppCompatActivity(R.layout.request_list) {

    private val binding by lazy {
        RequestListBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        val recyclerView = binding.activityRequestListRecyclerView
        recyclerView.adapter
        setContentView(binding.root)
    }
}