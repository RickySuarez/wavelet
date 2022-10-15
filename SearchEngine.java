import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class SearchHandler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> searches = new ArrayList<String>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("/add?s=anewstringtoadd : adds anewstring to the list.\n/search?s=example : displays all strings in the list that have example");
        } else if (url.getPath().equals("/add")) {
            String[] parameters = url.getQuery().split("=");
            if(parameters[0].equals("s")){
                searches.add(parameters[1]);
                return String.format("%s added to the list" , parameters[1]);
            }
            
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/search")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    if(searches.size() == 0) return "There are no searches containing " + parameters[1] + ".";
                    String s = "";
                    for(int i = 0; i < searches.size(); i++){
                        if(searches.get(i).contains(parameters[1])) s += searches.get(i) + ", "; 
                    }
                    if(s.equals("")) return "There are no search results containing " + parameters[1] + ".";
                    return "Searches containing " + parameters[1] + ": " + s.substring(0, s.length() - 2);
                }
            }
        }
        return "404 Not Found!";
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new SearchHandler());
    }
}
