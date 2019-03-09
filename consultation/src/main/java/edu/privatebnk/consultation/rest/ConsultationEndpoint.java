package edu.privatebnk.consultation.rest;

import edu.privatebnk.consultation.persistence.controller.Controller;
import edu.privatebnk.consultation.persistence.model.*;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;
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

    /**
     * Get specific customer data.
     * @param custid Customer id.
     * @return Return customer personal info, current profile, account.
     */
    @GET
    @Path("/customer/{custid}")
    @RolesAllowed({"CRO"})
    @ValidateUser
    public javax.ws.rs.core.Response getCustomer(@PathParam("custid") int custid) {
        return Response.ok(controller.findById(custid)).type(MediaType.APPLICATION_JSON_TYPE).build();
    }

    /**
     * Get all customers of a CRO.
     * @param userid CRO id.
     * @return Return all customers personal info, current profile, account.
     */
    @GET
    @Path("/customer/all/{userid}")
    @RolesAllowed({"CRO"})
    @ValidateUser
    public javax.ws.rs.core.Response getCustomersForCro(@PathParam("userid") int userid) {
        ResponseWrapper wrapper = new ResponseWrapper(ResponseCode.OK, controller.findCustomersForCro(userid),null);
        return Response.ok(wrapper).type(MediaType.APPLICATION_JSON_TYPE).build();
    }

    /**
     * Get all profiles of a customer.
     * @param userid User id.  CRO or MA.
     * @param custid Customer id.
     * @param auth Authorization header.
     * @return Return all customer profiles.
     */
    @GET
    @Path("/profiles/all/{userid}/{custid}")
    @RolesAllowed({"CRO","MA"})
    @ValidateUser
    public javax.ws.rs.core.Response getProfilesforCust(@PathParam("userid") int userid, @PathParam("custid") int custid, @HeaderParam("Authorization") String auth) {
        List<Role> id = jwtUtil.getRoles(auth);
        if(id.stream().anyMatch(role->role==Role.MA)) {
            ConsultRequest consultRequest = controller.findConsultRequestsMA(userid).stream().filter(req -> req.getCustomer().getCustomerid() == custid).findFirst().orElse(null);
            if (consultRequest != null) {
                ResponseWrapper wrapper = new ResponseWrapper(ResponseCode.OK, null, controller.findProfilesforCust(custid));
                return Response.ok(wrapper).type(MediaType.APPLICATION_JSON_TYPE).build();
            } else
                return Response.ok(new edu.privatebnk.consultation.rest.Response(ResponseCode.NODATA)).type(MediaType.APPLICATION_JSON_TYPE).build();
        } else if(id.stream().anyMatch(role->role==Role.CRO)) {
            Customer customer1 = controller.findCustomersForCro(userid).stream().filter(customer -> custid == customer.getCustomerid()).findFirst().orElse(null);
            if (customer1 != null) {
                ResponseWrapper wrapper = new ResponseWrapper(ResponseCode.OK, null, controller.findProfilesforCust(custid));
                return Response.ok(wrapper).type(MediaType.APPLICATION_JSON_TYPE).build();
            } else
                return Response.ok(new edu.privatebnk.consultation.rest.Response(ResponseCode.NODATA)).type(MediaType.APPLICATION_JSON_TYPE).build();
        }
        return Response.ok(new edu.privatebnk.consultation.rest.Response(ResponseCode.INVDATA)).type(MediaType.APPLICATION_JSON_TYPE).build();
    }

    @GET
    @Path("/user/{userid}")
    public javax.ws.rs.core.Response getUser(@PathParam("userid") int userid) {
        return Response.ok(controller.findByυId(userid)).type(MediaType.APPLICATION_JSON_TYPE).build();
    }

    /**
     * Mark a Consult Request that needs MA consultation.
     * @param requestid Request id.
     * @return Return the updated request.
     */
    @GET
    @Path("/request/help/{requestid}")
    @RolesAllowed({"CRO"})
    @ValidateUser
    public javax.ws.rs.core.Response askMAConsultRequests(@PathParam("requestid") int requestid) {
        ConsultRequest request = controller.findRequestById(requestid);
        request.setMarequired(true);
        controller.update(request);
        return Response.ok(request).type(MediaType.APPLICATION_JSON_TYPE).build();
    }

    private void nullifyPrivateCustomerData(Customer customer) {
        customer.setAddress(null);
        customer.setBirthdate(null);
        customer.setCro(null);
        customer.setDate_added(null);
        customer.setEmail(null);
        customer.setName(null);
        customer.setPhone(null);
        customer.setStatus(null);
        customer.setSurname(null);
    }

    /**
     * MA assign a request to himself.
     * @param requestid Request id.
     * @param maid MA id.
     * @return Return the updated request.
     */
    @GET
    @Path("/request/assigntoma/{requestid}/{maid}")
    @RolesAllowed({"MA"})
    @ValidateUser
    public javax.ws.rs.core.Response assignConsultRequests(@PathParam("requestid") int requestid, @PathParam("maid") int maid) {
        ConsultRequest request = controller.findRequestById(requestid);
        request.setMa(controller.findByυId(maid));
        controller.update(request);
        nullifyPrivateCustomerData(request.getCustomer());
        return Response.ok(request).type(MediaType.APPLICATION_JSON_TYPE).build();
    }

    /**
     * Get all requests assigned to a user.
     * @param userid User id. CRO or MA.
     * @param proccesed Status of request. true or false.
     * @param auth Authorization header.
     * @return Return all requests.
     */
    @GET
    @Path("/request/all/{userid}")
    @RolesAllowed({"CRO","MA"})
    @ValidateUser
    public javax.ws.rs.core.Response getConsultRequests(@PathParam("userid") int userid, @QueryParam("proccessed") Boolean proccesed, @HeaderParam("Authorization") String auth) {
        List<Role> id = jwtUtil.getRoles(auth);
        List<ConsultRequest> requests = new ArrayList<>();
        if(id.stream().anyMatch(role->role==Role.CRO))
            requests = proccesed == null ? controller.findConsultRequestsCRO(userid) : controller.findConsultRequestsBystatusCRO(userid, proccesed);
        else if(id.stream().anyMatch(role->role==Role.MA)) {
            requests = proccesed == null ? controller.findConsultRequestsMA(userid) : controller.findConsultRequestsBystatusMA(userid, proccesed);
            requests.forEach(req->{
                nullifyPrivateCustomerData(req.getCustomer());
            });
        }
        ResponseWrapper wrapper = new ResponseWrapper(); wrapper.setRequests(requests); wrapper.setErrorCode(ResponseCode.OK);
        return Response.ok(wrapper).type(MediaType.APPLICATION_JSON_TYPE).build();
    }

    /**
     * Get all reports.
     * @param userid MA id.
     * @return Return all reports of given MA.
     */
    @GET
    @Path("/report/all/{userid}")
    @RolesAllowed({"MA"})
    @ValidateUser
    public javax.ws.rs.core.Response getConsultReports(@PathParam("userid") int userid) {
        ResponseWrapper wrapper = new ResponseWrapper(); wrapper.setReports(controller.findConsultReportMA(userid)); wrapper.setErrorCode(ResponseCode.OK);
        return Response.ok(wrapper).type(MediaType.APPLICATION_JSON_TYPE).build();
    }

    /**
     * Create new Report.
     * @param maid MA id.
     * @param proposalRequest Report data.
     * @param auth AUthorization header.
     * @return Return the created report.
     */
    @POST
    @Path("/report/{maid}")
    @RolesAllowed({"MA"})
    @ValidateUser
    public javax.ws.rs.core.Response createConsultReport(@PathParam("maid") int maid, ProposalRequest proposalRequest, @HeaderParam("Authorization") String auth) {
        List<Role> id = jwtUtil.getRoles(auth);
        if(id.stream().anyMatch(role->role==Role.MA)) {
            ConsultRequest request = controller.findRequestById(proposalRequest.getRequestid());
            ConsultReport consultReport = new ConsultReport();
            consultReport.setRequest(request);
            consultReport.setMa(controller.findByυId(maid));
            JsonDocument jsonDocument = new JsonDocument();
            jsonDocument.setJson(proposalRequest.getJsondocument());
            consultReport.setDocument(jsonDocument);
            controller.create(consultReport);
            return Response.ok(consultReport).type(MediaType.APPLICATION_JSON_TYPE).build();
        } else
            return Response.ok(new edu.privatebnk.consultation.rest.Response(ResponseCode.INVDATA)).type(MediaType.APPLICATION_JSON_TYPE).build();
    }

    /**
     * Get all proposals.
     * @param userid CRO id.
     * @return Return all proposals of CRO.
     */
    @GET
    @Path("/proposal/all/{userid}")
    @RolesAllowed({"CRO"})
    @ValidateUser
    public javax.ws.rs.core.Response getInvestProposals(@PathParam("userid") int userid) {
        List<ConsultRequest> requests = controller.findConsultRequestsBystatusCRO(userid, true);
        List<InvestProposal> proposals = requests.stream().map(ConsultRequest::getProposal).collect(Collectors.toList());
        ResponseWrapper wrapper = new ResponseWrapper(); wrapper.setProposals(proposals); wrapper.setErrorCode(ResponseCode.OK);
        return Response.ok(wrapper).type(MediaType.APPLICATION_JSON_TYPE).build();
    }

    /**
     * Create new Proposal.
     * @param userid CRO id.
     * @param proposalRequest Proposal data.
     * @return Return created Proposal.
     */
    @POST
    @Path("/proposal/{userid}")
    @RolesAllowed({"CRO"})
    @ValidateUser
    public javax.ws.rs.core.Response createInvestProposals(@PathParam("userid") int userid, ProposalRequest proposalRequest) {
        ConsultRequest request = controller.findRequestById(proposalRequest.getRequestid());
        InvestProposal investProposal = new InvestProposal();
        investProposal.setRequest(request);
        JsonDocument jsonDocument = new JsonDocument();
        jsonDocument.setJson(proposalRequest.getJsondocument());
        investProposal.setDocument(jsonDocument);
        controller.create(investProposal);
        request.setProccessed(true);
        request.setDateProccessed(new Date());
        controller.update(request);
        return Response.ok(investProposal).type(MediaType.APPLICATION_JSON_TYPE).build();
    }

    /**
     * Login
     * @param login Login data.
     * @return Return user info.
     */
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

    @GET
    @Path("/pass/{pass}")
    public javax.ws.rs.core.Response generatePass(@PathParam("pass") String pass) {
        String salt = PasswordUtils.getSalt(3);
        return Response.ok(PasswordUtils.generateSecurePassword(pass, salt)+" "+salt).type(MediaType.APPLICATION_JSON_TYPE).build();
    }

    /**
     * Update proposal status.
     * @param proposalid Proposal id.
     * @param status Status. DENIED or ACCEPTED.
     * @return Return updated proposal.
     */
    @GET
    @Path("/proposal/updatestatus/{proposalid}/{status}")
    @RolesAllowed({"CRO"})
    @ValidateUser
    public javax.ws.rs.core.Response acceptProposal(@PathParam("proposalid") int proposalid, @PathParam("status") String status) {
        InvestProposal proposal = controller.findProposalById(proposalid);
        proposal.setStatus(ProposalStatus.valueOf(status));
        proposal.setDateStatusUpdated(new Date());
        controller.update(proposal);
        if(ProposalStatus.valueOf(status) == ProposalStatus.DENIED) {
            ConsultRequest request = proposal.getRequest();
            request.setRequestid(0);
            request.setMa(null);
            request.setDateProccessed(null);
            request.setProccessed(false);
            request.setMarequired(false);
            controller.create(request);
        }
        return Response.ok(proposal).type(MediaType.APPLICATION_JSON_TYPE).build();
    }
}
