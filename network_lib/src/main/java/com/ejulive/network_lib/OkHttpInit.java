package com.ejulive.network_lib;


/**
 * Created by XCC on 2017/2/22.
 */
@Deprecated
public class OkHttpInit {
    private String token ;
    private String channel;
    private String netWorkName;
    private String clientId;
    private String clientVersion;
    private OkHttpInit(Builder builder){
        this.token = builder.token;
        this.channel = builder.channel;
        this.clientId = builder.clientId;
        this.netWorkName = builder.netWorkName;
        this.clientVersion = builder.clientVersion;
        RequestContent.initOkHttp(this);
    }

    private static class Builder{
        private String token ;
        private String channel;
        private String netWorkName;
        private String clientId;
        private String clientVersion;
       private Builder(){}
        public Builder token(String token){
            this.token = token;
            return this;
        }
        public Builder channel(String channel){
            this.channel = channel;
            return this;
        }
        public Builder netWorkName(String netWorkName){
            this.netWorkName = netWorkName;
            return this;
        }
        public Builder clientId(String clientId){
            this.clientId = clientId;
            return this;
        }
        public Builder clientVersion(String clientVersion){
            this.clientVersion = clientVersion;
            return this;
        }
        public OkHttpInit build(){
            return new OkHttpInit(this);
        }

    }

    public String getToken() {
        return token;
    }

    public String getChannel() {
        return channel;
    }

    public String getnetWorkName() {
        return netWorkName;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientVersion(){
        return clientVersion;
    }
}
