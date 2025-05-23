package com.example.lab2.api;

import com.example.lab2.api.dto.LoginDto;
import com.example.lab2.api.dto.RegisterDto;
import com.example.lab2.entities.Profile;
import com.example.lab2.services.ProfileService;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @EJB
    private ProfileService profileService;


    @POST
    @Path("/login")
    public Response login(LoginDto loginDto) {
        Profile user = profileService.getByLogin(loginDto.getUsername());
        if (user == null || !profileService.checkPass(user, loginDto.getPassword())) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.ok(user.getId()).build();
    }

    @POST
    @Path("/register")
    public Response register(RegisterDto registerDto) {
        Profile user = profileService.getByLogin(registerDto.getUsername());
        if (user != null) {
            return Response.status(Response.Status.CONFLICT).build();
        }
        profileService.register(registerDto.getUsername(), registerDto.getPassword(), registerDto.getEmail(), registerDto.getBio(), registerDto.getAge());
        return Response.ok(user.getId()).build();
    }
}
