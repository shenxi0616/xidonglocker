package view;


//用于连接接口
public class HttpRequest {
    public String baseUrl = "http://192.168.43.6:8080/";
    public String baseUrlLogin = baseUrl+"locker/creatInfoCode/";
    public String baseUrlLoginByCode = baseUrl+"locker/loginByCode/";
    public String baseUrlGetNowStorageStatus = baseUrl+"locker/getNowStorageStatus";
    public String baseUrlAddUseRecord = baseUrl+"locker/addUseRecord";
    public String baseUrlGetSmallOrderList = baseUrl+"locker/getSmallOrderList/";
    public String baseUrlGetStorageDetail = baseUrl+"locker/getStorageDetail";
    public String baseUrlEndUseStorage = baseUrl+"locker/endUseStorage";
    public String basePayStoreLockerOrder = baseUrl+"locker/payStoreLockerOrder";
    public String baseUrlUpdateUser = baseUrl+"locker/updateUser";
    public String baseUrlCreateStoreLockerOrder = baseUrl+"locker/createStoreLockerOrder";
    public String baseUrlPayStoreLockerOrder = baseUrl+"locker/payStoreLockerOrder";
    public String baseUrlDeleteStoreLockerOrder = baseUrl+"locker/deleteStoreLockerOrder";


}
