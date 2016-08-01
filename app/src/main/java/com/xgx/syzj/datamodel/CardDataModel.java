package com.xgx.syzj.datamodel;

import com.xgx.syzj.app.Api;

/**
 * @author zajo
 * @created 2015年10月26日 11:13
 */
public class CardDataModel extends BaseDataModel {

    public static final byte LOAD_CARD_LIST = 0x0;
    public static final byte CREATE_CARD = 0x1;
    public static final byte DETAIL_CARD = 0x2;
    public static final byte DELETE_CARD = 0x3;
    public static final byte UPDATE_CARD = 0x4;

    //新增会员卡
    public static void createCard(String name, boolean integral, boolean value, boolean count, boolean password, int discount, int integralRatio) {
        code = CREATE_CARD;
        Api.addCardType(name, integral, value, count, password, discount, integralRatio, listener);
    }

    //获取会员卡列表
    public static void getList() {
        code = LOAD_CARD_LIST;
        Api.getCardTypeList(listener);
    }

    //获取会员卡详情
    public static void getCardDetail(int cardId) {
        code = DETAIL_CARD;
        Api.getCardDetail(cardId, listener);
    }

    //删除会员卡
    public static void deleteCard(int id) {
        code = DELETE_CARD;
        Api.deleteCardType(id, listener);
    }

    //更新会员卡
    public static void updateCard(int id, String name, boolean integral, boolean value, boolean count, boolean password, int discount, int integralRatio) {
        code = UPDATE_CARD;
        Api.updateCardType(id, name, integral, value, count, password, discount, integralRatio, listener);
    }
}
