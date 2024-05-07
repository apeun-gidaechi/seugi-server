package com.seugi.api.global.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.seugi.api.global.auth.jwt.JwtAuthenticationFilter
import com.seugi.api.global.auth.jwt.JwtUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
@EnableWebSecurity
class SecurityConfig (
    private val jwtUtils: JwtUtils,
    private val objectMapper: ObjectMapper,
    @Value("\${management.endpoints.web.base-path}") private val actuatorUrl : String
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
                    .requestMatchers("$actuatorUrl/**").permitAll()
                    .anyRequest().authenticated()
            }

            .addFilterBefore(JwtAuthenticationFilter(jwtUtils, objectMapper), UsernamePasswordAuthenticationFilter::class.java)

            .exceptionHandling {
                it.authenticationEntryPoint(HttpStatusEntryPoint(HttpStatus.NOT_FOUND))
            }

            .build()

    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val corsConfiguration = CorsConfiguration()
        corsConfiguration.addAllowedOriginPattern("*")
        corsConfiguration.addAllowedHeader("*")
        corsConfiguration.addAllowedMethod("*")
        corsConfiguration.allowCredentials = true

        val urlBasedCorsConfigurationSource = UrlBasedCorsConfigurationSource()
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration)

        return urlBasedCorsConfigurationSource
    }

}