package br.com.alura.firstchallenge.api

data class RepositoryJson(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
)