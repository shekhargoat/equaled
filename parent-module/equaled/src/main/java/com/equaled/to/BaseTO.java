package com.equaled.to;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter @Getter
public abstract class BaseTO implements Serializable {
	private static final long serialVersionUID = 3931456946932393365L;
	private String sid;
}
