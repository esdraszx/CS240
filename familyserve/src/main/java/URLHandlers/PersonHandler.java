package URLHandlers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

import DataAccessObjects.Database;
import ObjectCodeDecode.Encoder;
import Services.PersonIDService;
import Response.PersonIDResponse;
import Services.PersonGetAllService;
import Response.PersonGetAllResponse;



public class PersonHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        PersonIDResponse myPersonIDResponse = new PersonIDResponse();
        PersonGetAllResponse myPersonGetAllResponse = new PersonGetAllResponse();

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {
                if(exchange.getRequestHeaders().containsKey("Authorization")){
                    String authToken = exchange.getRequestHeaders().getFirst("Authorization");


                    String requestedURL = exchange.getRequestURI().toString();
                    StringBuilder url = new StringBuilder(requestedURL);
                    url.deleteCharAt(0); //gets rid of the first "/".

                    String[] arguments = url.toString().split("/");

                    if (arguments.length > 2 || arguments.length < 1){
                        myPersonIDResponse.setSuccess(false);
                        myPersonIDResponse.setMessage("Invalid number of arguments");

                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        String jsonStr = new String("{\"message\" : \"" + myPersonIDResponse.getMessage() + "\"}");
                        OutputStream respBody = exchange.getResponseBody();
                        writeString(jsonStr, respBody);
                        respBody.close();


                    } else if (arguments.length == 2) { //has a personID
                        PersonIDService myIDService = new PersonIDService();
                        myPersonIDResponse = myIDService.personID(arguments[1], authToken);

                        if (myPersonIDResponse.getSuccess()){
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                            String jsonStr = Encoder.encode(myPersonIDResponse);

                            OutputStream respBody = exchange.getResponseBody();
                            writeString(jsonStr, respBody);

                            respBody.close();
                        } else {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                            String jsonStr = new String("{\"message\" : \"" + myPersonIDResponse.getMessage() + "\"}");
                            OutputStream respBody = exchange.getResponseBody();
                            writeString(jsonStr, respBody);
                            respBody.close();
                        }
                    } else if (arguments.length == 1){
                        PersonGetAllService myPersonGetAllService = new PersonGetAllService();
                        PersonGetAllResponse out = myPersonGetAllService.personGetAll(authToken);
                        if (!out.getSuccess()) {
                        throw new Database.DatabaseException(out.getMessage());
                        }
                        myPersonGetAllResponse.setArray(out.getData());
                        myPersonGetAllResponse.setSuccess(out.getSuccess());


                        if (myPersonGetAllResponse.getSuccess()){
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                            String jsonStr = Encoder.encode(myPersonGetAllResponse);
                            OutputStream respBody = exchange.getResponseBody();
                            writeString(jsonStr, respBody);
                            respBody.close();
                        } else {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                            String jsonStr = new String("{\"message\" : \"" + myPersonGetAllResponse.getMessage() + "\"}");
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
        catch (Database.DatabaseException e){
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            String jsonStr = new String("{\"message\" : \"" + e.getMessage() + "\"}");
            OutputStream respBody = exchange.getResponseBody();
            writeString(jsonStr, respBody);
            respBody.close();
        }
    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }


}
