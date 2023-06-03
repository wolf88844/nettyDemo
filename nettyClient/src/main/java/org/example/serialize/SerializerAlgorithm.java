package org.example.serialize;

import org.example.serialize.impl.JSONSerializer;

public interface SerializerAlgorithm {
    Serializer DEFAULT = JSONSerializer.INSTANCE;
    byte JSON = 1;
}
