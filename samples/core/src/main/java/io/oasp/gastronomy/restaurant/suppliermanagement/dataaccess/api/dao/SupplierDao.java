package io.oasp.gastronomy.restaurant.suppliermanagement.dataaccess.api.dao;

import io.oasp.gastronomy.restaurant.general.dataaccess.api.dao.ApplicationDao;
import io.oasp.gastronomy.restaurant.suppliermanagement.dataaccess.api.SupplierEntity;
import io.oasp.gastronomy.restaurant.suppliermanagement.logic.api.to.SupplierSearchCriteriaTo;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;

/**
 * Data access interface for Supplier entities
 */
public interface SupplierDao extends ApplicationDao<SupplierEntity> {

  /**
   * Finds the {@link SupplierEntity suppliers} matching the given {@link SupplierSearchCriteriaTo}.
   *
   * @param criteria is the {@link SupplierSearchCriteriaTo}.
   * @return the {@link PaginatedListTo} with the matching {@link SupplierEntity} objects.
   */
  PaginatedListTo<SupplierEntity> findSuppliers(SupplierSearchCriteriaTo criteria);
}
