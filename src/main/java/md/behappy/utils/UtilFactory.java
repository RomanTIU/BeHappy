package md.behappy.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

@ApplicationScoped
public class UtilFactory {

    @Produces
    public Logger initiateLogger (InjectionPoint point) {
        if (point.getMember() != null) {
            return LoggerFactory.getLogger(point.getMember().getDeclaringClass().getName());
        }
        return LoggerFactory.getLogger(UtilFactory.class);
    }

    @Produces
    @ObjectMapperQualifier
    public ObjectMapper initiateObjectmapper() {
        return new ObjectMapper();
    }
}
