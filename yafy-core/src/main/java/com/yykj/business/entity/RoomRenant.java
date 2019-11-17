package com.yykj.business.entity;

import javax.persistence.*;

@Table(name = "r_room_renant")
public class RoomRenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 租客ID
     */
    @Column(name = "renant_id")
    private Integer renantId;

    /**
     * 房间ID
     */
    @Column(name = "room_id")
    private Integer roomId;

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
     * 获取租客ID
     *
     * @return renant_id - 租客ID
     */
    public Integer getRenantId() {
        return renantId;
    }

    /**
     * 设置租客ID
     *
     * @param renantId 租客ID
     */
    public void setRenantId(Integer renantId) {
        this.renantId = renantId;
    }

    /**
     * 获取房间ID
     *
     * @return room_id - 房间ID
     */
    public Integer getRoomId() {
        return roomId;
    }

    /**
     * 设置房间ID
     *
     * @param roomId 房间ID
     */
    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }
}