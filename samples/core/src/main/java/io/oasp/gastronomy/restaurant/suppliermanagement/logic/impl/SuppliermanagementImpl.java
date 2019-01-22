package io.oasp.gastronomy.restaurant.suppliermanagement.logic.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import io.oasp.gastronomy.restaurant.general.logic.base.AbstractComponentFacade;
import io.oasp.gastronomy.restaurant.suppliermanagement.dataaccess.api.SupplierEntity;
import io.oasp.gastronomy.restaurant.suppliermanagement.dataaccess.api.dao.SupplierDao;
import io.oasp.gastronomy.restaurant.suppliermanagement.logic.api.Suppliermanagement;
import io.oasp.gastronomy.restaurant.suppliermanagement.logic.api.to.SupplierCto;
import io.oasp.gastronomy.restaurant.suppliermanagement.logic.api.to.SupplierEto;
import io.oasp.gastronomy.restaurant.suppliermanagement.logic.api.to.SupplierSearchCriteriaTo;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;

/**
 * Implementation of component interface of suppliermanagement
 */
@Named
@Transactional
public class SuppliermanagementImpl extends AbstractComponentFacade implements Suppliermanagement {

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(SuppliermanagementImpl.class);

  /** @see #getSupplierDao() */
  @Inject
  private SupplierDao supplierDao;

  /**
   * The constructor.
   */
  public SuppliermanagementImpl() {

    super();
  }

  @Override
  @RolesAllowed(value = { "Manager" })
  public SupplierEto findSupplier(Long id) {

    LOG.debug("Get Supplier with id {} from database.", id);
    return getBeanMapper().map(getSupplierDao().findOne(id), SupplierEto.class);
  }

  @Override
  @RolesAllowed(value = { "Manager" })
  public PaginatedListTo<SupplierEto> findSupplierEtos(SupplierSearchCriteriaTo criteria) {

    criteria.limitMaximumPageSize(MAXIMUM_HIT_LIMIT);
    PaginatedListTo<SupplierEntity> suppliers = getSupplierDao().findSuppliers(criteria);
    return mapPaginatedEntityList(suppliers, SupplierEto.class);
  }

  @Override
  public boolean deleteSupplier(Long supplierId) {

    SupplierEntity supplier = getSupplierDao().find(supplierId);
    getSupplierDao().delete(supplier);
    LOG.debug("The supplier with id '{}' has been deleted.", supplierId);
    return true;
  }

  @Override
  public SupplierEto saveSupplier(SupplierEto supplier) {

    Objects.requireNonNull(supplier, "supplier");
    SupplierEntity supplierEntity = getBeanMapper().map(supplier, SupplierEntity.class);

    // initialize, validate supplierEntity here if necessary
    SupplierEntity resultEntity = getSupplierDao().save(supplierEntity);
    LOG.debug("Supplier with id '{}' has been created.", resultEntity.getId());

    return getBeanMapper().map(resultEntity, SupplierEto.class);
  }

  /**
   * Returns the field 'supplierDao'.
   *
   * @return the {@link SupplierDao} instance.
   */
  public SupplierDao getSupplierDao() {

    return this.supplierDao;
  }

  @Override
  public SupplierCto findSupplierCto(Long id) {

    LOG.debug("Get SupplierCto with id {} from database.", id);
    SupplierEntity entity = getSupplierDao().findOne(id);
    SupplierCto cto = new SupplierCto();
    cto.setSupplier(getBeanMapper().map(entity, SupplierEto.class));

    return cto;
  }

  @Override
  public PaginatedListTo<SupplierCto> findSupplierCtos(SupplierSearchCriteriaTo criteria) {

    criteria.limitMaximumPageSize(MAXIMUM_HIT_LIMIT);
    PaginatedListTo<SupplierEntity> suppliers = getSupplierDao().findSuppliers(criteria);
    List<SupplierCto> ctos = new ArrayList<>();
    for (SupplierEntity entity : suppliers.getResult()) {
      SupplierCto cto = new SupplierCto();
      cto.setSupplier(getBeanMapper().map(entity, SupplierEto.class));
      ctos.add(cto);

    }
    return new PaginatedListTo<>(ctos, suppliers.getPagination());
  }

}