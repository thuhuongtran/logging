package com.misa.logging.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.PropertyConfigurator;
import org.bson.Document;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import com.misa.core.pools.MongoPool;
import com.misa.logging.entity.User;
import com.misa.logging.entity.UserAccess;
import com.misa.logging.services.DateConvertion;

public class UserAccessProcessing {
	public static List<User> users;
	public static List<User> tructxnLi;
	public static List<User> thinhnkLi;
	public static List<User> thacduLi;
	public static List<User> testvnbaiLi;
	
	public static List<UserAccess> tructxnAccessLi;
	public static List<UserAccess> thinhnkAccessLi;
	public static List<UserAccess> thacduAccessLi;
	public static List<UserAccess> testvnbaiAccessLi;
	
	public static List<UserAccess> userAccLi;
	
	
	public UserAccessProcessing() {
		super();
		
		users = new ArrayList<User>();
		
		tructxnLi = new ArrayList<User>();
		thinhnkLi = new ArrayList<User>();
		thacduLi = new ArrayList<User>();
		testvnbaiLi = new ArrayList<User>();
		
		tructxnAccessLi = new ArrayList<UserAccess>();
		thinhnkAccessLi = new ArrayList<UserAccess>();
		thacduAccessLi = new ArrayList<UserAccess>();
		testvnbaiAccessLi = new ArrayList<UserAccess>();
		
		userAccLi = new ArrayList<UserAccess>();
		
	}
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
		//users = new ArrayList<User>();
	
		while (li.hasNext()) {
			users.add(li.next());
		}
		
	}
	//divide total userlist in each list of user
	public static void divideUserList(List<User> userli) {
	
		
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
	// sort list of userAcc by date
	public static void sortUsrAccByDate(List<UserAccess> userAccLi) {
		userAccLi.sort(new Comparator<UserAccess>() {

			public int compare(UserAccess o1, UserAccess o2) {
				return o1.getLoginDate().compareTo(o2.getLoginDate());
			}
			
		});
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
				count =0;
				date = DateConvertion.timestampToDate(user.get_id().getTimestamp());
			}
			else if(user.equals(userli.get(userli.size()-1))) {
				// add to list
				userAcc.setNickName(user.getNickName().replaceAll("[0-9]", ""));
				userAcc.setCount(count+1);
				userAcc.setLoginDate(date);
				userAccessLi.add(userAcc);
			}
			count++;
		}
	}
	// count login times of each user
	// add all in a list
	public static void setUserAccessLi() {
		//counting number of logins
		countLoginTimes(tructxnLi, tructxnAccessLi);
		countLoginTimes(thinhnkLi, thinhnkAccessLi);
		countLoginTimes(thacduLi, thacduAccessLi);
		countLoginTimes(testvnbaiLi, testvnbaiAccessLi);
		
		// add divided list in one list
		userAccLi.addAll(tructxnAccessLi);
		userAccLi.addAll(thinhnkAccessLi);
		userAccLi.addAll(thacduAccessLi);
		userAccLi.addAll(testvnbaiAccessLi);
		
		// sort userAccLi by date
		sortUsrAccByDate(userAccLi);
	}
	//write data of user login times in document
	public static void writeData(List<UserAccess> usrAccLi) {
		//Document doc = new Document();
		//String date = usrAccLi.get(0).getLoginDate();
		// if turn to another date then jump to next document
		for(UserAccess user : usrAccLi) {
			// write document 
			
			Document doc = new Document();
			doc.append(user.getNickName(), user.getCount());
			doc.append("Date", user.getLoginDate());
			
			MongoPool.log("UserLoginNumbers", doc);
		}
		
	}
	//write field count
	public static void writeFieldCount(UserAccess userAcc, Document doc) {
		if(userAcc.getNickName().contains("tructxn")) {
			System.out.println("truc "+userAcc.getCount());
			doc.append("tructxnNum", userAcc.getCount());
		}
		else if(userAcc.getNickName().contains("thacdu")) {
			System.out.println("thac "+userAcc.getCount());
			doc.append("thacduNum", userAcc.getCount());
		}
		else if(userAcc.getNickName().contains("testvnbai")) {
			System.out.println("test "+userAcc.getCount());
			doc.append("testvnbaiNum", userAcc.getCount());
		}
		else if(userAcc.getNickName().contains("thinhnk")) {
			System.out.println("thinh "+userAcc.getCount());
			doc.append("thinhnk", userAcc.getCount());
		}

		
	}
	
	
}
