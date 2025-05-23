package com.example.lab2.api;

import com.example.lab2.api.dto.CategorizedInvitationsDto;
import com.example.lab2.api.dto.ProfileDto;
import com.example.lab2.api.mappers.InvitationMapper;
import com.example.lab2.api.mappers.ProfileMapper;
import com.example.lab2.entities.Profile;
import com.example.lab2.entities.PublicInfo;
import com.example.lab2.models.CategorizedInvitations;
import com.example.lab2.services.ProfileService;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Path("/profiles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProfileResource {

    @EJB
    private ProfileService profileService;

    /** CREATE */
    @POST
    public Response create(ProfileDto dto, @Context UriInfo uriInfo) {
        Profile p = ProfileMapper.fromDto(dto);
        profileService.newProfile(p);
        ProfileDto created = ProfileMapper.toDto(p);
        URI location = uriInfo.getAbsolutePathBuilder().path(p.getId().toString()).build();
        return Response.created(location).entity(created).build();
    }

    /** READ (by id) */
    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Profile p = profileService.getById(id);
        if (p == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(ProfileMapper.toDto(p)).build();
    }

    /** READ (list) */
    @GET
    public Response list(
            @QueryParam("search") String search,
            @QueryParam("page") @DefaultValue("1") int page,
            @QueryParam("size") @DefaultValue("10") int size,
            @Context UriInfo uriInfo) {

        List<Profile> all = profileService.findByText(search).stream().toList();
        int from = (page - 1) * size;
        if (from >= all.size()) {
            return Response.ok(List.of()).build();
        }
        int to = Math.min(from + size, all.size());
        List<ProfileDto> paged = all.subList(from, to).stream().map(ProfileMapper::toDto).collect(Collectors.toList());

        Link self = Link.fromUri(uriInfo.getRequestUri()).rel("self").build();
        Response.ResponseBuilder resp = Response.ok(paged).links(self);

        if (to < all.size()) {
            URI nextUri = uriInfo.getRequestUriBuilder().replaceQueryParam("page", page + 1).build();
            resp.link(nextUri, "next");
        }

        return resp.build();
    }

    /** UPDATE */
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, ProfileDto dto) {
        Profile existing = profileService.getById(id);
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        existing.setUsername(dto.getUsername());
        existing.setPublicInfo(new PublicInfo(dto.getBio(), dto.getAge()));
        profileService.updateProfile(existing);
        return Response.ok(ProfileMapper.toDto(existing)).build();
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
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}/invitations")
    public Response getCategorizedInvitations(@PathParam("id") Long userId) {
        Profile user = profileService.getById(userId);
        if (user == null) return Response.status(Response.Status.NOT_FOUND).build();
        CategorizedInvitations inv = profileService.getCategorizedInvitation(user);
        CategorizedInvitationsDto dto = new CategorizedInvitationsDto(
                inv.getAcceptedIncoming().stream().map(InvitationMapper::toDto).toList(),
                inv.getPendingIncoming().stream().map(InvitationMapper::toDto).toList(),
                inv.getAcceptedOutgoing().stream().map(InvitationMapper::toDto).toList(),
                inv.getPendingOutgoing().stream().map(InvitationMapper::toDto).toList()
        );
        return Response.ok(dto).build();
    }

    @POST
    @Path("/{id}/sendInvitation/{toId}")
    public Response sendInvitation(@PathParam("id") Long userId, @PathParam("toId") Long toId) {
        Profile sender = profileService.getById(userId);
        Profile receiver = profileService.getById(toId);
        if (sender == null || receiver == null) return Response.status(Response.Status.NOT_FOUND).build();
        profileService.sendInvitation(sender, receiver);
        return Response.ok().build();
    }

    @POST
    @Path("/{id}/acceptInvitation/{fromId}")
    public Response acceptInvitation(@PathParam("id") Long userId, @PathParam("fromId") Long fromId) {
        Profile receiver = profileService.getById(userId);
        if (receiver == null ) return Response.status(Response.Status.NOT_FOUND).build();
        profileService.acceptInvitationFromUser(receiver, fromId);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}/deleteInvitation/{invId}")
    public Response deleteInvitation(@PathParam("id") Long userId, @PathParam("invId") Long invId) {
        Profile user = profileService.getById(userId);
        if (user == null) return Response.status(Response.Status.NOT_FOUND).build();
        profileService.deleteUserInvitation(user, invId);
        return Response.noContent().build();
    }
}
