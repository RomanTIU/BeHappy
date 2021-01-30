package md.behappy.endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import md.behappy.orm.Diet;
import md.behappy.orm.DietQuantity;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/rest/diet")
@Produces(MediaType.APPLICATION_JSON)
public class DietEndpoint {

    private final ObjectMapper mapper;

    public DietEndpoint(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @GET
    @Path("")
    public String getDiet() throws JsonProcessingException {
        return mapper.writeValueAsString(Diet.listAll());
    }

    @GET
    @Path("/getQuantities")
    public String getQuantities () throws JsonProcessingException {
        return mapper.writeValueAsString(DietQuantity.listAll());
    }
}
