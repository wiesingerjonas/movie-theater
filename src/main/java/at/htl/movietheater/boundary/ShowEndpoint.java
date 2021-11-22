package at.htl.movietheater.boundary;

import at.htl.movietheater.control.MovieRepository;
import at.htl.movietheater.control.TheaterRepository;
import at.htl.movietheater.entity.Show;

import javax.inject.Inject;
import javax.json.JsonValue;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/show")
public class ShowEndpoint {

    @Inject
    MovieRepository movieRepository;

    @Inject
    TheaterRepository theaterRepository;

    @POST
    public Response create(JsonValue json, @Context UriInfo uri) {
        if(json.getValueType().equals(JsonValue.ValueType.ARRAY)) {
            for (JsonValue jsonValue : json.asJsonArray()) {
                Show show = new Show(movieRepository.findByTitle(jsonValue.asJsonObject().getString("movie")),
                        theaterRepository.findByName(jsonValue.asJsonObject().getString("theater")),
                        null,
                        null);
            }
        } else if(json.getValueType().equals(JsonValue.ValueType.OBJECT)) {
            Show show = new Show(movieRepository.findByTitle(json.asJsonObject().getString("movie")),
                    theaterRepository.findByName(json.asJsonObject().getString("theater")),
                    null,
                    null);
        }

        return null;
    }

    @Path("/{id}")
    public Response get(long id) {
        return null;
    }
}
