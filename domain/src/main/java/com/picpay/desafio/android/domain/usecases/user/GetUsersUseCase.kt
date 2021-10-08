package com.picpay.desafio.android.domain.usecases.user

import com.picpay.desafio.android.domain.models.UserModel
import com.picpay.desafio.android.domain.repository.UserRepository
import com.picpay.desafio.android.domain.usecases.shared.NoParams
import com.picpay.desafio.android.domain.usecases.shared.UseCase
import kotlinx.coroutines.flow.Flow

class GetUsersUseCase(
    private val userRepository: UserRepository
) : UseCase<NoParams, Flow<List<UserModel>>> {

    override suspend fun invoke(params: NoParams): Flow<List<UserModel>> {
        return userRepository.getUsers()
    }

}
