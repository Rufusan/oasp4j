package io.oasp.gastronomy.restaurant.suppliermanagement.common.api;

import io.oasp.gastronomy.restaurant.general.common.api.ApplicationEntity;

public interface Supplier extends ApplicationEntity {

  public String getName();

  public void setName(String name);

  public String getDescription();

  public void setDescription(String description);

  public int getRate();

  public void setRate(int rate);

}
