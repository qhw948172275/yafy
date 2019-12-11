package com.yykj.business.entity;

import javax.persistence.*;

/**
 * @Author qhw
 * @Date 2019/12/11 0011 下午 14:50
 * @Version V1.0
 **/
@Table(name="r_rent_manage_room")
public class RentManageRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "room_id")
    private Integer roomId;
    @Column(name = "租金管理ID")
    private Integer rentManageId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getRentManageId() {
        return rentManageId;
    }

    public void setRentManageId(Integer rentManageId) {
        this.rentManageId = rentManageId;
    }
}
