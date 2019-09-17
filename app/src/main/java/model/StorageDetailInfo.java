package model;

import java.util.List;

public class StorageDetailInfo {

    /**
     * orderDetail : {"create_time":"2019-08-31 15:09:34.0","location":"佛山","pre_end_time":"2019-08-31 17:09:34.0","has_pay_fare":3,"storage_order_code":"024461509320"}
     * putRecord : []
     * overDetail : {"storage_order_code":"024461509320","over_time":54,"over_fare":81}
     */

    private OrderDetailBean orderDetail;
    private OverDetailBean overDetail;
    private List<PutRecordBean> putRecord;


    public OrderDetailBean getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderDetailBean orderDetail) {
        this.orderDetail = orderDetail;
    }

    public OverDetailBean getOverDetail() {
        return overDetail;
    }

    public void setOverDetail(OverDetailBean overDetail) {
        this.overDetail = overDetail;
    }

    public List<PutRecordBean> getPutRecord() {
        return putRecord;
    }

    public void setPutRecord(List<PutRecordBean> putRecord) {
        this.putRecord = putRecord;
    }


    public static class OrderDetailBean {
        /**
         * create_time : 2019-08-31 15:09:34.0
         * location : 佛山
         * pre_end_time : 2019-08-31 17:09:34.0
         * has_pay_fare : 3
         * storage_order_code : 024461509320
         */

        private String create_time;
        private String location;
        private String pre_end_time;
        private float has_pay_fare;
        private String storage_order_code;
        private String start_time;
        private String now_storage_code;

        public String getStart_time() {
            return start_time;
        }

        public String getNow_storage_code() {
            return now_storage_code;
        }

        public void setNow_storage_code(String now_storage_code) {
            this.now_storage_code = now_storage_code;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getPre_end_time() {
            return pre_end_time;
        }

        public void setPre_end_time(String pre_end_time) {
            this.pre_end_time = pre_end_time;
        }

        public float getHas_pay_fare() {
            return has_pay_fare;
        }

        public void setHas_pay_fare(float has_pay_fare) {
            this.has_pay_fare = has_pay_fare;
        }

        public String getStorage_order_code() {
            return storage_order_code;
        }

        public void setStorage_order_code(String storage_order_code) {
            this.storage_order_code = storage_order_code;
        }
    }

    public static class OverDetailBean {
        /**
         * storage_order_code : 024461509320
         * over_time : 54
         * over_fare : 81
         */

        private String storage_order_code;
        private float over_time;
        private float over_fare;

        public String getStorage_order_code() {
            return storage_order_code;
        }

        public void setStorage_order_code(String storage_order_code) {
            this.storage_order_code = storage_order_code;
        }

        public float getOver_time() {
            return over_time;
        }

        public void setOver_time(float over_time) {
            this.over_time = over_time;
        }

        public float getOver_fare() {
            return over_fare;
        }

        public void setOver_fare(float over_fare) {
            this.over_fare = over_fare;
        }
    }

    public static class PutRecordBean {
        /**
         * operation_time : 2019-09-03 20:46:19.0
         * operation : 打开储物柜
         */

        private String operation_time;
        private String operation;

        public String getOperation_time() {
            return operation_time;
        }

        public void setOperation_time(String operation_time) {
            this.operation_time = operation_time;
        }

        public String getOperation() {
            return operation;
        }

        public void setOperation(String operation) {
            this.operation = operation;
        }
    }
}
