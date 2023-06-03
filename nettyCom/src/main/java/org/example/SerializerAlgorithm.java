package org.example;

public interface SerializerAlgorithm {
    Serializer DEFAULT = JSONSerializer.INSTANCE;
    byte JSON = 1;
}
