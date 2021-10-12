package com.picpay.desafio.android.data

import com.picpay.desafio.android.data.source.cache.dbo.UserDBO
import com.picpay.desafio.android.data.source.remote.dto.UserDTO
import com.picpay.desafio.android.domain.models.UserModel
import org.junit.Assert
import org.junit.Test

class UserMapperTest {

    @Test
    fun `should mapper dbo to model`() {
        val mapper = UserMapper()

        val expected = UserModel(
            id = "id",
            name = "name",
            imageUrl = "imageUrl",
            username = "username"
        )

        val current = UserDBO(
            id = "id",
            name = "name",
            imageUrl = "imageUrl",
            username = "username",
            updateDate = 10_000
        )

        val actual = mapper.mapToModel(current)
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `should mapper dto to dbo`() {
        val mapper = UserMapper()

        val expected = UserDBO(
            id = "id",
            name = "name",
            imageUrl = "imageUrl",
            username = "username",
            updateDate = 10_000
        )

        val current = UserDTO(
            id = "id",
            name = "name",
            imageUrl = "imageUrl",
            username = "username"
        )

        val actual = mapper.mapToDbo(current, 10_000)
        Assert.assertEquals(expected, actual)
    }

}
