package com.eorder.app.interfaces

import com.eorder.domain.models.Seller

interface ISelectSeller {

    fun selectSeller(seller: Seller)
}