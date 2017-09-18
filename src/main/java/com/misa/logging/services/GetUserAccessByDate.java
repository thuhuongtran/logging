package com.misa.logging.services;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.PropertyConfigurator;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import com.misa.core.pools.MongoPool;
import com.misa.logging.entity.User;
import com.misa.logging.entity.UserAccess;

public class GetUserAccessByDate {
	static List<User> users;
	// create useraccess
	/*
	 * private static UserAccess newUserAccess(ResultsIterator<UserAccess> cursor) {
	 * UserAccess user = new UserAccess();
	 * 
	 * user.setUsername(cursor.iterator().next().getUsername());
	 * user.setId(cursor.iterator().next().getId());
	 * 
	 * return user;
	 * 
	 * }
	 */
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

	// testing getUserAccess
	public static void main(String[] args) throws IOException, ParseException {
		// record users
		getUser();
		int i=1;
		//System.out.print(users);
		for(User user : users) {
			System.out.print("\t "+i++);
			System.out.print("username: "+user.getUsername());
			System.out.print("\tnickname: "+user.getNickname());
			System.out.print("\tLogin Date :"+DateConvertion.timestampToDate(user.get_id().getTimestamp()));
			System.out.print("\t timestamp: "+user.get_id().getTimestamp());
			System.out.println();
		}		
		
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
