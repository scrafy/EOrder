package com.eorder.application.interfaces

import com.eorder.application.di.UnitOfWorkService

interface IUnitOfWorkService {

    fun getUnitOfWorkService() : UnitOfWorkService
}