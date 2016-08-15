package kr.rootuser.oltu.server.model;

public class Oauth2Response {
	
	private int status;
	private String body;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
}
