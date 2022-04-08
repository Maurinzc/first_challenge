package br.com.alura.firstchallenge.apiPullRequest

data class Base(
    val label: String,
    val ref: String,
    val repo: Repo,
    val sha: String,
    val user: User
)