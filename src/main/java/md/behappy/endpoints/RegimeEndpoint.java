package md.behappy.endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import md.behappy.orm.Diet;
import md.behappy.orm.Regime;
import md.behappy.utils.ObjectMapperQualifier;

import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.StringReader;
import java.util.Set;


@Path("/rest/regime")
@Produces(MediaType.APPLICATION_JSON)
public class RegimeEndpoint {
    private final ObjectMapper mapper;

    public RegimeEndpoint(@ObjectMapperQualifier ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @GET
    @Path("")
    public Response getList(@QueryParam("pageIndex") Integer pageIndex, @QueryParam("pageSize") Integer pageSize,
                            @QueryParam("sortField") String sortField, @QueryParam("sortOrder") Boolean sortOrder)
            throws JsonProcessingException {

            return Response.ok(Regime.listAll()).build();
    }

    @POST
    @Path("/create")
    @Transactional
    public Response postCreate(String body, @Context HttpServletRequest request) {
        try {
            JsonObject json = Json.createReader(new StringReader(body)).readObject();

            if (checkIfNameExist(json.getString("name"))) {
                return nameAlreadyExistResponse(json.getString("name"));
            }

            Regime regime = new Regime();
            regime.name = json.getString("name");
            regime.diet = Diet.findById((long) json.getInt("diet"));

            regime.persist();
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(422).entity(e.toString()).build();
        }
    }

    private boolean checkIfNameExist(String name) {
        return Regime.find("isDeleted = ?1 and name = ?2", false, name).firstResultOptional().isPresent();
    }

    private Response nameAlreadyExistResponse(String name) {
        return Response.status(422).entity(Json.createObjectBuilder()
                .add("reason", String.format("Name: '%s' already exist.", name)).build().toString()).build();
    }
}
