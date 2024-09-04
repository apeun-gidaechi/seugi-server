package com.seugi.api.global.config

import com.seugi.api.global.auth.jwt.JwtAuthenticationFilter
import com.seugi.api.global.auth.jwt.exception.ErrorResponseSender
import com.seugi.api.global.auth.jwt.exception.JwtExceptionFilter
import com.seugi.api.global.exception.CommonErrorCode
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
@EnableWebSecurity
class SecurityConfig (
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val jwtExceptionFilter: JwtExceptionFilter,
    private val responsor: ErrorResponseSender,
    @Value("\${management.endpoints.web.base-path}") private val actuatorUrl : String
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .cors {
                corsConfigurationSource()
            }

            .csrf {
                it.disable()
            }

            .formLogin {
                it.disable()
            }

            .sessionManagement { session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }

            .authorizeHttpRequests {
                it
                    .requestMatchers("/member/edit", "/member/myInfo", "/oauth/google/connect").authenticated()
                    .requestMatchers("/oauth/**", "/email/**", "/stomp/**", "$actuatorUrl/**").permitAll()
                    .requestMatchers("/member/**").permitAll()
                    .anyRequest().authenticated()
            }

            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter::class.java)

            .exceptionHandling {
                it.authenticationEntryPoint { _, response, _ ->
                    responsor.send(response, CommonErrorCode.NOT_FOUND)
                }
                it.accessDeniedHandler { _, response, _ ->
                    responsor.send(response, CommonErrorCode.FORBIDDEN_REQUEST)
                }
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