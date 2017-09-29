package com.misa.logging.services;

import java.util.List;

import org.bson.Document;

import com.misa.core.pools.MongoPool;
import com.misa.logging.entity.UserAccess;

public class WriteDB {
	public static void writeOnDB(List<UserAccess> userAccLi) {
		for(UserAccess userAcc : userAccLi) {
			Document doc = new Document();
			doc.append("NickName", userAcc.getNickName());
			doc.append("Count", userAcc.getCount());
			doc.append("Date", userAcc.getLoginDate());
			doc.append("Time", userAcc.getLoginHour());
			
			MongoPool.log("UserLoginNumbers", doc);
		}
	}
}
