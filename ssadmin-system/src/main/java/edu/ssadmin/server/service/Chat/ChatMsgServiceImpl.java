package edu.ssadmin.server.service.Chat;

import edu.ssadmin.server.controller.admin.chat.vo.ChatMsg;

import edu.ssadmin.server.mapper.user.ChatMsgMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class ChatMsgServiceImpl implements  ChatMsgService{
    @Resource
    private ChatMsgMapper chatMsgMapper;
    @Override
    public void addmsg(ChatMsg chatMsg) {

      chatMsgMapper.addmsg(chatMsg);
    }
}
