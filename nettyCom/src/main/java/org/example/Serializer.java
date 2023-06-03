package org.example;

public interface Serializer {

    byte getSerializerAlgorithm();

    byte[] serialize(Object object);

    <T> T deserialize(Class<T> clazz,byte[] bytes);
}
