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
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "pinkerInfo")
public class PinkerInfo implements Serializable {
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
	// 上车地点
	private String onSite;
	// 下车地点
	private String offSite;
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

	// 年龄段
	@Transient
	private Integer ageB;
	@Transient
	private Integer ageE;

	public Integer getAgeB() {
		return ageB;
	}

	public void setAgeB(Integer ageB) {
		this.ageB = ageB;
	}

	public Integer getAgeE() {
		return ageE;
	}

	public void setAgeE(Integer ageE) {
		this.ageE = ageE;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getFromZoneName() {
		return fromZoneName;
	}

	public Integer getCost() {
		return cost;
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

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
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

	public String getOnSite() {
		return onSite;
	}

	public void setOnSite(String onSite) {
		this.onSite = onSite;
	}

	public String getOffSite() {
		return offSite;
	}

	public void setOffSite(String offSite) {
		this.offSite = offSite;
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
