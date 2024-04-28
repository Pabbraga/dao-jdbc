package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.util.List;
import java.util.Scanner;

public class Program2 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        System.out.println("=== TEST 1: seller findById ===");
        Department department = departmentDao.findById(2);
        System.out.println(department);

        System.out.println("\n=== TEST 2: seller findAll ===");
        List<Department> list = departmentDao.findAll();
        for (Department dpt : list) {
            System.out.println(dpt);
        }

        System.out.println("\n=== TEST 3: seller insert ===");
        Department newDepartment = new Department(null, "Furniture");
        departmentDao.insert(newDepartment);
        System.out.println("Inserted! new id = " + newDepartment.getId());

        System.out.println("\n=== TEST 4: seller update ===");
        department = departmentDao.findById(6);
        department.setName("Bedroom");
        departmentDao.update(department);
        System.out.println("Update Completed");

        System.out.println("\n=== TEST 5: seller delete ===");
        System.out.println("Enter id for delete: ");
        int id = sc.nextInt();
        departmentDao.deleteById(id);
        System.out.println("Delete completed");

        sc.close();
    }
}
