package URLHandlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import ObjectCodeDecode.Decoder;
import ObjectCodeDecode.Encoder;



public class DefaultFileHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        boolean success = false;

        try {

            if (exchange.getRequestMethod().toLowerCase().equals("get")) {

                String requestedURL = exchange.getRequestURI().toString();

                if (requestedURL.length() == 1){
                    //return index.html

                    String urlPath = new String("web/index.html" );
                    Path filePath = FileSystems.getDefault().getPath(urlPath);

                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                    Files.copy(filePath, exchange.getResponseBody());

                    exchange.getResponseBody().close();

                } else {
                    String urlPath = "web" + requestedURL;
                    Path filePath = FileSystems.getDefault().getPath(urlPath);

                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                    Files.copy(filePath, exchange.getResponseBody());

                    exchange.getResponseBody().close();

                }
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


}
