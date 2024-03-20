package com.casefy.resource;

import org.jboss.logging.Logger;
import com.casefy.application.Result;
import com.casefy.model.*;
import com.casefy.dto.Administrador.*;
import com.casefy.service.Administrador.AdministradorService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/administradores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdministradorResource {

    @Inject
    AdministradorService service;
    private static final Logger LOG = Logger.getLogger(ClienteResource.class);

    @POST
    public Response insert(@Valid AdministradorDTO dto) {

        LOG.debug("Debug de inserção de administradores.");
        try {
            LOG.info("Inserindo administradores");
            AdministradorResponseDTO retorno = service.insert(dto);
            return Response.status(201).entity(retorno).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.debug("Debug de inserção de administradores.");
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @PUT
    @Transactional
    @Path("/{id}")
    public Response update(AdministradorDTO dto, @PathParam("id") Long id) {
        
        try {
            LOG.info("Atualizando cliente");
            service.update(dto, id);
        return Response.noContent().build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.debug("Debug da atualização de administradores.");
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        
        try {
            LOG.info("Deletando o cliente");
            service.delete(id);
        return Response.noContent().build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.debug("Debug da exclusão do cliente.");
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @GET
    public Response findAll() {
        LOG.info("Buscando todos os administradores.");
        LOG.debug("Debug de busca de lista de administradores.");
        return Response.ok(service.findByAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        try {
            AdministradorResponseDTO administrador = service.findById(id);
            LOG.info("Buscando um cliente por ID.");
            LOG.debug("Debug de busca de ID de administradores.");
            return Response.ok(administrador).build();
        } catch (EntityNotFoundException e) {
            LOG.info("Erro ao buscar um cliente por ID.");
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/search/nome/{nome}")
    public Response findByNome(@PathParam("nome") String nome) {
        try {
            LOG.debug("Debug de busca de ID de administradores.");
           return Response.ok(service.findByNome(nome)).build();
         } catch (EntityNotFoundException e) {
            LOG.info("Erro ao buscar um cliente por ID.");
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
    
}