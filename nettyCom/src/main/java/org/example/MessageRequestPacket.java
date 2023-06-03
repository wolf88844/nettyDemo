package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageRequestPacket extends Packet{
    private String toUserId;
    private String msg;
    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
