package br.com.alura.firstchallenge.apiRepository


import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRequest {

    @GET("/search/repositories?q=language:Java&sort=stars")
    suspend fun getSearchRepositories(
        @Query(value = "page") page: Int
    ): RepositoryJson


}