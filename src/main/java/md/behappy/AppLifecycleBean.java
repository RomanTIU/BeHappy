package md.behappy;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.slf4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class AppLifecycleBean {
    @Inject
    Logger logger;

    void onStart(@Observes StartupEvent ev) {
        logger.info("The application is starting ...");
    }

    void onStop (@Observes ShutdownEvent ev) {
        logger.info("The application is stopping...");
    }
}
