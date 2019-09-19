package br.senai.sp.informatica.clube.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.senai.sp.informatica.clube.filter.JwtAutheticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, 
							securedEnabled = true, 
							jsr250Enabled = true)

public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
@Autowired
private DataSource dataSource;

	@Bean
	public JwtAutheticationFilter jwtFilter() {
		return new JwtAutheticationFilter();
		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
			.exceptionHandling().and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.authorizeRequests()
			.antMatchers("/api/auth/**", "/api/listaCliente", "/api/listaSocio",
					"/api/carregaServicos","/api/logout").permitAll()
			.anyRequest().authenticated().and()
			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/api/logout"))
			.and()
			.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
		
		
	}
	
	@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.jdbcAuthentication()
			.dataSource(dataSource)
			.usersByUsernameQuery("select nome,"
					+ "senha,ativo from socio "
					+ "where nome= ?")
	        .authoritiesByUsernameQuery("select "
	        		+ "nome, perfil from "
	        		+ "autorizacao where nome= ?")
			.passwordEncoder(new BCryptPasswordEncoder());
		}
	
//	@Override
//		protected AuthenticationManager authenticationManager() throws Exception {
//			
//			return super.authenticationManagerBean();
//		}
	
	
	@Bean(name="AuthenticationManager")
	@Override
		public AuthenticationManager authenticationManagerBean() throws Exception {
			// TODO Auto-generated method stub
			return super.authenticationManagerBean();
		}
}
