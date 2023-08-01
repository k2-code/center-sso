package com.center.sso.phili.utils;



import com.fasterxml.jackson.annotation.JsonView;

public class ResultResponse {
    @JsonView({SimpleView.class})
    private Integer code = 0;
    @JsonView({SimpleView.class})
    private String message = "成功";
    @JsonView({SimpleView.class})
    private Object data;

    public ResultResponse(Object data) {
        this.data = data;
    }

    public ResultResponse(String msg) {
        this.message = msg;
    }

    public ResultResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

//    public static ResultResponse getPageResponse(Page page) {
//        PageResponse pageResponse = new PageResponse(page);
//        return new ResultResponse(pageResponse);
//    }

    public static ResultResponseBuilder builder() {
        return new ResultResponseBuilder();
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public Object getData() {
        return this.data;
    }

    @JsonView({SimpleView.class})
    public void setCode(Integer code) {
        this.code = code;
    }

    @JsonView({SimpleView.class})
    public void setMessage(String message) {
        this.message = message;
    }

    @JsonView({SimpleView.class})
    public void setData(Object data) {
        this.data = data;
    }


    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $code = this.getCode();
        result = result * 59 + ($code == null ? 43 : $code.hashCode());
        Object $message = this.getMessage();
        result = result * 59 + ($message == null ? 43 : $message.hashCode());
        Object $data = this.getData();
        result = result * 59 + ($data == null ? 43 : $data.hashCode());
        return result;
    }

    public String toString() {
        return "ResultResponse(code=" + this.getCode() + ", message=" + this.getMessage() + ", data=" + this.getData() + ")";
    }

    public ResultResponse(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResultResponse() {
    }

    public static class ResultResponseBuilder {
        private Integer code;
        private String message;
        private Object data;

        ResultResponseBuilder() {
        }

        @JsonView({SimpleView.class})
        public ResultResponseBuilder code(Integer code) {
            this.code = code;
            return this;
        }

        @JsonView({SimpleView.class})
        public ResultResponseBuilder message(String message) {
            this.message = message;
            return this;
        }

        @JsonView({SimpleView.class})
        public ResultResponseBuilder data(Object data) {
            this.data = data;
            return this;
        }

        public ResultResponse build() {
            return new ResultResponse(this.code, this.message, this.data);
        }

        public String toString() {
            return "ResultResponse.ResultResponseBuilder(code=" + this.code + ", message=" + this.message + ", data=" + this.data + ")";
        }
    }

    public interface MeetRoomView extends SimpleView {
    }

    public interface ThirdDeviceDetailView extends SimpleView {
    }

    public interface ServerDetailView extends SimpleView {
    }

    public interface DeviceDetailView extends SimpleView {
    }

    public interface ModelDataDetailView extends DASimpleView {
    }

    public interface DataSimpleView extends DASimpleView {
    }

    public interface DASimpleView extends SimpleView {
    }

    public interface PageDataSimpleView extends PageSimpleView {
    }

    public interface PageSimpleView extends SimpleView {
    }

    public interface SimpleView {
    }
}

