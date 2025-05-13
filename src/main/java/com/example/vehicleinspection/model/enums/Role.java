package com.example.vehicleinspection.model.enums;

public enum Role {
     ADMIN, INSPECTOR, ADJOINT;

     public static Role fromCode(String code) {
          return switch (code) {
               case "001" -> ADMIN;
               case "002" -> INSPECTOR;
               case "003" -> ADJOINT;
               default -> throw new IllegalArgumentException("Invalid role code: " + code);
          };
     }
}