package view;


//用于连接接口
public class HttpRequest {
    public String baseUrl = "http://172.20.10.5:8080/";
    public String baseUrlLogin = baseUrl+"locker/creatInfoCode/";
    public String baseUrlLoginByCode = baseUrl+"locker/loginByCode/";
    public String baseUrlGetNowStorageStatus = baseUrl+"locker/getNowStorageStatus";
    public String baseUrlAddUseRecord = baseUrl+"locker/addUseRecord";
    public String baseUrlGetSmallOrderList = baseUrl+"locker/getSmallOrderList/";
    public String baseUrlGetStorageDetail = baseUrl+"locker/getStorageDetail";
    public String baseUrlEndUseStorage = baseUrl+"locker/endUseStorage";
    public String basePayStoreLockerOrder = baseUrl+"locker/payStoreLockerOrder";
    public String baseUrlUpdateUser = baseUrl+"locker/updateUser";
    public String baseUrlCreateStoreLockerOrder = baseUrl+"locker/createStoreLockerOrder";//生成订单接口
    public String baseUrlPayStoreLockerOrder = baseUrl+"locker/payStoreLockerOrder";//支付订单接口
    public String baseUrlDeleteStoreLockerOrder = baseUrl+"locker/deleteStoreLockerOrder";
    public String baseUrlGetNearbyStorage = baseUrl+"locker/getNearbyStorage";//调用附近的储物柜信息接口

}
