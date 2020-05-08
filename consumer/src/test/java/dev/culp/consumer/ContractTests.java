package dev.culp.consumer;

import java.util.concurrent.CountDownLatch;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.StubTrigger;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties.StubsMode;

@SpringBootTest(properties = {"stubrunner.amqp.enabled=true"})
@AutoConfigureStubRunner(ids = "dev.culp:producer", stubsMode = StubsMode.LOCAL)
public class ContractTests {

  @Autowired
  StubTrigger trigger;

  @Autowired
  CountDownLatch latch;

  @Test
  public void test() throws InterruptedException {
    trigger.trigger("foo");
    latch.await();
  }
}
