package Journal;

public class Lecturer extends Thread{
    private final String lecturerName;
    private final Journal journal;
    private final int weeksCount;

    public Lecturer(String lecturerName, Journal journal, int weeksCount){
        this.lecturerName = lecturerName;
        this.journal = journal;
        this.weeksCount = weeksCount;
    }

    @Override
    public void run(){
        for(int w = 0; w < weeksCount; w++){
            for(int g = 0; g < journal.getGroupsAmount(); g++){
                Group group = journal.getGroup(g);
                for(int s = 0; s < group.getGroupSize(); s++){
                    Student student = group.getStudent(s);
                    int newRating = (int)(Math.round(100 * Math.random()));
                    student.addRating(newRating, w, lecturerName);
                }
            }
        }
    }
}
