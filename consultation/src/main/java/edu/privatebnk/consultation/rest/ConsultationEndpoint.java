package edu.privatebnk.consultation.rest;

import edu.privatebnk.consultation.persistence.controller.Controller;
import edu.privatebnk.consultation.persistence.model.Customer;
import edu.privatebnk.consultation.persistence.model.UserEntity;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@RequestScoped
@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
public class ConsultationEndpoint {
    @Inject
    Controller controller;
    @Inject
    JWTUtil jwtUtil;

    @GET
    @Path("/customer/{custid}")
    //@RolesAllowed({"MA"})
    //@ValidateUser
    public javax.ws.rs.core.Response getCustomer(@PathParam("custid") int custid) {
        return Response.ok(controller.findById(custid)).type(MediaType.APPLICATION_JSON_TYPE).build();
    }

    @GET
    @Path("/customer/all/{userid}")
    //@RolesAllowed({"CRO"})
    //@ValidateUser
    public javax.ws.rs.core.Response getCustomersForCro(@PathParam("userid") int userid) {
        ResponseWrapper wrapper = new ResponseWrapper(ResponseCode.OK, controller.findCustomersForCro(userid),null);
        return Response.ok(wrapper).type(MediaType.APPLICATION_JSON_TYPE).build();
    }

    @GET
    @Path("/profiles/all/{userid}/{custid}")
    //@RolesAllowed({"CRO","MA"})
    //@ValidateUser
    public javax.ws.rs.core.Response getProfilesforCust(@PathParam("userid") int userid, @PathParam("custid") int custid, @HeaderParam("Authorization") String auth) {
        int id = jwtUtil.getUserid(auth);
        Customer customer1 = controller.findCustomersForCro(userid).stream().filter(customer -> custid == customer.getCustomerid()).findFirst().orElse(null);
        if(customer1!=null) {
            ResponseWrapper wrapper = new ResponseWrapper(ResponseCode.OK, null, controller.findProfilesforCust(custid));
            return Response.ok(wrapper).type(MediaType.APPLICATION_JSON_TYPE).build();
        } else
            return Response.ok(new edu.privatebnk.consultation.rest.Response(ResponseCode.LOGINFAILED)).type(MediaType.APPLICATION_JSON_TYPE).build();
    }

    @GET
    @Path("/user")
    public javax.ws.rs.core.Response getUser() {
        return Response.ok(controller.findByυId(1)).type(MediaType.APPLICATION_JSON_TYPE).build();
    }

    @POST
    @Path("/login")
    public Response login(LoginRequest login) {
        UserEntity user = controller.findActiveByUsername(login.getUsername());
        boolean passwordMatch = PasswordUtils.verifyUserPassword(login.getPassword(), user.getPassword(), user.getSalt());
        if(passwordMatch) {
            List<Role> userroles = user.getRoles().stream().map(role -> Role.valueOf(role.getRole())).collect(Collectors.toList());
            JwtResponse resp = jwtUtil.generateJWT((long) user.getUserid(), userroles, null);
            resp.setUser(user);
            return Response.ok(resp).type(MediaType.APPLICATION_JSON_TYPE).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity(new edu.privatebnk.consultation.rest.Response(ResponseCode.LOGINFAILED)).type(MediaType.APPLICATION_JSON_TYPE).build();
    }
}
