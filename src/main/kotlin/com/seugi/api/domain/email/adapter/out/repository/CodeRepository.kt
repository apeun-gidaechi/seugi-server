package com.seugi.api.domain.email.adapter.out.repository

import com.seugi.api.domain.email.adapter.out.entity.CodeEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CodeRepository: CrudRepository<CodeEntity, String> {

}