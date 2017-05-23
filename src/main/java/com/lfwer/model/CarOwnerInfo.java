package com.lfwer.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "carOwnerInfo")
public class CarOwnerInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	// 从
	private String fromZone;
	// 到
	private String toZone;
	// 联系人
	private String contactUser;
	// 联系电话
	private String contacePhone;
	// 性别
	private String sex;
	// 年龄
	private Integer age;
	// 途经
	private String via1;
	private String via2;
	private String via3;
	private String via4;
	private String via5;
	// 车型
	private String carType;
	// 车辆款式
	private String carStyle;
	// 车辆颜色
	private String carColor;
	// 发布期限
	private Integer timeLimit;
	// 拼车日期
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date pdate;
	// 拼车周期
	private String pweek1;
	private String pweek2;
	private String pweek3;
	private String pweek4;
	private String pweek5;
	private String pweek6;
	private String pweek7;
	// 出发时间
	@DateTimeFormat(pattern = "HH:mm")
	private Date ptime;
	// 人数
	private Integer pnum;
	// 添加人
	private Integer addUser;
	// 添加时间
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date addTime;
	// 修改时间
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date modyTime;
	// 刷新时间
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date refreshTime;
	// 状态1有效0无效
	private Integer state;
	// 删除时间
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date delTime;
	// 置顶1是0否
	private Integer top;
	// 置顶顺序
	private Integer topOrder;
	// 浏览次数
	@Column(insertable = false, updatable = false)
	private Integer lookCount;
	// 费用
	private Integer cost;
	// 留言
	private String remark;
	// 从
	@Transient
	private String fromZoneName;
	// 到
	@Transient
	private String toZoneName;
	// 发布期限
	@Transient
	private Integer timeLimitName;
	// 拼车周期
	@Transient
	private String pweekName;
	// 途经地点
	@Transient
	private String viaName;

	// 车型
	@Transient
	private String carTypeName;
	 
	// 车辆颜色
	@Transient
	private String carColorName;

	public String getCarTypeName() {
		return carTypeName;
	}

	public void setCarTypeName(String carTypeName) {
		this.carTypeName = carTypeName;
	}


	public String getCarColorName() {
		return carColorName;
	}

	public void setCarColorName(String carColorName) {
		this.carColorName = carColorName;
	}

	public String getFromZoneName() {
		return fromZoneName;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public String getCarStyle() {
		return carStyle;
	}

	public void setCarStyle(String carStyle) {
		this.carStyle = carStyle;
	}

	public String getCarColor() {
		return carColor;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public void setCarColor(String carColor) {
		this.carColor = carColor;
	}

	public Integer getCost() {
		return cost;
	}

	public String getViaName() {
		return viaName;
	}

	public void setViaName(String viaName) {
		this.viaName = viaName;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}

	public void setFromZoneName(String fromZoneName) {
		this.fromZoneName = fromZoneName;
	}

	public Integer getLookCount() {
		return lookCount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setLookCount(Integer lookCount) {
		this.lookCount = lookCount;
	}

	public String getToZoneName() {
		return toZoneName;
	}

	public void setToZoneName(String toZoneName) {
		this.toZoneName = toZoneName;
	}

	public Integer getTimeLimitName() {
		return timeLimitName;
	}

	public void setTimeLimitName(Integer timeLimitName) {
		this.timeLimitName = timeLimitName;
	}

	public String getPweekName() {
		return pweekName;
	}

	public void setPweekName(String pweekName) {
		this.pweekName = pweekName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFromZone() {
		return fromZone;
	}

	public void setFromZone(String fromZone) {
		this.fromZone = fromZone;
	}

	public String getToZone() {
		return toZone;
	}

	public void setToZone(String toZone) {
		this.toZone = toZone;
	}

	public String getContactUser() {
		return contactUser;
	}

	public void setContactUser(String contactUser) {
		this.contactUser = contactUser;
	}

	public String getContacePhone() {
		return contacePhone;
	}

	public void setContacePhone(String contacePhone) {
		this.contacePhone = contacePhone;
	}

	public String getVia1() {
		return via1;
	}

	public void setVia1(String via1) {
		this.via1 = via1;
	}

	public String getVia2() {
		return via2;
	}

	public void setVia2(String via2) {
		this.via2 = via2;
	}

	public String getVia3() {
		return via3;
	}

	public void setVia3(String via3) {
		this.via3 = via3;
	}

	public String getVia4() {
		return via4;
	}

	public void setVia4(String via4) {
		this.via4 = via4;
	}

	public String getVia5() {
		return via5;
	}

	public void setVia5(String via5) {
		this.via5 = via5;
	}

	public Integer getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(Integer timeLimit) {
		this.timeLimit = timeLimit;
	}

	public Date getPdate() {
		return pdate;
	}

	public void setPdate(Date pdate) {
		this.pdate = pdate;
	}

	public Date getPtime() {
		return ptime;
	}

	public void setPtime(Date ptime) {
		this.ptime = ptime;
	}

	public Integer getPnum() {
		return pnum;
	}

	public void setPnum(Integer pnum) {
		this.pnum = pnum;
	}

	public Integer getAddUser() {
		return addUser;
	}

	public void setAddUser(Integer addUser) {
		this.addUser = addUser;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getModyTime() {
		return modyTime;
	}

	public void setModyTime(Date modyTime) {
		this.modyTime = modyTime;
	}

	public Date getRefreshTime() {
		return refreshTime;
	}

	public void setRefreshTime(Date refreshTime) {
		this.refreshTime = refreshTime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getDelTime() {
		return delTime;
	}

	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}

	public Integer getTop() {
		return top;
	}

	public void setTop(Integer top) {
		this.top = top;
	}

	public Integer getTopOrder() {
		return topOrder;
	}

	public void setTopOrder(Integer topOrder) {
		this.topOrder = topOrder;
	}

	public String getPweek1() {
		return pweek1;
	}

	public void setPweek1(String pweek1) {
		this.pweek1 = pweek1;
	}

	public String getPweek2() {
		return pweek2;
	}

	public void setPweek2(String pweek2) {
		this.pweek2 = pweek2;
	}

	public String getPweek3() {
		return pweek3;
	}

	public void setPweek3(String pweek3) {
		this.pweek3 = pweek3;
	}

	public String getPweek4() {
		return pweek4;
	}

	public void setPweek4(String pweek4) {
		this.pweek4 = pweek4;
	}

	public String getPweek5() {
		return pweek5;
	}

	public void setPweek5(String pweek5) {
		this.pweek5 = pweek5;
	}

	public String getPweek6() {
		return pweek6;
	}

	public void setPweek6(String pweek6) {
		this.pweek6 = pweek6;
	}

	public String getPweek7() {
		return pweek7;
	}

	public void setPweek7(String pweek7) {
		this.pweek7 = pweek7;
	}

}
