package proto1;

import javafx.scene.control.TextField;

public class User {
	 String firstName;
	 String lastName;
	 String userName;
	 String email;
	 String password;

	 public User () {}
	 
	 public User (String firstName, String lastName, String userName, String email, String password) {
		 this.firstName = firstName;
		 this.lastName = lastName;
		 this.userName = userName;
		 this.email = firstName;
		 this.password = password;
	 }
	 
}
