package br.com.alura.firstchallenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.alura.firstchallenge.databinding.RepositoryListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val recyclerView = binding.activityRepositoryListRecyclerView
        recyclerView.adapter = adapter
        setContentView(binding.root)
        getCurrentData()


    }

    private fun getCurrentData() {

        val okHttpClient = createOkHttpClient()
        val retrofit = createCatRetrofit(okHttpClient)
        val api = retrofit.create(ApiRequest::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            val response = api.getSearchRepositories()
            if (response.items.isNotEmpty()) {
                withContext(context = Dispatchers.Main) {
                    adapter.update(response.items)
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
        return OkHttpClient.Builder()
            .connectTimeout(timeoutInSeconds, TimeUnit.SECONDS)
            .readTimeout(timeoutInSeconds, TimeUnit.SECONDS)
            .build()
    }

}





