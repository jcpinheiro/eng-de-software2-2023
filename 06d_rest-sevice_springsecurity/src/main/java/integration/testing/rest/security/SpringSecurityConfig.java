package integration.testing.rest.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SpringSecurityConfig  {

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
		.authorizeHttpRequests((authz) -> authz
				.anyRequest().authenticated()
				)
		.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()		
		.addFilterBefore(jwtUserRequestFilter(), UsernamePasswordAuthenticationFilter.class )
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		.formLogin().disable()
		.httpBasic().disable()
		.logout().disable()
		;
		return http.build();
	}

	@Bean
	public JwtUserRequestFilter jwtUserRequestFilter() {
		return new JwtUserRequestFilter();
	}

	@Bean
	public InMemoryUserDetailsManager userDetailsService() {

		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		User user = new User(
				"john",
				passwordEncoder.encode("pass"),
				Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

		User admin = new User(
				"admin",
				passwordEncoder.encode("pass"),
				Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));

		User userApiKey = new User("gA3HD4cVREf9cUsprCRK93w",
				passwordEncoder.encode("gA3HD4cVREf9cUsprCRK93w"),
				Collections.singletonList(new SimpleGrantedAuthority("ROLE_KEY")));

		return new InMemoryUserDetailsManager(user, admin, userApiKey);
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().antMatchers("/login", "/swagger-ui/**", "/v3/**");
	}


}
