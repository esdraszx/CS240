package URLHandlers;
import ObjectCodeDecode.Decoder;
import ObjectCodeDecode.Encoder;
import Services.RegisterService;
import Response.RegisterResponse;
import Request.RegisterRequest;
import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;


public class RegisterHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        RegisterResponse myResponse = new RegisterResponse();

        try {

            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                    RegisterService myRegisterService = new RegisterService();

                        Reader reader = new InputStreamReader(exchange.getRequestBody());
                        RegisterRequest myRequest = Decoder.decodeRegisterRequest(reader);

                        myResponse = myRegisterService.register(myRequest);


                    if (myResponse.getSuccess()){
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        String jsonStr = Encoder.encode(myResponse);
                        OutputStream respBody = exchange.getResponseBody();
                        writeString(jsonStr, respBody);
                        respBody.close();

                    } else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        String jsonStr = new String("{\"message\" : \"" + myResponse.getMessage() + "\"}");
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
