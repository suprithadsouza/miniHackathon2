import jakarta.persistence.TypedQuery;
import model.Department;
import model.Teacher;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class manyToOneInteractive {
    public static void main(String[] args) {
        manyToOneInteractive();
    }

    public static void manyToOneInteractive() {
        System.out.println("Welcome to ManyToOneInteractive!");
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
//        Transaction transaction = session.beginTransaction();
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("\n0. Exit");
                System.out.println("1. Manage Departments");
                System.out.println("2. Manage Teachers");
                System.out.println("3. Assign Teacher to Department");
                System.out.println("4. List Teachers");
                System.out.println("5. List Department");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 0:
                        System.out.println("Exiting...");
                        return;
                    case 1:
                        manageDepartments(scanner, factory);
                        break;
                    case 2:
                        manageTeachers(scanner, factory);
                        break;
                    case 3:
                        assignTeacherToDepartment(scanner, session);
                        break;
                    case 4:
                        listTeachers(session);
                        break;
                    case 5:
                        listDepts(session);
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            session.close();
            factory.close();

        }



    }

    private static void manageDepartments(Scanner scanner, SessionFactory factory) {

        // YOUR CODE HERE
   try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
        System.out.println("\n1. Add Departments");
        System.out.println("2. Delete Department");
        System.out.println("3. Modify Department");
        System.out.println("4. Go back to menu");
 int choice = scanner.nextInt();
            scanner.nextLine(); //consume newline
            switch (choice) {
                case 1:
                    System.out.println("Enter Department name: ");
                    String deptName = scanner.nextLine();
                    Department newDept = new Department(deptName);
                    session.persist(newDept);
                    transaction.commit();
                    System.out.println("Department added successfully.");
                    break;
                case 2:
                    System.out.println("Enter Department ID to delete: ");
                    int deptIdToDelete = scanner.nextInt();
                    Department deptToDelete = session.get(Department.class, deptIdToDelete);
                    if (deptToDelete != null) {
                        session.delete(deptToDelete);
                        transaction.commit();
                        System.out.println("Department deleted successfully.");
                    } else {
                        System.out.println("Department not found.");
                    }
                    break;
                case 3:
                    System.out.println("Enter Department ID to modify: ");
                    int deptIdToModify = scanner.nextInt();
                    scanner.nextLine(); //consume newline
                    Department deptToModify = session.get(Department.class, deptIdToModify);
                    if (deptToModify != null) {
                        System.out.println(" Enter new Department name: ");
                        String newDeptName = scanner.nextLine();
                        deptToModify.setDeptName(newDeptName);
                        session.update(deptToModify);
                        transaction.commit();
                        System.out.println(" Department modified successfully.");
                    } else {
                        System.out.println("Department not found.");
                    }
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid option. Please try again. ");
            }
        }

    }

    private static void manageTeachers(Scanner scanner, SessionFactory factory) {

        // YOUR CODE HERE
try(Session session = factory.openSession()) {
    Transaction transaction = session.beginTransaction();
        System.out.println("\n1. Add Teachers");
        System.out.println("2. Delete Teacher");
        System.out.println("3. Modify Teacher");
        System.out.println("4. Go back to menu");
System.out.println(" Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice){
            case 1:
                System.out.println(" Enter Teacher name: ");
               String teacherName = scanner.nextLine();
                Teacher newTeacher = new Teacher(teacherName);
                session.persist(newTeacher);
                transaction.commit();
                System.out.println(" Teacher added successfully.");
                break;
            case 2:
                System.out.println(" Enter Teacher ID to delete: ");
                int teacherIDTODelete = scanner.nextInt();
                Teacher teacherToDelete = session.get(Teacher.class, teacherIDTODelete);
                if (teacherToDelete != null) {
                    session.delete(teacherToDelete);
                    transaction.commit();
                    System.out.println(" Teacher deleted successfully.");
                }else{
                    System.out.println("Teacher not found.");
                }
                break;
            case 3:
                System.out.println("Enter Teacher ID to modify: ");
                int teacherIdToModify = scanner.nextInt();
                scanner.nextLine(); //consume newline
                Teacher teacherToModify = session.get(Teacher.class, teacherIdToModify);
                if(teacherToModify != null) {
                    System.out.println("Enter new Teacher name: ");
                    String newTeacherName = scanner.nextLine();
                    teacherToModify.setTeacherName(newTeacherName);
                    session.update(teacherToModify);
                    transaction.commit();
                    System.out.println(" Teacher modified successfully.");
                } else {
                    System.out.println(" Teacher not found.");
                }
                break;
            case 4:
                return;
            default:
                System.out.println("Invalid option. Please try again.");
                break;

        }

        }
    }

    private static void assignTeacherToDepartment(Scanner scanner, Session session) {

        // YOUR CODE
        Transaction transaction = session.beginTransaction();
        System.out.println("Which Teacher would you like to modify: ");

int teacherId = scanner.nextInt();
Teacher teacher = session.get(Teacher.class, teacherId);
if (teacher == null){
    System.out.println("Teacher not found.");
    return;
}

        System.out.println("Which department would you like to assign to Teacher: ");
int departmentId = scanner.nextInt();
Department department = session.get(Department.class, departmentId);
if(department == null) {
    System.out.println("Department not found.");
    return;
}
teacher.setDepartment(department);
session.update(teacher);
transaction.commit();
        System.out.println(" Teacher assigned to department successfully.");
    }



    }

    private static void listDepts(Session session) {
TypedQuery<Department> query = session.createQuery("from Department", Department.class);
List<Department> departments = query.getResultList();
for(Department department : departments) {
    System.out.println("ID: " + department.getDeptId() + ", Name: " + department.getDeptName());
}

    }

    private static void listTeachers(Session session) {
TypedQuery<Teacher> query = session.createQuery(" from Teacher", Teacher.class);
List<Teacher> teachers = query.getResultList();
for (Teacher teacher : teachers) {
    System.out.println("ID:" + teacher.getTeacherId() + ", Name: "  + teacher.getTeacherName() + ", Department: " + (teacher.getDepartment() != null ? teacher.getDepartment().getDeptName() : "None"));
}

    }


}
