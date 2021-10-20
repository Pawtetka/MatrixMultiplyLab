package Journal;

import java.util.ArrayList;

public class Journal {
    private final String subjectName;
    private final ArrayList<Group> groups;

    public Journal(String subjectName, ArrayList<Group> groups){
        this.subjectName = subjectName;
        this.groups = groups;
    }

    public String getSubjectName(){
        return subjectName;
    }

    public int getGroupsAmount(){
        return groups.size();
    }

    public Group getGroup(int id){
        return groups.get(id);
    }
}
