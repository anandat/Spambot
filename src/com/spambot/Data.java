package com.spambot;

public class Data
{
  private String message;
 /* private String authorEmail;
  private boolean status;
  private int age;
*/
  public Data()
  {}

  public Data( String message, String authorEmail )
  {
    this.message = message;
 //   this.authorEmail = authorEmail;
  }
  public Data( String message )
  {
    this.message = message;
  /*  this.authorEmail = authorEmail;
    this.age= age;
    this.status = status;
  */}
  /*public boolean isStatus() {
	return status;
}

public void setStatus(boolean status) {
	this.status = status;
}

public int getAge() {
	return age;
}

public void setAge(int age) {
	this.age = age;
}*/

public String getMessage()
  {
    return message;
  }

  public void setMessage( String message )
  {
    this.message = message;
  }

  /*public String getAuthorEmail()
  {
    return authorEmail;
  }

  public void setAuthorEmail( String authorEmail )
  {
    this.authorEmail = authorEmail;
  }*/
  
};