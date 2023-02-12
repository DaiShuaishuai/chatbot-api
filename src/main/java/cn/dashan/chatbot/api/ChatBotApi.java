package cn.dashan.chatbot.api;

import cn.dashan.chatbot.common.RestResponse;
import cn.dashan.chatbot.model.enums.OpenAIModel;
import cn.dashan.chatbot.service.IOpenAI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ds
 * @since 2023/2/11
 */
@RestController
public class ChatBotApi {

    Logger logger = LoggerFactory.getLogger(ChatBotApi.class);

    private final IOpenAI openAI;

    public ChatBotApi(IOpenAI openAI) {
        this.openAI = openAI;
    }

    @GetMapping("/bot")
    public RestResponse<String> bot(
            @RequestParam("question") String question,
            @RequestParam("model") OpenAIModel model
            ){
        logger.info("执行bot接口，客户端传参-model：{}，question：{}",model,question);
        try {
            return new RestResponse.Builder<String>(RestResponse.MSG.SUCCESS).setDate(openAI.doChatGPT(question,model)).builder();
        }catch (RuntimeException e){
            logger.error("ChatGPT执行异常：",e);
            return new RestResponse.Builder<String>(RestResponse.MSG.FAILURE).setMsg(e.getMessage()).builder();
        }
    }
}
