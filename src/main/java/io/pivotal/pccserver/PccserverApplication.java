package io.pivotal.pccserver;

import org.apache.geode.cache.Cache;
import org.apache.geode.cache.ExpirationAction;
import org.apache.geode.cache.ExpirationAttributes;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.gemfire.PartitionedRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.CacheServerApplication;
import org.springframework.data.gemfire.config.annotation.EnableLocator;
import org.springframework.data.gemfire.config.annotation.EnableManager;

@SpringBootApplication
@CacheServerApplication(name = "SpringBootGemFireServer")
@EnableLocator(port = 20388)
@EnableManager
public class PccserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(PccserverApplication.class, args);
	}

	@Bean(name = "customer")
	PartitionedRegionFactoryBean customerRegion(Cache gemfireCache) {
		int expirationTime = 60;
		PartitionedRegionFactoryBean f1 = new PartitionedRegionFactoryBean<>();
		//ExpirationAttributes entryTimeToLive = new ExpirationAttributes(expirationTime, ExpirationAction.INVALIDATE);
		ExpirationAttributes idleTimeOut = new ExpirationAttributes(expirationTime, ExpirationAction.INVALIDATE);
		//f1.setEntryTimeToLive(idleTimeOut);
		f1.setEntryIdleTimeout(idleTimeOut);
		f1.setCache(gemfireCache);
		f1.setClose(false);
		f1.setPersistent(false);
		return f1;
	}
}
