package model;

import netscape.javascript.JSObject;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

public class LibraryDirectory implements Writable {

    private Library wantToRead;

    private Library inProgress;

    private Library completed;


    // EFFECTS: CREATES A LIBRARY DIRECTORY WITH GIVEN WANTTOREAD, INPROGRESS, AND COMPLETED LIBRARIES
    public LibraryDirectory(Library wantToRead, Library inProgress, Library completed) {
        this.wantToRead = wantToRead;
        this.inProgress = inProgress;
        this.completed = completed;

    }

    public Library getWantToRead() {
        return wantToRead;
    }


    public Library getCompleted() {
        return completed;
    }

    public Library getInProgress() {
        return inProgress;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("wantToRead", wantToRead.toJson());
        jsonObject.put("inProgress", inProgress.toJson());
        jsonObject.put("completed", completed.toJson());
        return jsonObject;
    }

}
