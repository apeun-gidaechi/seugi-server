package seugi.server.global.auth.oauth

class GoogleMemberInfo (
    private val attributes: Map<String, Any>
): OAuth2MemberInfo {

    override val email: String
        get() = attributes.getValue("email") as String
    override val name: String
        get() = attributes.getValue("name") as String

    override val provider: String
        get() = "google"

    override val providerId: String
        get() = attributes.getValue("sub") as String
}