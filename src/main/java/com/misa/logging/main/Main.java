package com.misa.logging.main;

import com.misa.logging.entity.UserAccess;
import com.misa.logging.services.UserAccessProcessing;

public class Main {

	public static void main(String[] args) {
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
	}

}
