package de.metalcon.sdd.request;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONValue;

import de.metalcon.common.JsonPrettyPrinter;
import de.metalcon.sdd.entity.Entity;
import de.metalcon.sdd.server.Server;

import static de.metalcon.sdd.entity.EntityByType.newEntityByType;

public class UpdateRequest extends Request {
    
    public UpdateRequest(Server server) {
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
        entity.loadFromUpdateParams(params);;
        server.writeEntity(entity);
    }
    
    public static void main(String[] args) throws InterruptedException {
        Server s = new Server();
        s.start();
        
        Map<String, String[]> params = new HashMap<String, String[]>();
        params.put("id",        new String[]{"11233033e2b36cff"});
        params.put("type",      new String[]{"city"});
        params.put("country",   new String[]{"Finnland"});
        UpdateRequest r = new UpdateRequest(s);
        r.setParams(params);
        String json = JSONValue.toJSONString(r.runHttp());
        json = JsonPrettyPrinter.prettyPrintJson(json);
        System.out.println(json);
        Thread.sleep(100);
        
        s.stop();
    }
    
}
