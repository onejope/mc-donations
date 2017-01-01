package com.onejope.mcdonations.servlet.resource;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.onejope.mcdonations.model.Account;
import com.onejope.mcdonations.model.utils.EntityManagerFactoryUtil;

@Path("/accounts")
public class AccountResource {
  @GET
  @Produces("application/json")
  public List<Account> getAccounts() {
    EntityManager entityManager = null;
    try {
      entityManager = EntityManagerFactoryUtil.getEntityManager();
      return entityManager.createNamedQuery(Account.FIND_ALL_QUERY,Account.class).getResultList();
    } finally {
      if(entityManager != null) {
        entityManager.close();
      }
    }
  }
  
  @POST
  @Consumes("application/json")
  public void addAccount(Account account) {
    
  }
}
