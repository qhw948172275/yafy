package com.yykj.ddkc.entity;

import javax.persistence.*;

@Table(name = "t_about_us")
public class AboutUs {
    /**
     * 服务协议ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 服务协议类型。0表示关于我们；1表示平台服务协议；2表示商家入驻协议
     */
    private Integer types;

    /**
     * 协议内容
     */
    private String content;

    /**
     * 获取服务协议ID
     *
     * @return id - 服务协议ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置服务协议ID
     *
     * @param id 服务协议ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取服务协议类型。0表示关于我们；1表示平台服务协议；2表示商家入驻协议
     *
     * @return types - 服务协议类型。0表示关于我们；1表示平台服务协议；2表示商家入驻协议
     */
    public Integer getTypes() {
        return types;
    }

    /**
     * 设置服务协议类型。0表示关于我们；1表示平台服务协议；2表示商家入驻协议
     *
     * @param types 服务协议类型。0表示关于我们；1表示平台服务协议；2表示商家入驻协议
     */
    public void setTypes(Integer types) {
        this.types = types;
    }

    /**
     * 获取协议内容
     *
     * @return content - 协议内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置协议内容
     *
     * @param content 协议内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}