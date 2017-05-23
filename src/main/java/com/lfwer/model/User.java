package com.lfwer.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "user")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	// 用户名
	private String username;
	// 密码
	private String password;
	// 手机
	private String phone;
	// 头像
	private String photoSmall;
	private String photoLarge;
	// 身份 1乘客 2车主
	private String type;
	// 真实姓名
	private String realName;
	// 身份证号
	private String idCard;
	// 现住地编码
	private String zone;
	// 详细地址
	private String addr;
	// 昵称
	private String nickName;
	// 性别 1男 2女
	private String sex;
	// 生日
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;
	// 年龄
	private Integer age;
	// 婚否
	private String marry;
	// 兴趣爱好
	private String hobby;
	// 行业
	private String industry;
	// 个性签名
	private String sign;
	// 车辆品牌
	private String carBrand;
	// 车型
	private String carType;
	// 车辆款式
	private String carStyle;
	// 车辆颜色
	private String carColor;
	//车牌归属地
	private Integer carProvince;
	// 车牌号
	private String carNum;
	// 车辆照片1
	private String carPhotoSmall1;
	private String carPhotoLarge1;
	// 车辆照片2
	private String carPhotoSmall2;
	private String carPhotoLarge2;

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getPhotoSmall() {
		return photoSmall;
	}

	public void setPhotoSmall(String photoSmall) {
		this.photoSmall = photoSmall;
	}

	public String getPhotoLarge() {
		return photoLarge;
	}

	public void setPhotoLarge(String photoLarge) {
		this.photoLarge = photoLarge;
	}

	public String getCarBrand() {
		return carBrand;
	}

	public void setCarBrand(String carBrand) {
		this.carBrand = carBrand;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public Integer getCarProvince() {
		return carProvince;
	}

	public void setCarProvince(Integer carProvince) {
		this.carProvince = carProvince;
	}

	public String getCarNum() {
		return carNum;
	}

	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}

	public String getCarPhotoSmall1() {
		return carPhotoSmall1;
	}

	public void setCarPhotoSmall1(String carPhotoSmall1) {
		this.carPhotoSmall1 = carPhotoSmall1;
	}

	public String getCarPhotoLarge1() {
		return carPhotoLarge1;
	}

	public void setCarPhotoLarge1(String carPhotoLarge1) {
		this.carPhotoLarge1 = carPhotoLarge1;
	}

	public String getCarPhotoSmall2() {
		return carPhotoSmall2;
	}

	public void setCarPhotoSmall2(String carPhotoSmall2) {
		this.carPhotoSmall2 = carPhotoSmall2;
	}

	public String getCarPhotoLarge2() {
		return carPhotoLarge2;
	}

	public void setCarPhotoLarge2(String carPhotoLarge2) {
		this.carPhotoLarge2 = carPhotoLarge2;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public String getMarry() {
		return marry;
	}

	public void setMarry(String marry) {
		this.marry = marry;
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

	public void setCarColor(String carColor) {
		this.carColor = carColor;
	}

}
