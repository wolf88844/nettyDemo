package org.example;

import lombok.Data;

@Data
public class MessageResponsePacket extends Packet{
    private String fromUserId;
    private String fromUserName;
    private String msg;
    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
