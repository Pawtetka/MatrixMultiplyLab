package Journal;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class JournalMain {
    public static void main(String[] args) {
        Journal journal = generateJournal();
        int weeksCount = 4;

        LinkedList<Thread> threads = new LinkedList<Thread>();

        threads.add(new Lecturer("Lecturer", journal, weeksCount));
        threads.add(new Lecturer("Assistant 1", journal, weeksCount));
        threads.add(new Lecturer("Assistant 2", journal, weeksCount));
        threads.add(new Lecturer("Assistant 3", journal, weeksCount));

        for(Thread thread : threads){
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        showJournal(journal);
    }

    private static Journal generateJournal(){
        Student student1 = new Student("Pavlo");
        Student student2 = new Student("Igor");
        Student student3 = new Student("Masha");
        Student student4 = new Student("Paul");
        Student student5 = new Student("Jessica");
        Student student6 = new Student("Leto");

        ArrayList<Student> students = new ArrayList<Student>();
        students.add(student1);
        students.add(student2);
        students.add(student3);
        Group group1 = new Group("Pustik group", students);

        ArrayList<Student> students2 = new ArrayList<Student>();
        students2.add(student4);
        students2.add(student5);
        students2.add(student6);
        Group group2 = new Group("Duna group", students2);

        ArrayList<Group> groups = new ArrayList<Group>();
        groups.add(group1);
        groups.add(group2);
        return new Journal("TDPC", groups);
    }

    private static void showJournal(Journal journal){
        System.out.println(journal.getSubjectName() + ":");
        for(int g = 0; g < journal.getGroupsAmount(); g++){
            Group group = journal.getGroup(g);
            System.out.println("\t" + group.getGroupName() + ":");
            for (int s = 0; s < group.getGroupSize(); s++){
                Student student = group.getStudent(s);
                System.out.println("\t\t" + student.getName() + ":");
                for(int r = 0; r < student.getRatingsCount(); r++){
                    String rating = student.getRating(r);
                    System.out.println("\t\t\t" + student.getRating(r) + ";");
                }
            }
        }
    }
}

