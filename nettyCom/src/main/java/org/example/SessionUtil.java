package org.example;

import io.netty.channel.Channel;
import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@UtilityClass
public class SessionUtil {

    private final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();

    public void bindSession(Session session, Channel channel) {
        userIdChannelMap.put(session.getUserId(), channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    public void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            userIdChannelMap.remove(getSession(channel).getUserId());
            channel.attr(Attributes.SESSION).set(null);
        }
    }

    public Channel getChannel(String userId){
        return userIdChannelMap.get(userId);
    }

    public Session getSession(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }

    public boolean hasLogin(Channel channel) {
        return channel.hasAttr(Attributes.SESSION);
    }
}
