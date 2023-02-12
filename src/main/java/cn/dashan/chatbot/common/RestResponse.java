package cn.dashan.chatbot.common;

import java.io.Serializable;

/**
 * @author ds
 * @since 2023/2/11
 */
public class RestResponse<T> implements Serializable {

    private final int code;
    private final String msg;
    private final T data;


    public enum MSG{
        SUCCESS(1,"SUCCESS"),
        FAILURE(0,"FAILURE");

        private final int code;
        private final String msg;

        MSG(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return this.code;
        }

        public String getMsg() {
            return this.msg;
        }
    }

    public static class Builder<T>{
        private final int code;
        private String msg;
        private T data;

        public Builder(int code){
            this.code=code;
        }

        public Builder(MSG msg){
            this.msg=msg.msg;
            this.code= msg.code;
        }

        public Builder<T> setMsg(String msg){
            this.msg=msg;
            return this;
        }

        public Builder<T> setDate(T data){
            this.data=data;
            return this;
        }

        public RestResponse<T> builder(){
            return new RestResponse<>(this);
        }

    }

    private RestResponse(Builder<T> builder){
        this.code=builder.code;
        this.msg=builder.msg;
        this.data=builder.data;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}
