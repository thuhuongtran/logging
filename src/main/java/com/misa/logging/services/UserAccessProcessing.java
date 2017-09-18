package com.misa.logging.services;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.PropertyConfigurator;
import org.bson.Document;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import com.misa.core.pools.MongoPool;
import com.misa.logging.entity.User;
import com.misa.logging.entity.UserAccess;

public class UserAccessProcessing {
	static List<User> users;
	
	// return login user info
	public static void getUser() throws IOException {
		// connect to database
		PropertyConfigurator.configure("config/log4j.properties");
		MongoPool.init();

		// get user list
		Jongo jongo = new Jongo(MongoPool.getJongoDb());
		MongoCollection collection = jongo.getCollection("LoginMessageLog");
		
		org.jongo.MongoCursor<User> userLi = collection.find("{}").as(User.class);
		users = new ArrayList<User>();
	
		while (userLi.hasNext()) {
			users.add(userLi.next());
		}
		
	}
	// sort user list by nickname
	public static void sortUserList(String compareName, String nickName) {
		if(nickName.contains(compareName)) {
			System.out.println("true");
		}
	}
	// divide user list by date
	public static void divideByDate() {
		int i = 1;
		while(i <users.size()) {
			// if date of login user is as same as the date of user before
			String befDate = DateConvertion.timestampToDate(users.get(i-1).get_id().getTimestamp());
			System.out.println(befDate);
			String nexDate = DateConvertion.timestampToDate(users.get(i).get_id().getTimestamp());
			System.out.println(nexDate);
			if(befDate.equals(nexDate)) {
				/*
				System.out.println();
				System.out.print("username: "+users.get(i).getUserName());
				System.out.print("\tnickname: "+users.get(i).getNickName());
				System.out.print("\tLogin Date :"+nexDate);
				System.out.print("\t timestamp: "+users.get(i).get_id().getTimestamp());
				*/
				// write data in a date in one document
				Document doc = new Document();
				doc.append("nickName", users.get(i).);
			}
			else {
				System.out.println("out");
				break;
			}
				
			i++;
		}
	}
	// testing getUserAccess
	public static void main(String[] args) throws IOException, ParseException {
		// record users
		getUser();
		//divideByDate();
		sortUserList("thacdu", "thacdu991");
	}
	public static void countUserByDate(List<User> userLi) {
		for(int i=0;i<userLi.size();i++) {
			User user = userLi.get(i);
			if(i==0) {
				
			}
			else if(i==userLi.size()-1) {
				
			}
			else {
				
			}
		}
		
	}

}
