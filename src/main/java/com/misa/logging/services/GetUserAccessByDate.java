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
	public static List<User> getUser() throws IOException {
		// connect to database
		PropertyConfigurator.configure("config/log4j.properties");
		MongoPool.init();

		// get user list
		Jongo jongo = new Jongo(MongoPool.getJongoDb());
		MongoCollection collection = jongo.getCollection("LoginMessageLog");
		
		org.jongo.MongoCursor<User> userLi = collection.find("{}").as(User.class);
		List<User> users = new ArrayList<User>();
	
		while (userLi.hasNext()) {
			users.add(userLi.next());
		}
		return users;
	}

	// testing getUserAccess
	public static void main(String[] args) throws IOException, ParseException {
		List<User> users = getUser();
		System.out.println(users.size());
		System.out.println(users.get(0).getNickname());
		
		String date = DateConvertion.timestampToDate(users.get(0).get_id().getTimestamp());
		// get timestamp of date
		long beforedate = users.get(0).get_id().getTimestamp();
		System.out.println(beforedate);
		System.out.println("date: "+date);
		
		System.out.println("the day before: "+DateConvertion.getDayAfter((beforedate)));
		
		
	}

}
