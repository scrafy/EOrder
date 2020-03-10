package com.eorder.infrastructure.models


class RecoverPasswordRequest(var oldPassword: String, var newPassword: String, var confirmPassword: String  )