package tikape.runko;

import java.sql.Timestamp;
import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.AihealueDao;
import tikape.runko.database.ViestiketjuDao;
import tikape.runko.database.ViestiDao;

public class Main {

    public static void main(String[] args) throws Exception {
        
        // asetetaan portti jos heroku antaa PORT-ympäristömuuttujan
        if (System.getenv("PORT") != null) {
            port(Integer.valueOf(System.getenv("PORT")));
        }
        
        Database database = new Database("jdbc:sqlite:foorumi.db");
        database.init();

        AihealueDao aihealueDao = new AihealueDao(database);
        ViestiketjuDao viestiketjuDao = new ViestiketjuDao(database);
        ViestiDao viestiDao = new ViestiDao(database);

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viesti", "tämä viesti tulee Main.javasta");

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        get("/aihealueet", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("aihealueet", aihealueDao.findAll());

            return new ModelAndView(map, "aihealueet");
        }, new ThymeleafTemplateEngine());

        get("/aihealueet/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("aihealue", aihealueDao.findOne(Integer.parseInt(req.params("id"))));
            map.put("viestiketju", viestiketjuDao.findByAihealue(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "aihealue");
        }, new ThymeleafTemplateEngine());
        
        get("/aihealueet/:id/viestiketju/:ketju_id", (req, res) -> {
            System.out.println("[Main.java] Kysellään viestejä viestiketjuun: "+ req.params("ketju_id"));
            HashMap map = new HashMap<>();
            map.put("viestiketju", viestiketjuDao.findOne(Integer.parseInt(req.params("ketju_id"))));
            map.put("viestit", viestiDao.findByViestiketju(Integer.parseInt(req.params("ketju_id"))));
            return new ModelAndView(map, "viestiketju");
        }, new ThymeleafTemplateEngine());
        
        
        get("/uusiviesti/:id/:ketju_id", (req, res) -> {
            System.out.println("[Main.java] pyydetty uusiviesti");
            HashMap map = new HashMap<>();
            map.put("aihealue", aihealueDao.findOne(Integer.parseInt(req.params("id"))));
            map.put("viestiketju", viestiketjuDao.findOne(Integer.parseInt(req.params("ketju_id"))));
            return new ModelAndView(map, "uusiviesti");
//              return new ModelAndView(new HashMap<>(), "uusiviesti");
        }, new ThymeleafTemplateEngine());
               
        post("/uusiviesti/:id/:ketju_id", (req, res) -> {
            System.out.println("Lähettäjä: " + req.queryParams("lahettaja"));
            System.out.println("Viesti: " + req.queryParams("sisalto"));
            return "Uusi viesti lisätty onnistuneesti!";
        });
        
        get("/halt", (req, res) -> {
            HashMap map = new HashMap<>();
            stop();
            System.exit(0);
            return null;
        }, new ThymeleafTemplateEngine());
        
    }
    
}
