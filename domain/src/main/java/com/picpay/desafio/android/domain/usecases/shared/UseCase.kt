package com.picpay.desafio.android.domain.usecases.shared

interface UseCase<Params, Result> {
    suspend operator fun invoke(params: Params): Result
}
