package edu.ssadmin.server.controller.admin.chat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ssadmin.common.pojo.CommonResult;
import edu.ssadmin.server.controller.admin.chat.vo.ChatMsg;
import edu.ssadmin.server.service.Chat.ChatMsgService;
import edu.ssadmin.server.utils.OpenAI;
import edu.ssadmin.server.utils.SensitiveWordFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

import static edu.ssadmin.common.pojo.CommonResult.success;

@RequestMapping("/system/chat")
@RestController
public class ChatController {
    @Resource
    private ChatMsgService chatMsgService;
    @Resource
    OpenAI openAI;
    @Resource
    SensitiveWordFilter sensitiveWordFilter;
    @PostMapping("/chat")
    public CommonResult<String> profile(@RequestBody String msg) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode = objectMapper.readTree(msg);

        ChatMsg chatMsg=new ChatMsg();
        chatMsg.setMsg(jsonNode.get("newMessage").asText());
        chatMsg.setCreateTime(new Date());
        chatMsg.setSendUserId(2);
        chatMsg.setReceiveUserId(0);
        chatMsg.setSignFlag(1);
        chatMsgService.addmsg(chatMsg);
       //String newmsg= openAI.getChatResult(msg);
        String newmsg=  sensitiveWordFilter.filter(jsonNode.get("newMessage").asText());
        ChatMsg chatMsg2=new ChatMsg();
        chatMsg2.setMsg(newmsg);
        chatMsg2.setCreateTime(new Date());
        chatMsg2.setSendUserId(2);
        chatMsg2.setReceiveUserId(0);
        chatMsg2.setSignFlag(1);
        chatMsgService.addmsg(chatMsg2);
        return success(newmsg);
    }

    /**
     *
     * @param modelResult
     * @return
     */
    public String performManualEditing(String modelResult) {
        if ("y".equalsIgnoreCase("")) {


            return modelResult;
        } else {
            // 编辑人员选择不编辑，返回原始结果
            return modelResult;
        }
    }
}
