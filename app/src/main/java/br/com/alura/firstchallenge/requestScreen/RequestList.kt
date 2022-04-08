package br.com.alura.firstchallenge.requestScreen

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.firstchallenge.R
import br.com.alura.firstchallenge.apiPullRequest.ApiRequests

import br.com.alura.firstchallenge.databinding.RequestListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val BASE_URL = "https://api.github.com"
private val TAG = "RequestList"

class RequestList : AppCompatActivity(R.layout.request_list) {

    private val binding by lazy {
        RequestListBinding.inflate(layoutInflater)
    }

    private val adapter = AdapterRequest(
        context = this
    )

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        val recyclerView = binding.activityRequestListRecyclerView
        recyclerView.adapter = adapter
        setContentView(binding.root)
        getCurrentData()
    }

    private fun getCurrentData() {
        val okHttpClient = createOkHttpClient()
        val retrofit = createCatRetrofit(okHttpClient)
        val api = retrofit.create(ApiRequests::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            val response = api.getPullRequest()
            if (response.requested_reviewers.isNotEmpty()) {
                withContext(context = Dispatchers.Main) {
                    adapter.update(response.requested_reviewers)
                }
            }
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