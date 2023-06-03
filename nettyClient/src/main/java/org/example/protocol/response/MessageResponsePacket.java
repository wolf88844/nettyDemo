package org.example.protocol.response;

import lombok.Data;
import org.example.protocol.Packet;
import org.example.protocol.command.Command;

@Data
public class MessageResponsePacket extends Packet {
    private String fromUserId;
    private String fromUserName;
    private String msg;
    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
