package cn.dashan.chatbot.model.enums;

/**
 * @author ds
 * @since 2023/2/11
 */
public enum OpenAIModel {
    TEXT_DEVINCI_003(1,"text-davinci-003",2048),
    TEXT_CURIE_001(2,"text-curie-001",1024),
    TEXT_BABBAGE_001(3,"text-babbage-001",1024),
    TEXT_ADA_001(4,"text-ada-001",1024)
    ;


    private final int modelId;
    private final String model;
    private final int maxTokens;

    OpenAIModel(int modelId, String model, int maxTokens) {
        this.modelId = modelId;
        this.model = model;
        this.maxTokens = maxTokens;
    }

    public int getMaxTokens() {
        return maxTokens;
    }

    public int getModelId() {
        return modelId;
    }

    public String getModel() {
        return model;
    }
}
