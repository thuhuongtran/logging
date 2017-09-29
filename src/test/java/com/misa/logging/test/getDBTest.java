package com.misa.logging.test;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.PropertyConfigurator;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import com.misa.core.pools.MongoPool;
import com.misa.logging.entity.User;

public class getDBTest {

	/*
	 * get the number amount of login persons in exact time per day
	 * limit timestamp
	 * 
<<<<<<< HEAD
	 * get login access users by input date --> then sort by username --> count number of access
	 * write to document
	 * 
	 * 
	 * plan B: get all user list then process in model
	 * chia list user thanh cac list nho boi date 
	 * then count user 
	 * maybe get all users
	 * write to document
	 * start from 31-8
	 * 
	 */
	static List<User> loginUsers;

	public static void main(String[] args) throws ParseException {
		loginUsers = new ArrayList<User>();
		try {
			PropertyConfigurator.configure("config/log4j.properties");
			MongoPool.init();

			// MongoDatabase db = MongoPool.getDB();

			Jongo jongo = new Jongo(MongoPool.getJongoDb());
			MongoCollection Users = jongo.getCollection("LoginMessageLog");
			/*
			 * long a = loginPersion.find().as(LoginPersonInfo.class).count();
			 * System.out.println(a);
			 * 
			 */
			org.jongo.MongoCursor<User> users = Users.find("{username: 'tructxn'}")
					.as(User.class);

			while (users.hasNext()) {

				loginUsers.add(users.next());

			}
			
			System.out.println(loginUsers.size());
			/*
			long timestamp = loginUsers.get(1).get_id().getTimestamp();
			//long timestamp = loginUsers.get(1).getUpdateTime();
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(timestamp);
			s
			System.out.println("RESULT: "  + format.format(cal.getTime()));
			*/
			/*
			long timeStamp = loginUsers.get(1).get_id().getTimestamp();
			java.sql.Timestamp stamp = new java.sql.Timestamp(timeStamp);
			DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date date = new Date(stamp.getTime());
			System.out.println(format.format(date));
			*/
			
			
			/*
			System.out.println();
			System.out.println(loginUsers.get(0).getUpdateTime());
			long epoch = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse("01/01/1970 01:00:00").getTime() / 1000;
			*/
			long timeStamp = loginUsers.get(1).getUpdateTime();
			System.out.println(timeStamp);
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(timeStamp*1000);
			
			DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			System.out.println(format.format(calendar.getTime()));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		// testing get user --good
		List<User> userLi = DataProcess.getUser();
		for(User user : userLi) {
			System.out.println();
			System.out.print("\t"+DateConvertion.timestampToDate(user.get_id().getTimestamp()));
			System.out.print("\t"+user.getNickName());
		}
		*/
		/*
		// testing divided list of user by date --good
		List<User> dividedUserLi = DataProcess.divideUserListbyDate(DataProcess.getUser());
		for(User user : dividedUserLi) {
			System.out.println();
			if(user.get_id()==null)
				System.out.print("\t login date null");
			else
				System.out.print("\t"+DateConvertion.timestampToDate(user.get_id().getTimestamp()));
			System.out.print("\t"+user.getNickName());
		}
		*/
		/*
		// testing divided by nickName --good
		List<User> dividedByNameUserLi = DataProcess.sortByNickName(DataProcess.getUser().subList(4, 48));
		
		for(User user : dividedByNameUserLi) {
			System.out.println();
			if(user.get_id()==null)
				System.out.print("\t login date null");
			else
				System.out.print("\t"+DateConvertion.timestampToDate(user.get_id().getTimestamp()));
			System.out.print("\t"+user.getNickName());
		}
		*/
		/*
		try {
			
			
            UserAccessProcessing usrAccProcess = new UserAccessProcessing();
           // get list of all user
            usrAccProcess.getUser();
            // divide to list of each user sorted by nickName
            usrAccProcess.divideUserList(usrAccProcess.users);
            
            // counting number of logins of each user
            // then adding in a list
            // then sorting by date
            // finally write on database
            usrAccProcess.setUserAccessLi();
            usrAccProcess.writeData(usrAccProcess.userAccLi);
            
            for(UserAccess user: usrAccProcess.userAccLi) {
    			System.out.println();
    			System.out.print("\tnickname: "+user.getNickName());
    			System.out.print("\t count: "+user.getCount());
    			System.out.print("\t login Date: "+user.getLoginDate());
    		}
            
         }catch (Exception e){
            e.printStackTrace();
        }
        */
		

	}

}
