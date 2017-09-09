package xyz.fullstackahead.where2go.pojo

import java.io.Serializable

data class User(
        val username: String = "",
        val email: String = "",
        val token: String = ""
) : Serializable
