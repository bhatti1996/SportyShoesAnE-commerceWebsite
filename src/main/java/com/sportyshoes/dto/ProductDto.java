package com.sportyshoes.dto;

import lombok.Data;

@Data
public class ProductDto {

	private Long id;
	private String name;
	private int categoryId;
	private double price;
	private float size;
	private String description;
	private String imageName;
}
