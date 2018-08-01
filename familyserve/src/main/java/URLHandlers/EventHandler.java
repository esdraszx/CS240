package URLHandlers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

import ObjectCodeDecode.Encoder;
import Response.EventGetAllResponse;
import Services.EventGetAllService;
import Services.EventIDService;
import Response.EventIDResponse;


public class EventHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        EventIDResponse myEventIDResponse = new EventIDResponse();
        EventGetAllResponse myEventGetAllResponse = new EventGetAllResponse();

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {
                if(exchange.getRequestHeaders().containsKey("Authorization")){
                    String authToken = exchange.getRequestHeaders().getFirst("Authorization");

                    String requestedURL = exchange.getRequestURI().toString();
                    StringBuilder url = new StringBuilder(requestedURL);
                    url.deleteCharAt(0); //gets rid of the first "/".

                    String[] arguments = url.toString().split("/");

                    if (arguments.length > 2 || arguments.length < 1){
                        myEventIDResponse.setSuccess(false);
                        myEventIDResponse.setMessage("Invalid number of arguments");
                        myEventGetAllResponse.setSuccess(false);
                        myEventGetAllResponse.setMessage("Bad Request");

                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        String jsonStr = new String("{\"message\" : \"" + myEventIDResponse.getMessage() + "\"}");
                        OutputStream respBody = exchange.getResponseBody();
                        writeString(jsonStr, respBody);
                        respBody.close();


                    } else if (arguments.length == 2) { //has a personID
                        EventIDService myIDService = new EventIDService();
                        myEventIDResponse = myIDService.eventID(arguments[1], authToken);

                        if (myEventIDResponse.getSuccess()){
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                            String jsonStr = Encoder.encode(myEventIDResponse);

                            OutputStream respBody = exchange.getResponseBody();
                            writeString(jsonStr, respBody);

                            respBody.close();
                        } else {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                            String jsonStr = new String("{\"message\" : \"" + myEventIDResponse.getMessage() + "\"}");
                            OutputStream respBody = exchange.getResponseBody();
                            writeString(jsonStr, respBody);
                            respBody.close();
                        }
                    } else if (arguments.length == 1){
                        EventGetAllService myEventGetAllService = new EventGetAllService();
                        EventGetAllResponse out = myEventGetAllService.eventGetAll(authToken);
                        myEventGetAllResponse.setData(out.getData());
                        myEventGetAllResponse.setSuccess(out.getSuccess());

                        if (myEventGetAllResponse.getSuccess()){
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                            String jsonStr = Encoder.encode(myEventGetAllResponse);
                            OutputStream respBody = exchange.getResponseBody();
                            writeString(jsonStr, respBody);
                            respBody.close();
                        } else {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                            String jsonStr = new String("{\"message\" : \"" + myEventGetAllResponse.getMessage() + "\"}");
                            OutputStream respBody = exchange.getResponseBody();
                            writeString(jsonStr, respBody);
                            respBody.close();
                        }
                    }


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
