package org.example.protocol;

import io.netty.buffer.ByteBuf;
import org.example.protocol.command.Command;
import org.example.serialize.impl.JSONSerializer;
import org.example.serialize.Serializer;
import org.example.serialize.SerializerAlgorithm;

public class PacketCodeC {
    private static final int MAGIC_NUMBER = 0x12345678;

    public static final PacketCodeC INSTANCE = new PacketCodeC();
    private PacketCodeC(){}

    public ByteBuf encode(ByteBuf byteBuf,Packet packet){
        byte[] bytes = SerializerAlgorithm.DEFAULT.serialize(packet);

        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(SerializerAlgorithm.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    public Packet decode(ByteBuf byteBuf){
        byteBuf.skipBytes(4);
        byteBuf.skipBytes(1);
        byte serializeAlgorithm = byteBuf.readByte();
        byte command = byteBuf.readByte();
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);
        if(requestType!=null && serializer!=null){
            return serializer.deserialize(requestType,bytes);
        }
        return null;
    }

    private Class<? extends Packet> getRequestType(byte command) {
        return Command.map.get(command);
    }

    private Serializer getSerializer(byte serializeAlgorithm) {
        if(SerializerAlgorithm.JSON==serializeAlgorithm){
            return JSONSerializer.INSTANCE;
        }
        return null;
    }
}
