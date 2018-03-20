package controllers;

import db.DBHelper;
import models.Department;
import models.Engineer;
import org.dom4j.rule.Mode;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;

import static spark.Spark.get;
import static spark.Spark.post;

public class EngineerController {

    public EngineerController() {

        this.setupEndPoints(); }

            private void setupEndPoints() {
                VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();
                get("/engineers", (req, res) -> {
                    List<Engineer> engineers = DBHelper.getAll(Engineer.class);
                    HashMap<String, Object> model = new HashMap<>();
                    model.put("engineers", engineers);
                    model.put("template", "templates/engineers/index.vtl");
                    return new ModelAndView(model, "templates/layout.vtl");
                }, velocityTemplateEngine);

                get("/engineers/new", (req, res) -> {
                    List<Department> departments = DBHelper.getAll(Department.class);
                    HashMap<String, Object> model = new HashMap<>();
                    model.put("departments", departments);
                    model.put("template", "templates/engineers/create.vtl");
                    return new ModelAndView(model, "templates/layout.vtl");
                }, velocityTemplateEngine);

                post("/engineers", (req, res) -> {
                    String firstName = req.queryParams("firstName");
                    String lastName = req.queryParams("lastName");
                    int salary = Integer.parseInt(req.queryParams("salary"));
                    int departmentId = Integer.parseInt(req.queryParams("department"));
                    Department department = DBHelper.find(departmentId, Department.class);
                    Engineer engineer = new Engineer(firstName, lastName, salary, department);
                    DBHelper.save(engineer);
                    res.redirect("/engineers");
                    return null;
                }, velocityTemplateEngine);


            }




}
