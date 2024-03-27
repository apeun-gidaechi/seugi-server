package seugi.server.domain.chat.application.service.joined

import org.springframework.stereotype.Service
import seugi.server.domain.chat.domain.joined.JoinedRepository

@Service
class JoinedServiceImpl(
    private val joinedRepository: JoinedRepository
) : JoinedService {
}