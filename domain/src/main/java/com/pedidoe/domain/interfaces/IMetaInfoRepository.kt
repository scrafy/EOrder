package com.pedidoe.domain.interfaces

import com.pedidoe.domain.models.ApkVersion
import com.pedidoe.domain.models.ServerResponse

interface IMetaInfoRepository {

    fun getCurrentApkVersion(): ServerResponse<ApkVersion>
    fun getApkVersions(): ServerResponse<List<ApkVersion>>

}
