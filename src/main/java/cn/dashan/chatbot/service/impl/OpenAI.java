package cn.dashan.chatbot.service.impl;

import cn.dashan.chatbot.model.enums.OpenAIModel;
import cn.dashan.chatbot.model.vo.AIAnswer;
import cn.dashan.chatbot.model.vo.Choices;
import cn.dashan.chatbot.service.IOpenAI;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ds
 * @since 2023/2/11
 */
@Service
public class OpenAI implements IOpenAI {
    Logger logger = LoggerFactory.getLogger(OpenAI.class);

    @Value("${chatbot-api.bot.openAiKey}")
    private String openAiKey;

    private final RestTemplate restTemplate;
    public OpenAI(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * 使用RestTemplate 实现chatGPT发送请求
     * @param question 问题
     * @param model 型号
     * @return 回答
     * @throws RuntimeException 请求chatGPT异常或解析响应结果异常
     */
    @Override
    public String doChatGPT(String question, OpenAIModel model) throws RuntimeException {
        logger.info("机器人{}，接收消息：{}",model,question);
        //构建请求头
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization","Bearer "+openAiKey);
        headers.set("Content-Type", "application/json");

        //请求参数
        Map<String,Object> params = new HashMap<>();
        params.put("model",model.getModel());
        params.put("prompt",question);
        params.put("temperature",0);
        params.put("max_tokens",model.getMaxTokens());

        //构建请求体
        HttpEntity<String> request = new HttpEntity<>(JSON.toJSONString(params),headers);

        //执行请求
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("https://api.openai.com/v1/completions", request, String.class);

        //判断执行是否成功
        if (!HttpStatus.OK.equals(responseEntity.getStatusCode())){
            logger.error("OpenAI 服务端接口请求异常:{}-{}",responseEntity.getStatusCode().value(),responseEntity.getStatusCode().getReasonPhrase());
            throw new RuntimeException("ChatGPT 任务执行异常");
        }


        //解析请求
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AIAnswer answer = JSON.parseObject(responseEntity.getBody(),AIAnswer.class);
            assert answer != null;
            for (Choices choice : answer.getChoices()) {
                stringBuilder.append(choice.getText());
            }
        }catch (RuntimeException e){
            logger.error("answer 解析失败",e);
            throw new RuntimeException("answer 解析失败");
        }

        String answerTest = stringBuilder.toString().replaceFirst("\\n", "").replaceFirst("\\n", "");

        logger.info("机器人{}，回答问题：{}",model,answerTest);
        return answerTest;
    }
}
