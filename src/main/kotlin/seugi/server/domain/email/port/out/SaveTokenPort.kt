package seugi.server.domain.email.port.out

interface SaveTokenPort {

    fun saveToken(code: String, token: String)

}