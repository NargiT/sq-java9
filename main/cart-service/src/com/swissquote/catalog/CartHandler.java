package com.swissquote.catalog;

import com.swissquote.web.web.GenericHandler;
import com.swissquote.web.web.HttpRequest;
import com.swissquote.web.web.HttpResponse;
import jdk.nashorn.internal.parser.JSONParser;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * Created by tigran on 09/04/2017.
 */
public class CartHandler extends GenericHandler {

    private final Cart cart;

    public CartHandler() {
        cart = new Cart();
    }

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws IOException {
        String collect = cart.items().stream().map(Item::toJson).collect(Collectors.joining(","));
        response.send("[" + collect + "]");
    }

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) throws IOException {
        System.out.println("New item added");
        addItems(request);
        response.send("OK");
    }

    private void addItems(HttpRequest request) {
        String params = getStringFromInputStream(request.getBody());
        String[] items = params.split("&");
        Arrays.asList(items).forEach(raw -> {
            String[] entry = raw.split("=");
            if ("item".equals(entry[0])) {
                String name = entry[1];
                System.out.println("Adding item " + name);
                cart.add(new Item(name));
            }
        });
    }

    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }
}



/*
public class ImageHandler extends GenericHandler {

    private final static Logger logger = new Logger(ImageHandler.class);

    private static final String JSON_ELEMENT_TEMPLATE = "{\"name\":\"%1$s\",\"url\":\"%2$s\",\"thumbnail\":\"%3$s\"}";
    private static final String JSON_MAIN_TEMPLATE = "{%s}";
    private static final String HTML_IMG_TEMPLATE = "<img src=\"%2$s\" width=\"600\"/>";
    private static final String HTML_ELEMENT_TEMPLATE = "<p><a href=\"%2$s\"><img src=\"%3$s\" title=\"%1$s\"/></a></p>";
    private static final String HTML_MAIN_TEMPLATE = "<html><head></head><body>%s</body></html>";

    private final ImageService service = new ImageService();

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) throws IOException {
        Optional<Part> optional = Multiparts.parse(request).stream()
                .filter(part -> part.hasHeader("filename"))
                .findFirst();

        if (optional.isPresent()) {
            saveImage(response, optional.get());
        } else {
            response.error404();
        }
    }

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws IOException {
        String format = request.getPathElement(0);
        String name = request.getPathElement(1);
        ImageDescription image = service.get(name);
        String accept = request.getHeader("Accept");
        if (image == null) {
            if (accept.contains("application/json")) {
                response.send(json(viewImageList(request, JSON_ELEMENT_TEMPLATE)));
            } else if (accept.contains("text/html")) {
                response.send(html(viewImageList(request, HTML_ELEMENT_TEMPLATE)));
            } else {
                response.error(501);
            }
        } else if ("thumb".equals(format)) {
            response.download(image.getFileName(), image.getThumbnail());
        } else {
            if (accept.contains("application/json")) {
                response.send(viewImage(request, JSON_ELEMENT_TEMPLATE, name));
            } else if (accept.contains("text/html")) {
                response.send(html(viewImage(request, HTML_IMG_TEMPLATE, name)));
            } else {
                response.download(image.getFileName(), image.getContent());
            }
        }
    }

    private String html(String content) {
        return String.format(HTML_MAIN_TEMPLATE, content);
    }

    private String json(String content) {
        return String.format(JSON_MAIN_TEMPLATE, content);
    }

    private String viewImageList(HttpRequest request, String template) {
        return service.all().stream()
                .map(image -> viewImage(request, template, image.getFileName()))
                .collect(Collectors.joining());
    }

    private String viewImage(HttpRequest request, String template, String name) {
        return String.format(template, name, request.getRootURI() + "/img/" + name, request.getRootURI() + "/thumb/" + name);
    }

    private void saveImage(HttpResponse response, Part part) {
        try {
            ImageDescription image = new ImageDescription();
            image.setFileName(part.getHeader("filename"));
            image.setContent(part.getContent());

            service.createOrUpdate(image);

            response.send("OK: " + image.getFileName());
        } catch (IOException e) {
            throw new HttpException(e);
        }
    }

}
*/