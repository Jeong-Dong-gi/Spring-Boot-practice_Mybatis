package com.testboard2.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/*
 * @Configuration : 스프링부트 환경설정 클래스임을 명시. 자동으로 빈 등록.
 * 이 애너테이션이 붙게되면 @ComponentScan이 스캔할 때 이 클래스에 @Bean으로
 * 지정한 모든 빈들도 IoC(Inversion of Control) 컨테이너에 등록.
 * 
 */
@Configuration
@PropertySource("classpath:/application.properties")
public class DBConfiguration {
	
	@Autowired
	private ApplicationContext applicationContext;

	/*
	 * HikariCP 설정1
	 * 
	 * @Bean : return되는 객체를 IoC 컨테이너에 등록.
	 * 특별히 지정하는 이름이 없다면 IoC 컨테이너에 해당 메서드명으로 등록
	 * 이름 지어도 가능. 보통은 메서드명으로 등록. 중복X
	 * application.properties 파일로 부터 데이터베이스 관련된 정보를 읽어와서 히카리 설정 객체를 리턴.
	 * 접두어는 해당 접두어로 시작하는 정보들을 읽어온다는 뜻임.
	 */
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	public HikariConfig hikariConfig() {
		
		return new HikariConfig();
	}
	
	/*
	 * HikariCP 설정2
	 * 
	 * 히카리 설정 객체(HikariConfig)를 넘겨받아서 DataSource 객체를 리턴.
	 * 이 단계에서 데이터베이스 연결이 완성.
	 * 만약 아이디나 패스워드가 틀렸다면 당연히 이 단계에서 오류 발생. application.properties 파일 체크
	 * DB 연결이 잘 되었는지 확인해보기 위해서 콘솔에 dataSource 객체를 toString() 메서드로 출력
	 * 히카리풀 뒤에 숫자가 붙어 나옴 --> HikariDataSource (HikariPool-1)
	 * 이 단계를 통해서 히카리CP(Connection Pool) 연결이 완성.
	 * 이제 DB 연결이 필요한 부분에서 이 dataSource를 가지고 연결해주면 됨.
	 */
	@Bean
	public DataSource dataSource() {
		DataSource dataSource = new HikariDataSource( hikariConfig() );
		System.out.println( dataSource.toString() );
		
		return dataSource;
	}
	
	
	/*
	 *  MyBatis 설정1
	 *  SqlSessionFactory 객체 생성.
	 *  SqlSessionFactory 생성을 위해 내부의 SqlSessionFactoryBean을 사용.
	 *  이때, 데이터소스 객체를 넘겨 받아서 처리해도 되고, 아니면 setDataSource(dataSource()) 이렇게 해줘도 됨.
	 *  
	 *  기본적인 설정 3가지
	 *  	setDataSource		:	빌드된 DataSource를 셋팅.
	 *  	setMapperLocations	:	SQL 구문이 작성된 *Mapper.xml의 경로를 정확히 등록.
	 *  	setTypeAliasesPackage	:	인자로 Alias 대상 클래스가 위치한 패키지 경로.
	 *  
	 *  주의사항!
	 *  SqlSessionFactory에 저장할 config 설정 시 Mapper에서 사용하고자하는 DTO, VO, Entity에 대해서 setTypeAliasesPackage 저장 필요.
	 *  만약 지정해주지 않는다면 aliases 찾지 못한다는 오류가 발생할 수 있음.
	 */
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource( dataSource );
		factoryBean.setMapperLocations(applicationContext.getResources("classpath:/mapper/**/MemberMapper.xml"));
		/*
		 * 메퍼에 대한 리소스는 어디에서 가져오는가?
		 * 			- ApplicationContext 객체에서 가져올 수 있다.
		 * 			- ApplicationContext는 쉽게 말해 프레임워크 컨테이너라고 생각하면 된다.
		 * 			- ApplicationContext는 애플리케이션이 시작해서 끝나는 그 순간까지 이 애플리케이션에서 필요한 모든 자원들을 모아놓고 관리.
		 */
		factoryBean.setTypeAliasesPackage("com.testboard2.dto");
		
		return factoryBean.getObject(); 
	}
	
	/*
	 * MyBatis 설정2
	 * 
	 * sqlSessionTemplate 객체 생성 <-- SqlSessionFactory
	 * 넘겨받은 sqlSessionFactory를 통해서 sqlSessionTemplate 객체를 생성 및 리턴
	 * SQL 구문의 실행과 트랜잭션 등을 관리하는 가장 열일.
	 * MyBatis의 sqlSession 객체가 Spring+MyBatis 연동 모듈에서는 sqlSessionTemplate이 대체.
	 */
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
		
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}


