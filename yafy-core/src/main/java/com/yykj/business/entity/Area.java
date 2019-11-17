package com.yykj.business.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_area")
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 小区名称
     */
    private String name;

    /**
     * 幢
     */
    private String block;

    /**
     * 单元
     */
    private String unit;

    /**
     * 门牌号
     */
    @Column(name = "room_number")
    private Integer roomNumber;

    /**
     * 创建人ID
     */
    @Column(name = "creator_id")
    private Long creatorId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 状态：0-启用，1-禁用
     */
    private Byte status;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取小区名称
     *
     * @return name - 小区名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置小区名称
     *
     * @param name 小区名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取幢
     *
     * @return block - 幢
     */
    public String getBlock() {
        return block;
    }

    /**
     * 设置幢
     *
     * @param block 幢
     */
    public void setBlock(String block) {
        this.block = block == null ? null : block.trim();
    }

    /**
     * 获取单元
     *
     * @return unit - 单元
     */
    public String getUnit() {
        return unit;
    }

    /**
     * 设置单元
     *
     * @param unit 单元
     */
    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    /**
     * 获取门牌号
     *
     * @return room_number - 门牌号
     */
    public Integer getRoomNumber() {
        return roomNumber;
    }

    /**
     * 设置门牌号
     *
     * @param roomNumber 门牌号
     */
    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    /**
     * 获取创建人ID
     *
     * @return creator_id - 创建人ID
     */
    public Long getCreatorId() {
        return creatorId;
    }

    /**
     * 设置创建人ID
     *
     * @param creatorId 创建人ID
     */
    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
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
     * 获取状态：0-启用，1-禁用
     *
     * @return status - 状态：0-启用，1-禁用
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置状态：0-启用，1-禁用
     *
     * @param status 状态：0-启用，1-禁用
     */
    public void setStatus(Byte status) {
        this.status = status;
    }
}