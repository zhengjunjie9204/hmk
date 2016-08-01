package com.xgx.syzj.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sam on 2016/3/18 21:55.
 */
public class AnalyAllConsumpt implements Parcelable{


    /**
     * outsiderConsumption : 271872.3
     * associatorConsumption : 349220.2
     * totalConsumption : 621092.3
     */

    private double outsiderConsumption;
    private double associatorConsumption;
    private double totalConsumption;

    public void setOutsiderConsumption(double outsiderConsumption) {
        this.outsiderConsumption = outsiderConsumption;
    }

    public void setAssociatorConsumption(double associatorConsumption) {
        this.associatorConsumption = associatorConsumption;
    }

    public void setTotalConsumption(double totalConsumption) {
        this.totalConsumption = totalConsumption;
    }

    public double getOutsiderConsumption() {
        return outsiderConsumption;
    }

    public double getAssociatorConsumption() {
        return associatorConsumption;
    }

    public double getTotalConsumption() {
        return totalConsumption;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.outsiderConsumption);
        dest.writeDouble(this.associatorConsumption);
        dest.writeDouble(this.totalConsumption);
    }

    public AnalyAllConsumpt() {
    }

    protected AnalyAllConsumpt(Parcel in) {
        this.outsiderConsumption = in.readDouble();
        this.associatorConsumption = in.readDouble();
        this.totalConsumption = in.readDouble();
    }

    public static final Creator<AnalyAllConsumpt> CREATOR = new Creator<AnalyAllConsumpt>() {
        public AnalyAllConsumpt createFromParcel(Parcel source) {
            return new AnalyAllConsumpt(source);
        }

        public AnalyAllConsumpt[] newArray(int size) {
            return new AnalyAllConsumpt[size];
        }
    };
}
