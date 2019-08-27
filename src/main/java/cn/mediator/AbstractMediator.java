package cn.mediator;

import lombok.Data;

/**
 * Created by xiaoni on 2019/8/27.
 * 抽象中介者
 */
@Data
public abstract class AbstractMediator {
    protected MysqlDatabase mysqlDatabase;
    protected RedisDatabase redisDatabase;
    protected EsDatabase esDatabase;

    public abstract void sync(String databaseName, String data);

}
