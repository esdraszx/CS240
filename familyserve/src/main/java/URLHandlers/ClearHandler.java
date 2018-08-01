package URLHandlers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

import ObjectCodeDecode.Encoder;
import Services.ClearService;
import Response.ClearResponse;



public class ClearHandler implements HttpHandler {


    public ClearHandler(){
        //To be implemented later
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        boolean success = false;

        try {

            if (exchange.getRequestMethod().toLowerCase().equals("post")) {



                    ClearService myClearService = new ClearService();

                    ClearResponse myClearResponse = myClearService.clear();

                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                    String jsonStr = Encoder.encode(myClearResponse);
                    OutputStream respBody = exchange.getResponseBody();
                    writeString(jsonStr, respBody);

                    respBody.close();


                success = true;


            }

            if (!success) {

                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch (IOException e) {

            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);

            exchange.getResponseBody().close();

            // Display/log the stack trace
            e.printStackTrace();
        }
    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
