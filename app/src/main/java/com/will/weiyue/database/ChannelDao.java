package com.will.weiyue.database;

import com.will.weiyue.bean.Channel;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * author: liweixing
 * date: 2018/2/8
 */

public class ChannelDao {


    public static List<Channel> getChannels() {
        return DataSupport.findAll(Channel.class);
    }
}
