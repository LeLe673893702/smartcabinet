package com.llc.smartcabinet.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单类
 * @author newler
 * @date 2019/12/28
 *
 */
public class Order implements Serializable, Parcelable {
    /**
     * id
     */
    private String id;

    /**
     * 租用人id
     */
    private String userId;

    /**
     * 租用人手机号
     */
    private String userPhone;

    /**
     * 租用人昵称
     */
    private String userNickname;

    /**
     * 柜子id
     */
    private String cabinetId;

    /**
     * 柜子名称
     */
    private String cabinetName;

    /**
     * 柜子区id
     */
    private String cabinetAreaId;

    /**
     * 柜子区名称
     */
    private String cabinetAreaName;

    /**
     * 服务区id
     */
    private String serverAreaId;

    /**
     * 服务区名称
     */
    private String serverAreaName;

    /**
     * 费用
     */
    private BigDecimal cost;

    /**
     * 押金
     */
    private BigDecimal deposit;

    /**
     * 备注
     */
    private String note;

    /**
     * 状态( 1.待支付 2.进行中 3.已完成 4.已取消 5.已退款)
     */
    private Integer status;

    /**
     * 柜子编号
     */
    private String cabinetCode;

    /**
     * 创建人id
     */
    private String createUserId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人id
     */
    private String updateUserId;

    private String payQrCodeUrl;

    /**
     * 修改时间
     */
    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getCabinetId() {
        return cabinetId;
    }

    public void setCabinetId(String cabinetId) {
        this.cabinetId = cabinetId;
    }

    public String getCabinetName() {
        return cabinetName;
    }

    public void setCabinetName(String cabinetName) {
        this.cabinetName = cabinetName;
    }

    public String getCabinetAreaId() {
        return cabinetAreaId;
    }

    public void setCabinetAreaId(String cabinetAreaId) {
        this.cabinetAreaId = cabinetAreaId;
    }

    public String getCabinetAreaName() {
        return cabinetAreaName;
    }

    public void setCabinetAreaName(String cabinetAreaName) {
        this.cabinetAreaName = cabinetAreaName;
    }

    public String getServerAreaId() {
        return serverAreaId;
    }

    public void setServerAreaId(String serverAreaId) {
        this.serverAreaId = serverAreaId;
    }

    public String getServerAreaName() {
        return serverAreaName;
    }

    public void setServerAreaName(String serverAreaName) {
        this.serverAreaName = serverAreaName;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCabinetCode() {
        return cabinetCode;
    }

    public void setCabinetCode(String cabinetCode) {
        this.cabinetCode = cabinetCode;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getPayQrCodeUrl() {
        return payQrCodeUrl;
    }

    public void setPayQrCodeUrl(String payQrCodeUrl) {
        this.payQrCodeUrl = payQrCodeUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.userId);
        dest.writeString(this.userPhone);
        dest.writeString(this.userNickname);
        dest.writeString(this.cabinetId);
        dest.writeString(this.cabinetName);
        dest.writeString(this.cabinetAreaId);
        dest.writeString(this.cabinetAreaName);
        dest.writeString(this.serverAreaId);
        dest.writeString(this.serverAreaName);
        dest.writeSerializable(this.cost);
        dest.writeSerializable(this.deposit);
        dest.writeString(this.note);
        dest.writeValue(this.status);
        dest.writeString(this.cabinetCode);
        dest.writeString(this.createUserId);
        dest.writeLong(this.createTime != null ? this.createTime.getTime() : -1);
        dest.writeString(this.updateUserId);
        dest.writeString(this.payQrCodeUrl);
        dest.writeLong(this.updateTime != null ? this.updateTime.getTime() : -1);
    }

    public Order() {
    }

    protected Order(Parcel in) {
        this.id = in.readString();
        this.userId = in.readString();
        this.userPhone = in.readString();
        this.userNickname = in.readString();
        this.cabinetId = in.readString();
        this.cabinetName = in.readString();
        this.cabinetAreaId = in.readString();
        this.cabinetAreaName = in.readString();
        this.serverAreaId = in.readString();
        this.serverAreaName = in.readString();
        this.cost = (BigDecimal) in.readSerializable();
        this.deposit = (BigDecimal) in.readSerializable();
        this.note = in.readString();
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        this.cabinetCode = in.readString();
        this.createUserId = in.readString();
        long tmpCreateTime = in.readLong();
        this.createTime = tmpCreateTime == -1 ? null : new Date(tmpCreateTime);
        this.updateUserId = in.readString();
        this.payQrCodeUrl = in.readString();
        long tmpUpdateTime = in.readLong();
        this.updateTime = tmpUpdateTime == -1 ? null : new Date(tmpUpdateTime);
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel source) {
            return new Order(source);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
}