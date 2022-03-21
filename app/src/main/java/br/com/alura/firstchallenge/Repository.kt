package br.com.alura.firstchallenge

import java.math.BigDecimal

data class Repository(
    val name: String,
    val description: String,
    val userName: String,
    val nameSurname: String,
    val forksNumber: Int,
    val starRateNumber: BigDecimal
)
