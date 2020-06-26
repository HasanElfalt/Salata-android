package com.ar.salata.repositories.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class OrderUnit extends RealmObject implements Parcelable {
    public static final Creator<OrderUnit> CREATOR = new Creator<OrderUnit>() {
        @Override
        public OrderUnit createFromParcel(Parcel in) {
            return new OrderUnit(in);
        }

        @Override
        public OrderUnit[] newArray(int size) {
            return new OrderUnit[size];
        }
    };
    @SerializedName("order_id")
    private int orderId;
    @SerializedName("amount_price")
    private double amountPrice;
    @SerializedName("product_id")
    private int productId;
    @SerializedName("product_name")
    private String productName;
    @SerializedName("unit_name")
    private String productUnitName;
    private String productImage;
    @SerializedName("unit_price")
    private Double productPrice;
    @SerializedName("units_count")
    private double count;

    public OrderUnit() {
    }

    public OrderUnit(StockProduct product, double count) {
        this.productId = product.getId();
        this.productPrice = product.getPrice();
        this.count = count;
        this.productName = product.getProductName();
        this.productUnitName = product.getUnitName();
        this.productImage = product.getInvoiceImage();
    }

    protected OrderUnit(Parcel in) {
        productId = in.readInt();
        productName = in.readString();
        productUnitName = in.readString();
        productImage = in.readString();
        if (in.readByte() == 0) {
            productPrice = null;
        } else {
            productPrice = in.readDouble();
        }
        count = in.readDouble();
        orderId = in.readInt();
        amountPrice = in.readDouble();
    }

    public int getProductId() {
        return productId;
    }

    public void setProduct(int productId) {
        this.productId = productId;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductUnitName() {
        return productUnitName;
    }

    public void setProductUnitName(String productUnitName) {
        this.productUnitName = productUnitName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(productId);
        dest.writeString(productName);
        dest.writeString(productUnitName);
        dest.writeString(productImage);
        if (productPrice == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(productPrice);
        }
        dest.writeDouble(count);
        dest.writeInt(orderId);
        dest.writeDouble(amountPrice);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderUnit)) return false;
        OrderUnit unit = (OrderUnit) o;
        return getProductId() == unit.getProductId() &&
                Double.compare(unit.getCount(), getCount()) == 0 &&
                getProductName().equals(unit.getProductName()) &&
                getProductUnitName().equals(unit.getProductUnitName());
    }

//    @Override
//    public int hashCode() {
//        return Objects.hash(orderId, amountPrice, getProductId(), getProductName(), getProductUnitName(), getCount());
//    }
}
