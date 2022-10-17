package org.hibernate.bugs;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.DynamicInsert;

import java.time.ZonedDateTime;

@Entity
@DynamicInsert
public class DemoEntity {

  @Id
  private String identificationString;

  private ZonedDateTime updated;

  public String getIdentificationString() {
    return identificationString;
  }

  public void setIdentificationString(String identificationString) {
    this.identificationString = identificationString;
  }

  public ZonedDateTime getUpdated() {
    return updated;
  }

  public void setUpdated(ZonedDateTime updated) {
    this.updated = updated;
  }
}