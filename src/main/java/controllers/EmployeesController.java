package controllers;

import db.DBHelper;
import db.Seeds;
import models.Department;
import models.Employee;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.SparkBase.staticFileLocation;

import java.util.HashMap;
import java.util.List;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.SparkBase.staticFileLocation;

public class EmployeesController {
    public static void main(String[] args) {
        staticFileLocation("public");
        ManagersController managersController = new ManagersController();
        EngineerController engineerController = new EngineerController();
        DepartmentController departmentController = new DepartmentController();
        Seeds.seedData();
        VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();

        get("/employees", (req, res) -> {
            List<Employee> employees = DBHelper.getAll(Employee.class);
            HashMap<String, Object> model = new HashMap<>();
            model.put("template", "templates/employees/index.vtl");
            model.put("employees", employees);
            return new ModelAndView(model, "templates/layout.vtl");
        }, velocityTemplateEngine);

        get("/employees/:id", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            Employee employee = DBHelper.find(id, Employee.class);
            HashMap<String, Object> model = new HashMap<>();
            model.put("employee", employee);
            model.put("template", "templates/employees/read.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
            }, velocityTemplateEngine);

        post("/employees/:id/delete", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            Employee employee = DBHelper.find(id, Employee.class);
            DBHelper.delete(employee);
            res.redirect("/employees");
            return null;

        }, velocityTemplateEngine);

    }
}
