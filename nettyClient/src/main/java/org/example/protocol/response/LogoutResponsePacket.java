package org.example.protocol.response;

import lombok.Data;
import org.example.protocol.Packet;
import org.example.protocol.command.Command;
@Data
public class LogoutResponsePacket extends Packet {
    private boolean success;
    @Override
    public Byte getCommand() {
        return Command.LOGOUT_RESPONSE;
    }
}
