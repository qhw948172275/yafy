package com.yykj.ddkc.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_address")
@ApiModel(description = "會会员收货地址")
public class Address {
    /**
     * 收货地址ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "收货地址ID")
    private Integer id;

    /**
     * 会员ID
     */
    @Column(name = "member_id")
    @ApiModelProperty(value = "会员ID")
    private Integer memberId;

    /**
     * 是否默认。0表示否；1表示是
     */
    @ApiModelProperty(value = "是否默认。0表示否；1表示是")
    private Integer def;

    /**
     * 省
     */
    @ApiModelProperty(value = "省")
    private String province;

    /**
     * 市
     */
    @ApiModelProperty(value = "市")
    private String city;

    /**
     * 区
     */
    @ApiModelProperty(value = "区")
    private String area;

    /**
     * 详细地址
     */
    @ApiModelProperty(value = "详细地址")
    private String address;

    /**
     * 状态。0表示正常；1表示已删除
     */
    @ApiModelProperty(value = "状态。0表示正常；1表示已删除")
    private Integer status;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 经度
     */
    @ApiModelProperty(value = "经度")
    private BigDecimal lat;

    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度")
    private BigDecimal lng;

    /**
     * 标签
     */
    @ApiModelProperty(value = "标签")
    private String tag;

    /**
     * 收货人
     */
    @ApiModelProperty(value = "收货人")
    private String contact;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    private String phone;
    /**
     * 门牌号
     */
    @ApiModelProperty(value = "门牌号")
    private String doorNumber;

    /**
     * 称谓。先生/女士
     */
    @ApiModelProperty(value = "称谓。先生/女士")
    @Column(name = "contact_title")
    private String contactTitle;

    /**
     * 获取收货地址ID
     *
     * @return id - 收货地址ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置收货地址ID
     *
     * @param id 收货地址ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取会员ID
     *
     * @return member_id - 会员ID
     */
    public Integer getMemberId() {
        return memberId;
    }

    /**
     * 设置会员ID
     *
     * @param memberId 会员ID
     */
    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    /**
     * 获取是否默认。0表示否；1表示是
     *
     * @return def - 是否默认。0表示否；1表示是
     */
    public Integer getDef() {
        return def;
    }

    /**
     * 设置是否默认。0表示否；1表示是
     *
     * @param def 是否默认。0表示否；1表示是
     */
    public void setDef(Integer def) {
        this.def = def;
    }

    /**
     * 获取省
     *
     * @return province - 省
     */
    public String getProvince() {
        return province;
    }

    /**
     * 设置省
     *
     * @param province 省
     */
    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    /**
     * 获取市
     *
     * @return city - 市
     */
    public String getCity() {
        return city;
    }

    /**
     * 设置市
     *
     * @param city 市
     */
    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    /**
     * 获取区
     *
     * @return area - 区
     */
    public String getArea() {
        return area;
    }

    /**
     * 设置区
     *
     * @param area 区
     */
    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    /**
     * 获取详细地址
     *
     * @return address - 详细地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置详细地址
     *
     * @param address 详细地址
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * 获取状态。0表示正常；1表示已删除
     *
     * @return status - 状态。0表示正常；1表示已删除
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态。0表示正常；1表示已删除
     *
     * @param status 状态。0表示正常；1表示已删除
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取经度
     *
     * @return lat - 经度
     */
    public BigDecimal getLat() {
        return lat;
    }

    /**
     * 设置经度
     *
     * @param lat 经度
     */
    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    /**
     * 获取纬度
     *
     * @return lng - 纬度
     */
    public BigDecimal getLng() {
        return lng;
    }

    /**
     * 设置纬度
     *
     * @param lng 纬度
     */
    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }

    /**
     * 获取标签
     *
     * @return tag - 标签
     */
    public String getTag() {
        return tag;
    }

    /**
     * 设置标签
     *
     * @param tag 标签
     */
    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    /**
     * 获取收货人
     *
     * @return contact - 收货人
     */
    public String getContact() {
        return contact;
    }

    /**
     * 设置收货人
     *
     * @param contact 收货人
     */
    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    /**
     * 获取联系电话
     *
     * @return phone - 联系电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置联系电话
     *
     * @param phone 联系电话
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * 获取称谓。先生/女士
     *
     * @return contact_title - 称谓。先生/女士
     */
    public String getContactTitle() {
        return contactTitle;
    }

    /**
     * 设置称谓。先生/女士
     *
     * @param contactTitle 称谓。先生/女士
     */
    public void setContactTitle(String contactTitle) {
        this.contactTitle = contactTitle == null ? null : contactTitle.trim();
    }

    public String getDoorNumber() {
        return doorNumber;
    }

    public void setDoorNumber(String doorNumber) {
        this.doorNumber = doorNumber;
    }
}