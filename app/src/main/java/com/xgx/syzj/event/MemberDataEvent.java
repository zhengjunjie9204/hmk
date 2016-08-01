package com.xgx.syzj.event;

import com.xgx.syzj.bean.Member;

/**
 * @author zajo
 * @created 2015年11月03日 16:58
 */
public class MemberDataEvent {

    public static final byte ADD_RECHARGE = 0x10;
    public static final byte DELETE_MEMBER = 0x11;

    private byte code;

    private Member member;

    public MemberDataEvent(byte c, Member m) {
        this.code = c;
        this.member = m;
    }

    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
