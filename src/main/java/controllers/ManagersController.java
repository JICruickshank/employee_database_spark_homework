package controllers;

import com.sun.tools.internal.xjc.model.Model;
import db.DBHelper;
import models.Department;
import models.Manager;

import static spark.Spark.get;
import static spark.Spark.post;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManagersController {

    public ManagersController() {

            this.setupEndpoints();
        }

        private void setupEndpoints() {
            VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();
            get("/managers", (req, res) -> {
            List<Manager> managers = DBHelper.getAll(Manager.class);
            HashMap<String, Object> model = new HashMap<>();
            model.put("template", "templates/managers/index.vtl");
            model.put("managers", managers);
            return new ModelAndView(model, "templates/layout.vtl");
        }, velocityTemplateEngine);

            get("/managers/new", (req, res) -> {
                Map<String, Object> model = new HashMap<>();
                List<Department> departments = DBHelper.getAll(Department.class);
                model.put("template", "templates/managers/create.vtl");
                model.put("departments", departments);
                return new ModelAndView(model, "templates/layout.vtl");
            }, velocityTemplateEngine);

            post("/managers", (req, res) -> {
                int departmentId = Integer.parseInt(req.queryParams("department"));
                Department department = DBHelper.find(departmentId, Department.class);
                String firstName = req.queryParams("firstName");
                String lastName = req.queryParams("lastName");
                int salary = Integer.parseInt(req.queryParams("salary"));
                double budget = Double.parseDouble(req.queryParams("budget"));
                Manager manager = new Manager(firstName, lastName, salary, department, budget);
                DBHelper.save(manager);
                res.redirect("/managers");
                return null;
            }, velocityTemplateEngine);



        }

}
