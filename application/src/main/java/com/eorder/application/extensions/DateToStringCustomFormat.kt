package com.eorder.application.extensions

import java.text.SimpleDateFormat



fun java.util.Date?.toCustomDateFormatString(s: String): CharSequence? {
    return SimpleDateFormat(s).format(this)
}
