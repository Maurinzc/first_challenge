package br.com.alura.firstchallenge.apiPullRequest

import retrofit2.http.GET

interface ApiRequests {
    @GET("/repos/{owner}/{repo}/pulls/{pull_number}")
    suspend fun getPullRequest(): PullRequestJson
}