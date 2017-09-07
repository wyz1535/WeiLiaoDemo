package com.leyifu.weiliaodemo.bean;

/**
 * Created by hahaha on 2017/9/5 0005.
 */

public class RegistBean {

    /**
     * message : 用户注册成功
     * omMember : {"ommEmail":"","ommHeader":"","ommId":12961,"ommIntegral":0,"ommIsVip":false,"ommLevel":0,"ommMoney":0,"ommNickname":"","ommOpenId":"","ommPassword":"E10ADC3949BA59ABBE56E057F20F883E","ommPlace":357,"ommRemarks1":"","ommRemarks2":"","ommStatus":1,"ommType":0,"ommUsername":"123"}
     * state : 1
     */

    private String message;
    private OmMemberBean omMember;
    private String state;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public OmMemberBean getOmMember() {
        return omMember;
    }

    public void setOmMember(OmMemberBean omMember) {
        this.omMember = omMember;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public static class OmMemberBean {
        /**
         * ommEmail :
         * ommHeader :
         * ommId : 12961
         * ommIntegral : 0
         * ommIsVip : false
         * ommLevel : 0
         * ommMoney : 0
         * ommNickname :
         * ommOpenId :
         * ommPassword : E10ADC3949BA59ABBE56E057F20F883E
         * ommPlace : 357
         * ommRemarks1 :
         * ommRemarks2 :
         * ommStatus : 1
         * ommType : 0
         * ommUsername : 123
         */

        private String ommEmail;
        private String ommHeader;
        private int ommId;
        private int ommIntegral;
        private boolean ommIsVip;
        private int ommLevel;
        private int ommMoney;
        private String ommNickname;
        private String ommOpenId;
        private String ommPassword;
        private int ommPlace;
        private String ommRemarks1;
        private String ommRemarks2;
        private int ommStatus;
        private int ommType;
        private String ommUsername;

        public String getOmmEmail() {
            return ommEmail;
        }

        public void setOmmEmail(String ommEmail) {
            this.ommEmail = ommEmail;
        }

        public String getOmmHeader() {
            return ommHeader;
        }

        public void setOmmHeader(String ommHeader) {
            this.ommHeader = ommHeader;
        }

        public int getOmmId() {
            return ommId;
        }

        public void setOmmId(int ommId) {
            this.ommId = ommId;
        }

        public int getOmmIntegral() {
            return ommIntegral;
        }

        public void setOmmIntegral(int ommIntegral) {
            this.ommIntegral = ommIntegral;
        }

        public boolean isOmmIsVip() {
            return ommIsVip;
        }

        public void setOmmIsVip(boolean ommIsVip) {
            this.ommIsVip = ommIsVip;
        }

        public int getOmmLevel() {
            return ommLevel;
        }

        public void setOmmLevel(int ommLevel) {
            this.ommLevel = ommLevel;
        }

        public int getOmmMoney() {
            return ommMoney;
        }

        public void setOmmMoney(int ommMoney) {
            this.ommMoney = ommMoney;
        }

        public String getOmmNickname() {
            return ommNickname;
        }

        public void setOmmNickname(String ommNickname) {
            this.ommNickname = ommNickname;
        }

        public String getOmmOpenId() {
            return ommOpenId;
        }

        public void setOmmOpenId(String ommOpenId) {
            this.ommOpenId = ommOpenId;
        }

        public String getOmmPassword() {
            return ommPassword;
        }

        public void setOmmPassword(String ommPassword) {
            this.ommPassword = ommPassword;
        }

        public int getOmmPlace() {
            return ommPlace;
        }

        public void setOmmPlace(int ommPlace) {
            this.ommPlace = ommPlace;
        }

        public String getOmmRemarks1() {
            return ommRemarks1;
        }

        public void setOmmRemarks1(String ommRemarks1) {
            this.ommRemarks1 = ommRemarks1;
        }

        public String getOmmRemarks2() {
            return ommRemarks2;
        }

        public void setOmmRemarks2(String ommRemarks2) {
            this.ommRemarks2 = ommRemarks2;
        }

        public int getOmmStatus() {
            return ommStatus;
        }

        public void setOmmStatus(int ommStatus) {
            this.ommStatus = ommStatus;
        }

        public int getOmmType() {
            return ommType;
        }

        public void setOmmType(int ommType) {
            this.ommType = ommType;
        }

        public String getOmmUsername() {
            return ommUsername;
        }

        public void setOmmUsername(String ommUsername) {
            this.ommUsername = ommUsername;
        }
    }
}
