package com.xgx.syzj.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 会员
 */
public class Member implements Parcelable {

    private int id;
    private String carNumber;
    private String carType;
    private String cardId;
    private String cardNumber;
    private double consumeAmount;//消费总计
    private double consumeRecord;
    private String memberCardCode;/*会员卡号*/
    private int memberItemAmount;/*项目计次*/
    private int memberMetering;/*项目总计次*/
    private String name;
    private String nickName;
    private String openId;
    private String phone;
    private String sex;
    private String status;/*会员状态*/
    private double storedMoney;
    private String userMetering;
    private double distance;//里程数

    public Member()
    {
    }

    protected Member(Parcel in)
    {
        id = in.readInt();
        carNumber = in.readString();
        carType = in.readString();
        cardId = in.readString();
        cardNumber = in.readString();
        consumeAmount = in.readDouble();
        consumeRecord = in.readDouble();
        memberCardCode = in.readString();
        memberItemAmount = in.readInt();
        memberMetering = in.readInt();
        name = in.readString();
        nickName = in.readString();
        openId = in.readString();
        phone = in.readString();
        sex = in.readString();
        status = in.readString();
        storedMoney = in.readDouble();
        userMetering = in.readString();
        distance=in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(id);
        dest.writeString(carNumber);
        dest.writeString(carType);
        dest.writeString(cardId);
        dest.writeString(cardNumber);
        dest.writeDouble(consumeAmount);
        dest.writeDouble(consumeRecord);
        dest.writeString(memberCardCode);
        dest.writeInt(memberItemAmount);
        dest.writeInt(memberMetering);
        dest.writeString(name);
        dest.writeString(nickName);
        dest.writeString(openId);
        dest.writeString(phone);
        dest.writeString(sex);
        dest.writeString(status);
        dest.writeDouble(storedMoney);
        dest.writeString(userMetering);
        dest.writeDouble(distance);
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    public static final Creator<Member> CREATOR = new Creator<Member>() {
        @Override
        public Member createFromParcel(Parcel in)
        {
            return new Member(in);
        }

        @Override
        public Member[] newArray(int size)
        {
            return new Member[size];
        }
    };

    public String getCardId()
    {
        return cardId;
    }

    public void setCardId(String cardId)
    {
        this.cardId = cardId;
    }

    public double getConsumeAmount()
    {
        return consumeAmount;
    }

    public void setConsumeAmount(double consumeAmount)
    {
        this.consumeAmount = consumeAmount;
    }

    public String getMemberCardCode()
    {
        return memberCardCode;
    }

    public void setMemberCardCode(String memberCardCode)
    {
        this.memberCardCode = memberCardCode;
    }

    public int getMemberItemAmount()
    {
        return memberItemAmount;
    }

    public void setMemberItemAmount(int memberItemAmount)
    {
        this.memberItemAmount = memberItemAmount;
    }

    public int getMemberMetering()
    {
        return memberMetering;
    }

    public void setMemberMetering(int memberMetering)
    {
        this.memberMetering = memberMetering;
    }

    public String getNickName()
    {
        return nickName;
    }

    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }

    public String getOpenId()
    {
        return openId;
    }

    public void setOpenId(String openId)
    {
        this.openId = openId;
    }

    public String getSex()
    {
        return sex;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getCarNumber()
    {
        return carNumber;
    }

    public void setCarNumber(String carNumber)
    {
        this.carNumber = carNumber;
    }

    public String getCardNumber()
    {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber)
    {
        this.cardNumber = cardNumber;
    }

    public String getCarType()
    {
        return carType;
    }

    public void setCarType(String carType)
    {
        this.carType = carType;
    }

    public double getConsumeRecord()
    {
        return consumeRecord;
    }

    public void setConsumeRecord(double consumeRecord)
    {
        this.consumeRecord = consumeRecord;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public double getStoredMoney()
    {
        return storedMoney;
    }

    public void setStoredMoney(double storedMoney)
    {
        this.storedMoney = storedMoney;
    }

    public String getUserMetering()
    {
        return userMetering;
    }

    public void setUserMetering(String userMetering)
    {
        this.userMetering = userMetering;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
