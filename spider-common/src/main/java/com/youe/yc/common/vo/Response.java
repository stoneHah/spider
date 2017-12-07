package com.youe.yc.common.vo;

public class Response<T> {
    public static final int CODE_SUCCESS = 200;
    public static final int CODE_ERROR = 400;

    private T data;
    private boolean success = true;
    private int code;

    private String errorMsg;

    public Response() {
    }

    private Response(ResponseBuilder<T> builder) {
        this.data = builder.data;
        this.success = builder.success;
        this.code = builder.code;
        this.errorMsg = builder.errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getCode() {
        return code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public static class ResponseBuilder<T> {
        private T data;
        private boolean success;
        private int code;

        private String errorMsg;

        public ResponseBuilder() {
            this.data = null;
        }

        public ResponseBuilder(T data) {
            this.data = data;
        }

        public ResponseBuilder code(int code) {
            this.code = code;
            return this;
        }

        public ResponseBuilder data(T data) {
            this.data = data;
            return this;
        }

        public ResponseBuilder success(boolean success) {
            this.success = success;
            return this;
        }

        public ResponseBuilder errorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
            return this;
        }

        public Response<T> build() {
            return new Response(this);
        }
    }
}
