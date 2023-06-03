package org.example.protocol.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.protocol.Packet;
import org.example.protocol.command.Command;

@Data
@AllArgsConstructor
public class MessageRequestPacket extends Packet {
    private String toUserId;
    private String msg;
    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
