package cn.dashan.chatbot.service;

import cn.dashan.chatbot.model.enums.OpenAIModel;

/**
 * @author ds
 * @since 2023/2/11
 */
public interface IOpenAI {

    /**
     * 向chatGPT发送请求
     * @param question 问题
     * @param model 型号
     * @return 回答
     * @throws RuntimeException 请求chatGPT异常或解析响应结果异常
     */
    String doChatGPT(String question, OpenAIModel model) throws RuntimeException;
}
