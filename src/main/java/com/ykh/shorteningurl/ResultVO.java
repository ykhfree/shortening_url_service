package com.ykh.shorteningurl;

import java.io.Serializable;

public class ResultVO implements Serializable {

    private static final long serialVersionUID = -6698122807184410135L;

    private boolean success;

    private String resultMsg;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
}
