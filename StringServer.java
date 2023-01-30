import java.io.IOException;
import java.net.URI;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    // String output = "";
    int index = 0;
    String[] messages = new String[100];

    public String handleRequest(URI url) {
        messages[0] = "";

        if (url.getPath().equals("/")) {
            return String.format("Start Message: %s ", printMessages());

        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add-message")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    if (parameters[1].equals(null)) {
                        return String.format("Current Message: %s", printMessages());
                    }
                    index++;
                    messages[index] = (parameters[1]);
                    return String.format("Message: %s", printMessages());
                }
            }
            return "404 Not Found!";
        }
    }

    public String printMessages() {
        String output = "";
        if (messages == null) {
            return output;
        }

        for (int i = 0; i < messages.length; i++) {
            System.out.println(messages[i]);

            if (messages[i] != null) {
                output = output + "\n" + messages[i];
            }
        }
        return output;
    }

}

class StringServer {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
