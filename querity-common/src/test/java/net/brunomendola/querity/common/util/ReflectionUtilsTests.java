package net.brunomendola.querity.common.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.AccessibleObject;
import java.util.Arrays;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

class ReflectionUtilsTests {

  @Test
  void findSubclasses() {
    assertThat(ReflectionUtils.findSubclasses(MyInterface.class))
        .containsExactlyInAnyOrder(MyClass1.class, MyClass2.class);
  }

  @Test
  void getAccessibleField() {
    assertThat(ReflectionUtils.getAccessibleField(MyClass1.class, "stringValue"))
        .map(AccessibleObject::isAccessible)
        .contains(true);
  }

  @Test
  void findClassWithConstructorArgumentOfType() {
    assertThat(ReflectionUtils.findClassWithConstructorArgumentOfType(
        new HashSet<>(Arrays.asList(MyClass1.class, MyClass2.class)),
        String.class))
        .contains(MyClass1.class);
  }

  @Test
  void constructInstanceWithArgument() {
    assertThat(ReflectionUtils.constructInstanceWithArgument(MyClass1.class, "test"))
        .isInstanceOf(MyClass1.class)
        .matches(c -> c.stringValue.equals("test"));
  }

  public interface MyInterface {
  }

  public static class MyClass1 implements MyInterface {
    private final String stringValue;

    public MyClass1(String stringValue) {
      this.stringValue = stringValue;
    }
  }

  public static class MyClass2 implements MyInterface {
  }
}
