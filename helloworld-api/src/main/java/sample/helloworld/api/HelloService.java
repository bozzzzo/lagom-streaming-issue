/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package sample.helloworld.api;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.pathCall;

import akka.Done;
import akka.NotUsed;
import akka.stream.javadsl.Source;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.Method;

import java.util.Optional;

/**
 * The hello service interface.
 * <p>
 * This describes everything that Lagom needs to know about how to serve and
 * consume the HelloService.
 */
public interface HelloService extends Service {

  /**
   * Example: curl http://localhost:9000/api/hello/Alice
   */
  ServiceCall<NotUsed, String> hello(String id);

  /**
   * Example: curl -H "Content-Type: application/json" -X POST -d '{"message":
   * "Hi"}' http://localhost:9000/api/hello/Alice
   */
  ServiceCall<GreetingMessage, Done> useGreeting(String id);


  /**
   * Example: connect with https://github.com/bozzzzo/rxjava2-chirper-client running in a debugger and break on the Thread.sleep() call
   * @param message
   * @return
   */
  ServiceCall<NotUsed, Source<String, NotUsed>> debugEcho(Optional<String> message);

  @Override
  default Descriptor descriptor() {
    // @formatter:off
    return named("helloservice").withCalls(
        pathCall("/api/hello/:id", this::hello),
        pathCall("/api/hello/:id", this::useGreeting),
        pathCall("/api/echo?message", this::debugEcho)
      ).withAutoAcl(true);
    // @formatter:on
  }
}
