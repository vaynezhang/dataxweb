package com.alibaba.datax.plugin.reader.mongodbreader.util;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.alibaba.datax.common.exception.DataXException;
import com.alibaba.datax.common.util.Configuration;
import com.alibaba.datax.plugin.reader.mongodbreader.KeyConstant;
import com.alibaba.datax.plugin.reader.mongodbreader.MongoDBReaderErrorCode;

//import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;

/**
 * Created by jianying.wcj on 2015/3/17 0017.
 * Modified by mingyan.zc on 2016/6/13.
 */
public class MongoUtil {

    public static MongoClient initMongoClient(Configuration conf) {
        List<ServerAddress> serverAddresses = new ArrayList<>();
        List<Object> addressList = conf.getList(KeyConstant.MONGO_ADDRESS);
        if(addressList == null || addressList.size() <= 0) {
            throw DataXException.asDataXException(MongoDBReaderErrorCode.ILLEGAL_VALUE,"不合法参数");
        }
        for (Object obj : addressList) {
            String addressString = (String) obj;
            serverAddresses.add(new ServerAddress(addressString));
        }
        try {
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyToClusterSettings(builder -> builder.hosts(serverAddresses))
                    .build();
            return MongoClients.create(settings);
        } catch (NumberFormatException e) {
            throw DataXException.asDataXException(MongoDBReaderErrorCode.ILLEGAL_VALUE,"不合法参数");
        } catch (Exception e) {
            throw DataXException.asDataXException(MongoDBReaderErrorCode.UNEXCEPT_EXCEPTION,"未知异常");
        }
    }

    public static MongoClient initCredentialMongoClient(Configuration conf, String userName, String password, String database) {
        List<ServerAddress> serverAddresses = new ArrayList<>();
        List<Object> addressList = conf.getList(KeyConstant.MONGO_ADDRESS);
        if(addressList == null || addressList.size() <= 0) {
            throw DataXException.asDataXException(MongoDBReaderErrorCode.ILLEGAL_VALUE,"不合法参数");
        }
        for (Object obj : addressList) {
            String addressString = (String) obj;
            serverAddresses.add(new ServerAddress(addressString));
        }
        try {
            MongoCredential credential = MongoCredential.createCredential(userName, database, password.toCharArray());
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyToClusterSettings(builder -> builder.hosts(serverAddresses))
                    .credential(credential)
                    .build();
            return MongoClients.create(settings);
        } catch (NumberFormatException e) {
            throw DataXException.asDataXException(MongoDBReaderErrorCode.ILLEGAL_VALUE,"不合法参数");
        } catch (Exception e) {
            throw DataXException.asDataXException(MongoDBReaderErrorCode.UNEXCEPT_EXCEPTION,"未知异常");
        }
    }
    /**
     * 判断地址类型是否符合要求
     * @param addressList
     * @return
     */
    private static boolean isHostPortPattern(List<Object> addressList) {
        for(Object address : addressList) {
            String regex = "(\\S+):([0-9]+)";
            if(!((String)address).matches(regex)) {
                return false;
            }
        }
        return true;
    }
    /**
     * 转换为mongo地址协议
     * @param rawAddressList
     * @return
     */
    private static List<ServerAddress> parseServerAddress(List<Object> rawAddressList) throws UnknownHostException{
        List<ServerAddress> addressList = new ArrayList<ServerAddress>();
        for(Object address : rawAddressList) {
            String[] tempAddress = ((String)address).split(":");
            try {
                ServerAddress sa = new ServerAddress(tempAddress[0],Integer.valueOf(tempAddress[1]));
                addressList.add(sa);
            } catch (Exception e) {
                throw new UnknownHostException();
            }
        }
        return addressList;
    }
}
