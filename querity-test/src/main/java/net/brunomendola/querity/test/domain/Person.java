package net.brunomendola.querity.test.domain;

public interface Person<A extends Address> {
  String getFirstName();

  void setFirstName(String firstName);

  String getLastName();

  void setLastName(String lastName);

  java.time.LocalDate getBirthDate();

  void setBirthDate(java.time.LocalDate birthDate);

  Integer getChildren();

  void setChildren(Integer children);

  Pet getFavouritePet();

  void setFavouritePet(Pet favouritePet);

  A getAddress();

  void setAddress(A address);
}
