package com.eorder.infrastructure.di

import org.koin.dsl.module

val infrastructureModule = module {

    /*SERVICES*/
    single { UnitOfWorkService( get() ) }


    /*REPOSITORIES*/
    single { UnitOfWorkRepository( get(), get() , get())}
}


