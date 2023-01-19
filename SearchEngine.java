import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class SearchEngineHandler implements URLHandler {
    ArrayList<String> strings;

    public SearchEngineHandler() {
        super();
        strings = new ArrayList<String>();
    }

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            String message = "Stored Strings: \n";
            for (String str : strings) {
                message += str + "\n";
            }
            System.out.println(strings);
            return message;
        } else if (url.getPath().equals("/add")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("string")) {
                System.out.println(parameters[1]);
                strings.add(parameters[1]);
                return "Added \"" + parameters[1] + "\" to list";
            }
        } else if (url.getPath().equals("/query")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("string")) {
                String message = "Search Results: \n";
                for (String str : strings) {
                    if (str.contains(parameters[1])) {
                        message += str + "\n";
                    }
                }
                return message;
            }
        }
        return "404 Not Found!";
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new SearchEngineHandler());
    }
}