package com.xgx.syzj.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sam on 2016/3/11 15:56.
 */
public class BillListItemBean implements Parcelable{

    /**
     * billDetails : [{"sellingPrice":3.8,"costPrice":0.8,"flag":0,"billDetailsId":46,"billId":34,"quantity":5,"storeId":15,"productName":"eggg","totalValue":19,"returnReason":0,"productId":43}]
     * customerType : 1
     * accountId : 15820259933
     * modeOfPay : 2
     * associatorId : 16
     * returnValue : 0
     * billDatetime : 1457939238000
     * receivableValue : 19
     * flag : 0
     * description :
     * billId : 34
     * associatorName : Sam
     * paidValue : 15.2
     * storeId : 15
     */

    private int customerType;
    private String accountId;
    private int modeOfPay;
    private int associatorId;
    private int returnValue;
    private long billDatetime;
    private int receivableValue;
    private int flag;
    private String description;
    private int billId;
    private String associatorName;
    private double paidValue;
    private int storeId;
    /**
     * sellingPrice : 3.8
     * costPrice : 0.8
     * flag : 0
     * billDetailsId : 46
     * billId : 34
     * quantity : 5
     * storeId : 15
     * productName : eggg
     * totalValue : 19
     * returnReason : 0
     * productId : 43
     */

    private List<BillDetailsEntity> billDetails;

    public void setCustomerType(int customerType) {
        this.customerType = customerType;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setModeOfPay(int modeOfPay) {
        this.modeOfPay = modeOfPay;
    }

    public void setAssociatorId(int associatorId) {
        this.associatorId = associatorId;
    }

    public void setReturnValue(int returnValue) {
        this.returnValue = returnValue;
    }

    public void setBillDatetime(long billDatetime) {
        this.billDatetime = billDatetime;
    }

    public void setReceivableValue(int receivableValue) {
        this.receivableValue = receivableValue;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public void setAssociatorName(String associatorName) {
        this.associatorName = associatorName;
    }

    public void setPaidValue(double paidValue) {
        this.paidValue = paidValue;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public void setBillDetails(List<BillDetailsEntity> billDetails) {
        this.billDetails = billDetails;
    }

    public int getCustomerType() {
        return customerType;
    }

    public String getAccountId() {
        return accountId;
    }

    public int getModeOfPay() {
        return modeOfPay;
    }

    public int getAssociatorId() {
        return associatorId;
    }

    public int getReturnValue() {
        return returnValue;
    }

    public long getBillDatetime() {
        return billDatetime;
    }

    public int getReceivableValue() {
        return receivableValue;
    }

    public int getFlag() {
        return flag;
    }

    public String getDescription() {
        return description;
    }

    public int getBillId() {
        return billId;
    }

    public String getAssociatorName() {
        return associatorName;
    }

    public double getPaidValue() {
        return paidValue;
    }

    public int getStoreId() {
        return storeId;
    }

    public List<BillDetailsEntity> getBillDetails() {
        return billDetails;
    }

    public static class BillDetailsEntity {
        private double sellingPrice;
        private double costPrice;
        private int flag;
        private int billDetailsId;
        private int billId;
        private int quantity;
        private int storeId;
        private String productName;
        private double totalValue;
        private int returnReason;
        private int productId;

        public void setSellingPrice(double sellingPrice) {
            this.sellingPrice = sellingPrice;
        }

        public void setCostPrice(double costPrice) {
            this.costPrice = costPrice;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public void setBillDetailsId(int billDetailsId) {
            this.billDetailsId = billDetailsId;
        }

        public void setBillId(int billId) {
            this.billId = billId;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public void setStoreId(int storeId) {
            this.storeId = storeId;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public void setTotalValue(double totalValue) {
            this.totalValue = totalValue;
        }

        public void setReturnReason(int returnReason) {
            this.returnReason = returnReason;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public double getSellingPrice() {
            return sellingPrice;
        }

        public double getCostPrice() {
            return costPrice;
        }

        public int getFlag() {
            return flag;
        }

        public int getBillDetailsId() {
            return billDetailsId;
        }

        public int getBillId() {
            return billId;
        }

        public int getQuantity() {
            return quantity;
        }

        public int getStoreId() {
            return storeId;
        }

        public String getProductName() {
            return productName;
        }

        public double getTotalValue() {
            return totalValue;
        }

        public int getReturnReason() {
            return returnReason;
        }

        public int getProductId() {
            return productId;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.customerType);
        dest.writeString(this.accountId);
        dest.writeInt(this.modeOfPay);
        dest.writeInt(this.associatorId);
        dest.writeInt(this.returnValue);
        dest.writeLong(this.billDatetime);
        dest.writeInt(this.receivableValue);
        dest.writeInt(this.flag);
        dest.writeString(this.description);
        dest.writeInt(this.billId);
        dest.writeString(this.associatorName);
        dest.writeDouble(this.paidValue);
        dest.writeInt(this.storeId);
        dest.writeList(this.billDetails);
    }

    public BillListItemBean() {
    }

    protected BillListItemBean(Parcel in) {
        this.customerType = in.readInt();
        this.accountId = in.readString();
        this.modeOfPay = in.readInt();
        this.associatorId = in.readInt();
        this.returnValue = in.readInt();
        this.billDatetime = in.readLong();
        this.receivableValue = in.readInt();
        this.flag = in.readInt();
        this.description = in.readString();
        this.billId = in.readInt();
        this.associatorName = in.readString();
        this.paidValue = in.readDouble();
        this.storeId = in.readInt();
        this.billDetails = new ArrayList<BillDetailsEntity>();
        in.readList(this.billDetails, List.class.getClassLoader());
    }

    public static final Creator<BillListItemBean> CREATOR = new Creator<BillListItemBean>() {
        public BillListItemBean createFromParcel(Parcel source) {
            return new BillListItemBean(source);
        }

        public BillListItemBean[] newArray(int size) {
            return new BillListItemBean[size];
        }
    };
}
