package com.misa.logging.main;

import java.io.IOException;
import java.util.List;
import java.util.TimerTask;

import com.misa.logging.entity.UserAccess;
import com.misa.logging.services.DataProcess;
import com.misa.logging.services.WriteDB;

public class Task extends TimerTask{

	@Override
	public void run() {
		try {
			// get processed list then write on db
			List<UserAccess> userAccLi = DataProcess.getUserAccLi(DataProcess.getUser());
			WriteDB.writeOnDB(userAccLi);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
