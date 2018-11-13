package com.baidubce.services.smarthome.model;

import com.baidubce.model.AbstractBceResponse;

/**
 * Represent the response for getting device's security info
 */
public class DeviceSecurityInfoResponse extends AbstractBceResponse {
    private int status;
    private String publicKey;
    private String certContent;
    private String privateKey;
    private String uuid;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getCertContent() {
        return certContent;
    }

    public void setCertContent(String certContent) {
        this.certContent = certContent;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
