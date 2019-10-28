package model;

import java.util.List;

public class NearlyStorage {

    /**
     * code : 0
     * msg : 获取到附近的储物柜
     * data : [{"storage_code":"20190904583283","put_in_time":"2019-10-26 10:30:54.0","province":"广东省","city":"佛山市","concrete_location":"狮山","longitude":113.034892,"latitude":23.147792,"dimensions":null,"locat":null},{"storage_code":"20190904583283","put_in_time":"2019-10-26 10:30:54.0","province":"广东省","city":"佛山市","concrete_location":"狮山","longitude":113.034892,"latitude":23.147792,"dimensions":null,"locat":null},{"storage_code":"20190904583283","put_in_time":"2019-10-26 10:30:54.0","province":"广东省","city":"佛山市","concrete_location":"狮山","longitude":113.034892,"latitude":23.147792,"dimensions":null,"locat":null},{"storage_code":"20190904583283","put_in_time":"2019-10-26 10:30:54.0","province":"广东省","city":"佛山市","concrete_location":"狮山","longitude":113.034892,"latitude":23.147792,"dimensions":null,"locat":null},{"storage_code":"20190904583283","put_in_time":"2019-10-26 10:30:54.0","province":"广东省","city":"佛山市","concrete_location":"狮山","longitude":113.034892,"latitude":23.147792,"dimensions":null,"locat":null},{"storage_code":"20190904583283","put_in_time":"2019-10-26 10:30:54.0","province":"广东省","city":"佛山市","concrete_location":"狮山","longitude":113.034892,"latitude":23.147792,"dimensions":null,"locat":null},{"storage_code":"20190904583283","put_in_time":"2019-10-26 10:30:54.0","province":"广东省","city":"佛山市","concrete_location":"狮山","longitude":113.034892,"latitude":23.147792,"dimensions":null,"locat":null},{"storage_code":"20190904583283","put_in_time":"2019-10-26 10:30:54.0","province":"广东省","city":"佛山市","concrete_location":"狮山","longitude":113.034892,"latitude":23.147792,"dimensions":null,"locat":null},{"storage_code":"20190904583283","put_in_time":"2019-10-26 10:30:54.0","province":"广东省","city":"佛山市","concrete_location":"狮山","longitude":113.034892,"latitude":23.147792,"dimensions":null,"locat":null}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * storage_code : 20190904583283
         * put_in_time : 2019-10-26 10:30:54.0
         * province : 广东省
         * city : 佛山市
         * concrete_location : 狮山
         * longitude : 113.034892
         * latitude : 23.147792
         * dimensions : null
         * locat : null
         */

        private String storage_code;
        private String put_in_time;
        private String province;
        private String city;
        private String concrete_location;
        private double longitude;
        private double latitude;
        private Object dimensions;
        private Object locat;

        public String getStorage_code() {
            return storage_code;
        }

        public void setStorage_code(String storage_code) {
            this.storage_code = storage_code;
        }

        public String getPut_in_time() {
            return put_in_time;
        }

        public void setPut_in_time(String put_in_time) {
            this.put_in_time = put_in_time;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getConcrete_location() {
            return concrete_location;
        }

        public void setConcrete_location(String concrete_location) {
            this.concrete_location = concrete_location;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public Object getDimensions() {
            return dimensions;
        }

        public void setDimensions(Object dimensions) {
            this.dimensions = dimensions;
        }

        public Object getLocat() {
            return locat;
        }

        public void setLocat(Object locat) {
            this.locat = locat;
        }
    }
}
