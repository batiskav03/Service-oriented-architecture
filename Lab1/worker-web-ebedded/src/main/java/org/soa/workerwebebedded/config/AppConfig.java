package org.soa.workerwebebedded.config;

import com.soa.ejb.remotes.MyEjbRemote;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiTemplate;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

@Configuration
public class AppConfig {
    @Bean
    public Context context() throws NamingException {
        Properties jndiProps = new Properties();
        jndiProps.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        jndiProps.put("jboss.naming.client.ejb.context", Boolean.TRUE);
        jndiProps.put("remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED", Boolean.FALSE);
        jndiProps.put("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS", Boolean.FALSE);
        jndiProps.put("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOPLAINTEXT", Boolean.FALSE);
        jndiProps.put(Context.SECURITY_PRINCIPAL, "user");
        jndiProps.put(Context.SECURITY_CREDENTIALS, "user");
        jndiProps.put(Context.PROVIDER_URL, "http-remoting://worker-service-ejb:8080");

        return new InitialContext(jndiProps);
    }

    @Bean
    public JndiTemplate jndiTemplate() {
        Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        env.put(Context.PROVIDER_URL, "http-remoting://worker-service-ejb:8080");
        return new JndiTemplate(env);
    }

    @Bean
    public MyEjbRemote myEjb() throws NamingException {
        return jndiTemplate().lookup("ejb:/worker-ejb-1.0-SNAPSHOT-jar-with-dependencies/MyEjb!com.soa.ejb.remotes.MyEjbRemote", MyEjbRemote.class);
    }
}
