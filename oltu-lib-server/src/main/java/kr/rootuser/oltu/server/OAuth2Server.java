package kr.rootuser.oltu.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.rootuser.oltu.server.model.Oauth2Response;

public class OAuth2Server{
	private Logger LOG = LoggerFactory.getLogger(OAuth2Server.class);
	private Oauth2Response oauth2Response;
	
	public OAuth2Server(){
		oauth2Response = new Oauth2Response();
	}
	
	public Oauth2Response authorize(HttpServletRequest request) throws OAuthSystemException{
		OAuthResponse response = null;
		OAuthAuthzRequest oauthRequest = null;
		try {
			oauthRequest = new OAuthAuthzRequest(request);
			
		} catch (OAuthProblemException e) {
			LOG.warn("Error to create response - 400");
			response = OAuthResponse
					.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
					.error(e).buildJSONMessage();
			return makeResponse(response);
		}
		try {
			if (!ResponseType.CODE.toString().equals(oauthRequest.getResponseType())){
				response = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
					.setError(OAuthError.CodeResponse.UNSUPPORTED_RESPONSE_TYPE)
					.setErrorDescription("The response type must be '"
						+ ResponseType.CODE.toString()
						+ "' but was instead: " + oauthRequest.getResponseType())
					.setState(oauthRequest.getState())
					.buildJSONMessage();
				return makeResponse(response);
			}
		} catch (IllegalArgumentException e) { 
			response = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
					.setError(OAuthError.CodeResponse.UNSUPPORTED_RESPONSE_TYPE)
					.setErrorDescription("The response type is unknown: "
							+ oauthRequest.getResponseType())
					.setState(oauthRequest.getState())
					.buildJSONMessage();
			return makeResponse(response);
		}
		if(oauthRequest.getRedirectURI() == null) {
			response = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
					.setError(OAuthError.CodeResponse.INVALID_REQUEST)
					.setErrorDescription("A redirect URI must be given.")
					.setState(oauthRequest.getState())
					.buildJSONMessage();
			return makeResponse(response);
		}
		
		return null;
	}

	private Oauth2Response makeResponse(OAuthResponse response) {
		oauth2Response.setStatus(response.getResponseStatus());
		oauth2Response.setBody(response.getBody());
		return oauth2Response;
	}
}
