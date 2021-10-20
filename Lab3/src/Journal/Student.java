package Journal;

import java.util.ArrayList;

public class Student {
    private final String name;
    private final ArrayList<String> ratings;

    public Student(String name){
        this.name = name;
        ratings = new ArrayList<String>();
    }

    public synchronized void addRating(int rating, int week, String lecturerName){
        ratings.add(lecturerName + ": Week - " + week + "; Rating - " + rating);
    }

    public int getRatingsCount(){
        return ratings.size();
    }

    public String getRating(int id){
        return ratings.get(id);
    }

    public String getName(){
        return name;
    }
}
