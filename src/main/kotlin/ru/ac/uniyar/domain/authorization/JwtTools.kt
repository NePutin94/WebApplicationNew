package ru.ac.uniyar.domain.authorization

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import java.sql.Date

class JwtTools(val secret: String, val orgName: String, val lifeTime: Long) {

    fun create(id: String): String? {
        return JWT.create()
            .withSubject(id)
            .withExpiresAt(Date(System.currentTimeMillis() + lifeTime))
            .withIssuer(orgName)
            .sign(Algorithm.HMAC512(secret))
    }

    fun vertify(): JWTVerifier {
        return JWT.require(Algorithm.HMAC512(secret)).withIssuer(orgName).acceptExpiresAt(lifeTime).build()
    }

    fun check(token: String): DecodedJWT? {
        return vertify().verify(token)
    }
}