package com.onejope.mcdonations.servlet.resource;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.onejope.mcdonations.model.Category;

@Path("/categories")
@Stateless
public class CategoryService {
  @PersistenceContext(unitName = "model")
  EntityManager em;

  @GET
  @Produces("application/json")
  public List<Category> getCategories() {
    return em.createNamedQuery(Category.FIND_ALL_QUERY, Category.class).getResultList();
  }

  @POST
  @Consumes("application/json")
  public void addCategory(Category category) {
    em.persist(category);
  }
}
