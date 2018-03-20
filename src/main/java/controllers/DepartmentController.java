package controllers;

import com.sun.tools.internal.xjc.model.Model;
import db.DBHelper;
import models.Department;
import org.apache.velocity.app.Velocity;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import static spark.Spark.get;
import static spark.Spark.post;

import java.util.HashMap;
import java.util.List;

public class DepartmentController {

    public DepartmentController() {
        this.setupEndPoints();
    }

        public void setupEndPoints() {

            VelocityTemplateEngine velocityTemplateEngine = new VelocityTemplateEngine();
            get("/departments", (req, res) -> {
                List<Department> departments = DBHelper.getAll(Department.class);
                HashMap<String, Object> model = new HashMap<>();
                model.put("departments", departments);
                model.put("template", "templates/departments/index.vtl");
                return new ModelAndView(model, "templates/layout.vtl");
            }, velocityTemplateEngine);

            get("/departments/new", (req, res) -> {
                HashMap<String, Object> model = new HashMap<>();
                model.put("template", "templates/departments/create.vtl");
                return new ModelAndView(model, "templates/layout.vtl");
            }, velocityTemplateEngine);

            post("/departments", (req, res) -> {
                String title = req.queryParams("title");
                Department department = new Department(title);
                DBHelper.save(department);
                res.redirect("/departments");
                return null;
            }, velocityTemplateEngine);
        }


}
