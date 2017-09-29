package com.misa.logging.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.PropertyConfigurator;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import com.misa.core.pools.MongoPool;
import com.misa.logging.entity.User;
import com.misa.logging.entity.UserAccess;

public class DataProcess {
	// return login user info which is sorted by date
	public static List<User> getUser() throws IOException {
		List<User> userLi = new ArrayList<User>();
		// connect to database

		PropertyConfigurator.configure("config/log4j.properties");
		MongoPool.init();

		// get user list
		Jongo jongo = new Jongo(MongoPool.getJongoDb());
		MongoCollection collection = jongo.getCollection("LoginMessageLog");

		// org.jongo.MongoCursor<User> userLi = collection.find("{}").as(User.class);
		org.jongo.MongoCursor<User> li = collection.find("{}").sort("{nickName:1}").as(User.class);
		// users = new ArrayList<User>();

		while (li.hasNext()) {
			userLi.add(li.next());
		}
		
		// sort userLi by date
		sortUserListByDate(userLi);
		
		return userLi;
	}
	/*
	 * sorting userlist by date
	 * */
	public static void sortUserListByDate(List<User> userLi) {
		userLi.sort(new Comparator<User>() {

			public int compare(User o1, User o2) {
				return DateConvertion.timestampToDate(o2.get_id().getTimestamp())
						.compareTo(DateConvertion.timestampToDate(o1.get_id().getTimestamp()));
			}
		});
	}
	
	/*
	 * divide userLi into each small lists depend on date which are separated by
	 * each object that has nickname=""
	 **/
	public static List<User> divideUserListbyDate(List<User> userLi) {
		List<User> dividedUserLi = new ArrayList<User>();

		String date = DateConvertion.timestampToDate(userLi.get(0).get_id().getTimestamp());

		for (User user : userLi) {

			// if turn to an other date then add new object which has nickName = ""
			if (!DateConvertion.timestampToDate(user.get_id().getTimestamp()).equals(date)) {
				User newUser = new User();
				newUser.setNickName("");
				dividedUserLi.add(newUser);

			}
			// update date
			dividedUserLi.add(user);
			date = DateConvertion.timestampToDate(user.get_id().getTimestamp());
		}
		// if list is over then add one more object which has nickName =""
		User newUser = new User();
		newUser.setNickName("");
		dividedUserLi.add(newUser);
		return dividedUserLi;
	}

	/*
	 * sort list of user by nickName
	 * then divide list by nickName separated by object has nickname = ""
	 */
	public static List<User> sortByNickName(List<User> smallUserLi) {
		List<User> dividedSmallUserLi = new ArrayList<User>();
		smallUserLi.sort(new Comparator<User>() {

			public int compare(User o1, User o2) {
				return o1.getNickName().compareTo(o2.getNickName());
			}
		});
		// divide by object which has nickname = ""
		String compareNickname = smallUserLi.get(0).getNickName();
		for(User user : smallUserLi) {
			if(!user.getNickName().equals(compareNickname)) {
				User newuser = new User();
				newuser.setNickName("");
				dividedSmallUserLi.add(newuser);
				
			}
			// update compareNickName
			compareNickname = user.getNickName();
			dividedSmallUserLi.add(user);
		}
		// if list is over then add one more object which has nickname =""
		User newuser = new User();
		newuser.setNickName("");
		dividedSmallUserLi.add(newuser);
		return dividedSmallUserLi;
	}
	
	/*
	 * counting user logging times
	 * params: list must be sorted by nickName and separated by object that has nickname=""
	 * */
	public static List<UserAccess> countLoggingTimes(List<User> smallUserLi){
		List<UserAccess> userAccLi = new ArrayList<UserAccess>();
		int count =0;
		for(int i = 0; i<smallUserLi.size();i++) {
			if(smallUserLi.get(i).getNickName().equals("")) {
				// add userAccess in list of UserAccess
				UserAccess userAcc = new UserAccess();
				userAcc.set_id(smallUserLi.get(i-1).get_id());
				userAcc.setNickName(smallUserLi.get(i-1).getNickName());
				userAcc.setCount(count);
				userAcc.setLoginDate(DateConvertion.timestampToDate(smallUserLi.get(i-1).get_id().getTimestamp()));
				userAcc.setLoginHour(DateConvertion.timestampToHour(smallUserLi.get(i-1).get_id().getTimestamp()));
				userAccLi.add(userAcc);
				
				// set count = 0
				count = 0;
				continue;
			}
			count ++;
		}
		return userAccLi;
	}
	
	/*
	 * get all divided small lists of user
	 * count user logging times which has param as above small list of user 
	 *  add all small list of userAcc in an other list
	 * */
	public static List<UserAccess> getUserAccLi(List<User> userLi){
		// divide list of user firstly
		List<User> dividedUserLi = divideUserListbyDate(userLi);
		List<User> smallUserLi = new ArrayList<User>();
		List<UserAccess> userAccLi = new ArrayList<UserAccess>();
		int startPoint = 0;
		int endPoint = 0;
		for(User user : dividedUserLi) {
			if(user.getNickName().equals("")) {
				// get small list of user
				endPoint = dividedUserLi.indexOf(user);
				smallUserLi = dividedUserLi.subList(startPoint, endPoint);
				// get list of userAccess in date
				// add all into userAccLi
				userAccLi.addAll(countLoggingTimes(sortByNickName(smallUserLi)));
				// update start point
				startPoint = endPoint +1;
			}
		}
		return userAccLi;
	}
	
}
