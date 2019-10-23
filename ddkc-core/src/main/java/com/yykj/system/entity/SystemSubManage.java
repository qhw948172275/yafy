package com.yykj.system.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 子系统管理
 */
@Entity
@Table(name = "t_sys_sub_manage")
public class SystemSubManage implements Serializable {

    private static final long serialVersionUID = -2127031374793895140L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String value;

    private String url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
