package URLHandlers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;

import ObjectCodeDecode.Decoder;
import Request.RegisterRequest;
import Services.LoadService;
import Response.LoadResponse;
import Request.LoadRequest;


public class LoadHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        LoadResponse myLoadResponse = new LoadResponse();

        try {

            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                LoadService myLoadService = new LoadService();

                Reader reader = new InputStreamReader(exchange.getRequestBody());
                LoadRequest myLoadRequest = Decoder.decodeLoadRequest(reader);

                myLoadResponse = myLoadService.load(myLoadRequest);


                if (myLoadResponse.getSuccess()){
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    String jsonStr = "{\"message\" :\"Successfully added " + myLoadResponse.getNumUsers() + " users, " + myLoadResponse.getNumPersons() + " persons, and " +myLoadResponse.getNumEvents() + " events to the database.\"}";
                    OutputStream respBody = exchange.getResponseBody();
                    writeString(jsonStr, respBody);

                    respBody.close();

                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    String jsonStr = new String("{\"message\" : \"" + myLoadResponse.getMessage() + "\"}");
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
