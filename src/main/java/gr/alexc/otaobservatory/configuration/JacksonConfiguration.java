package gr.alexc.otaobservatory.configuration;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import gr.alexc.otaobservatory.dto.serializer.GeometrySerializer;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfiguration {

    @Bean
    public Module geomSerializeModule(){
        SimpleModule module = new SimpleModule("geomSerializeModule");
        module.addSerializer(Geometry.class, new GeometrySerializer(Geometry.class));
        module.addSerializer(MultiPolygon.class, new GeometrySerializer(Geometry.class));
        module.addSerializer(Point.class, new GeometrySerializer(Geometry.class));
        return module;
    }

}
