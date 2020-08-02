package com.pedidoe.application.usecases

import com.pedidoe.application.interfaces.IApkVersionUseCase
import com.pedidoe.domain.interfaces.IMetaInfoRepository
import com.pedidoe.domain.models.ApkVersion
import com.pedidoe.domain.models.ServerResponse

class ApkVersionUseCase(
    private val metaInfoRepository: IMetaInfoRepository
) : IApkVersionUseCase {


    override fun getApkVersion(): ServerResponse<ApkVersion> {

        return metaInfoRepository.getCurrentApkVersion()
    }

    override fun getApkVersions(): ServerResponse<List<ApkVersion>> {
        return metaInfoRepository.getApkVersions()
    }

}