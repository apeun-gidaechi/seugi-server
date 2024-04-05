package seugi.server.global.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import seugi.server.global.auth.jwt.JwtAuthenticationFilter
import seugi.server.global.auth.jwt.JwtUtils
import seugi.server.global.auth.oauth.OAuth2MemberService
import seugi.server.global.auth.oauth.handler.OAuth2FailureHandler
import seugi.server.global.auth.oauth.handler.OAuth2SuccessfulHandler

@Configuration
@EnableWebSecurity
class SecurityConfig (
    private val jwtUtils: JwtUtils,
    private val oAuth2MemberService: OAuth2MemberService,
    private val oAuth2SuccessfulHandler: OAuth2SuccessfulHandler,
    private val oAuth2FailureHandler: OAuth2FailureHandler,
    private val objectMapper: ObjectMapper
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .cors {
                it.disable()
            }
            .csrf {
                it.disable()
            }
            .formLogin {
                it.disable()
            }
            .sessionManagement { session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            }
            .authorizeHttpRequests {
                it
                    .requestMatchers("/member/**", "/oauth2/**", "/email/**").permitAll()
                    .requestMatchers("/stomp/**").permitAll()
                    .anyRequest().authenticated()
            }

            .oauth2Login { req ->
                req.authorizationEndpoint {
                    it.baseUri("/oauth2/authorize")
                }

                req.redirectionEndpoint {
                    it.baseUri("/oauth2/callback/*")
                }

                req.userInfoEndpoint {
                    it.userService(oAuth2MemberService)
                }

                req.successHandler(oAuth2SuccessfulHandler)

                req.failureHandler(oAuth2FailureHandler)
            }

            .addFilterBefore(JwtAuthenticationFilter(jwtUtils, objectMapper), UsernamePasswordAuthenticationFilter::class.java)

            .exceptionHandling {
                it.authenticationEntryPoint(HttpStatusEntryPoint(HttpStatus.NOT_FOUND))
            }

            .build()

    }

}