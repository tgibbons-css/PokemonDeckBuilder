package CIS3334.deckbuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Card implements Serializable {

    private String id;
    private String name;
    private ArrayList<String> types;

    public Card(String id, String name, ArrayList<String> types){
        this.id = id;
        this.name = name;
        this.types = types;
    }

    public Card(){
        id = "";
        name = "";
        types = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getTypes() {
        String allTypes = "";
        for(String type : types) {
            allTypes += type + "\n";
        }
        return allTypes;
    }
}
