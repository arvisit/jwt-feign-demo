package com.demo.mainservice.security.dto;

import java.io.Serializable;
import java.util.Collection;

public record UserDto(String username, Collection<String> authorities) implements Serializable {

}
