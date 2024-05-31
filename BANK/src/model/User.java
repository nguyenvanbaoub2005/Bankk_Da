package model;


	import java.util.ArrayList;
	import java.util.Date;
	import java.util.List;
import java.util.Objects;

	// Model
	class User {
	    private String username;
	    private String password;

	    public User(String username, String password) {
	        this.username = username;
	        this.password = password;
	    }
	    

	    @Override
		public String toString() {
			return "User [username=" + username + ", password=" + password + "]";
		}


		@Override
		public int hashCode() {
			return Objects.hash(password, username);
		}


		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			User other = (User) obj;
			return Objects.equals(password, other.password) && Objects.equals(username, other.username);
		}


		public void setUsername(String username) {
			this.username = username;
		}


		public void setPassword(String password) {
			this.password = password;
		}


		public String getUsername() {
	        return username;
	    }

	    public String getPassword() {
	        return password;
	    }
	}


