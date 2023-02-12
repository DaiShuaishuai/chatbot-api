package cn.dashan.chatbot.model.enums;

/**
 * @author ds
 * @since 2023/2/11
 */
public enum OpenAIModel {
    TEXT_DEVINCI_003(1,"text-davinci-003",4000),
    TEXT_CURIE_001(2,"text-curie-001",2000),
    TEXT_BABBAGE_001(3,"text-babbage-001",2000),
    TEXT_ADA_001(4,"text-ada-001",2000)
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
