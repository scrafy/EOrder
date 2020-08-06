package com.pedidoe.domain.models

import org.threeten.bp.LocalDate


class ProductAmountByDay(
    val day: LocalDate,
    var amount: Int

)