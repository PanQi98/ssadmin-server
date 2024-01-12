package edu.ssadmin.server.mapper.user;

import edu.ssadmin.server.controller.admin.chat.vo.ChatMsg;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatMsgMapper {
    void addmsg(ChatMsg chatMsg);
}
