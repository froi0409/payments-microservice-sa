package com.froi.payments.bill.domain;

import com.froi.payments.common.exceptions.IllegalEnumException;

public enum BillPaidType {
    HOTEL,
    RESTAURANT;

    public static BillPaidType fromOrdinal(int ordinal) throws IllegalEnumException {
        BillPaidType[] values = BillPaidType.values();
        if (ordinal < 0 || ordinal >= values.length) {
            throw new IllegalEnumException("Invalid ordinal for RoomType: " + ordinal);
        }
        return values[ordinal];
    }
}
