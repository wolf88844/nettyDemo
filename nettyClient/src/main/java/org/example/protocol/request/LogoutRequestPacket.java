package org.example.protocol.request;

import org.example.protocol.Packet;
import org.example.protocol.command.Command;

public class LogoutRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.LOGOUT_REQUEST;
    }
}
