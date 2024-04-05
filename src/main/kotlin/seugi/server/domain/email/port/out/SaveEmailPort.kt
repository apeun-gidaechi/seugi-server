package seugi.server.domain.email.port.out

interface SaveEmailPort {

    fun saveEmail(token: String, email: String)

}