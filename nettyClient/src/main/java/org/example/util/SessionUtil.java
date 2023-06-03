package org.example.util;

import io.netty.channel.Channel;
import lombok.experimental.UtilityClass;
import org.example.attribute.Attributes;
import org.example.session.Session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@UtilityClass
public class SessionUtil {

    private final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();
    private final Map<String, Session> userNameIdMap = new ConcurrentHashMap<>();

    public void bindSession(Session session, Channel channel) {
        userIdChannelMap.put(session.getUserId(), channel);
        userNameIdMap.put(session.getUsername(),session);
        channel.attr(Attributes.SESSION).set(session);
    }

    public void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            userIdChannelMap.remove(getSession(channel).getUserId());
            userNameIdMap.remove(getSession(channel).getUsername());
            channel.attr(Attributes.SESSION).set(null);
        }
    }

    public Channel getChannel(String userId){
        return userIdChannelMap.get(userId);
    }

    public Session getSession(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }

    public String getUserId(String userName){
        Session session = userNameIdMap.get(userName);
        if(null!=session){
            return session.getUserId();
        }
        return null;
    }

    public boolean hasLogin(Channel channel) {
        return channel.hasAttr(Attributes.SESSION);
    }
}
