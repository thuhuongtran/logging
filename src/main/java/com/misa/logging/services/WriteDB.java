package com.misa.logging.services;

import java.util.List;

import org.bson.Document;

import com.misa.core.pools.MongoPool;
import com.misa.logging.entity.UserAccess;
import com.mongodb.MongoException;

public class WriteDB {
	public static final int MONGODB_DUPLICATE_CODE = 11000;
	public static void writeOnDB(List<UserAccess> userAccLi) {
		for(UserAccess userAcc : userAccLi) {
			try {
				Document doc = new Document();
				doc.append("NickName", userAcc.getNickName());
				doc.append("Count", userAcc.getCount());
				doc.append("Date", userAcc.getLoginDate()+" "+userAcc.getLoginHour());
				MongoPool.log("UserLoginNumbers", doc);
			}
			catch(MongoException e) {
				if(e.getMessage().contains("E11000")) {
					continue;
				}
			}
			
		}
	}
}
