package com.tawrun.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration{

	@Configuration
	@Order(1)
	public static class SecurityConfiguration1 extends WebSecurityConfigurerAdapter {
		@Autowired
		private BCryptPasswordEncoder bCryptPasswordEncoder;

		@Autowired
		private DataSource dataSource;

		@Value("${spring.queries.users-query}")
		private String usersQuery;

		@Value("${spring.queries.roles-query}")
		private String rolesQuery;
		@Override
		protected void configure(AuthenticationManagerBuilder auth)
			{
		try {
			auth.
					jdbcAuthentication()
					.usersByUsernameQuery(usersQuery)
					.authoritiesByUsernameQuery(rolesQuery)
					.dataSource(dataSource)
					.passwordEncoder(bCryptPasswordEncoder);
		}
		catch (Exception e) {
			e.printStackTrace();

		}
	}

		@Override
		protected void configure(HttpSecurity http) throws Exception {

			http.antMatcher( "/user/**" )
				.authorizeRequests()
				.antMatchers("/user/login").permitAll()
				.antMatchers("/user/registration").permitAll()
				.antMatchers("/user/**").hasAuthority("ADMIN").anyRequest()
				.authenticated().and().csrf().disable().formLogin()
				.loginPage("/user/login").loginProcessingUrl( "/user/login" )
					.failureUrl("/user/login?error=true")
				.defaultSuccessUrl("/user/home")
				.usernameParameter("email")
				.passwordParameter("password")
				.and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
				.logoutSuccessUrl("/user/login").and().exceptionHandling()
				.accessDeniedPage("/access-denied");
	}

		@Override
		public void configure(WebSecurity web) throws Exception {
				web
				.ignoring()
				.antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
	}

}
	@Configuration
	@Order(2)
	public static class SecurityConfiguration2 extends WebSecurityConfigurerAdapter {
		@Autowired
		private BCryptPasswordEncoder bCryptPasswordEncoder;

		@Autowired
		private DataSource dataSource;

		@Value("${spring.queries.packer-query}")
		private String packersQuery;

		@Value("${spring.queries.packer-roles-query}")
		private String packerRolesQuery;
		@Override
		protected void configure(AuthenticationManagerBuilder auth)
		{
			try {
				auth.
						jdbcAuthentication()
						.usersByUsernameQuery(packersQuery)
						.authoritiesByUsernameQuery(packerRolesQuery)
						.dataSource(dataSource)
						.passwordEncoder(bCryptPasswordEncoder);
			}
			catch (Exception e) {
				e.printStackTrace();

			}
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {

			http.antMatcher( "/packer/**" )
					.authorizeRequests()
					.antMatchers("/packer/login").permitAll()
					.antMatchers("/packer/registration").permitAll()
					.antMatchers("/packer/**").hasAuthority("PACKER").anyRequest()
					.authenticated().and().csrf().disable().formLogin()
					.loginPage("/packer/login")
					.loginProcessingUrl("/packer/login")
					.failureUrl("/packer/login?error=true")
					.defaultSuccessUrl("/packer/home")
					.usernameParameter("email")
					.passwordParameter("password")
					.and().logout()
					.logoutRequestMatcher(new AntPathRequestMatcher("/packer/logout"))
					.logoutSuccessUrl("/packer/login").and().exceptionHandling()
					.accessDeniedPage("/access-denied");
		}

		@Override
		public void configure(WebSecurity web) throws Exception {
			web
					.ignoring()
					.antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
		}

	}

}



