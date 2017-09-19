package com.misa.logging.services;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
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
	static List<User> tructxnLi;
	static List<User> thinhnkLi;
	static List<User> thacduLi;
	static List<User> testvnbaiLi;
	
	static List<UserAccess> tructxnAccessLi;
	static List<UserAccess> thinhnkAccessLi;
	static List<UserAccess> thacduAccessLi;
	static List<UserAccess> testvnbaiAccessLi;
	
	// return login user info
	public static void getUser() throws IOException {
		// connect to database
		PropertyConfigurator.configure("config/log4j.properties");
		MongoPool.init();

		// get user list
		Jongo jongo = new Jongo(MongoPool.getJongoDb());
		MongoCollection collection = jongo.getCollection("LoginMessageLog");
		
		//org.jongo.MongoCursor<User> userLi = collection.find("{}").as(User.class);
		org.jongo.MongoCursor<User> li = collection.find("{}").sort("{nickName:1}").as(User.class);
		users = new ArrayList<User>();
	
		while (li.hasNext()) {
			users.add(li.next());
		}
		
	}
	//divide total userlist in each list of user
	public static void divideUserList(List<User> userli) {
		tructxnLi = new ArrayList<User>();
		thinhnkLi = new ArrayList<User>();
		thacduLi = new ArrayList<User>();
		testvnbaiLi = new ArrayList<User>();
		
		//if nickname contains name then add to list, otherwise turn to other list
		for(User user : userli) {
			if(user.getNickName().contains("testvnbai")) {
				testvnbaiLi.add(user);
			}
			else if(user.getNickName().contains("thacdu")) {
				thacduLi.add(user);
			}
			else if(user.getNickName().contains("thinhnk")) {
				thinhnkLi.add(user);
			}
			else if(user.getNickName().contains("tructxn")) {
				tructxnLi.add(user);
			}
		}
	}
	// count number of login times by date
	// firstly, sort list by date
	// then record in new List with object is UserAccess
	public static void countLoginTimes(List<User> userli, List<UserAccess> userAccessLi) {
		// sort list by date
		userli.sort(new Comparator<User>() {

			public int compare(User o1, User o2) {
				return DateConvertion.timestampToDate(o1.get_id().getTimestamp())
						.compareTo(DateConvertion.timestampToDate(o2.get_id().getTimestamp()));
			}
			
		});
		UserAccess userAcc = new UserAccess();
		//count by date
		String date = DateConvertion.timestampToDate(userli.get(0).get_id().getTimestamp());
		int count =0;
		for(User user : userli) {
			// if true --> update date, count
			// add userAccess to list
			if(!DateConvertion.timestampToDate(user.get_id().getTimestamp())
					.equals(date)) {
				// add to list
				userAcc.setNickName(user.getNickName().replaceAll("[0-9]", ""));
				userAcc.setCount(count);
				userAcc.setLoginDate(date);
				userAccessLi.add(userAcc);
				// update
				userAcc = new UserAccess();
				count =1;
				date = DateConvertion.timestampToDate(user.get_id().getTimestamp());
			}
			count++;
		}
	}
	// testing getUserAccess
	public static void main(String[] args) throws IOException, ParseException {
		// record users
		getUser();
		//  divide to each user list depends on nickname
		divideUserList(users);
		for(User user: thacduLi) {
			System.out.println();
			System.out.print("username: "+user.getUserName());
			System.out.print("\tnickname: "+user.getNickName());
			System.out.print("\t login Date: "+DateConvertion.timestampToDate(user.get_id().getTimestamp()));
			System.out.print("\t timestamp: "+user.get_id().getTimestamp());
			
		}
		System.out.println();
		System.out.println("sort by date");
		// sort by date
		thacduAccessLi =  new ArrayList<UserAccess>();
		countLoginTimes(thacduLi, thacduAccessLi);
		for(User user: thacduLi) {
			System.out.println();
			System.out.print("username: "+user.getUserName());
			System.out.print("\tnickname: "+user.getNickName());
			System.out.print("\t login Date: "+DateConvertion.timestampToDate(user.get_id().getTimestamp()));
			System.out.print("\t timestamp: "+user.get_id().getTimestamp());
			
		}
		for(UserAccess user: thacduAccessLi) {
			System.out.println();
			System.out.print("\tnickname: "+user.getNickName());
			System.out.print("\t login Date: "+user.getLoginDate());
			System.out.print("\t count: "+user.getCount());
			
		}
	}
	
}
