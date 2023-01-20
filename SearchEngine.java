import java.io.IOException;
import java.net.URI;
import java.util.HashSet;

class Handler2 implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    int num = 0;
    HashSet<String> serverStrings = new HashSet<String>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("On Skylers SearchEngine, there are %d strings in the list", num);
        } else if (url.getPath().equals("/strings")) {
            return serverStrings.toString();
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    num++;
                    serverStrings.add(parameters[1]);
                    return String.format("The string %s was added to the list! There are now %d words in the list.", parameters[1], num);
                }
            }
            return "404 Not Found!";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler2());
    }
}
