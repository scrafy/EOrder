package com.pedidoe.application.interfaces

import com.pedidoe.domain.models.ApkVersion
import com.pedidoe.domain.models.ServerResponse

interface IApkVersionUseCase {

    fun getApkVersion() : ServerResponse<ApkVersion>
    fun getApkVersions() : ServerResponse<List<ApkVersion>>
}