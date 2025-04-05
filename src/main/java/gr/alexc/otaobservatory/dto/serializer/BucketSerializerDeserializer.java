package gr.alexc.otaobservatory.dto.serializer;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketState;
import io.github.bucket4j.distributed.serialization.SerializationHandle;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class BucketSerializationUtils {

    public static byte[] serialize(Bucket bucket) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        SerializationHandle<BucketState> handle = SERIALIZATION_HANDLE;
        handle.serialize(bucket, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static Bucket deserialize(byte[] data) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        SerializationHandle<Bucket> handle = Bucket.SERIALIZATION_HANDLE;
        return handle.deserialize(byteArrayInputStream);
    }
}