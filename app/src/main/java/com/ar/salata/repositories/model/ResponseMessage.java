package com.ar.salata.repositories.model;

import androidx.annotation.NonNull;

public class ResponseMessage {
	private String message;
	
	@NonNull
	@Override
	public String toString() {
		return message;
	}
}
