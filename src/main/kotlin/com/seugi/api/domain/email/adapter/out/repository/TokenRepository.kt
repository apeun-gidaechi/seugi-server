package com.seugi.api.domain.email.adapter.out.repository

import com.seugi.api.domain.email.adapter.out.entity.TokenEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TokenRepository: CrudRepository<TokenEntity, String> {

}