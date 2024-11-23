package gr.alexc.otaobservatory.dto.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.geojson.GeoJsonWriter;
import org.locationtech.jts.io.geojson.OrientationTransformer;

import java.io.IOException;

public class GeometrySerializer extends StdSerializer<Geometry> {



    public GeometrySerializer(Class<Geometry> t) {
        super(t);
    }

    @Override
    public void serialize(Geometry geometry, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        GeoJsonWriter writer = new GeoJsonWriter(15);
        writer.setEncodeCRS(true);
        writer.setForceCCW(true);
        jsonGenerator.writeRawValue(writer.write(OrientationTransformer.transformCCW(geometry.norm())));
    }
}
