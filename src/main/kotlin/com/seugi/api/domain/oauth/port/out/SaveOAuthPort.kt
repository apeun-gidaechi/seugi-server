package com.seugi.api.domain.oauth.port.out

import com.seugi.api.domain.oauth.application.model.OAuth

interface SaveOAuthPort {

    fun saveOAuth(oauth: OAuth)

}