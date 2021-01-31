package md.behappy.endpoints;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import md.behappy.orm.Diet;
import md.behappy.orm.DietTotal.Macronutrients;
import md.behappy.orm.DietQuantity;
import md.behappy.orm.DietTotal;
import org.slf4j.Logger;

import javax.json.Json;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import java.lang.reflect.Type;
import java.util.HashSet;



@Path("/rest/diet")
@Produces(MediaType.APPLICATION_JSON)
public class DietEndpoint {

    private final ObjectMapper mapper;
    private final Logger logger;

    public DietEndpoint(ObjectMapper mapper, Logger logger) {
        this.mapper = mapper;
        this.logger = logger;
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

    @GET
    @Path("/macronutrients")
    public Response getMacronutrients() {
        return Response.ok(Macronutrients.values()).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteDiet(@PathParam("id") String id) {
        try {
            if (parseStringToLong(id) > 0) {
                Diet diet = Diet.findById(parseStringToLong(id));
                diet.delete();
                return Response.status(Status.OK).build();
            } else {
                return Response.status(Status.CONFLICT).entity(
                        Json.createObjectBuilder().add("reason","No entity has been deleted").build().toString())
                        .build();
            }
        } catch (NumberFormatException nfe) {
            return Response.status(422).entity(nfe.toString()).build();
        }
    }



    @POST
    @Path("")
    @Transactional
    public Response createUpdateQuantities (String body, @Context HttpServletRequest request) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(body);

//            Check if name exist ----
//            if (checkIfNameExist(json.getString("name"))) {
//                return nameAlreadyExistResponse(json.getString("name"));
//            }

            Diet diet = new Diet();
            diet.name = jsonNode.get("name").asText();
            diet.recipe = jsonNode.get("recipe").asText();
            diet.dietQuantity = new HashSet<>();
            diet.dietQuantity = objectMapper.readValue(jsonNode.get("dietQuantity").toPrettyString(), new TypeReference<>() {
                @Override
                public Type getType() {
                    return super.getType();
                }
            });
            diet.dietTotal = objectMapper.readValue(jsonNode.get("dietTotal").toPrettyString(),DietTotal.class);
            diet.persist();

            return Response.status(Status.CREATED).build();
        } catch (Exception e ) {
            e.printStackTrace();
            return Response.status(422).entity(e.toString()).build();
        }
    }
    private long parseStringToLong(String string) {
        return Long.parseLong(string);
    }
}
