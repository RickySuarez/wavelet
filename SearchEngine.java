import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler2 implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    int num = 0;
    ArrayList<String> searches = new ArrayList<String>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("Number: %d", num);
        } else if (url.getPath().equals("/increment")) {
            num += 1;
            return String.format("Number incremented!");
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("count")) {
                    num += Integer.parseInt(parameters[1]);
                    return String.format("Number increased by %s! It's now %d", parameters[1], num);
                }
                if(parameters[0].equals("s")){
                    searches.add(parameters[1]);
                    return String.format("%s has been added to the list." , parameters[1]);
                }
            }
            else if (url.getPath().contains("/add"));{
                String[] parameters = url.getQuery().split("=");
                if(parameters[0].equals("s")){
                    String s = "";
                    for(int i = 0; i < searches.size(); i++){
                        if(searches.get(i).toLowerCase().contains(parameters[1].toLowerCase())){
                            s = s + searches.get(i) + ", ";
                        }
                    }
                    return "Searches containing " + parameters[1] + ": " + s.substring(0, s.length()-2);
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
