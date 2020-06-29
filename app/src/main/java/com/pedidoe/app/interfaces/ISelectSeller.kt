package com.pedidoe.app.interfaces

import com.pedidoe.domain.models.Seller

interface ISelectSeller {

    fun selectSeller(seller: Seller)
}