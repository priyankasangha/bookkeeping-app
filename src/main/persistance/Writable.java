package persistence;


import org.json.JSONObject;

// BASED ON WORKROOM EXAMPLE FOR CPSC 210

public interface Writable {

    // EFFECTS: RETURNS THIS AS JSON OBJECT
    JSONObject toJson();
}
