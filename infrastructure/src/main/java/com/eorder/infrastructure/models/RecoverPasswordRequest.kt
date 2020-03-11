package com.eorder.infrastructure.models


class RecoverPasswordRequest(val oldPassword: String, val newPassword: String, val confirmPassword: String  )