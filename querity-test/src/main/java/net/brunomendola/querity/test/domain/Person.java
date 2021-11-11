package net.brunomendola.querity.test.domain;

public interface Person {
  String getFirstName();

  String getLastName();

  java.time.LocalDate getBirthDate();

  Integer getChildren();

  Pet getFavouritePet();

  void setFirstName(String firstName);

  void setLastName(String lastName);

  void setBirthDate(java.time.LocalDate birthDate);

  void setChildren(Integer children);

  void setFavouritePet(Pet favouritePet);
}
