package kr.rootuser.oltu.control;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.rootuser.oltu.server.OAuth2Server;

@Controller
@RequestMapping("/oauth2")
public class OAuthController {
	
	@RequestMapping(value="/authorize",
			method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String authorize(final HttpServletRequest request,
			final HttpServletResponse res) throws IOException {
		OAuth2Server oauthServer = new OAuth2Server();
		oauthServer.authorize(request);
		return null;
	}
}
