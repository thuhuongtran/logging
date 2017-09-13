package com.misa.logging.test;
import com.misa.core.pools.MongoPool;
import org.apache.log4j.PropertyConfigurator;
import org.bson.Document;

import java.util.Calendar;

public class MongoConnectionTest {
    public static void main(String[] args){
        try {
            PropertyConfigurator.configure("config/log4j.properties");
            MongoPool.init();
            Document doc = new Document();
            /*
            doc.append("name", "Thu Huong");
            doc.append("school", "HUST");
            */
            MongoPool.log("StudenIntership", doc);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void calculateA1Test(){
        Calendar start = Calendar.getInstance();
        start.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        start.set(Calendar.HOUR, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);

        Calendar end = Calendar.getInstance();
        end.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        end.set(Calendar.HOUR, 23);
        end.set(Calendar.MINUTE, 59);
        end.set(Calendar.SECOND, 59);


    }
    
    public static void testMongoDB() {
    	
    }
}
