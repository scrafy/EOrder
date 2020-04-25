package com.eorder.domain.models

import java.time.LocalDate

class ProductAmountByDay(
    val day: LocalDate,
    var amount: Int

)