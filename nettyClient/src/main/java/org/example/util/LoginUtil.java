package org.example.util;

import io.netty.channel.Channel;
import io.netty.util.Attribute;
import lombok.experimental.UtilityClass;
import org.example.attribute.Attributes;


@UtilityClass
public class LoginUtil {
    public void markAsLogin(Channel channel){
        System.out.println(channel.id()+"设置为已登录");
        channel.attr(Attributes.LOGIN).set(true);
    }

    public boolean hasLogin(Channel channel){
        System.out.println(channel.id()+"判断是否登录");
        Attribute<Boolean> attr = channel.attr(Attributes.LOGIN);
        return attr.get()!=null;
    }
}
