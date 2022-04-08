package br.com.alura.firstchallenge.apiPullRequest

data class Permissions(
    val admin: Boolean,
    val pull: Boolean,
    val push: Boolean
)