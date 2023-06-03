package org.example.protocol.command;

import lombok.experimental.UtilityClass;
import org.example.protocol.Packet;
import org.example.protocol.request.CreateGroupRequestPacket;
import org.example.protocol.request.LoginRequestPacket;
import org.example.protocol.request.LogoutRequestPacket;
import org.example.protocol.request.MessageRequestPacket;
import org.example.protocol.response.CreateGroupResponsePacket;
import org.example.protocol.response.LoginResponsePacket;
import org.example.protocol.response.LogoutResponsePacket;
import org.example.protocol.response.MessageResponsePacket;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class Command {
    public Byte LOGIN_REQUEST = 1;
    public Byte LOGIN_RESPONSE = 2;
    public Byte MESSAGE_REQUEST = 3;
    public Byte MESSAGE_RESPONSE = 4;

    public Byte LOGOUT_REQUEST = 5;
    public Byte LOGOUT_RESPONSE = 6;
    public Byte CREATEGROUP_REQUEST = 7;
    public Byte CREATEGROUP_RESPONSE = 8;

    public Map<Byte,Class<? extends Packet>> map;

    static {
        map = new HashMap<>();
        map.put(LOGIN_REQUEST, LoginRequestPacket.class);
        map.put(LOGIN_RESPONSE, LoginResponsePacket.class);
        map.put(MESSAGE_REQUEST, MessageRequestPacket.class);
        map.put(MESSAGE_RESPONSE, MessageResponsePacket.class);
        map.put(LOGOUT_REQUEST, LogoutRequestPacket.class);
        map.put(LOGOUT_RESPONSE, LogoutResponsePacket.class);
        map.put(CREATEGROUP_REQUEST, CreateGroupRequestPacket.class);
        map.put(CREATEGROUP_RESPONSE, CreateGroupResponsePacket.class);
    }

}
