package org.lovi.psdemo.session;

import java.util.ArrayList;

import org.lovi.psdemo.models.Menu;
import org.lovi.psdemo.models.User;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope( value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserSession {

	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
