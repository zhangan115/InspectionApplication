package com.inspection.application.mode.bean.secure;

import java.util.List;

/**
 * Created by Yangzb on 2017/7/23 16:43
 * E-mail：yangzongbin@si-top.com
 * 安全包
 */
public class SecureBean {

    private long createTime;
    private int deleteState;
    private long deleteTime;
    private int securityId;
    private String securityName;
    private String securityRemark;
    private List<PageListBean> pageList;

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getDeleteState() {
        return deleteState;
    }

    public void setDeleteState(int deleteState) {
        this.deleteState = deleteState;
    }

    public long getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(long deleteTime) {
        this.deleteTime = deleteTime;
    }

    public int getSecurityId() {
        return securityId;
    }

    public void setSecurityId(int securityId) {
        this.securityId = securityId;
    }

    public String getSecurityName() {
        return securityName;
    }

    public void setSecurityName(String securityName) {
        this.securityName = securityName;
    }

    public String getSecurityRemark() {
        return securityRemark;
    }

    public void setSecurityRemark(String securityRemark) {
        this.securityRemark = securityRemark;
    }

    public List<PageListBean> getPageList() {
        return pageList;
    }

    public void setPageList(List<PageListBean> pageList) {
        this.pageList = pageList;
    }

    public static class PageListBean {

        private String pageContent;
        private int pageId;
        private String pageName;
        private int pageSequence;

        public String getPageContent() {
            return pageContent;
        }

        public void setPageContent(String pageContent) {
            this.pageContent = pageContent;
        }

        public int getPageId() {
            return pageId;
        }

        public void setPageId(int pageId) {
            this.pageId = pageId;
        }

        public String getPageName() {
            return pageName;
        }

        public void setPageName(String pageName) {
            this.pageName = pageName;
        }

        public int getPageSequence() {
            return pageSequence;
        }

        public void setPageSequence(int pageSequence) {
            this.pageSequence = pageSequence;
        }
    }
}
