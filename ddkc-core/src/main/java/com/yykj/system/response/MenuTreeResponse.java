package com.yykj.system.response;

import java.io.Serializable;
import java.util.List;

public class MenuTreeResponse implements Serializable {
	public MenuTreeResponse(){
		
	}
	
	public MenuTreeResponse(Integer id, String resourceName, String resourceUrl, Integer isBasic, Integer parentId,
			Integer level, String remark, Integer status, Integer opened, Integer resourceType, Integer resourceKind,
			Integer seq, String icon, Integer openMode) {
		this.id = id;
		this.resourceName = resourceName;
		this.resourceUrl = resourceUrl;
		this.isBasic = isBasic;
		this.parentId = parentId;
		this.level = level;
		this.remark = remark;
		this.status = status;
		this.opened = opened;
		this.resourceType = resourceType;
		this.resourceKind = resourceKind;
		this.seq = seq;
		this.icon = icon;
		this.openMode = openMode;
	}

	private Integer id;

    private String resourceName;

    private String resourceUrl;

    private Integer isBasic;

    private Integer parentId;

    private Integer level;

    private String remark;

    private Integer status;
    
    private Integer opened;
    
    //资源所属类别（商城1、平台2、直播3）
    private Integer resourceKind;
    
    //资源是菜单0，按钮1
    private Integer resourceType;
    
    private Integer seq;
    
	private String icon;
    
    private Integer openMode;
    
    private List<MenuTreeResponse> childList;

    private static final long serialVersionUID = 1L;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getResourceUrl() {
		return resourceUrl;
	}

	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}

	public Integer getIsBasic() {
		return isBasic;
	}

	public void setIsBasic(Integer isBasic) {
		this.isBasic = isBasic;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getOpened() {
		return opened;
	}

	public void setOpened(Integer opened) {
		this.opened = opened;
	}

	public Integer getResourceType() {
		return resourceType;
	}

	public void setResourceType(Integer resourceType) {
		this.resourceType = resourceType;
	}

	public Integer getResourceKind() {
		return resourceKind;
	}

	public void setResourceKind(Integer resourceKind) {
		this.resourceKind = resourceKind;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getOpenMode() {
		return openMode;
	}

	public void setOpenMode(Integer openMode) {
		this.openMode = openMode;
	}

	public List<MenuTreeResponse> getChildList() {
		return childList;
	}

	public void setChildList(List<MenuTreeResponse> childList) {
		this.childList = childList;
	}

}
