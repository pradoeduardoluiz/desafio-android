package com.picpay.desafio.android.data

import com.picpay.desafio.android.data.source.cache.dbo.UserDBO
import com.picpay.desafio.android.data.source.remote.dto.UserDTO
import com.picpay.desafio.android.domain.models.UserModel

class UserMapper {

    fun mapToModel(user: UserDBO) = UserModel(
        id = user.id,
        name = user.name,
        imageUrl = user.imageUrl,
        username = user.username
    )

    fun mapToDbo(user: UserDTO, updateDate: Long) = UserDBO(
        id = user.id,
        name = user.name,
        imageUrl = user.imageUrl,
        username = user.username,
        updateDate = updateDate
    )

}
