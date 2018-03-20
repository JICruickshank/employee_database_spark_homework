package controllers;

import db.DBHelper;
import models.Engineer;
import org.dom4j.rule.Mode;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;

import static spark.Spark.get;

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

//                get("/engineers/new", (req, res) -> {
//                    HashMap<String, Object> model = new HashMap<>();
//                    model.put("template", "templayes/engineers/create.vtl");
//                    return new ModelAndView(model, "templates/layout.vtl");
//                }, velocityTemplateEngine);


            }




}
