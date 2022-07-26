package token

import com.auth0.jwt.*
import com.auth0.jwt.algorithms.Algorithm
import com.github.unqUi.model.RottenTomatoesSystem
import com.github.unqUi.model.User
import com.github.unqUi.model.UserError
import io.javalin.http.UnauthorizedResponse
import javalinjwt.JWTGenerator
import javalinjwt.JWTProvider


class JwtController(val system: RottenTomatoesSystem) {

    private val algorithm: Algorithm = Algorithm.HMAC256("very_secret")
    private val generator: JWTGenerator<User> = JWTGenerator<User>{
            user: User, alg: Algorithm? ->
        val token: JWTCreator.Builder = JWT.create()
            .withClaim("id", user.id)
        token.sign(alg)
    }

    private val verifier: JWTVerifier = JWT.require(algorithm).build()
    private val provider = JWTProvider(algorithm, generator, verifier)

    fun generate(user: User) : String = provider.generateToken(user)

    fun validate(token: String): User {

        val token = provider.validateToken(token)
        if (!token.isPresent) throw UnauthorizedResponse("Token invalid")
        val userId = token.get().getClaim("id").asString()
        return system.getUserById(userId)
    }

}