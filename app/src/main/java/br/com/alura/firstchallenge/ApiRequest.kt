package br.com.alura.firstchallenge


import br.com.alura.firstchallenge.api.RepositoryJson
import retrofit2.http.GET

interface ApiRequest {

    @GET("/search/repositories?q=language:Java&sort=stars&page=1")
    suspend fun getSearchRepositories(): RepositoryJson
}