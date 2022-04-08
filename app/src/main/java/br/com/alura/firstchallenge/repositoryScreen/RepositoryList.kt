package br.com.alura.firstchallenge.repositoryScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.firstchallenge.apiRepository.ApiRequest
import br.com.alura.firstchallenge.R
import br.com.alura.firstchallenge.RepositoryAdapter
import br.com.alura.firstchallenge.databinding.RepositoryListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

const val BASE_URL = "https://api.github.com"
private var TAG = "RepositoryList"

class RepositoryList : AppCompatActivity(R.layout.repository_list) {

    private val binding by lazy {
        RepositoryListBinding.inflate(layoutInflater)
    }

    private val adapter = RepositoryAdapter(
        context = this

    )

    private val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    private val recyclerView by lazy { binding.activityRepositoryListRecyclerView }

    private var page: Int = 1

    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        setContentView(binding.root)
        getRepositoriesByPage()
        recyclerViewScrollListener()

    }

    private fun recyclerViewScrollListener() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                    val itIsTheListEnd =
                        visibleItemCount + firstVisibleItemPosition >= totalItemCount
                    val shouldRequestNextPage = itIsTheListEnd && isLoading.not()

                    if (shouldRequestNextPage) {
                        getRepositoriesByPage()

                    }

                }
            }

        })

    }

    private fun getRepositoriesByPage() {

        val okHttpClient = createOkHttpClient()
        val retrofit = createCatRetrofit(okHttpClient)
        val api = retrofit.create(ApiRequest::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            isLoading = true
            val response = api.getSearchRepositories(page)
            if (response.items.isNotEmpty()) {
                withContext(context = Dispatchers.Main) {
                    adapter.update(response.items)
                }
            }
            isLoading = false
            page = page++
        }
    }

    private fun createCatRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private fun createOkHttpClient(): OkHttpClient {
        val timeoutInSeconds = 10L
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        return OkHttpClient.Builder()
            .connectTimeout(timeoutInSeconds, TimeUnit.SECONDS)
            .readTimeout(timeoutInSeconds, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

}





