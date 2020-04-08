package com.eorder.app.viewmodels.fragments

import com.eorder.app.viewmodels.BaseViewModel
import com.eorder.application.models.UrlLoadedImage

class CenterInfoFragmentViewModel: BaseViewModel() {

    fun loadImages(list: List<UrlLoadedImage>) =
        unitOfWorkService.getLoadImageService().loadImages(list)
}