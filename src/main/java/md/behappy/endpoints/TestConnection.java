package md.behappy.endpoints;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("rest/test")
public class TestConnection {

    @GET
    @Path("/list")
    public long getList()

    {
        return 1L;

    }

}
