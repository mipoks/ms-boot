package design.kfu.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import design.kfu.entity.chat.Chat;
import design.kfu.helper.converter.StringToChatConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.format.FormatterRegistry;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.persistence.Converter;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;


@EnableAspectJAutoProxy
@Configuration
@EnableWebMvc
@EnableJpaRepositories(basePackages = "design.kfu.repository")
@PropertySource("classpath:application.properties")
public class Config implements WebMvcConfigurer {


  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    // Css resource.
    registry.addResourceHandler("/static/**") //
            .addResourceLocations("classpath:static/").setCachePeriod(31556926);

  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public StringToChatConverter stringToChatConverter() {
    return new StringToChatConverter();
  }

  @Override
  public void addFormatters(FormatterRegistry registry) {
    registry.addConverter(stringToChatConverter());
  }

  @Autowired
  private Environment environment;

  @Bean
  public DataSource dataSource() {
    return new HikariDataSource(hikariConfig());
  }

  @Bean
  public HikariConfig hikariConfig() {
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setJdbcUrl(environment.getProperty("spring.datasource.url"));
    hikariConfig.setMaximumPoolSize(Integer.parseInt(environment.getProperty("db.hikari.max-pool-size")));
    hikariConfig.setUsername(environment.getProperty("db.username"));
    hikariConfig.setPassword(environment.getProperty("db.password"));
    hikariConfig.setDriverClassName(environment.getProperty("db.driver.classname"));
    return hikariConfig;
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    // создаем адаптер, который позволит Hibernate работать с Spring Data Jpa
    HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
    hibernateJpaVendorAdapter.setDatabase(Database.POSTGRESQL);
    // создали фабрику EntityManager как Spring-бин
    LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
    entityManagerFactory.setDataSource(dataSource());
    entityManagerFactory.setPackagesToScan("design.kfu.entity");
    entityManagerFactory.setJpaVendorAdapter(hibernateJpaVendorAdapter);
    entityManagerFactory.setJpaProperties(additionalProperties());
    return entityManagerFactory;
  }

  private Properties additionalProperties() {
    Properties properties = new Properties();
    properties.setProperty("hibernate.hbm2ddl.auto", "create");
    properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL95Dialect");
    properties.setProperty("hibernate.show_sql", "true");
    return properties;
  }

  @Bean
  public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory){
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory);

    return transactionManager;
  }

  @Bean
  public ResourceBundleMessageSource resourceBundleMessageSource() {
    ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
    resourceBundleMessageSource.setBasename("messages");
    return resourceBundleMessageSource;
  }

  @Bean
  public CookieLocaleResolver localeResolver() {
    CookieLocaleResolver localeResolver = new CookieLocaleResolver();
    localeResolver.setCookieName("lang");
    return localeResolver;
  }

  @Bean
  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource res = new ReloadableResourceBundleMessageSource();
    res.setBasenames("classpath:i18n/messages", "classpath:i18n/validation");
    res.setCacheSeconds(0);
    res.setDefaultEncoding("UTF-8");
    res.setUseCodeAsDefaultMessage(false);
    return res;
  }

}
