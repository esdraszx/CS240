package URLHandlers;
import ObjectCodeDecode.Decoder;
import ObjectCodeDecode.Encoder;
import Services.LoginService;
import Response.LoginResponse;
import Request.LoginRequest;
import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;


public class LoginHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        LoginResponse myLoginResponse = new LoginResponse();

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                LoginService myLoginService = new LoginService();

                Reader reader = new InputStreamReader(exchange.getRequestBody());
                LoginRequest myLoginRequest = Decoder.decodeLoginRequest(reader);

                myLoginResponse = myLoginService.login(myLoginRequest);

                if (myLoginResponse.getSuccess()){
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    String jsonStr = Encoder.encode(myLoginResponse);
                    OutputStream respBody = exchange.getResponseBody();
                    writeString(jsonStr, respBody);

                    respBody.close();

                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    String jsonStr = new String("{\"message\" : \"" + myLoginResponse.getMessage() + "\"}");
                    OutputStream respBody = exchange.getResponseBody();
                    writeString(jsonStr, respBody);
                    respBody.close();
                }
            }
        }
        catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            String jsonStr = new String("{\"message\": \"Internal server error\"}");
            OutputStream respBody = exchange.getResponseBody();
            writeString(jsonStr, respBody);
            respBody.close();

            // Display/log the stack trace
            //e.printStackTrace();
        }
    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
