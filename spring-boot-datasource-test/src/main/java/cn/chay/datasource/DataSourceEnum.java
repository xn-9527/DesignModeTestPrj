package cn.chay.datasource;

/**
 * @author Chay
 * @date 2020/4/7 14:11
 */
public enum DataSourceEnum {
    DB1("db1"),DB2("db2");

    private String value;
    DataSourceEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
