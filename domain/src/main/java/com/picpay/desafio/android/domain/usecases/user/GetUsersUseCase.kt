package com.picpay.desafio.android.domain.usecases.user

import com.picpay.desafio.android.domain.models.UserModel
import com.picpay.desafio.android.domain.repository.UserRepository
import com.picpay.desafio.android.domain.usecases.shared.UseCase
import kotlinx.coroutines.flow.Flow

class GetUsersUseCase(
    private val userRepository: UserRepository
) : UseCase<GetUsersUseCase.Params, Flow<List<UserModel>>> {

    override suspend fun invoke(params: Params): Flow<List<UserModel>> =
        userRepository.getUsers(forceRefresh = params.forceRefresh)

    data class Params(val forceRefresh: Boolean)
}
