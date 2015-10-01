package ca.rhythmtech.doit.business;

import javax.ejb.EJBException;
import javax.persistence.OptimisticLockException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by mdoucette on 01/10/15.
 */
@Provider
public class EJBExceptionMapper implements ExceptionMapper<EJBException> {
    @Override
    public Response toResponse(EJBException e) {
        Throwable cause = e.getCause();

        Response unknownError = Response.serverError()
                .header("cause", e.toString())
                .build();
        if (cause == null) {
            return unknownError;
        }

        if (cause instanceof OptimisticLockException) {
            return Response.status(Response.Status.CONFLICT)
                    .header("cause", "conflict occurred: " + cause)
                    .build();
        }

        return unknownError;
    }
}
