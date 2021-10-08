package com.picpay.desafio.android.shared.kotlin

import java.time.LocalDateTime
import java.time.ZoneOffset

class GetCurrentTime {
    operator fun invoke() = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli()
}
