package dev.culp.producer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Foo {

  public final String bar;

  @JsonCreator
  Foo(@JsonProperty("bar") String bar) {
    this.bar = bar;
  }

  @Override
  public String toString() {
    return "Foo{" +
        "bar='" + bar + '\'' +
        '}';
  }
}
