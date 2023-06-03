package org.example;

import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class Command {
    public Byte LOGIN_REQUEST = 1;
    public Byte LOGIN_RESPONSE = 2;
    public Byte MESSAGE_REQUEST = 3;
    public Byte MESSAGE_RESPONSE = 4;

    public Map<Byte,Class<? extends Packet>> map;

    static {
        map = new HashMap<>();
        map.put(LOGIN_REQUEST, LoginRequestPacket.class);
        map.put(LOGIN_RESPONSE,LoginResponsePacket.class);
        map.put(MESSAGE_REQUEST, MessageRequestPacket.class);
        map.put(MESSAGE_RESPONSE,MessageResponsePacket.class);
    }

}
