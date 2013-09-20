package de.metalcon.sdd.request;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONValue;

import de.metalcon.common.JsonPrettyPrinter;
import de.metalcon.sdd.entity.Entity;
import de.metalcon.sdd.server.Server;

import static de.metalcon.sdd.entity.EntityByType.newEntityByType;

public class CreateRequest extends Request {
    
    public CreateRequest(Server server) {
        super(server);
    }
    
    @Override
    protected Map<String, Object> runHttpAction() {
        Map<String, Object> result = new HashMap<String, Object>();
        
        if (server.addRequest(this)) {
            // TODO: good response
            result.put("status", "worked");
        } else {
            // TODO: bad response
            result.put("status", "didnt work");
        }
        
        return result;
    }
    
    @Override
    public void runQueueAction() {
        Entity entity = newEntityByType(getParam("type"), server);
//        Entity entity = Entity.newEntityByType(id.getType());
        entity.loadFromCreateParams(params);
        server.writeEntity(entity);
    }
    
    public static void main(String[] args) throws InterruptedException {
        Server s = new Server();
        s.start();
        
        Map<String, String[]> params = new HashMap<String, String[]>();
        // --- old
//        Koblenz (City)
//        params.put("id",        new String[]{"2731c67201ae29ae"});
//        params.put("type",      new String[]{"city"});
//        params.put("name",      new String[]{"Koblenz"});
//        params.put("url",       new String[]{"/city/Koblenz"});
//        params.put("country",   new String[]{"Deutschland"});
//        Thrudvangr Rising (Person)
//        params.put("id",        new String[]{"ce0058ac39a33616"});
//        params.put("type",      new String[]{"person"});
//        params.put("firstname", new String[]{"Thrudvangr"});
//        params.put("lastname",  new String[]{"Rising"});
//        params.put("url",       new String[]{"/person/Thrudvangr+Rising"});
//        params.put("birthday",  new String[]{"1991-01-01"});
//        params.put("city",      new String[]{"2731c67201ae29ae"});
//        Helsinki (City)
//        params.put("id",        new String[]{"11233033e2b36cff"});
//        params.put("type",      new String[]{"city"});
//        params.put("name",      new String[]{"Helsinki"});
//        params.put("url",       new String[]{"/city/Helsinki"});
//        params.put("country",   new String[]{"Finland"});
        // --- new
//        params.put("id",        new String[]{"2f364c13c0114e16"});
//        params.put("type",      new String[]{"Musician"});
//        params.put("name",      new String[]{"Marilyyn Manson"});
//        params.put("url",       new String[]{"/musician/Marilyn+Manson"});
//        params.put("active",    new String[]{"true"});
//        params.put("founder",   new String[]{"true"});
//        params.put("spans",     new String[]{"1989-now"});
//        params.put("id",        new String[]{"11233033e2b36cff"});
//        params.put("type",      new String[]{"Musician"});
//        params.put("name",      new String[]{"Johan Hegg"});
//        params.put("url",       new String[]{"/musician/Johan+Hegg"});
//        params.put("active",    new String[]{"true"});
//        params.put("founder",   new String[]{"true"});
//        params.put("spans",     new String[]{"1992-now"});
//        params.put("id",        new String[]{"ce0058ac39a33616"});
//        params.put("type",      new String[]{"Band"});
//        params.put("name",      new String[]{"Ensiferum"});
//        params.put("url",       new String[]{"/music/Ensiferum"});
//        params.put("foundation",new String[]{"1995"});
//        params.put("musicians", new String[]{"2f364c13c0114e16,11233033e2b36cff"});
        CreateRequest r = new CreateRequest(s);
        r.setParams(params);
        String json = JSONValue.toJSONString(r.runHttp());
        json = JsonPrettyPrinter.prettyPrintJson(json);
        System.out.println(json);
        Thread.sleep(100);
        
        s.stop();
    }
    
}
