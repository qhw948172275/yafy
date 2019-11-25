package com.yykj.business.response;

import io.swagger.annotations.ApiModelProperty;

/**
 * @ClassName BigLandlordDetailResponse
 * @Description: TODO
 * @Author qhw
 * @Date 2019/11/17 0017 下午 22:40
 * @Version V1.0
 **/
public class BigLandlordDetailResponse {
    /**
     * 小区名称
     */
    @ApiModelProperty("小区名称")
    private String name;

    /**
     * 幢
     */
    @ApiModelProperty("幢")
    private String block;

    /**
     * 单元
     */
    @ApiModelProperty("单元")
    private String unit;

    /**
     * 门牌号
     */
    @ApiModelProperty("门牌号")
    private Integer roomNumber;
    /**
     * 大房东名称
     */
    @ApiModelProperty("大房东名称")
    private String landlordName;
    /**
     * 大房东电话
     */
    @ApiModelProperty("大房东电话")
    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getLandlordName() {
        return landlordName;
    }

    public void setLandlordName(String landlordName) {
        this.landlordName = landlordName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
