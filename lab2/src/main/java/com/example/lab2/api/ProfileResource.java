package com.example.lab2.api;

import com.example.lab2.services.ProfileService;
import com.example.lab2.entities.Profile;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Path("/profiles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProfileResource {

    @EJB
    private ProfileService profileService;

    /** CREATE */
    @POST
    public Response create(Profile p, @Context UriInfo uriInfo) {
        profileService.newProfile(p);
        URI created = uriInfo.getAbsolutePathBuilder().path(p.getId().toString()).build();
        return Response.created(created).entity(p).build(); // 201 Created
    }

    /** READ (by id) */
    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Profile p = profileService.getById(id);
        if (p == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(p).build(); // 200 OK
    }

    /** READ (list) with filtering & pagination */
    @GET
    public Response list(
            @QueryParam("search") String search,
            @QueryParam("page") @DefaultValue("1") int page,
            @QueryParam("size") @DefaultValue("10") int size,
            @Context UriInfo uriInfo) {

        List<Profile> all = (List<Profile>) profileService.findByText(search);
        // simple in-memory pagination
        int from = (page - 1) * size;
        if (from >= all.size()) {
            return Response.ok(List.of()).build();
        }
        int to = Math.min(from + size, all.size());
        List<Profile> paged = all.subList(from, to);

        // build pagination links
        Link self = Link.fromUri(uriInfo.getRequestUri()).rel("self").build();
        Link next = null;
        if (to < all.size()) {
            URI nextUri = uriInfo.getRequestUriBuilder()
                    .replaceQueryParam("page", page + 1)
                    .build();
            next = Link.fromUri(nextUri).rel("next").build();
        }
        Response.ResponseBuilder resp = Response.ok(paged).links(self);
        if (next != null) resp.link(next.getUri(), next.getRel());

        return resp.build();
    }

    /** UPDATE */
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Profile p) {
        Profile existing = profileService.getById(id);
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // оновити поля
        existing.setUsername(p.getUsername());
        existing.setPublicInfo(p.getPublicInfo());
        // (не міняємо пароль тут, або окремим ендпоінтом)
        profileService.updateProfile(existing);
        return Response.ok(existing).build(); // 200 OK
    }

    /** DELETE */
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        Profile existing = profileService.getById(id);
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        profileService.deleteProfile(existing);
        return Response.noContent().build(); // 204 No Content
    }
}
