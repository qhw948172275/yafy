package com.yykj.ddkc.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yykj.system.commons.CalendarUtils;
import com.yykj.system.commons.StringUtils;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_shop")
public class Shop {
    /**
     * 店铺ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 店铺名称
     */
    @ApiModelProperty(value = "店铺名称")
    private String name;

    /**
     * 营业执照
     */
    @ApiModelProperty(value = "营业执照")
    private String cert;

    /**
     * 联系人
     */
    @ApiModelProperty(value = "联系人")
    private String contact;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    private String phone;

    /**
     * 商家登录密码
     */
    @ApiModelProperty(value = "商家登录密码")
    private String password;

    /**
     * 微信openID
     */
    @ApiModelProperty(value = "微信openID")
    private String openid;

    /**
     * 微信商户昵称
     */
    @ApiModelProperty(value = "微信商户昵称")
    @Column(name = "nick_name")
    private String nickName;

    /**
     * 微信头像地址
     */
    @ApiModelProperty(value = "微信头像地址")
    private String avastar;

    /**
     * 地址：省
     */
    @ApiModelProperty(value = "地址：省")
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
     * 状态。0表示启用；1表示禁用，2表示删除
     */
    @ApiModelProperty(value = "状态。0表示启用；1表示禁用，2表示删除")
    private Integer status;

    /**
     * 店铺封面
     */
    @ApiModelProperty(value = "店铺封面")
    private String cover;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = CalendarUtils.yyyy_MM_dd)
    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建人ID
     */
    @Column(name = "create_id")
    private Integer createId;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 总销售额
     */
    @Column(name = "total_sales")
    @ApiModelProperty(value = "总销售额")
    private BigDecimal totalSales;

    /**
     * 总销售量
     */
    @Column(name = "total_sales_counts")
    @ApiModelProperty(value = "总销售量")
    private Long totalSalesCounts;

    /**
     * 开始营业时间
     */
    @Column(name = "buss_start_time")
    @ApiModelProperty(value = "开始营业时间")
    private String bussStartTime;

    /**
     * 营业截止时间
     */
    @Column(name = "buss_end_time")
    @ApiModelProperty(value = "营业截止时间")
    private String bussEndTime;

    /**
     * 上传附件地址
     */
    @Column(name = "annex_address")
    @ApiModelProperty(value = "上传附件地址")
    private String annexAddress;
    /**
     * 所在小区
     */
    @ApiModelProperty(value = "所在小区")
    private String  estate;
    /**
     * 商家剩余总金额
     */
    @ApiModelProperty(value = "商家剩余总金额")
    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    /**
     * 可见余额
     */
    @ApiModelProperty(value = "可见余额")
    @Column(name = "so_balance")
    private BigDecimal  soBalance;
    /**
     * 头像地址
     */
    @ApiModelProperty(value = "头像地址")
    @Column(name = "head_address")
    private String headAddress;
    /**
     * 微信二维码
     */
    @ApiModelProperty(value = "微信二维码")
    private String wxaqrcode;
    /**
     * 获取店铺ID
     *
     * @return id - 店铺ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置店铺ID
     *
     * @param id 店铺ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取店铺名称
     *
     * @return name - 店铺名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置店铺名称
     *
     * @param name 店铺名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取营业执照
     *
     * @return cert - 营业执照
     */
    public String getCert() {
        return cert;
    }

    /**
     * 设置营业执照
     *
     * @param cert 营业执照
     */
    public void setCert(String cert) {
        this.cert = cert == null ? null : cert.trim();
    }

    /**
     * 获取联系人
     *
     * @return contact - 联系人
     */
    public String getContact() {
        return contact;
    }

    /**
     * 设置联系人
     *
     * @param contact 联系人
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
     * 获取商家登录密码
     *
     * @return password - 商家登录密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置商家登录密码
     *
     * @param password 商家登录密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 获取微信openID
     *
     * @return openid - 微信openID
     */
    public String getOpenid() {
        return openid;
    }

    /**
     * 设置微信openID
     *
     * @param openid 微信openID
     */
    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    /**
     * 获取微信商户昵称
     *
     * @return nick_name - 微信商户昵称
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * 设置微信商户昵称
     *
     * @param nickName 微信商户昵称
     */
    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    /**
     * 获取微信头像地址
     *
     * @return avastar - 微信头像地址
     */
    public String getAvastar() {
        return avastar;
    }

    /**
     * 设置微信头像地址
     *
     * @param avastar 微信头像地址
     */
    public void setAvastar(String avastar) {
        this.avastar = avastar == null ? null : avastar.trim();
    }

    /**
     * 获取地址：省
     *
     * @return province - 地址：省
     */
    public String getProvince() {
        return province;
    }

    /**
     * 设置地址：省
     *
     * @param province 地址：省
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
     * 获取状态。0表示启用；1表示禁用
     *
     * @return status - 状态。0表示启用；1表示禁用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态。0表示启用；1表示禁用
     *
     * @param status 状态。0表示启用；1表示禁用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取店铺封面
     *
     * @return cover - 店铺封面
     */
    public String getCover() {
        return cover;
    }

    /**
     * 设置店铺封面
     *
     * @param cover 店铺封面
     */
    public void setCover(String cover) {
        this.cover = cover;
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
     * 获取创建人
     *
     * @return creator - 创建人
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 设置创建人
     *
     * @param creator 创建人
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * 获取创建人ID
     *
     * @return create_id - 创建人ID
     */
    public Integer getCreateId() {
        return createId;
    }

    /**
     * 设置创建人ID
     *
     * @param createId 创建人ID
     */
    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取总销售额
     *
     * @return total_sales - 总销售额
     */
    public BigDecimal getTotalSales() {
        return totalSales;
    }

    /**
     * 设置总销售额
     *
     * @param totalSales 总销售额
     */
    public void setTotalSales(BigDecimal totalSales) {
        this.totalSales = totalSales;
    }

    /**
     * 获取总销售量
     *
     * @return total_sales_counts - 总销售量
     */
    public Long getTotalSalesCounts() {
        return totalSalesCounts;
    }

    /**
     * 设置总销售量
     *
     * @param totalSalesCounts 总销售量
     */
    public void setTotalSalesCounts(Long totalSalesCounts) {
        this.totalSalesCounts = totalSalesCounts;
    }

    /**
     * 获取开始营业时间
     *
     * @return buss_start_time - 开始营业时间
     */
    public String getBussStartTime() {
        return bussStartTime;
    }

    /**
     * 设置开始营业时间
     *
     * @param bussStartTime 开始营业时间
     */
    public void setBussStartTime(String bussStartTime) {
        this.bussStartTime = bussStartTime == null ? null : bussStartTime.trim();
    }

    /**
     * 获取营业截止时间
     *
     * @return buss_end_time - 营业截止时间
     */
    public String getBussEndTime() {
        return bussEndTime;
    }

    /**
     * 设置营业截止时间
     *
     * @param bussEndTime 营业截止时间
     */
    public void setBussEndTime(String bussEndTime) {
        this.bussEndTime = bussEndTime == null ? null : bussEndTime.trim();
    }

    public String getAnnexAddress() {
        return annexAddress;
    }

    public void setAnnexAddress(String annexAddress) {
        this.annexAddress = annexAddress;
    }

    public String getEstate() {
        return estate;
    }

    public void setEstate(String estate) {
        this.estate = estate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getHeadAddress() {
        return headAddress;
    }

    public void setHeadAddress(String headAddress) {
        this.headAddress = headAddress;
    }

    public String getWxaqrcode() {
        if(StringUtils.isEmpty(this.wxaqrcode)){
            return "";
        }
        return wxaqrcode;
    }

    public void setWxaqrcode(String wxaqrcode) {
        this.wxaqrcode = wxaqrcode;
    }

    public BigDecimal getSoBalance() {
        if(this.soBalance==null){
            return new BigDecimal(0);
        }
        return soBalance;
    }

    public void setSoBalance(BigDecimal soBalance) {
        this.soBalance = soBalance;
    }
}