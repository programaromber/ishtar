package br.com.ishtar.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, br.com.ishtar.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, br.com.ishtar.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, br.com.ishtar.domain.User.class.getName());
            createCache(cm, br.com.ishtar.domain.Authority.class.getName());
            createCache(cm, br.com.ishtar.domain.User.class.getName() + ".authorities");
            createCache(cm, br.com.ishtar.domain.Cidade.class.getName());
            createCache(cm, br.com.ishtar.domain.Cidade.class.getName() + ".participantes");
            createCache(cm, br.com.ishtar.domain.Cidade.class.getName() + ".polos");
            createCache(cm, br.com.ishtar.domain.Contato.class.getName());
            createCache(cm, br.com.ishtar.domain.Responsavel.class.getName());
            createCache(cm, br.com.ishtar.domain.Responsavel.class.getName() + ".contatos");
            createCache(cm, br.com.ishtar.domain.Responsavel.class.getName() + ".polos");
            createCache(cm, br.com.ishtar.domain.Participante.class.getName());
            createCache(cm, br.com.ishtar.domain.Participante.class.getName() + ".participacoes");
            createCache(cm, br.com.ishtar.domain.Polo.class.getName());
            createCache(cm, br.com.ishtar.domain.Polo.class.getName() + ".locais");
            createCache(cm, br.com.ishtar.domain.Polo.class.getName() + ".agendas");
            createCache(cm, br.com.ishtar.domain.Polo.class.getName() + ".responsavels");
            createCache(cm, br.com.ishtar.domain.Local.class.getName());
            createCache(cm, br.com.ishtar.domain.Local.class.getName() + ".eventos");
            createCache(cm, br.com.ishtar.domain.Agenda.class.getName());
            createCache(cm, br.com.ishtar.domain.Agenda.class.getName() + ".eventos");
            createCache(cm, br.com.ishtar.domain.Evento.class.getName());
            createCache(cm, br.com.ishtar.domain.Evento.class.getName() + ".participacoes");
            createCache(cm, br.com.ishtar.domain.Participacao.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }
}
